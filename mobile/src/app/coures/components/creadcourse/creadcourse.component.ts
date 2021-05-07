import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { PopoverController } from '@ionic/angular';
import { NavController, NavParams } from '@ionic/angular';
import { GetschoolComponent } from '../getschool/getschool.component';

import { HttpserviceService } from '../../../service/httpservice.service';
@Component({
  selector: 'app-creadcourse',
  templateUrl: './creadcourse.component.html',
  styleUrls: ['./creadcourse.component.scss'],
})
export class CreadcourseComponent implements OnInit {

  public currentPopover: any;
  public createcourseapi = "/cloudClass";
  public college1: any;
  public course: any = {
    className: '',
    teacherName: '',
    grade: '',
    teachingMateria: '',
    school: '',
    college: '',
    lessonStartDate: '',
    lessonEndDate: '',
    introduction: '',
  };
  public schoolslist: any = [];
  public collegeslist: any = [];
  public majorslist: any = [];
  public getschoolsapi = '/structure/orgs/schools?page=1&pageSize=100';
  public getcollegesapi = '/structure/orgs/colleges?schoolId=';
  public getmajorsapi = '/structure/orgs/majors?collegeId=';
  public schoolid: any;
  public collegeid: any;
  constructor(public modalCtrl: ModalController, public navCtrl: NavController, public popoverController: PopoverController, public httpclient: HttpserviceService) { }

  formatDate = (time: any) => {
    // 格式化日期，获取今天的日期
    const Dates = new Date(time);
    const year: number = Dates.getFullYear();
    const month: any = (Dates.getMonth() + 1) < 10 ? '0' + (Dates.getMonth() + 1) : (Dates.getMonth() + 1);
    const day: any = Dates.getDate() < 10 ? '0' + Dates.getDate() : Dates.getDate();
    return year + '-' + month + '-' + day;
  };
  formatyear = (time: any) => {
    // 格式化日期，获取今天的日期
    const Dates = new Date(time);
    const year: number = Dates.getFullYear();
    return year;
  };
  ngOnInit() {
    this.httpclient.get(this.getschoolsapi).then((response) => {
      this.schoolslist = response['result']
    })
  }

  createcourse() {
    this.course['grade'] = this.formatyear(this.course['grade']);
    this.course['lessonStartDate'] = this.formatDate(this.course['lessonStartDate']);
    this.course['lessonEndDate'] = this.formatDate(this.course['lessonEndDate']);

    console.log(this.course);
    this.httpclient.upData(this.createcourseapi, this.course).then((response) => {
      if (response['state'] == 'success') {
        //对跳转  附带参数
        // this.navCtrl.navigateForward('/tabs');
        location.reload();
      } else {
        //这里会出什么错
      }
      console.log(response)
    })
    // this.modalCtrl.dismiss({
    //   'dismissed': true
    // }) 
  }
  // async presentPopover(ev: any) {
  //   const popover = await this.popoverController.create({
  //     component: GetschoolComponent,
  //     event: ev,
  //     backdropDismiss:true,
  //     translucent: true
  //   });
  //   this.currentPopover=popover;

  //   await popover.present();
  //   await popover.onDidDismiss().then((response)=>{
  //       console.log(111)
  //       //这里到时候刷新页面
  //   })
  // } 

  async presentPopover() {
    const modal = await this.modalCtrl.create({
      component: GetschoolComponent
    });

    await modal.present();
    const { data } = await modal.onDidDismiss();
    console.log(data);
    // await modal.onDidDismiss().then(()=>{
    //   this.popoverController.dismiss();
    // }
    // )
  }
  findsiid() {
    this.course.college = ''
    this.college1 = ''
    for (let n = 0; n < this.schoolslist.length; n++) {
      if (this.schoolslist[n].schoolName == this.course.school) {
        this.schoolid = this.schoolslist[n].id
        break;
      }
    }
    this.httpclient.get(this.getcollegesapi + this.schoolid + '&page=1&pageSize=100').then((response) => {
      this.collegeslist = response['result']
      console.log(this.collegeslist);
    })
  }

  findciid() {
    this.course.college = ''
    for (let n = 0; n < this.collegeslist.length; n++) {
      if (this.collegeslist[n].collegeName == this.college1) {
        this.collegeid = this.collegeslist[n].id
        break;
      }
    }
    this.httpclient.get(this.getmajorsapi + this.collegeid + '&page=1&pageSize=100').then((response) => {
      this.majorslist = response['result']
      console.log(this.majorslist);
    })
  }
}
