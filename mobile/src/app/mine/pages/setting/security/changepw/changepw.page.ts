import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { HttpserviceService } from 'src/app/service/httpservice.service';
import { NavController } from '@ionic/angular';
import { LocalStorageService } from '../../../../../service/local-storage.service';
import { ToastController } from '@ionic/angular';
import { AlertController } from '@ionic/angular';

@Component({
  selector: 'app-changepw',
  templateUrl: './changepw.page.html',
  styleUrls: ['./changepw.page.scss'],
})
export class ChangepwPage implements OnInit {
  public changepasswordapi = '/user/password';
  public mailcodeapi="/verification/mail?email=";
  public getusermsg: any = [];
  public timer:any;
  public getusermsgapi = '/user/info';
  public timelimit:any=60;
  public flag:any = true;
  public failedmsg:any=[]
  public text='org.fzu.cs03.daoyun.exception.'
  public userpassword:any={
    id:'',
    newPassword:'',
    oldPassword:'',
    email:'',
    mailVerificationCode:''
  } 

  constructor(public toastController: ToastController, public localStorage:LocalStorageService, public ref : ChangeDetectorRef,public httpclient:HttpserviceService,public navCtrl: NavController , public alertController: AlertController) { }
  ngOnInit(): void {
    this.httpclient.get(this.getusermsgapi).then((response) => {
      this.getusermsg = response['result']
    })
    this.userpassword.id=this.localStorage.get("userId",0);
    // console.log(this.userpassword.id)
  }

  async presentAlertConfirm() {
    const alert = await this.alertController.create({
      header: '提示信息!',
      message: '您确定要更改密码吗？',
      buttons: [
        {
          text: '取消',
          role: 'cancel',
          cssClass: 'secondary',
          handler: (blah) => {
            // console.log('Confirm Cancel: blah');
          }
        }, {
          text: '确定',
          handler: () => {
            this.changepw();
            // console.log('Confirm Okay');
          }
        }
      ]
    });

    await alert.present();
  }

  async presentToast_suc() {
    const toast = await this.toastController.create({
      message: '保存成功.',
      duration: 2000,
      cssClass: 'mytoast',
      color: 'light'
    });
    toast.present();
  }
  async presentToast_fla() {
    const toast = await this.toastController.create({
      message: '错误：'+this.failedmsg,
      duration: 2000,
      cssClass: 'mytoast',
      color: 'light'
    });
    toast.present();
  }

  changepw(){
    this.userpassword.email=this.getusermsg.email
    console.log(this.userpassword)
    this.httpclient.put(this.changepasswordapi, this.userpassword).then((response) => {
      this.failedmsg=response['msg']
      // console.log(this.failedmsg)
      // console.log(response)
      if (response['state'] == 'success') {
        this.presentToast_suc()
        this.navCtrl.navigateForward('/tabs');
      } else {
        //提示信息错误
        this.presentToast_fla();
      }
    })
  }

  get_check_code() {
    this.httpclient.get(this.mailcodeapi+this.getusermsg.email).then((response)=>{
    //let cookie =response.headers['Set-Cookie']
    // console.log(response)
    })
    //倒计时
    this.flag=false;
    this.timer = setInterval(()=>{
      this.timelimit--;
      if(this.timelimit==0)
      {
        this.timelimit=60;
        this.flag=true;
        clearInterval(this.timer);
      }
      },1000)
    // this.dataService.getCaptchas().subscribe(res => {
    //   this.captchaCodeImg = res.code;
    // });
  }
}