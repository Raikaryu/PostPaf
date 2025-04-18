import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PostService } from '../../services/post.service';
import { UserService } from '../../services/user.service';
import { PostItemComponent } from '../post-item/post-item.component';
import { BottomNavComponent } from '../bottom-nav/bottom-nav.component';
import { forkJoin } from 'rxjs';

@Component({
  standalone: true,
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  imports: [
    CommonModule,
    FormsModule,
    PostItemComponent,
    BottomNavComponent
  ]
})
export class HomeComponent implements OnInit {
  posts: any[] = [];
  users: any[] = [];
  searchTerm: string = '';
  isLoading: boolean = false;
  error: string | null = null;

  constructor(
    private postService: PostService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.loadPostsAndUsers();
  }

  loadPostsAndUsers(): void {
    this.isLoading = true;
    this.error = null;
    
    // Récupérer les posts et les utilisateurs en parallèle
    forkJoin({
      posts: this.postService.getPosts(),
      users: this.userService.getUsers()
    }).subscribe({
      next: (result) => {
        console.log('Posts récupérés:', result.posts);
        console.log('Posts avec leurs userId:', this.posts.map(post => ({ id: post.id, userId: post.userId })));
        console.log('Utilisateurs récupérés:', result.users);
        this.posts = result.posts;
        this.users = result.users;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des données:', err);
        this.error = 'Impossible de charger les données. Veuillez réessayer plus tard.';
        this.isLoading = false;
      }
    });
  }

  search(): void {
    if (!this.searchTerm.trim()) {
      this.loadPostsAndUsers();
      return;
    }

    this.isLoading = true;
    this.error = null;
    
    // Rechercher des posts tout en gardant les utilisateurs déjà chargés
    this.postService.searchPosts(this.searchTerm).subscribe({
      next: (data) => {
        this.posts = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erreur lors de la recherche:', err);
        this.error = 'Erreur lors de la recherche. Veuillez réessayer.';
        this.isLoading = false;
      }
    });
  }
  getUserImage(idUser: number): string {
    if (!idUser) {
      console.warn('ID utilisateur non défini pour un post');
      return 'assets/default-profile.jpg';
    }
    const user = this.users.find(u => u.id === idUser);
    if (!user) {
      console.warn(`Aucun utilisateur trouvé avec l'ID ${idUser}`);
    }
    return user?.image || 'assets/default-profile.jpg';
  }
}