import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PostService } from '../../services/post.service';
import { UserService } from '../../services/user.service';
import { PostItemComponent } from '../post-item/post-item.component';
import { BottomNavComponent } from '../bottom-nav/bottom-nav.component';
import { RouterModule } from '@angular/router';
import { forkJoin } from 'rxjs'; // Permet de combiner plusieurs observables et d'attendre qu'ils soient tous complétés

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
  posts: any[] = []; // Liste des posts affichés
  allPosts: any[] = []; // Liste complète des posts (non filtrée)
  users: any[] = []; // Liste des utilisateurs
  searchTerm: string = ''; // Terme de recherche
  isLoading: boolean = false; // Indicateur de chargement
  error: string | null = null; // Message d'erreur
  sortAscending: boolean = false; // Ordre de tri (false = du plus récent au plus ancien)

  constructor(
    private postService: PostService, // Service pour les opérations liées aux posts
    private userService: UserService // Service pour les opérations liées aux utilisateurs
  ) {}

  ngOnInit(): void {
    // Méthode du cycle de vie Angular appelée lors de l'initialisation du composant
    this.loadPostsAndUsers(); // Chargement initial des données
  }

  loadPostsAndUsers(): void {
    // Méthode pour charger simultanément les posts et les utilisateurs
    this.isLoading = true; // Début du chargement
    this.error = null; // Réinitialisation des erreurs précédentes

    // forkJoin permet d'exécuter plusieurs requêtes en parallèle et d'attendre que toutes soient terminées
    forkJoin({
      posts: this.postService.getPosts(), // Récupération des posts
      users: this.userService.getUsers() // Récupération des utilisateurs
    }).subscribe({
      next: (result) => {
        // Callback exécuté en cas de succès
        this.allPosts = result.posts; // Stockage de tous les posts pour référence
        this.posts = result.posts; // Initialisation des posts affichés
        this.users = result.users; // Stockage des utilisateurs
        this.isLoading = false; // Fin du chargement
      },
      error: (err) => {
        // Callback exécuté en cas d'erreur
        console.error('Erreur lors de la récupération des données:', err);
        this.error = 'Impossible de charger les données. Veuillez réessayer plus tard.';
        this.isLoading = false; // Fin du chargement même en cas d'erreur
      }
    });
  }

  search(): void {
    // Méthode déclenchée lors d'une recherche
    if (!this.searchTerm.trim()) {
      // Si le champ de recherche est vide
      this.posts = this.allPosts; // Réinitialisation à la liste complète
      return;
    }

    this.filterPosts(); // Application du filtre
  }

  filterPosts(): void {
    // Méthode pour filtrer les posts selon le terme de recherche
    const term = this.searchTerm.toLowerCase(); // Conversion en minuscules pour une recherche insensible à la casse
  
    this.posts = this.allPosts.filter(post => {
      // Récupération des propriétés avec vérification de nullité (utilisation de l'opérateur ??)
      const title = post?.title?.toLowerCase() ?? '';
      const contenu = post?.contenu?.toLowerCase() ?? '';
      const authorPseudo = post?.userPseudo?.toLowerCase() ?? '';
  
      // Filtrage des posts contenant le terme dans le titre, le contenu ou le pseudo de l'auteur
      return (
        title.includes(term) ||
        contenu.includes(term) ||
        authorPseudo.includes(term)
      );
    });
  }

  getUserImage(idUser: number): string {
    // Méthode pour récupérer l'image d'un utilisateur selon son ID
    if (!idUser) return 'assets/default-profile.jpg'; // Image par défaut si aucun ID
    const user = this.users.find(u => u.id === idUser); // Recherche de l'utilisateur
    return user?.image || 'assets/default-profile.jpg'; // Renvoie l'image ou l'image par défaut
  }

  clearSearch(): void {
    // Méthode pour effacer la recherche
    this.searchTerm = ''; // Réinitialisation du terme de recherche
    this.posts = this.allPosts; // Restauration de la liste complète
  }
  
  sortByDate(): void {
    // Méthode pour trier les posts par date
    this.sortAscending = !this.sortAscending; // Inversion de l'ordre de tri à chaque appel
  
    this.posts.sort((a, b) => {
      // Conversion des dates en timestamps pour la comparaison
      const dateA = new Date(a.creationDate).getTime();
      const dateB = new Date(b.creationDate).getTime();
  
      // Tri croissant ou décroissant selon la valeur de sortAscending
      return this.sortAscending ? dateA - dateB : dateB - dateA;
    });
  }
}