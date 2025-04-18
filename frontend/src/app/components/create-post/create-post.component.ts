import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms'; // Ajoutez cette ligne
import { CommonModule } from '@angular/common';
import { PostService } from '../../services/post.service';
import { Post } from '../../models/post.model';
import { Router } from '@angular/router'; // Importez Router pour la redirection

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css'],
  imports: [FormsModule, CommonModule]
})
export class CreatePostComponent {
  post: Post = {
    id: 0,
    idUser: 0, // Vous devrez probablement définir cet ID avec l'ID de l'utilisateur actuel
    userPseudo: '', // Vous pouvez initialiser avec le pseudo de l'utilisateur actuel
    title: '',
    contenu: '',
    creationDate: new Date().toISOString()
  };

  constructor(private postService: PostService, private router: Router) {}

  onSubmit() {
    this.postService.createPost(this.post).subscribe(response => {
      console.log('Post created successfully', response);
      // Redirect to home page
      this.router.navigate(['/home']);
    });
  }
}
