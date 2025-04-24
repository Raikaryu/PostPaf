import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service';
import { UserIdService } from '../../services/user-id.service'; // Importer UserIdService
import { User } from '../../models/user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  imports: [FormsModule, CommonModule]
})
export class RegisterComponent {
  user: User = { id: 0, pseudo: '', email: '', bio: '', image: '', createdAt: new Date(), password: '' };
  loginData = { email: '', password: '' };

  registerError: string = '';
  loginError: string = '';

  constructor(
    private userService: UserService,
    private userIdService: UserIdService, // Injecter UserIdService
    private router: Router
  ) {}

  onSubmit() {
    this.registerError = '';
    this.userService.register(this.user).subscribe({
      next: (response) => {
        console.log('Utilisateur inscrit avec succès', response);
        this.userIdService.setUserId(response.id); // Définir l'ID de l'utilisateur dans le service
        this.router.navigate(['/']); // Redirection après inscription
      },
      error: (error) => {
        console.error('Erreur lors de l\'inscription', error);
        this.registerError = error.error || 'Une erreur est survenue lors de l\'inscription';
      }
    });
  }

  onLogin() {
    this.loginError = '';
    this.userService.login(this.loginData.email, this.loginData.password).subscribe({
      next: (user) => {
        console.log('Connecté avec succès', user);
        this.userIdService.setUserId(user.id); // Définir l'ID de l'utilisateur dans le service
        this.router.navigate(['/']);
      },
      error: (error) => {
        console.error('Erreur de connexion', error);
        this.loginError = 'Email ou mot de passe incorrect';
      }
    });
  }
}
