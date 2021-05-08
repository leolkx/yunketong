import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { HttpserviceService } from 'src/app/service/httpservice.service';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-changephone',
  templateUrl: './changephone.page.html',
  styleUrls: ['./changephone.page.scss'],
})

export class ChangephonePage implements OnInit {
  public verimage=this.httpclient.ip+"/verification/code";
  public nums:any=1;
  public user:any={
    userName:'',
    password:'',
    verificationCode:'',
    phone:''
  } 

  constructor(public ref : ChangeDetectorRef,public httpclient:HttpserviceService,public navCtrl: NavController ) { }

  ngOnInit() {
    console.log(this.verimage)
  }
  ngAfterViewInit(){
    
    document.getElementById('vericode').setAttribute('src',this.verimage+'?'+this.nums);
    this.nums++;
  }

}
