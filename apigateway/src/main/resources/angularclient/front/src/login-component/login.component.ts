import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../serivces/login/login.service';

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


  constructor(private fb: FormBuilder, private loginService: LoginService) {

  }


  testAuth(){
    this.loginService.testAuth().subscribe({
      next: (response) => {
        alert('Auth test success: ' + response);
      },
      error: (error) => {
        alert('Auth test failed: ' + JSON.stringify(error));
      },
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      let username = this.loginForm.controls.username.value ?? "";
      let password = this.loginForm.controls.password.value ?? "";

      let token = "not a token";

      this.loginService.signIn(username, password).subscribe({
        next: (res) => {
          let token = res.token;
          const expiryDate = new Date();
          expiryDate.setHours(expiryDate.getHours() + 6);  
          document.cookie = `authToken=${token}; expires=${expiryDate.toUTCString()}; path=/`;
          // добавить редирект
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
