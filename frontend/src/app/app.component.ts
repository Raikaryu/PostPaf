import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './components/home/home.component';
import { PostItemComponent } from './components/post-item/post-item.component';
import { BottomNavComponent } from './components/bottom-nav/bottom-nav.component';

@Component({
  standalone: true,
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [
    CommonModule,
    RouterOutlet,
    BottomNavComponent
  ]
})
export class AppComponent {
  title = 'frontend-app';
}
