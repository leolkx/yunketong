import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular'; 
import { PopoverController } from '@ionic/angular';
import { RecordComponent } from '../record/record.component'; 

import { StumsgComponent } from '../stumsg/stumsg.component';
@Component({
  selector: 'app-stafflist',
  templateUrl: './stafflist.component.html',
  styleUrls: ['./stafflist.component.scss'],
})
export class StafflistComponent implements OnInit {

  constructor(public modalController: ModalController,public popoverController: PopoverController ) {

  }

  ngOnInit() {}

  //跳转到  签到组件 的模态框
  daorecord()
  {

  }
//模态框获取  获取学生详情
  stumsg(){

  }
  async gorecord() {
  
    const modal = await this.modalController.create({
      component: RecordComponent
    });
    
    await modal.present();
    // await modal.onDidDismiss().then(()=>{
    //   this.popoverController.dismiss();
    // }
    // )
  } 
  async studetail()
  {
    //进入学生页面  带着学生id  模态框传值把
    const modal = await this.modalController.create({
      component: StumsgComponent
    });
    
    await modal.present();
  }
}
