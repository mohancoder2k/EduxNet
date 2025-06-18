export interface LoginResponse {
  token: string;
  role: 'STUDENT' | 'ALUMNI' | 'FACULTY';
  message?: string;
}
