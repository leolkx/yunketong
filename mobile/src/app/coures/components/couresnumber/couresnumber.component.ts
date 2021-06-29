import { PositionRes } from '@ionic-native/gao-de-location/ngx';
import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';  
import { PopoverController } from '@ionic/angular';
import { NavController , NavParams } from '@ionic/angular'; 

import { LocalStorageService } from '../../../service/local-storage.service';
import { HttpserviceService } from '../../../service/httpservice.service';
import { ToastController} from '@ionic/angular';
import { BarcodeScanner } from '@ionic-native/barcode-scanner/ngx';

@Component({
  selector: 'app-couresnumber',
  templateUrl: './couresnumber.component.html',
  styleUrls: ['./couresnumber.component.scss'],
})
export class CouresnumberComponent implements OnInit {

  public type:any=1;
  public couresnumbe:any='';
  public addcourseapi:any='/cloudClass?orgCode=';
  public enterclassapi:any='/cloudClass/members?orgCode='
  constructor(public localStorageService:LocalStorageService,public modalCtrl:ModalController,public httpservice:HttpserviceService,public barcodeScanner: BarcodeScanner,private toastController: ToastController) { }
  public orgCode:any='';

  public coursemsg:any={
    className: "",
    orgCode: "",
    college: "",
    grade: "",
    introduction: "",
    lessonEndDate: "",
    lessonStartDate: "",
    school: "",
    teacherName: "",
    teachingMateria: ""
  };

  public course:any={
    className: "",
    orgCode: "",
    college: "",
    grade: "",
    introduction: "",
    lessonEndDate: "",
    lessonStartDate: "",
    school: "",
    teacherName: "",
    teachingMateria: ""
  }
  async ngOnInit() {
      let toast: any;
      toast = await this.toastController.create({
      duration: 500,
      position: 'middle',
      message: ''
    });
    //通过扫描进来的
      // this.type=1;
      // this.orgCode=this.localStorageService.get('scanText','xxx');
      // this.localStorageService.remove('scanText')
      // this.barcodeScanner.scan().then(async barcodeData => {
      //   // alert(barcodeData['text'])
      //   this.localStorageService.get('scanText',barcodeData['text']);
      //   this.orgCode = this.localStorageService.get('scanId',this.localStorageService.get('scanText',barcodeData['text']))
      // }),
      this.httpservice.get(this.addcourseapi+this.orgCode).then((response)=>{
        // console.log(response)
        if(response['state']=='success')
        {
          this.course=response['result']['classCloud'];
          this.coursemsg=response['result']['classCloud'];
          // toast.message =  this.course;
          // toast.present();
        }else{
          //后面优化
          // alert('没有该课程');
          // toast.message =  '班课不存在';
          // toast.present();
        }
      });
    // if(this.localStorageService.get('scanText','xxx')!='xxx'){ 
    //   this.type=2;
    //   this.orgCode=this.localStorageService.get('scanText','xxx');
    //   this.localStorageService.remove('scanText')
    //   this.httpservice.get(this.addcourseapi+this.orgCode).then((response)=>{
    //     // console.log(response)
    //     if(response['state']=='success')
    //     {
    //       this.coursemsg=response['result']['classCloud'];
    //     }else{
    //       //后面优化
    //       // alert('没有该课程');
    //       toast.message =  '班课不存在';
    //       toast.present();
    //     }
    //   })
    // }
  }
  
  async nextadd(){
    let toast: any;
    toast = await this.toastController.create({
      duration: 500,
      position: 'middle',
      message: ''
    });
    
    this.httpservice.get(this.addcourseapi+this.orgCode).then((response)=>{
      // console.log(response)
      if(response['state']=='success')
      {
        this.coursemsg=response['result']['classCloud'];
        this.type=2;
      }else{
        //后面优化
        // alert('没有该课程');
        toast.message =  '班课不存在';
        toast.present();
      }
    })
    //通过班课号获取班课信息，展示在页面上
  } 
  async addcourse(){
    let toast: any;
    toast = await this.toastController.create({
      duration: 500,
      position: 'middle',
      message: ''
    });
    
    this.httpservice.upData(this.enterclassapi+this.orgCode,'').then((response)=>{
      console.log(response['msg'].split(':')[1])
      //alert(JSON.stringify(response))
      if(response['state']=='success')
      {
        toast.message =  '成功加入';
        toast.present();
        location.reload();
      }
      else
      {
        toast.message =  response['msg'].split(':')[1];
        toast.present();
        // if(response['msg'].split(':')[1]=='教师不能加入自己创建的班课'){
        //   toast.message =  '教师不能加入自己创建的班课';
        //   toast.present();
        // }else if(response['msg'].split(':')[1]=='班课已结课'){
        //   toast.message =  '班课已结课';
        //   toast.present();
        // }else if((response['msg'].split(':')[1]) == '用户已经存在于该班课中'){
        //   toast.message =  '用户已经存在于该班课中';
        //   toast.present();
        // }else{
        //   // alert(response['msg'])
        //   toast.message =  '加入失败';
        //   toast.present();
          
        // }
      }
    })
    
  
  }
  concel_1(){
      this.modalCtrl.dismiss({
      'dismissed': true
    })
  }

  concel_2(){
    this.type=1;
  }
} 

 