import { Component, Input, OnInit } from '@angular/core';
import { Post } from '../../models/post.model';
import { DatePipe, CommonModule } from '@angular/common';
import { PostService } from '../../services/post.service';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-post-item',
  templateUrl: './post-item.component.html',
  styleUrls: ['./post-item.component.css'],
  imports: [DatePipe, CommonModule]
})
export class PostItemComponent implements OnInit {
  @Input() post!: Post;
  @Input() userImage: string = 'assets/default-profile.jpg';
  @Input() isOwner: boolean = false;

  constructor(private postService: PostService, private router: Router) {}

  ngOnInit(): void {
  
  console.log('Post ID:', this.post.id, 'isOwner:', this.isOwner);
  
  }

  editPost(): void {
    if (this.post.id !== undefined) {
      console.log('Modifier le post:', this.post.id);
      // Rediriger vers la page de modification du post
      this.router.navigate(['/edit-post', this.post.id]);
    } else {
      console.error('ID du post non défini');
    }
  }

  deletePost(): void {
    if (this.post.id !== undefined) {
      console.log('Supprimer le post:', this.post.id);
      if (confirm('Êtes-vous sûr de vouloir supprimer ce post ?')) {
        this.postService.deletePost(this.post.id).subscribe({
          next: () => {
            console.log('Post supprimé avec succès');
            // Rediriger ou mettre à jour la liste des posts après la suppression
            window.location.reload(); // Recharge la page pour mettre à jour la liste des posts
          },
          error: (error) => {
            console.error('Erreur lors de la suppression du post:', error);
          }
        });
      }
    } else {
      console.error('ID du post non défini');
    }
  }
}
