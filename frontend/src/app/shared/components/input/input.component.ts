import { Component, HostListener, Input, OnInit, Output, output, SimpleChange } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css'],
  standalone: true,
  imports: [FormsModule]
})
export class InputComponent implements OnInit {
  @Input()
  type: InputType = InputType.Text;
  @Input()
  placeholder: string = '';

  protected inputed: string = ''
  value = output<string>();

  constructor() { }

  ngOnInit() {
  }

  @HostListener('change', ['$event'])
  onInputChange(event: any) {
    this.value.emit(this.inputed);
  }

}
export enum InputType{
  Text = "text",
  Number = "number",
  Password = "password",
  Email = "email"
}
