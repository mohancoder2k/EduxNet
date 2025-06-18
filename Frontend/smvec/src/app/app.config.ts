// src/app/app.config.ts
import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { LoginComponent } from './components/login/login.component'; // ✅ update path if needed

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter([
      { path: '', component: LoginComponent },          // Homepage
      { path: 'login', component: LoginComponent }      // Login route
    ]),
    provideHttpClient()
  ]
};
