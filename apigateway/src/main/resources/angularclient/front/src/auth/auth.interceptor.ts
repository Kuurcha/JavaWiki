import { HttpRequest, HttpHandlerFn, HttpEvent } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';


function getAuthTokenFromCookies(): string | null {
    const tokenMatch = document.cookie.match(/(^| )authToken=([^;]+)/);
    return tokenMatch ? tokenMatch[2] : null;
  }

export function authInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
    function getAuthToken(): string | null {
        const cookieMatch = document.cookie.match(/(^| )authToken=([^;]+)/);
        return cookieMatch ? cookieMatch[2] : null;
      }
  const authToken = getAuthToken();

  
  if (authToken){
    const newReq = req.clone({
        headers: req.headers.set('Authorization', `Bearer ${authToken}`)
    });
    return next(newReq);
  }


 
  return next(req);
}