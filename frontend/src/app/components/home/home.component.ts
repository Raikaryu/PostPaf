import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PostService } from '../../services/post.service';
import { UserService } from '../../services/user.service';
import { UserIdService } from '../../services/user-id.service';
import { PostItemComponent } from '../post-item/post-item.component';
import { BottomNavComponent } from '../bottom-nav/bottom-nav.component';
import { RouterModule } from '@angular/router';
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
    BottomNavComponent,
    RouterModule
  ]
})
export class HomeComponent implements OnInit {
  posts: any[] = [];
  allPosts: any[] = [];
  users: any[] = [];
  searchTerm: string = '';
  isLoading: boolean = false;
  error: string | null = null;
  sortAscending: boolean = false;
  currentUserId: number | null = null;

  constructor(
    private postService: PostService,
    private userService: UserService,
    private userIdService: UserIdService
  ) {}

  ngOnInit(): void {
    this.currentUserId = this.userIdService.getUserId();
    console.log("ID utilisateur connecté :", this.currentUserId);
    this.loadPostsAndUsers();
  }

  loadPostsAndUsers(): void {
    this.isLoading = true;
    this.error = null;

    forkJoin({
      posts: this.postService.getPosts(),
      users: this.userService.getUsers()
    }).subscribe({
      next: (result) => {
        this.allPosts = result.posts;
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
      this.posts = this.allPosts;
      return;
    }

    this.filterPosts();
  }

  filterPosts(): void {
    const term = this.searchTerm.toLowerCase();

    this.posts = this.allPosts.filter(post => {
      const title = post?.title?.toLowerCase() ?? '';
      const contenu = post?.contenu?.toLowerCase() ?? '';
      const authorPseudo = post?.userPseudo?.toLowerCase() ?? '';

      return (
        title.includes(term) ||
        contenu.includes(term) ||
        authorPseudo.includes(term)
      );
    });
  }

  getUserImage(idUser: number): string {
    if (!idUser) return 'assets/default-profile.jpg';
    const user = this.users.find(u => u.id === idUser);
    return user?.image || 'assets/default-profile.jpg';
  }

  clearSearch(): void {
    this.searchTerm = '';
    this.posts = this.allPosts;
  }

  sortByDate(): void {
    this.sortAscending = !this.sortAscending;

    this.posts.sort((a, b) => {
      const dateA = new Date(a.creationDate).getTime();
      const dateB = new Date(b.creationDate).getTime();
      return this.sortAscending ? dateA - dateB : dateB - dateA;
    });
  }

  isPostOwner(postUserId: number): boolean {
    console.log('Comparaison -> post.idUser:', postUserId, '=== currentUserId:', this.currentUserId);
    return this.currentUserId === postUserId;
  }
  
}
