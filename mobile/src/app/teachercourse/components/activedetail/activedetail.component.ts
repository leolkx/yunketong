import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-activedetail',
  templateUrl: './activedetail.component.html',
  styleUrls: ['./activedetail.component.scss'],
})
export class ActivedetailComponent implements OnInit {

  constructor() { }

  public actype:any=true;
  public wri:any=0;
  ngOnInit() {}
  acmsg(){
    this.actype=!this.actype;
  }
  acwri()
  {

  }

}
