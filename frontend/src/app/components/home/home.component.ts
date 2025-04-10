import { Component, OnInit } from '@angular/core';
import { PostService } from '../../services/post.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  posts: any[] = [];
  searchTerm: string = '';

  constructor(private postService: PostService) { }

  ngOnInit(): void {
    this.loadPosts();
  }

  loadPosts(): void {
    this.postService.getPosts().subscribe(
      (data) => {
        this.posts = data;
      },
      (error) => {
        console.error('Erreur lors du chargement des posts:', error);
      }
    );
  }

  search(): void {
    if (this.searchTerm.trim()) {
      this.postService.searchPosts(this.searchTerm).subscribe(
        (data) => {
          this.posts = data;
        },
        (error) => {
          console.error('Erreur lors de la recherche:', error);
        }
      );
    } else {
      this.loadPosts();
    }
  }
}
