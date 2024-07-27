import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { MessageResponse } from '../model/response/MessageResponse';
import { environment } from '../../../environments/environment';
import { ChangePasswordModel } from '../model/request/ChangePasswordModel';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private client: HttpClient) { }

  public changePassword(model: ChangePasswordModel, emailAuth?: string): Observable<MessageResponse>{
    let url: string = environment.baseUrl + environment.changePasswordUrl
    let header: HttpHeaders = new HttpHeaders()
    if(emailAuth) header = header.append("email-auth", emailAuth)
    return this.client.patch<MessageResponse>(url, model, {headers: header})
  }
}
