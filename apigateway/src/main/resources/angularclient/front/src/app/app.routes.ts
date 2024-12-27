import { Routes } from '@angular/router';
import { LoginComponent } from '../login-component/login.component';
import { RegisterComponent } from '../register-component/register/register.component';
import { MainPageComponent } from './main-page/main-page.component';

export const routes: Routes = [
    { path: '', component: LoginComponent },
    { path: 'register', component: RegisterComponent},
    { path: 'login', component: MainPageComponent},
  ];
