import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular'; 
import { PopoverController } from '@ionic/angular';
import { RecordComponent } from '../record/record.component';  
import { HttpserviceService } from '../../../service/httpservice.service';  

import { LocalStorageService } from '../../../service/local-storage.service';
import { UsermsgserviceService } from '../../../service/usermsgservice.service';  
import { StumsgComponent } from '../stumsg/stumsg.component';

@Component({
  selector: 'app-stafflist',
  templateUrl: './stafflist.component.html',
  styleUrls: ['./stafflist.component.scss'],
})
export class StafflistComponent implements OnInit {

  public membersapi:any='/cloudClass/members?orgCode=';
  public orgCode:any=this.localstorage.get('orgCode','xxxx');
  public members:any=[];
  public size:any;
  constructor(public userservice:UsermsgserviceService,
    public httpservice:HttpserviceService,
    public modalController: ModalController,
    public popoverController: PopoverController,
    public localstorage:LocalStorageService
    ) {}
  public getstuandscore:any='/activities/orgMemberScore?orgCode='
  ngOnInit() {
    this.httpservice.get(this.getstuandscore+this.localstorage.get('orgCode','xxxx')).then((response)=>{
      // console.log('有返回分数？？？？')
      // console.log(response)
      // this.members=response['result']
      // this.size=this.members.length
      let i = 0
      for (let stu of response['result']) {
        if (stu['username'] != '15900000002') {
          this.members[i] = stu
          i = i + 1
        }
      }
      this.size=i
    })
  //  alert(this.membersapi+this.localstorage.get('orgCode','xxxx'))
//     this.httpservice.get(this.membersapi+this.localstorage.get('orgCode','xxxx')).then((response)=>{
//       if(response['state']=='success')
//       {
//         this.members=response['result']
//         this.size=this.members.length
//       }
// //alert(response)
//     })
  }

  //跳转到  签到组件 的模态框
  daorecord()
  {

  }
//模态框获取  获取学生详情
  stumsg(){

  }
  async gorecord(type:any) {
  
    //type==0 查看签到记录
    //type ==2 直接发起签到
    const modal = await this.modalController.create({
      component: RecordComponent,
      componentProps:{
        'type':type
      }
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
