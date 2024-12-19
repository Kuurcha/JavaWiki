import { Component } from '@angular/core';
import { RouterOutlet, provideRouter } from '@angular/router';
import { LoginService } from '../serivces/login/login.service';
import { CommonModule } from '@angular/common';
import { bootstrapApplication } from '@angular/platform-browser';
import { routes } from './app.routes'



@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'front';


  
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

bootstrapApplication(AppComponent, {
  providers: [provideRouter(routes)],
}).catch((err) => console.error(err));
