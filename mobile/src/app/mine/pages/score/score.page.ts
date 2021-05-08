import { Component, OnInit } from '@angular/core';
import { UsermsgserviceService } from 'src/app/service/usermsgservice.service';
import { HttpserviceService } from 'src/app/service/httpservice.service';
import { NavController } from '@ionic/angular';


@Component({
  selector: 'app-score',
  templateUrl: './score.page.html',
  styleUrls: ['./score.page.scss'],
})
export class ScorePage implements OnInit {
  public getrecordmsgapi = '/activities/class/self?page=1&pageSize=1000&orgCode=';
  public activity: any = [];
  public addlist: any = [];
  public score: any = [];
  public userid: any = [];
  public scoreslist: any = [];
  public recordslist: any = [];
  public getallscoreapi: any = '/activities/getOrgScoreByUserId?userId=';
  public getmyentercourseapi: any = '/user/joinedClass';
  constructor(public userserivce: UsermsgserviceService, public httpclient: HttpserviceService, public navCtrl: NavController) { }

  public user: any = {

  }
  ngOnInit() {
    this.userid = this.userserivce.getuId()
    this.httpclient.get(this.getallscoreapi + this.userid).then((response) => {
      this.scoreslist = response['result']
      this.httpclient.get(this.getmyentercourseapi).then((response) => {
        this.addlist = response['result']
        for (let n = 0; n < this.addlist.length; n++) {
          for (let i = 0; i < this.scoreslist.length; i++){
            if (this.addlist[n].orgCode == this.scoreslist[i].orgCode) {
              this.addlist[n].sumScore = this.scoreslist[i].sumScore
            }
          }
        }
        console.log(this.addlist)
      })
    })
    /*this.httpclient.get(this.getmyentercourseapi).then((response) => {
      this.addlist = response['result']
      for (let n = 0; n < this.addlist.length; n++) {
        this.addlist[n].s = 0
        this.addlist[n].l = 0
      }
      for (let n = 0; n < this.addlist.length; n++) {
        this.httpclient.get(this.getrecordmsgapi + this.addlist[n].orgCode).then((response) => {
          this.recordslist = response['result']
          console.log(this.recordslist)
          for (let activity of response['result']) {
            //签到才添加
            if (activity['activityTypeId'] == 1) {
              this.addlist[n].l++
            } else if (activity['activityTypeId'] == 2) {
              this.addlist[n].l++
              this.addlist[n].l++
            } else if (activity['activityTypeId'] == 3) {
              this.addlist[n].l++
              this.addlist[n].l++
              this.addlist[n].l++
            }
            if (activity['userId'] == undefined) {
            } else {
              if (activity['activityTypeId'] == 1) {
                this.addlist[n].s++
              } else if (activity['activityTypeId'] == 2) {
                this.addlist[n].s++
                this.addlist[n].s++
              } else if (activity['activityTypeId'] == 3) {
                this.addlist[n].s++
                this.addlist[n].s++
                this.addlist[n].s++
              }
            }
          }
          this.addlist[n].score = this.addlist[n].s / this.addlist[n].l * 100
        })
      }
      console.log(this.addlist)
    })*/
  }
  ngAfterViewInit() {

  }
  scoredetail(org: any) {
    this.userserivce.setorgCode(org)
    console.log(org)
    this.navCtrl.navigateForward('/mine/scoredetil');
  }

}
