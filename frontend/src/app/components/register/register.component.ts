import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';
import { Router } from '@angular/router'

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  user: User = { id: 0, pseudo: '', email: '', bio: '', imageUrl: '', createdAt: new Date(), password: '' };

  constructor(private userService: UserService, private router: Router) {}

  onSubmit() {
    this.userService.register(this.user).subscribe(response => {
      console.log('User registered successfully', response);
      this.router.navigate(['/login']); // Redirect to login or home page
    });
  }
}
