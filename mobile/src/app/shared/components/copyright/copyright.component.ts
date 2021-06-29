import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-copyright',
  templateUrl: './copyright.component.html',
  styleUrls: ['./copyright.component.scss']
})
export class CopyrightComponent implements OnInit {

  @Input() bottom: string;
  text: string;
  constructor() {
    const year = (new Date()).getFullYear();
    this.text = `2021-${year} 18小组云课通移动端`;
    this.bottom = '10px';
  }

  ngOnInit() {
  }

}
