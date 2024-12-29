import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private COOKIE_NAME = 'authToken';

  constructor() { }


  setToken(token: string, expiryDate: Date): void {
    document.cookie = `authToken=${token}; expires=${expiryDate.toUTCString()}; path=/`;
  }

  getUsername(): string | null {
    return localStorage.getItem('username');
  }


  getToken(): string | null {
    const cookieString = document.cookie;
    const cookies = cookieString.split('; ').reduce((acc: { [key: string]: string }, cookie) => {
      const [name, value] = cookie.split('=');
      acc[name] = value;
      return acc;
    }, {});

    return cookies[this.COOKIE_NAME] || null;
  }


  isAuthenticated(): boolean {
    var result = this.getToken();  
    return !!result
  }


  logout(): void {
    document.cookie = `${this.COOKIE_NAME}=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/`;  // Remove the token
  }

}
