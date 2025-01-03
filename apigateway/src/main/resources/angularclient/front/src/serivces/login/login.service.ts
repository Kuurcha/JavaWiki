import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {


  private signInUrl: string = "http://localhost:4201/api/auth/sign-in";
  private signUpUrl: string = "http://localhost:4201/api/auth/sign-up";
  constructor(private http: HttpClient) {}


  signIn(username: string, password: string): Observable<any> {

    const body = {
      username: username,
      password: password
    };
    return this.http.post<any>(this.signInUrl, body);
  }

  
  signUp(username: string, email: string, password: string): Observable<any> {

    const body = {
      username: username,
      password: password,
      email: email
    };
    return this.http.post<any>(this.signUpUrl, body);
  }
  private baseTestUrl: string = "http://localhost:4201/api/auth/test";

  test(): Observable<any> {
    return this.http.get<any>(`${this.baseTestUrl}`);
  }

  private baseAutTesthUrl: string = "http://localhost:4201/api/auth/hello"
  testAuth(): Observable<any> {
    return this.http.get<any>(`${this.baseAutTesthUrl}`);
  }

}
