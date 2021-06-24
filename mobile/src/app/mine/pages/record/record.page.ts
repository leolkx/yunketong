import { Component, OnInit } from '@angular/core';
import { UsermsgserviceService } from '../../../service/usermsgservice.service';
import { HttpserviceService } from 'src/app/service/httpservice.service';
import { NavController } from '@ionic/angular';
import { LocalStorageService } from '../../../service/local-storage.service';
import { ToastController } from '@ionic/angular';
import { AlertController } from '@ionic/angular';
@Component({
  selector: 'app-record',
  templateUrl: './record.page.html',
  styleUrls: ['./record.page.scss'],
})
export class RecordPage implements OnInit {
  public getrecordmsgapi = '/activities/class/self?page=1&pageSize=1000&orgCode=';
  public activity: any = [];
  public addlist: any = [];
  public recordslist: any = [];
  public getmyentercourseapi: any = '/user/joinedClass';
  constructor(public localStorage: LocalStorageService, public userserivce: UsermsgserviceService, public httpclient: HttpserviceService, public navCtrl: NavController) { }

  public user: any = {

  }
  ngOnInit() {
    this.httpclient.get(this.getmyentercourseapi).then((response) => {
      this.addlist = response['result']
      for (let n = 0; n < this.addlist.length; n++) {
        this.addlist[n].s = 0
        this.addlist[n].l = 0
      }
      //console.log(this.addlist);
      for (let n = 0; n < this.addlist.length; n++) {
        this.httpclient.get(this.getrecordmsgapi + this.addlist[n].orgCode).then((response) => {
          this.recordslist = response['result']
          // console.log(this.recordslist)

          for (let activity of response['result']) {
            //签到才添加

            if (activity['activityTypeId'] == 1) {
              this.addlist[n].l++
              if (activity['userId'] == undefined) {
              } else {
                //参加
                this.addlist[n].s++
              }
            }
          }
        })
      }
      // console.log(this.addlist)
    })
  }
  recorddetail(org: any) {
    this.userserivce.setorgCode(org)
    // console.log(org)
    this.navCtrl.navigateForward('/mine/recorddetil');
  }
  ngAfterViewInit() {
  }
}
