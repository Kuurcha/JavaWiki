import { Routes } from '@angular/router';
import { LoginComponent } from '../login-component/login.component';
import { RegisterComponent } from '../register-component/register/register.component';
import { MainPageComponent } from './main-page/main-page.component';
import { EditorComponent } from '../Records/RecordEditor/editor.component';

export const routes: Routes = [
    { path: '', component: MainPageComponent },
    { path: 'register', component: RegisterComponent},
    { path: 'login', component: LoginComponent},
    { path: 'editor', component: EditorComponent}
  ];
