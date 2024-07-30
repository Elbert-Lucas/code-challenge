import { Injectable } from '@angular/core';
import { LoginModel } from '../model/request/LoginModel';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { AuthTokens } from '../model/response/AuthTokens';

@Injectable({
  providedIn: 'root',
})
export class LoginService {

  constructor(private client: HttpClient) { }

  public doLogin(model: LoginModel): Observable<AuthTokens>{
    const loginUrl: string = environment.baseUrl + environment.loginUrl;
    return this.client.post<AuthTokens>(loginUrl, model);
  }
}
