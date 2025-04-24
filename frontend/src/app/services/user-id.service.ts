import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserIdService {
  private userId: number | null = null;

  setUserId(id: number): void {
    console.log('Setting user ID:', id);
    this.userId = id;
  }

  getUserId(): number | null {
    console.log('Getting user ID:', this.userId);
    return this.userId;
  }
}
