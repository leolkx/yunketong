import { Component, OnInit } from '@angular/core';
import { UsermsgserviceService } from 'src/app/service/usermsgservice.service';
import { HttpserviceService } from 'src/app/service/httpservice.service';
import { NavController, ActionSheetController } from '@ionic/angular';
@Component({
  selector: 'app-scoredetil',
  templateUrl: './scoredetil.page.html',
  styleUrls: ['./scoredetil.page.scss'],
})
export class ScoredetilPage implements OnInit {
  public getrecordmsgapi = '/activities/class/self?page=1&pageSize=1000&orgCode=';
  public getmyentercourseapi: any = '/user/joinedClass';
  public getallscoreapi: any = '/activities/getOrgScoreByUserId?userId=';
  public recordslist: any = [];
  public sumscore: any = [];
  public sums: any = 0;
  public getsums: any = 0;
  public List: any = [
    { itemN: '签到', sumscore: 0, getscore: 0 },
    { itemN: '作业', sumscore: 0, getscore: 0 },
  ]
  public orgmsg: any = [];
  public getscore: any = [];
  public orgCode: any = [];
  public addlist: any = [];
  public userid: any = [];
  public scoreslist: any = [];

  constructor(public usermsg: UsermsgserviceService, public httpclient: HttpserviceService, public navCtrl: NavController, public actionsheetController: ActionSheetController) { }

  ngOnInit() {
    this.userid = this.usermsg.getuId()
    this.httpclient.get(this.getallscoreapi + this.userid).then((response) => {
      this.scoreslist = response['result']
      for (let n = 0; n < this.scoreslist.length; n++) {
        if (this.scoreslist[n].orgCode == this.usermsg.getorgCode()) {
          this.orgmsg = this.scoreslist[n].className
          this.sumscore = this.scoreslist[n].sumScore
          console.log(this.orgmsg)
        }
      }
    })
    /*this.httpclient.get(this.getmyentercourseapi).then((response) => {
      this.addlist = response['result']
      for (let n = 0; n < this.addlist.length; n++) {
        if (this.addlist[n].orgCode == this.usermsg.getorgCode()) {
          this.orgmsg = this.addlist[n].cloudClass
          console.log(this.orgmsg)
        }
      }
    })*/
    this.orgCode = this.usermsg.getorgCode()
    this.httpclient.get(this.getrecordmsgapi + this.orgCode).then((response) => {
      this.recordslist = response['result']
      console.log(this.recordslist)
      for (let activity of response['result']) {
        if (activity['activityTypeId'] == 1) {
          this.List[0].sumscore++
        } else if (activity['activityTypeId'] == 2) {
          this.List[1].sumscore = this.List[1].sumscore + 1
        }
        if (activity['userId'] != undefined) {
          if (activity['activityTypeId'] == 1) {
            this.List[0].getscore = this.List[0].getscore + 1
          } else if (activity['activityTypeId'] == 2) {
            this.List[1].getscore = this.List[1].getscore + 1
          }
        }
        this.getsums = this.List[0].getscore + this.List[1].getscore
        this.sums = this.List[0].sumscore + this.List[1].sumscore
      }
    })

  }

}
