import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from '../../models/user.model';
import { Post } from '../../models/post.model';
import { UserService } from '../../services/user.service';
import { PostService } from '../../services/post.service';
import { CommonModule, DatePipe } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router'; // Importer Router
import { UserIdService } from '../../services/user-id.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule]
})
export class ProfileComponent implements OnInit {
  currentUser: User | null = null;
  userPosts: Post[] = [];
  profileForm: FormGroup;
  isEditing = false;
  isLoading = true;
  errorMessage = '';
  successMessage = '';

  constructor(
    private userService: UserService,
    private postService: PostService,
    private fb: FormBuilder,
    private userIdService: UserIdService,
    private router: Router // Injecter Router ici
  ) {
    this.profileForm = this.fb.group({
      pseudo: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      bio: [''],
      image: ['']
    });
  }

  ngOnInit(): void {
    const userId = this.userIdService.getUserId();
    console.log('User ID from service:', userId);
    if (userId) {
      this.loadUserProfile(userId);
    } else {
      this.errorMessage = 'ID utilisateur non trouvé.';
      this.isLoading = false;
    }
  }

  loadUserProfile(userId: number): void {
    this.isLoading = true;
    this.userService.getUserById(userId).subscribe({
      next: (user) => {
        console.log('User data:', user); // Ajoutez ce log pour vérifier les données reçues
        this.currentUser = user;
        this.initForm(user);
        this.loadUserPosts(userId);
      },
      error: (error) => {
        this.errorMessage = 'Erreur lors du chargement du profil: ' + error.message;
        this.isLoading = false;
      }
    });
  }

  loadUserPosts(userId: number): void {
    this.postService.getPosts().subscribe({
      next: (posts) => {
        this.userPosts = posts.filter(post => post.idUser === userId);
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Erreur lors du chargement des posts: ' + error.message;
        this.isLoading = false;
      }
    });
  }

  initForm(user: User): void {
    this.profileForm.patchValue({
      pseudo: user.pseudo,
      email: user.email,
      bio: user.bio || '',
      image: user.image || ''
    });
  }

  toggleEditMode(): void {
    this.isEditing = !this.isEditing;
    if (!this.isEditing && this.currentUser) {
      // Reset form to current values if canceling edit
      this.initForm(this.currentUser);
    }
  }

  submitForm(): void {
    if (this.profileForm.valid && this.currentUser) {
      const updatedUser = {
        ...this.currentUser,
        ...this.profileForm.value
      };

      this.userService.updateUser(this.currentUser.id, updatedUser).subscribe({
        next: (user) => {
          this.currentUser = user;
          this.successMessage = 'Profil mis à jour avec succès!';
          this.isEditing = false;
          setTimeout(() => this.successMessage = '', 3000);
        },
        error: (error) => {
          this.errorMessage = 'Erreur lors de la mise à jour du profil: ' + error.message;
        }
      });
    }
  }

  deletePost(postId: number | undefined): void {
    if (!postId) return;

    if (confirm('Êtes-vous sûr de vouloir supprimer ce post?')) {
      this.postService.deletePost(postId).subscribe({
        next: () => {
          this.userPosts = this.userPosts.filter(post => post.id !== postId);
          this.successMessage = 'Post supprimé avec succès!';
          setTimeout(() => this.successMessage = '', 3000);
        },
        error: (error) => {
          this.errorMessage = 'Erreur lors de la suppression du post: ' + error.message;
        }
      });
    }
  }

  deleteProfile(): void {
    if (this.currentUser && confirm('Êtes-vous sûr de vouloir supprimer votre profil?')) {
      this.userService.deleteUser(this.currentUser.id).subscribe({
        next: () => {
          this.successMessage = 'Profil supprimé avec succès!';
          setTimeout(() => {
            this.successMessage = '';
            this.router.navigate(['/register']);
          }, 3000);
        },
        error: (error) => {
          this.errorMessage = 'Erreur lors de la suppression du profil: ' + error.message;
        }
      });
    }
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString('fr-FR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }
}
