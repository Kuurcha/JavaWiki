import { Component } from '@angular/core';
import { RouterOutlet, provideRouter } from '@angular/router';
import { LoginService } from '../serivces/login/login.service';
import { CommonModule } from '@angular/common';
import { bootstrapApplication } from '@angular/platform-browser';
import { routes } from './app.routes'
import { appConfig } from './app.config';
import { AuthService } from '../serivces/auth/auth.service';
import {MatIconModule} from '@angular/material/icon';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, MatIconModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'front';
  constructor(private authService: AuthService ) {

  }


  getUsername(): string | null{
    var result =this.authService.getUsername(); 
    return result;
  }

  isAuthenticated(){
    let isAuthenticated = this.authService.isAuthenticated();
    return isAuthenticated;
  }

  logOut(){
    this.authService.logout();
  }

  
  // constructor(private loginService: LoginService){
  //   this.loginService.test().subscribe({
  //     next: (result) => {
  //       console.log(JSON.stringify(result))
  //       this.title = result; 
  //     },
  //     error: (err) => {
  //       console.error('Error occurred:', err); 
  //     }
  //   });
  // } 
}

