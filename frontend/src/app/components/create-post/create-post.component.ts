// src/app/components/create-post/create-post.component.ts
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { PostService } from '../../services/post.service';
import { UserService } from '../../services/user.service';
import { Post } from '../../models/post.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css'],
  standalone: true,
  imports: [FormsModule, CommonModule]
})
export class CreatePostComponent {
  post: Post = {
    idUser: 0, // Sera défini après la recherche de l'utilisateur
    userPseudo: '',
    title: '',
    contenu: '',
    creationDate: new Date().toISOString()
  };

  errorMessage: string = '';
  isSubmitting: boolean = false;

  constructor(
    private postService: PostService,
    private userService: UserService,
    private router: Router
  ) {}

  onSubmit() {
    // Vérification des champs obligatoires
    if (!this.post.title || !this.post.userPseudo || !this.post.contenu) {
      this.errorMessage = 'Tous les champs sont obligatoires';
      return;
    }

    // Vérification de la longueur du contenu
    if (this.post.contenu.length > 500) {
      this.errorMessage = 'Le contenu ne doit pas dépasser 500 caractères.';
      return;
    }

    // Rechercher l'utilisateur par son pseudo
    this.userService.getUserByPseudo(this.post.userPseudo).subscribe({
      next: (user) => {
        console.log('Utilisateur trouvé:', user);
        this.post.idUser = user.id;
        this.submitPost();
      },
      error: (error) => {
        console.error('Erreur lors de la recherche de l\'utilisateur:', error);
        this.errorMessage = 'Pseudo utilisateur non trouvé';
        this.isSubmitting = false;
      }
    });
  }

  submitPost() {
    this.isSubmitting = true;
    this.errorMessage = '';

    console.log('Données du post avant envoi:', this.post);

    this.postService.createPost(this.post).subscribe({
      next: (response) => {
        console.log('Post créé avec succès:', response);
        this.isSubmitting = false;
        this.router.navigate(['/home']);
      },
      error: (error) => {
        console.error('Erreur lors de la création du post:', error);
        this.isSubmitting = false;

        if (error.status === 401) {
          this.errorMessage = 'Vous devez être connecté pour créer un post';
        } else if (error.status === 400) {
          this.errorMessage = 'Données invalides. Veuillez vérifier vos informations';
        } else {
          this.errorMessage = 'Une erreur est survenue. Veuillez réessayer plus tard';
        }
      }
    });
  }
}