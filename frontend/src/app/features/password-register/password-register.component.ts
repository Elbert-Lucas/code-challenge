import { Component, OnInit } from '@angular/core';
import { CardComponent } from '../../shared/components/card/card.component';
import { LogoComponent } from '../../shared/components/logo/logo.component';
import { InputComponent, InputType } from '../../shared/components/input/input.component';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { UserService } from '../../core/service/User.service';
import { ChangePasswordModel } from '../../core/model/request/ChangePasswordModel';

@Component({
  selector: 'app-password-register',
  templateUrl: './password-register.component.html',
  styleUrls: ['./password-register.component.css'],
  standalone: true,
  imports: [CardComponent, LogoComponent, InputComponent, ButtonComponent]
})
export class PasswordRegisterComponent implements OnInit {

  protected readonly passwordType: InputType = InputType.Password;

  protected name: string = '';

  protected newPassword: string = '';
  protected confirmPassword: string = '';

  constructor(private userService: UserService) { }

  ngOnInit() {
  }

  changePassword() {
    const requestModel: ChangePasswordModel = {
      old_password: '',
      new_password: this.newPassword,
      confirm_password: this.confirmPassword
    }

    this.userService.changePassword(requestModel).subscribe()
  }

}
