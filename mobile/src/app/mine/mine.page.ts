import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { UsermsgserviceService } from '../service/usermsgservice.service';
import { HttpserviceService } from '../service/httpservice.service';
import { NavController } from '@ionic/angular';
import { ResourceLoader } from '@angular/compiler';

@Component({
  selector: 'app-mine',
  templateUrl: './mine.page.html',
  styleUrls: ['./mine.page.scss'],
})
export class MinePage implements OnInit {

  constructor(public ref: ChangeDetectorRef, public usermsg: UsermsgserviceService, public httpclient: HttpserviceService, public navCtrl: NavController) { }

  public getusermsgapi = '/user/info';
  public getusermsg: any = [];
  public picadd:any;
  public user: any = {
    nickname: '',
    gender: '',
    profilePhotoUrl: '',
    studentId: '',
    school: '',
    major: '',
    education: '',
    college: '',
    birthDate: '',
    address: '', city: '', province: '', nation: '',
  }
  ngOnInit() {
    this.httpclient.get(this.getusermsgapi).then((response) => {
      this.getusermsg = response['result']
      this.usermsg.setuId(this.getusermsg.id)
      this.usermsg.setaccount(this.getusermsg.username)
      if(this.getusermsg.gender == '男'){
        this.picadd="assets/image/M.png"
      }
      else if(this.getusermsg.gender == '女'){
        this.picadd="assets/image/F.png"
      }
      else{
        this.picadd="assets/image/Q.png"
      }
      // console.log(this.getusermsg);
    })
  }

  ngAfterViewInit() {
    /*this.httpclient.get(this.getusermsgapi).then((response) => {
      this.getusermsg = response['result']
      console.log(this.getusermsg);
    })*/
  }
  ionViewWillEnter() {
    if (this.usermsg.getmod() == "no") {
    } else {
      this.usermsg.setmod("no");
    this.httpclient.get(this.getusermsgapi).then((response) => {
      this.getusermsg = response['result']
      if(this.getusermsg.gender == '男'){
        this.picadd="assets/image/M.png"
      }
      else if(this.getusermsg.gender == '女'){
        this.picadd="assets/image/F.png"
      }
      else{
        this.picadd="assets/image/Q.png"
      }
      // console.log(this.getusermsg);
    })
    }
    
  }
}
