import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.model'
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = `http://localhost:8081/api/users`;

  constructor(private http: HttpClient) { }

  getUsers(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getUserById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  getCurrentUser(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/me`);
  }

  updateUser(id: number, user: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, user);
  }
  register(user: User): Observable<User> {
    return this.http.post<User>(this.apiUrl, user);
  }
}
