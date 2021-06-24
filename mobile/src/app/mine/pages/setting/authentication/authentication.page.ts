import { Component, OnInit, Output, EventEmitter, ChangeDetectorRef } from '@angular/core';
import { HttpserviceService } from 'src/app/service/httpservice.service';
import { NavController } from '@ionic/angular';
import { UsermsgserviceService } from 'src/app/service/usermsgservice.service';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.page.html',
  styleUrls: ['./authentication.page.scss'],
})
export class AuthenticationPage implements OnInit {

  @Output() public outer = new EventEmitter<any>();

  public timer: any;
  public tab = 'tab1';
  public verimage = this.httpclient.ip + "/verification/code";
  public getusermsgapi = '/user/info';
  public getusermsg: any = [];
  public user: any = {
    userName: '',
    password: '',
    verificationCode: '',
    answer: '',
    questionlist: ['我的父亲生日是???????？', '我的母亲生日是？', '我的配偶生日是？'],
    //question: '我的父亲生日是？'
  }

  public timelimit: any = 60;
  public flag: any = true;
  constructor(public ref: ChangeDetectorRef, public httpclient: HttpserviceService, public navCtrl: NavController, public usermsg: UsermsgserviceService) { }

  ngOnInit() {
    this.httpclient.get(this.getusermsgapi).then((response) => {
      this.getusermsg = response['result']
      // console.log(this.getusermsg);
    })
    // console.log(this.verimage)
  }


  // 获取验证码
  get_check_code() {
    // var api="http://localhost:8080/test";
    // this.httpclient.get(api).then((response)=>{
    //   console.log(response)
    // })
    // console.log('获取验证码' + this.user.phone)
    //倒计时
    this.flag = false;
    this.timer = setInterval(() => {
      this.timelimit--;
      if (this.timelimit == 0) {
        this.timelimit = 60;
        this.flag = true;
        clearInterval(this.timer);
      }
    }, 1000)
    // this.dataService.getCaptchas().subscribe(res => {
    //   this.captchaCodeImg = res.code;
    // });
  }

  clear() {
    this.user.phone = '';
    this.user.password = '';
    this.user.checkCode = '';
    this.timelimit = 60;
    this.flag = true;
    clearInterval(this.timer);
  }

}

