import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Post } from '../models/post.model';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private apiUrl = 'http://localhost:8081/api/posts';

  constructor(private http: HttpClient) { }

  getPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(this.apiUrl)
      .pipe(
        catchError(this.handleError)
      );
  }

  getPostById(id: number): Observable<Post> {
    return this.http.get<Post>(`${this.apiUrl}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  createPost(post: Post): Observable<Post> {
    const { id, ...postData } = post; // Exclure l'id des données envoyées
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    console.log('Envoi du post au serveur:', postData);

    return this.http.post<Post>(this.apiUrl, postData, { headers })
      .pipe(
        catchError(this.handleError)
      );
  }

  updatePost(id: number, post: Post): Observable<Post> {
    return this.http.put<Post>(`${this.apiUrl}/${id}`, post)
      .pipe(
        catchError(this.handleError)
      );
  }

  deletePost(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  searchPosts(term: string): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.apiUrl}/search?q=${term}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = '';

    if (error.error instanceof ErrorEvent) {
      // Erreur côté client
      errorMessage = `Erreur: ${error.error.message}`;
      console.error('Erreur côté client:', error.error.message);
    } else {
      // Erreur côté serveur
      errorMessage = `Code d'erreur: ${error.status}, Message: ${error.message}`;
      console.error('Détails de l\'erreur:', {
        status: error.status,
        statusText: error.statusText,
        url: error.url,
        error: error.error
      });
    }

    // Retourne un Observable avec un message d'erreur
    return throwError(() => new Error(errorMessage));
  }
}
