import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css'],
  standalone: true
})
export class InputComponent implements OnInit {
  @Input()
  type: InputType = InputType.Text;
  @Input()
  placeholder: string = '';

  constructor() { }

  ngOnInit() {
  }

}
export enum InputType{
  Text = "text",
  Number = "number",
  Password = "password",
  Email = "email"
}
