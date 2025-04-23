import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-header',
  imports: [
    CommonModule
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  constructor(private router: Router) {}

  navigateTo(route: string): void {
    this.router.navigate([route]);
  }
}
