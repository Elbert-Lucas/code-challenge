import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LocalStorageService } from './LocalStorage.service';
import { environment } from '../../../environments/environment';
import { AuthTokens } from '../model/response/AuthTokens';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private client: HttpClient, private storageService: LocalStorageService) { }

  saveTokens(tokens: AuthTokens) {
    this.storageService.setObject(tokens)
  }

  public getAuthToken(): string{
    return "Bearer " + this.storageService.getItem(environment.authTokenKey) as string
  }
  
  public refreshToken(): Observable<AuthTokens>{
    let refreshToken: string = "Bearer " + this.storageService.getItem(environment.refreshTokenKey) as string
    let refreshUrl: string = environment.baseUrl + environment.refreshUrl

    return this.client.post<AuthTokens>(refreshUrl, { "refresh_token": refreshToken })
  }
}
