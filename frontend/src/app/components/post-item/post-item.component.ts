import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-post-item',
  templateUrl: './post-item.component.html',
  styleUrls: ['./post-item.component.scss']
})
export class PostItemComponent implements OnInit {
  @Input() post: any;

  constructor() { }

  ngOnInit(): void {
  }

  editPost(): void {
    // Implémenter la logique de modification
    console.log('Modifier le post:', this.post.id);
  }

  deletePost(): void {
    // Implémenter la logique de suppression
    console.log('Supprimer le post:', this.post.id);
  }
}
