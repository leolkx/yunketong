import { Component, OnInit } from '@angular/core';
import { UsermsgserviceService } from 'src/app/service/usermsgservice.service';
import { HttpserviceService } from 'src/app/service/httpservice.service';
import { NavController, ActionSheetController } from '@ionic/angular';

@Component({
  selector: 'app-recorddetil',
  templateUrl: './recorddetil.page.html',
  styleUrls: ['./recorddetil.page.scss'],
})
export class RecorddetilPage implements OnInit {
  public getrecordmsgapi = '/activities/class/self?page=1&pageSize=1000&orgCode=';
  public getmyentercourseapi: any = '/user/joinedClass';
  public addlist: any = [];
  public recordslist: any = [];
  public orgCode: any = [];
  public orgmsg: any = [];
  public arrList: any = [];
  public arrsums: any = 0;
  public sums: any = 0;
  constructor(public usermsg: UsermsgserviceService, public httpclient: HttpserviceService, public navCtrl: NavController, public actionsheetController: ActionSheetController) { }

  ngOnInit() {
    this.httpclient.get(this.getmyentercourseapi).then((response) => {
      this.addlist = response['result']
      for (let n = 0; n < this.addlist.length; n++) {
        if (this.addlist[n].orgCode == this.usermsg.getorgCode()) {
          this.orgmsg = this.addlist[n].cloudClass
          console.log(this.orgmsg)
        }
      }
    })
    this.orgCode = this.usermsg.getorgCode()
    this.httpclient.get(this.getrecordmsgapi + this.orgCode).then((response) => {
      this.recordslist = response['result']
      console.log(this.recordslist)
      for (let activity of response['result']) {
        //签到才添加
        if (activity['activityTypeId'] == 1) {
          if (activity['userId'] == undefined) {
            //没参加
            this.sums++
            activity['isarr'] = '未签到'
            activity['type'] = 'danger'
          } else {
            //参加
            this.sums++
            this.arrsums++
            activity['isarr'] = '已签到'
            activity['type'] = 'success'
          }
          this.arrList.push(activity)
        }
      }
    })
  }
}
