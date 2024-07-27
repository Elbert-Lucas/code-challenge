import { HttpErrorResponse, HttpEvent, HttpEventType, HttpHandler, HttpHandlerFn, HttpInterceptor, HttpRequest, type HttpInterceptorFn } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BehaviorSubject, catchError, delay, filter, finalize, from, map, Observable, of, retry, retryWhen, switchMap, tap, throwError } from 'rxjs';
import { AuthTokens } from '../model/response/AuthTokens';
import { AuthService } from '../service/Auth.service';
import { environment } from '../../../environments/environment';


@Injectable({
  providedIn: 'root',
})
export class AuthInterceptor {

  authService!: AuthService;

  constructor(){}

  interceptor: HttpInterceptorFn = (req, next) => {
    this.authService = inject(AuthService);
    if(req.url.includes('/login')) return this.handleLogin(req, next);
    
    return this.handleErrors(this.getAuthReq(req), next);
  };

  private handleLogin(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>>{
    return next(req).pipe(
      tap(event => {
          if (event.type === HttpEventType.Response) {
            this.authService.saveTokens(event.body as AuthTokens)
          }
        }
      ),
    );
  } 
  private handleErrors(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>>{
    return next(req).pipe(
      catchError((error:any) => {
        if (error instanceof HttpErrorResponse) {
          if(error.status === 401){
            return this.tryRefreshToken(req, next)
          }
        }
        return throwError(() => error);
      }))
      
  }
  private getAuthReq(req: HttpRequest<unknown>): HttpRequest<unknown>{
    return req.clone({ headers: req.headers.set("Authorization", this.authService.getAuthToken()) })
  }
  private tryRefreshToken(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>>{
    return from(this.authService.refreshToken()).pipe(
      switchMap((newTokens: AuthTokens) => {
        this.authService.saveTokens(newTokens)
        return next(this.getAuthReq(req));
      })
    );
    }
}