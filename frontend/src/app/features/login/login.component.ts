import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{

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
  
}
