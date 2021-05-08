import { Component, OnInit } from '@angular/core';
import { AlertController } from '@ionic/angular';
import { HttpserviceService } from 'src/app/service/httpservice.service';
import { UsermsgserviceService } from 'src/app/service/usermsgservice.service';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.page.html',
  styleUrls: ['./feedback.page.scss'],
})
export class FeedbackPage implements OnInit {

  constructor(public alertController: AlertController, public httpclient: HttpserviceService, public usermsg: UsermsgserviceService) { }
  public user: any = {
    name: '',
    roleId: '',
    userId: '',
    fbinfo: '',
    time: ''
  }
  ngOnInit() {
    this.user.name = this.usermsg.getaccount();
  }
  async presentAlert() {
    const alert = await this.alertController.create({
      header: '成功',
      subHeader: '提交成功',
      message: '用户' + this.user.name + '反馈信息已提交',
      buttons: ['确认']
    });
    await alert.present();
  }
  public submit() {
    this.user.fbinfo = ''
    this.user.time = new Date(new Date().getTime() + 8 * 60 * 60 * 1000).toISOString();
    console.log(this.user.time);

    this.presentAlert();
  }

}
