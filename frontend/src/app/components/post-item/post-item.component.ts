import { Component, Input, OnInit } from '@angular/core';
import { Post } from '../../models/post.model';
import { DatePipe, NgIf } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-post-item',
  templateUrl: './post-item.component.html',
  styleUrls: ['./post-item.component.css'],
  imports: [DatePipe, NgIf]
})
export class PostItemComponent implements OnInit {
  @Input() post!: Post;
  @Input() userImage: string = 'assets/default-profile.jpg';

  ngOnInit(): void {}
  
  editPost(): void {
    console.log('Modifier le post:', this.post.id);
  }

  deletePost(): void {
    console.log('Supprimer le post:', this.post.id);
  }
}
