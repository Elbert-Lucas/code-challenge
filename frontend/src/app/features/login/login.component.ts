import { Component, OnInit } from '@angular/core';
import { CardComponent } from "../../shared/components/card/card.component";
import { InputComponent, InputType } from '../../shared/components/input/input.component';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { LogoComponent } from '../../shared/components/logo/logo.component';
import { LoginService } from '../../core/service/Login.service';
import { LoginModel } from '../../core/model/request/LoginModel';
import { LocalStorageService } from '../../core/service/LocalStorage.service';
import { AuthTokens } from '../../core/model/response/AuthTokens';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CardComponent,LogoComponent, InputComponent, ButtonComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{
  
  protected readonly emailType: InputType = InputType.Email;
  protected passwordType: InputType = InputType.Password;

  protected email: string = '';
  protected password: string = '';

  constructor(private loginService: LoginService, private storageService: LocalStorageService){}

  ngOnInit(): void {
    this.raffleFirstBackground()
    setInterval(this.fadeBackground, 1000*10)
  }
  private raffleFirstBackground(): void{
    if(Math.floor(Math.random() * 10) % 2 == 0){
      document.getElementById("bkg-img-container")?.classList.add("fadeOut")
      document.getElementById("welcome-text")?.classList.add("fadeIn")
    }  
    else{
      document.getElementById("bkg-img-container")?.classList.add("fadeIn")
      document.getElementById("welcome-text")?.classList.add("fadeOut")
    } 
  }

  private fadeBackground(): void{
   let imageBkg: HTMLElement | null = document.getElementById("bkg-img-container")
   let textBkg: HTMLElement | null = document.getElementById("welcome-text")

   if(imageBkg?.classList.contains("fadeIn")){
    imageBkg.classList.add("fadeOut")
    imageBkg.classList.remove("fadeIn")
    
    textBkg?.classList.add("fadeIn")
    textBkg?.classList.remove("fadeOut")
   }else{
    imageBkg?.classList.add("fadeIn")
    imageBkg?.classList.remove("fadeOut")

    textBkg?.classList.add("fadeOut")
    textBkg?.classList.remove("fadeIn")
   }
  }
  protected doLogin(): void{
    const requestModel: LoginModel = {
      username: this.email,
      password: this.password
    }

    this.loginService.doLogin(requestModel).subscribe({
      next: (response) => {
        this.storageService.setObject(response)
      }
    })
  }  
}
