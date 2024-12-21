import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {


  private baseUrl: string = "http://localhost:4201/api/auth/sign-in";

  constructor(private http: HttpClient) {}



  signIn(username: string, password: string): Observable<any> {

    const body = {
      username: username,
      password: password
    };
    return this.http.post<any>(this.baseUrl, body);
  }
  private baseTestUrl: string = "http://localhost:4201/api/auth/test";

  test(): Observable<any> {
    return this.http.get<any>(`${this.baseTestUrl}`);
  }
  private baseAutTesthUrl: string = "http://localhost:8082/auth/hello"
  testAuth(): Observable<any> {
    return this.http.get<any>(`${this.baseAutTesthUrl}`);
  }

}
