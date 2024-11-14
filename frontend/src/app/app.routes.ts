import { Routes } from '@angular/router';
import { LoginComponent } from './features/login/login.component';
import { PasswordRegisterComponent } from './features/password-register/password-register.component';

export const routes: Routes = [
    { path: '', component: PasswordRegisterComponent}
];
