import { Component, OnInit } from '@angular/core';
import { NgIf } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../../services/post.service';
import { Post } from '../../models/post.model';
import { FormsModule } from '@angular/forms';


@Component({
  standalone: true,
  selector: 'app-edit-post',
  templateUrl: './edit-post.component.html',
  styleUrls: ['./edit-post.component.css'],
  imports: [FormsModule, NgIf]
})
export class EditPostComponent implements OnInit {
  post: Post = {
    idUser: 1, // Valeur temporaire - idéalement à récupérer d'un service d'authentification
    userPseudo: '',
    title: '',
    contenu: '',
    creationDate: new Date().toISOString()
  };

  errorMessage: string = '';
  isSubmitting: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = +params['id'];
      if (id) {
        this.postService.getPostById(id).subscribe(post => {
          this.post = post;
        });
      }
    });
  }

  onSubmit(): void {
    // Vérification des champs obligatoires
    if (!this.post.title || !this.post.userPseudo || !this.post.contenu) {
      this.errorMessage = 'Veuillez remplir tous les champs obligatoires';
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = '';

    this.postService.updatePost(this.post.id!, this.post).subscribe({
      next: (response) => {
        console.log('Post mis à jour avec succès:', response);
        this.isSubmitting = false;
        this.router.navigate(['/home']);
      },
      error: (error) => {
        console.error('Erreur lors de la mise à jour du post:', error);
        this.isSubmitting = false;

        if (error.status === 401) {
          this.errorMessage = 'Vous devez être connecté pour modifier un post';
        } else if (error.status === 400) {
          this.errorMessage = 'Données invalides. Veuillez vérifier vos informations';
        } else {
          this.errorMessage = 'Une erreur est survenue. Veuillez réessayer plus tard';
        }
      }
    });
  }
}
