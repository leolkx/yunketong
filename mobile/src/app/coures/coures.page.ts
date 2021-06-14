import { Component, OnInit,ChangeDetectionStrategy, ChangeDetectorRef,OnDestroy,Input,Output,EventEmitter} from '@angular/core';

import { ModalController } from '@ionic/angular'; 
import { PopoverController } from '@ionic/angular';
import { AddcouresComponent } from './components/addcoures/addcoures.component';

import { ScanComponent } from './components/scan/scan.component';
import { HttpserviceService } from '../service/httpservice.service';  
import { UsermsgserviceService } from '../service/usermsgservice.service';  
import { LocalStorageService } from '../service/local-storage.service';
import { NavController , NavParams } from '@ionic/angular';
import {Geolocation} from "@ionic-native/geolocation/ngx";  
import { GaoDeLocation,PositionOptions,LocationProtocolEnum,LocationModeEnum,DesiredAccuracyEnum,PositionRes } from '@ionic-native/gao-de-location/ngx';
import { BarcodeScanner } from '@ionic-native/barcode-scanner/ngx';
// declare var baidumap_location:any;
// import { ViewChild, ElementRef } from '@angular/core';
import { CouresnumberComponent } from '../coures/components/couresnumber/couresnumber.component';

@Component({
  selector: 'app-coures',
  templateUrl: './coures.page.html',
  styleUrls: ['./coures.page.scss'],
})
export class CouresPage implements OnInit {
   
  
  public currentPopover:any = null;
  public type:any=1;
  public getcreatecourseapi:any='/user/createdClass';
  public getaddcourseapi:any='/user/joinedClass';
  public getmyentercourseapi:any='/user/joinedClass';
  public createlist:any=[];
  public addlist:any=[];

  classList = []

  constructor( 
    public barcodeScanner: BarcodeScanner,
    public geolocation:Geolocation,
    public gaoDeLocation: GaoDeLocation, 
    public localStorageService:LocalStorageService,
    public userserivce:UsermsgserviceService,
    public httpclient:HttpserviceService,
    public modalController: ModalController,
    public popoverController: PopoverController,
    public navCtrl: NavController) {}
  public coures:any={
    id:11611
  }
  public latitude:any 
  public longitude :any;
  public curlatitude:any 
  public curlongitude :any;
  // 进行定位  
  ngOnInit() {
    // var t = new Test()
    // t.mean()
    // consol/e.log(Test())
     //以下要放开
    console.log(this.localStorageService.get('token',null));
    this.httpclient.get(this.getcreatecourseapi).then((response)=>{
      this.createlist=response['result'] 
      console.log('getright---')
      console.log(this.createlist);
      console.log('getright----')
    }).catch((err)=>{
      console.log('get err')
      console.log(JSON.stringify(err))
      console.log('get err----')
    })
    this.httpclient.get(this.getmyentercourseapi).then((response)=>{
      this.addlist=response['result']
      console.log(this.addlist);
    })
  }
    
 
  
