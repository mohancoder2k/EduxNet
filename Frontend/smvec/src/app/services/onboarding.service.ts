import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StudentProfile } from '../models/student-profile';

@Injectable({
  providedIn: 'root'
})
export class OnboardingService {

  private baseUrl = 'http://localhost:8082/onboarding';

  constructor(private http: HttpClient) {}

  addStudentProfile(profile: StudentProfile): Observable<any> {
    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.http.post(`${this.baseUrl}/student`, profile, { headers });
  }
}
