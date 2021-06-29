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
  // public changepasswordapi = '/user/password';
  // public mailcodeapi="/verification/mail?email=";
  public phonecodeapi="/phonecode?phone=";
  public passwordapi="/user/password";
  public getusermsg: any = [];
  public timer:any;
  public getusermsgapi = '/user/info';
  public timelimit:any=60;
  public flag:any = true;
  public failedmsg:any=[]
  public text='org.fzu.cs03.daoyun.exception.'
  public userpassword:any={
    phone:'',
    password:'',
    verificationCode:''
  } 

  constructor(public toastController: ToastController, public localStorage:LocalStorageService, public ref : ChangeDetectorRef,public httpclient:HttpserviceService,public navCtrl: NavController , public alertController: AlertController) { }
  ngOnInit(): void {
    this.httpclient.get(this.getusermsgapi).then((response) => {
      this.getusermsg = response['result']
    })
    // this.userpassword.id=this.localStorage.get("userId",0);
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

  async changepw(){
    let toast: any;
    toast = await this.toastController.create({
      duration: 500,
      position: 'middle',
      message: ''
    });
    this.userpassword.phone=this.getusermsg.phone
    // console.log(this.userpassword)
    this.httpclient.upDataNotoken(this.passwordapi, this.userpassword).then((response) => {
      this.failedmsg=response['msg']
      console.log(response)
      // console.log(response)
      // if (response['state'] == 'success') {
      //   this.presentToast_suc()
      //   this.navCtrl.navigateForward('/tabs');
      // } else {
      //   //提示信息错误
      //   this.presentToast_fla();
      // }
      if(response['msg']=='修改用户密码成功'){
        // alert('修改成功')
        toast.message =  '修改成功';
        toast.present();
        // this.presentToast_suc()
        this.navCtrl.navigateForward('/tabs');
      }else{
        toast.message =  response['msg'].split(':')[1];
        toast.present();
        this.presentToast_fla();
      }
    })
  }

  get_check_code() {
    console.log(this.getusermsg)
    this.httpclient.getNotoken(this.phonecodeapi+this.getusermsg.phone).then((response)=>{
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