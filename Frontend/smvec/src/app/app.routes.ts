import { provideRouter, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { provideHttpClient } from '@angular/common/http';


export const appConfig = {
  providers: [
    provideRouter([
      { path: '', component: LoginComponent }, // 👈 Make this the home page
      { path: 'login', component: LoginComponent }
    ]),
    provideHttpClient()
  ]
};