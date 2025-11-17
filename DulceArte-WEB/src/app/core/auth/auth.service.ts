import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly TOKEN_KEY = 'dulcearte_token';

  constructor(private router: Router) {}

  // Login simulado
  login(username: string, password: string): boolean {
    if (username === 'admin' && password === 'dulcearte') {
      localStorage.setItem(this.TOKEN_KEY, 'OK');
      return true;
    }
    return false;
  }

  // Cerrar sesión
  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    this.router.navigate(['/login']);
  }

  // Verifica si el usuario está logueado
  isLoggedIn(): boolean {
    return localStorage.getItem(this.TOKEN_KEY) === 'OK';
  }
}
