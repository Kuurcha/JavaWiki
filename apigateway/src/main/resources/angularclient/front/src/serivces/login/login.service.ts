import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private baseUrl: string = "http://localhost:4201/api/test";

  constructor(private http: HttpClient) {}

  test(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}`);
  }

}