    public positionRes: PositionRes;
    async loc1(){
      let  positionOptions: PositionOptions = {
        androidOption: {
         locationMode: LocationModeEnum.Hight_Accuracy,
         gpsFirst: false,
          HttpTimeOut: 30000,
          interval: 2000,
          needAddress: true,
          onceLocation: false,
          onceLocationLatest: false,
          locationProtocol: LocationProtocolEnum.HTTP,
          sensorEnable: false,
          wifiScan: true,
          locationCacheEnable: true
        }, iosOption: {
          desiredAccuracy: DesiredAccuracyEnum.kCLLocationAccuracyBest,
          pausesLocationUpdatesAutomatically: 'YES',
          allowsBackgroundLocationUpdates: 'NO',
          locationTimeout: 10,
          reGeocodeTimeout: 5,
        }
      };  
    
     let positionRes: PositionRes = await this.gaoDeLocation.getCurrentPosition(positionOptions).catch((e: any) => {
      console.log(e);
    }) || null;

    alert(JSON.stringify(positionRes))
    // this.gaoDeLocation.getCurrentPosition(positionOptions).catch((e: any) => {
    //   console.log(e);
    // }) || null;
  } 
  ngAfterViewInit(){
    //这个要开放
    this.httpclient.get(this.getcreatecourseapi).then((response)=>{
      this.createlist=response['result'] 
      console.log(this.createlist);
    })
    this.httpclient.get(this.getmyentercourseapi).then((response)=>{
      this.addlist=response['result']
      console.log(this.addlist);
    })
  }
  typechang(type:any)
  {
    this.type=type;
    if(type==1)
    {
      //获取我创建的课程
      // this.initClassList(true)
      /* this.httpclient.get('/cloudClass').then((response)=>{
        console.log(response) 
        this.initClassList(true)
      }) */
      // this.httpclient.get('/cloudClass').then(async (res:any)=>{
      //   //this.classList = res
      // })
    }
    else if(type==2)
    {
     // 这个要开放
     //const id = this.localStorageService.get(USER_KEY, '').id
     this.httpclient.get('/user/joinedClass').then(async (res:any)=>{
      this.classList = res
    })
     // 获取我加入的课程 
    }
  }
  gocourse(id:any){
    console.log(id);
    //通过路由传参，跳转到课程详情页面
  } 

  async initClassList(isCreater){
    /* let api='/mobileApp/course/'
    const id = this.localStorageService.get(USER_KEY, '').id
    if(isCreater){
      api += 'belong'
    }else{
      api += 'join'
    }
    api += '?id=' + id */
    this.httpclient.get('/user/joinedClass').then(async (res:any)=>{
      this.classList = res
    })
  }

  async presentPopover(ev: any) {
    const popover = await this.popoverController.create({
      component: AddcouresComponent,
      event: ev,
      backdropDismiss:true,
      translucent: true
    });
    this.currentPopover=popover;
     
    await popover.present();
    await popover.onDidDismiss().then((response)=>{
        // console.log(111)
        //这里到时候刷新页面
    })
  } 

  async createCode(code:any) {
    this.localStorageService.set('scanId',code)
    const popover = await this.popoverController.create({
      component: ScanComponent, 
      backdropDismiss:true,
      translucent: true
    });
    this.currentPopover=popover;
     
    await popover.present();
    await popover.onDidDismiss().then((response)=>{
        console.log(111)
        //这里到时候刷新页面
    })
  } 

  goteacher(orgCode:any,className:any)
  {
    console.log(orgCode) 
    this.localStorageService.set('orgCode',orgCode);
    this.localStorageService.set('orgName',className);
    this.navCtrl.navigateForward('/teachercourse');
  }
  coursedetail(orgCode:any,className:any){
    //this.userserivce.setorgCode(orgCode);
    console.log(orgCode) 
    this.localStorageService.set('orgCode',orgCode);
    this.localStorageService.set('orgName',className);
     this.navCtrl.navigateForward('/coursemanage');
  } 
  upload(){
    
  }
 
  public text: any;
  public Code: string;//二维码内容变量  
  // createCode(orgcode:any) {
  //   this.Code = orgcode;
  // }
 doBSFun() {
    this.barcodeScanner.scan().then(async barcodeData => {
      alert(barcodeData['text'])
      this.localStorageService.set('scanText',barcodeData['text']);

      const modal = await this.modalController.create({
        component: CouresnumberComponent
      });
      
      await modal.present();
      await modal.onDidDismiss().then(()=>{
        this.popoverController.dismiss();
      }
      )
     // alert(JSON.stringify(barcodeData));
    }).catch(err => {
      alert(err);
    });
   
  }

  // async coursedetail() {
  //   const modal = await this.modalController.create({
  //     component: CoursemanagePage
  //   });
    
  //   await modal.present();
  //   // await modal.onDidDismiss().then(()=>{
  //   //   this.popoverController.dismiss();
  //   // }
  //   // )
  // } 
  
}
