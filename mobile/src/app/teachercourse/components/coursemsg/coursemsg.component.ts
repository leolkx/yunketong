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
        this.coursemsg=response['result'];
      }
    })
  } 
  edit(type:any){
    this.type=type;
    if(type==2)
    {
      if(this.coursemsg['classCloud']['lessonStartDate'].length>15){
        this.coursemsg['classCloud']['lessonStartDate']=this.formatDate(this.coursemsg['classCloud']['lessonStartDate'])
      } 
      if(this.coursemsg['classCloud']['lessonEndDate'].length>15){
        this.coursemsg['classCloud']['lessonEndDate']=this.formatDate(this.coursemsg['classCloud']['lessonEndDate'])
      }
      //提交编辑数据
      this.httpservice.put(this.coursemshapi+this.localstorage.get('orgCode','xxx'),this.coursemsg['classCloud']).then((response)=>{
        if(response['state']=='success')
        { 
          this.type=0;
        }
      })
      //修改状态
    }
  } 
  
  async del() {
    const alert = await this.alertController.create({
      header: '确认删除',
      message: '是否删除该班课!!!',
      buttons: [
        {
          text: '取消',
          role: 'cancel',
          cssClass: 'secondary',
          handler: (blah) => {
            console.log('Confirm Cancel: blah');
          }
        }, {
          text: '删除',
          handler: () => {
            this.httpservice.delete(this.coursemshapi+this.localstorage.get('orgCode','xxx')).then((response)=>{
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

  // del(courseid:any)
  // {
  //   //提示是否删除
  //   this.httpservice.delete(this.coursemshapi+this.userservice.getorgCode(),'').then((response)=>{
  //     if(response['state']=='success')
  //     {
  //       this.navCtrl.navigateForward('/tabs/coures');
  //     }
  //   })
  //   //删除班课
  // } 
}
