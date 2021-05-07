import { Component, OnInit } from '@angular/core';

import { ModalController } from '@ionic/angular'; 
import { PopoverController } from '@ionic/angular'; 
import { HttpserviceService } from '../../../service/httpservice.service';  
 
import { LocalStorageService } from '../../../service/local-storage.service'; 
import { AlertController } from '@ionic/angular';    
import { NavController , NavParams } from '@ionic/angular';
@Component({
  selector: 'app-coursemsg',
  templateUrl: './coursemsg.component.html',
  styleUrls: ['./coursemsg.component.scss'],
})
export class CoursemsgComponent implements OnInit {

  public coursemshapi:any='/cloudClass?orgCode=';
  public type:any=0;
  public course:any={
    orgCode:this.localstorage.get('orgCode','xxx'),
    className: "",
    college: "",
    grade: "",
    introduction: "",
    lessonEndDate: "",
    lessonStartDate: "",
    school: "",
    teacherName: "",
    teachingMateria: ""
  }
  public coursemsg:any;

  constructor(
    public localstorage:LocalStorageService,
    public navCtrl: NavController, 
    public httpservice:HttpserviceService,
    public modalController: ModalController,
    public popoverController: PopoverController,
    public alertController:AlertController
    ) {}
 
  formatDate = ( time: any ) => {
    // 格式化日期，获取今天的日期
    const Dates = new Date( time );
    const year: number = Dates.getFullYear();
    const month: any = ( Dates.getMonth() + 1 ) < 10 ? '0' + ( Dates.getMonth() + 1 ) : ( Dates.getMonth() + 1 );
    const day: any = Dates.getDate() < 10 ? '0' + Dates.getDate() : Dates.getDate();
    return year + '-' + month + '-' + day;
  };

  ngOnInit() {
    this.httpservice.get(this.coursemshapi+this.localstorage.get('orgCode','xxx')).then((response)=>{
      console.log(response);
      if(response['state']=='success')
      {
        this.course=response['result']['classCloud'];
        this.course['orgCode']=this.localstorage.get('orgCode','xxx')
        console.log(this.course)
      }
    })

  }
  public gooutOrgapi:any='/cloudClass/members?orgCode=';
  async goout() {
    const alert = await this.alertController.create({
      header: '退出班课',
      message: '是否退出该班课!!!',
      buttons: [
        {
          text: '取消',
          role: 'cancel',
          cssClass: 'secondary',
          handler: (blah) => {
            console.log('Confirm Cancel: blah');
          }
        }, {
          text: '确定',
          handler: () => {
            this.httpservice.delete(this.gooutOrgapi+this.localstorage.get('orgCode','xxx')).then((response)=>{
              if(response['state']=='success')
              {
                // this.navCtrl.navigateForward('/tabs/coures');
                // location.reload();
                //这样是重新加载 或者是传参数回去直接删除页面数据  这样体验会更好
                window.location.href='/tabs/coures'

              }
            })
          }
        }
      ]
    });

    await alert.present();
  }
}
