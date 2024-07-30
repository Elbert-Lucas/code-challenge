import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.css'],
  standalone: true
})
export class ButtonComponent implements OnInit {
  
  @Input()
  text: string = '';

  constructor() { }

  ngOnInit() {
  }

}
