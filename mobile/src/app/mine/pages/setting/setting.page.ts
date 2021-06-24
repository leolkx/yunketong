import { Component, OnInit } from '@angular/core';
import { ToastController } from '@ionic/angular';
import { AlertController } from '@ionic/angular';
import { LocalStorageService } from '../../../service/local-storage.service';
import { HttpserviceService } from 'src/app/service/httpservice.service';
import { NavController } from '@ionic/angular';
import { UsermsgserviceService } from '../../../service/usermsgservice.service';

@Component({
  selector: 'app-setting',
  templateUrl: './setting.page.html',
  styleUrls: ['./setting.page.scss'],
})
export class SettingPage implements OnInit {


  public signoutapi = '/signout';
  public user: any = {
    username: []
  }
  constructor(public usermsg: UsermsgserviceService, public toastController: ToastController, public localStorage: LocalStorageService, public alertController: AlertController, public httpclient: HttpserviceService, public navCtrl: NavController) { }

  ngOnInit() {
    this.user.username = this.usermsg.getaccount();
  }
  async presentAlertConfirm() {
    const alert = await this.alertController.create({
      header: '提示信息!',
      message: '您确定要登出吗？',
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
            this.signout();
            // console.log('Confirm Okay');
          }
        }
      ]
    });

    await alert.present();
  }
  async presentToast_suc() {
    const toast = await this.toastController.create({
      message: '登出成功.',
      duration: 2000,
      cssClass: 'mytoast',
      color: 'light'
    });
    toast.present();
  }
  async presentToast_fla() {
    const toast = await this.toastController.create({
      message: '登出失败.',
      duration: 2000,
      cssClass: 'mytoast',
      color: 'light'
    });
    toast.present();
  }
  signout() {
    // console.log(this.user)
    this.httpclient.upData(this.signoutapi, this.user).then((response) => {
      this.localStorage.remove("token") 
      this.localStorage.clearAll()
      // console.log(response)
      if (response['state'] == 'success') {
        this.presentToast_suc()
        this.navCtrl.navigateForward('/login');
      } else {
        //提示信息错误
        this.presentToast_fla();
      }
    })
  }
}
