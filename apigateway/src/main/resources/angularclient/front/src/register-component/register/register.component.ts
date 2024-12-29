import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LoginService } from '../../serivces/login/login.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    username: new FormControl('', [Validators.required, Validators.minLength(3)]),
    password: new FormControl('', [Validators.required, Validators.minLength(6)]),
  })


  constructor(private fb: FormBuilder, private loginService: LoginService, private router: Router) {

  }

  onSubmit() {
    if (this.loginForm.valid) {
      let username = this.loginForm.controls.username.value ?? "";
      let password = this.loginForm.controls.password.value ?? "";
      let email = this.loginForm.controls.password.value ?? "";
      let token = "not a token";

      this.loginService.signUp(username, email, password).subscribe({
        next: (res) => {
          let token = res.token;
          const expiryDate = new Date();
          expiryDate.setHours(expiryDate.getHours() + 6);  
          document.cookie = `authToken=${token}; expires=${expiryDate.toUTCString()}; path=/`;
          this.router.navigate(['/']).then(() => {
            alert('Успешная регистрация!');
          });

          localStorage.setItem('username', username);

        },
        
        error: (err) => {
          console.error('Error occurred:', err);
  

          if (err.status === 409) {
            alert(err.error.error || "Пользователь уже существует, невозможно зарегистрироваться");
          } else {
            alert("An unexpected error occurred. Please try again later.");
          }
        }
      });
      
    }
  }
}
