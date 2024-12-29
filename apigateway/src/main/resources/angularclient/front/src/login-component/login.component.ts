import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../serivces/login/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-component',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginForm = new FormGroup({
    username: new FormControl('', [Validators.required, Validators.minLength(3)]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)]),
  })


  constructor(private fb: FormBuilder, private loginService: LoginService, private router: Router) {

  }


  testAuth(){
    this.loginService.testAuth().subscribe({
      next: (response) => {
        console.log("OK! " + response.message)
      },
      error: (error) => {
        console.log('Auth test failed: ' + JSON.stringify(error));
      },
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      let username = this.loginForm.controls.username.value ?? "";
      let password = this.loginForm.controls.password.value ?? "";


      this.loginService.signIn(username, password).subscribe({
        next: (res) => {  
          let token = res.token;

          const expiryDate = new Date();
          expiryDate.setHours(expiryDate.getHours() + 6);  
          document.cookie = `authToken=${token}; expires=${expiryDate.toUTCString()}; path=/`;

          localStorage.setItem('username', username);
          this.router.navigate(['/']).then(() => {
            alert('Успешная авторизация!');
          });
        },
        
        error: (err) => {
          console.error('Error occurred:', err);
  

          if (err.status === 401) {
            alert(err.error.error || "Unauthorized: Invalid username or password");
          } else {
            alert("An unexpected error occurred. Please try again later.");
          }
        }
      });
      
    }
  }
}
