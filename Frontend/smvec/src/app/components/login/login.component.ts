import { Component, OnInit, OnDestroy, HostListener } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../authService/auth.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';

interface LoginResponse {
  token: string;
  role: 'STUDENT' | 'ALUMNI' | 'FACULTY';
  message?: string;
}

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  loginForm: FormGroup;
  isLoading = false;
  showPassword = false;
  buttonText = '<span>Sign In</span>';
  errorMessage = '';
  showErrorNotification = false;
  
  private subscriptions: Subscription[] = [];

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  ngOnInit(): void {
    // Add mouse move effect for particles
    this.addMouseMoveEffect();
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }

  @HostListener('document:mousemove', ['$event'])
  onMouseMove(event: MouseEvent): void {
    const particles = document.querySelectorAll('.particle');
    particles.forEach((particle, index) => {
      const speed = (index + 1) * 0.00005;
      const x = event.clientX * speed;
      const y = event.clientY * speed;
      (particle as HTMLElement).style.transform = `translate(${x}px, ${y}px)`;
    });
  }

  togglePassword(): void {
    this.showPassword = !this.showPassword;
  }

  showForgotPassword(event: Event): void {
    event.preventDefault();
    alert('Forgot password functionality would be implemented here');
  }

  socialLogin(provider: string): void {
    alert(`${provider.charAt(0).toUpperCase() + provider.slice(1)} login would be implemented here`);
  }

  onSubmit(): void {
    if (this.loginForm.valid && !this.isLoading) {
      this.isLoading = true;
      this.buttonText = '<i class="fas fa-spinner fa-spin"></i> Signing In...';

      const loginData = {
        email: this.loginForm.get('email')?.value,
        password: this.loginForm.get('password')?.value
      };

      const loginSub = this.authService.login(loginData).subscribe({
        next: (response: LoginResponse) => {
          // Success state
          this.buttonText = '<i class="fas fa-check"></i> Success!';
          this.setButtonStyle('linear-gradient(135deg, #10b981 0%, #059669 100%)');
          
          // Store auth data
          localStorage.setItem('token', response.token);
          localStorage.setItem('role', response.role);

          // Navigate based on role
          setTimeout(() => {
            this.navigateBasedOnRole(response.role);
          }, 1000);
        },
        error: (error) => {
          this.handleLoginError(error);
        }
      });

      this.subscriptions.push(loginSub);
    }
  }

  private navigateBasedOnRole(role: string): void {
    switch (role) {
      case 'STUDENT':
        this.router.navigate(['/student-profile']);
        break;
      case 'ALUMNI':
        this.router.navigate(['/alumni-profile']);
        break;
      case 'FACULTY':
        this.router.navigate(['/faculty-profile']);
        break;
      default:
        this.router.navigate(['/dashboard']);
    }
  }

  private handleLoginError(error: any): void {
    // Error state
    this.buttonText = '<i class="fas fa-times"></i> Login Failed';
    this.setButtonStyle('linear-gradient(135deg, #ef4444 0%, #dc2626 100%)');
    
    // Show error notification
    this.errorMessage = error.error?.message || error.message || 'Login failed. Please try again.';
    this.showError();

    // Reset button after 2 seconds
    setTimeout(() => {
      this.resetButton();
    }, 2000);
  }

  private setButtonStyle(background: string): void {
    const button = document.querySelector('button[type="submit"]') as HTMLElement;
    if (button) {
      button.style.background = background;
    }
  }

  private resetButton(): void {
    this.buttonText = '<span>Sign In</span>';
    this.setButtonStyle('linear-gradient(135deg, #667eea 0%, #764ba2 100%)');
    this.isLoading = false;
  }

  private showError(): void {
    this.showErrorNotification = true;
    
    // Hide error after 4 seconds
    setTimeout(() => {
      this.showErrorNotification = false;
      this.errorMessage = '';
    }, 4000);
  }

  private addMouseMoveEffect(): void {
    // This is handled by the HostListener decorator
    // Additional initialization if needed
  }
}