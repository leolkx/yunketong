import { Component, OnInit } from '@angular/core';
import { NavController , NavParams } from '@ionic/angular';
@Component({
  selector: 'app-getschool',
  templateUrl: './getschool.component.html',
  styleUrls: ['./getschool.component.scss'],
})
export class GetschoolComponent implements OnInit { 
  //0 选学校 1 选专业
  public type:any=0;
  constructor(public navParams: NavParams) { }

  ngOnInit() {}
  gettec(schid:any){
    //保存学校信息
    //去获取学校的专业
    this.type=1;

  }
  public msg:any={
    school:'福州大学',
    tech:'计算机科学技术'
  }
  backdata(data:any){
    this.navParams.data.modal.dismiss(this.msg);
  }
}
