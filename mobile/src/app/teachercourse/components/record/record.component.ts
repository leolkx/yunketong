import { Component, OnInit } from '@angular/core';
import { ElementRef, ViewChild, Renderer2 } from '@angular/core';
import { NavController,NavParams, ToastController } from '@ionic/angular';
import { AlertController } from '@ionic/angular';
import { ModalController } from '@ionic/angular'; 
import { HttpserviceService } from '../../../service/httpservice.service';   
import { LocalStorageService } from '../../../service/local-storage.service';  

import {Geolocation} from "@ionic-native/geolocation/ngx";  
import { GaoDeLocation,PositionOptions,LocationProtocolEnum,LocationModeEnum,DesiredAccuracyEnum,PositionRes } from '@ionic-native/gao-de-location/ngx';

import { GestureComponent } from '../gesture/gesture.component';
 

@Component({
  selector: 'app-record',
  templateUrl: './record.component.html',
  styleUrls: ['./record.component.scss'],
})
export class RecordComponent implements OnInit {
 
  public arrivetype:any=0;//1.限时签到  3.手势签到 

  
  constructor(
    public geolocation:Geolocation,
    public gaoDeLocation: GaoDeLocation,
    public httpclient:HttpserviceService,
    public localstorageService:LocalStorageService,
    public alertController: AlertController,
    public navCtrl: NavController,
    private toastController: ToastController,
    public navParams:NavParams,
    private render: Renderer2,
    public modalController: ModalController) {
      this.type = this.navParams.data.type;
    }
 // public getRecord:any='/activities/class/self?page=1&pageSize=1000&orgCode=';
  public createactiveapi:any="/activities";
  public endactivitiesapi="/activities?activityId=";
  public activity:any={
    activityTypeId:1,		//活动类型，签到为1
    answer:'',			//活动答案：可以是签到顺序，如123456789
    maxscore:3,			//活动分数
    activityDescription:'',//活动描述
    orgCode:this.localstorageService.get('orgCode','xx'), 			//活动对应的班课号
    latitude:'23',
    longitude:'34',
    maxDist:100
  };
  public timer:any;
  public second:any=0;
  public minite:any=0;
  public hour:any=0;
  public type:any=0;
  public getRecord:any='/activities/class/self?page=1&pageSize=1000&orgCode=';
  public getactivityspai:any='/activities?orgCode=';
  public arrivals:any=[];
  public arrSize:any=0;
  ngOnInit() {
    //获取签到列表
    this.httpclient.get(this.getRecord+this.localstorageService.get('orgCode','wrongxx')).then((response)=>{
      console.log(response) 
      for(let activity of response['result']){
        if(activity['activityTypeId']==1){
          this.arrivals.push(activity)
        }
      }
      this.arrSize = this.arrivals.length
    })
    

  }
   

  public arrRecord:any="/activities/records?page=1&pageSize=30&activityId=";
  public getRecordStu:any="/activities/orgParState?activityId="
  public arrActivity:any;
  public curActivity:any;
  public stuCaseListPar:any=[];
  public stuCaseListNoPar:any=[];
  public Nowdate:any;
  //通过id获取参与人情况
  recordetail(id:any,isActive:any,beginDate:any)
  { 
    this.Nowdate=beginDate;
    // console.log( this.Nowdate)
    // console.log(this.getRecordStu+id)
    this.arrActivity=isActive;
    this.curActivity = id
    this.httpclient.get(this.getRecordStu+id).then((response)=>{
      this.stuCaseListPar=[];
      this.stuCaseListNoPar=[];
      if(response['msg']=='查询成功'){
        for(let stu of response['result']){
          if(stu['isPar']=='yes'){
            this.stuCaseListPar.push(stu)
          }else{
            this.stuCaseListNoPar.push(stu)
          }
          
        }
      }
      // console.log(response)
      // console.log(id)
    })
    //查看记录列表
    this.type=1;
  }
  endActivity(type:any){
    //关闭活动
    this.httpclient.put(this.endactivitiesapi+this.curActivity,'').then((response)=>{
      // console.log(response)
      this.arrActivity=false;
    })
    if(type==1)
    {
      clearInterval(this.timer);
      this.httpclient.get(this.getRecord+this.localstorageService.get('orgCode','wrongxx')).then((response)=>{
        // console.log(response) 
        this.arrivals=[]
        for(let activity of response['result']){
          if(activity['activityTypeId']==1){
            this.arrivals.push(activity)
          }
        }
        this.arrSize = this.arrivals.length
      })
      this.type=0
    }
  }
  async godao()
  {
    //关闭签到

    //这里有签到方式
    let toast: any;
      toast = await this.toastController.create({
        duration: 500,
        position: 'middle',
        message: ''
      });
    this.type=2;
    // console.log('签到成功');
    toast.message =  '签到成功';
    toast.present();
    //
    //签到完去哪里

  }
  endarrive()
  {
    //签到id待定
    let acid = this.endactivitiesapi+"xxxx";
    //限时签到
    if(this.arrivetype==1){
      this.httpclient.put(acid,'').then((response)=>{
        // console.log(response)
        //吧签到id保存到本地 到关闭的时候再删除
        this.localstorageService.set('arriveId','waiting');
      })
    }
    //手势签到
    if(this.arrivetype==3){
      this.httpclient.upData(this.createactiveapi,this.activity).then((response)=>{
        // console.log(response)
        //吧签到id保存到本地 到关闭的时候再删除
        this.localstorageService.set('arriveId','waiting');
      })
    }

  }
  //获取当前位置
  public positionRes: PositionRes;
  //发布签到
  async arrive(type:any)
  {
    let toast: any;
      toast = await this.toastController.create({
        duration: 500,
        position: 'middle',
        message: ''
      });
    this.type=3;
    this.arrivetype=type;
    
    this.minite=0;
    this.second=0;
    this.hour=0;
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
        // console.log(e);
        toast.message =  e;
        toast.present();
      }) || null; 
      if(positionRes!=null){
        this.activity['latitude']=positionRes['latitude']
        this.activity['longitude']=positionRes['longitude']
      // alert(JSON.stringify(positionRes))
      }
    //一键签到
    if(this.arrivetype==1){
      //倒计时  
    this.timer = setInterval(()=>{
      this.second++;
      if(this.second==60)
      {
        this.second=0;
        this.minite++;
        if(this.minite==60)
        {
          this.minite=0;
          this.hour++;
        } 
      }
      },1000)  
    this.httpclient.upData(this.createactiveapi,this.activity).then((response)=>{
      // console.log(response)
      this.curActivity=response['result']
      //吧签到id保存到本地 到关闭的时候再删除
      this.localstorageService.set('arriveId',this.curActivity);
    })
    }else if(this.arrivetype==2){//密码签到
     this.type=4
    } else if(this.arrivetype==3){
        // console.log(this.activity)
    //要把手势也带上
        this.httpclient.upData(this.createactiveapi,this.activity).then((response)=>{
          console.log(response)
          this.curActivity=response['result']
          this.type=3
          this.timer = setInterval(()=>{
            this.second++;
            if(this.second==60)
            {
              this.second=0;
              this.minite++;
              if(this.minite==60)
              {
                this.minite=0;
                this.hour++;
              } 
            }
            },1000) 
          //吧签到id保存到本地 到关闭的时候再删除
          this.localstorageService.set('arriveId',this.curActivity);
        }) 
    }
  } 

  dismiss(){
    // console.log(111)
    if(this.type==0)
      this.modalController.dismiss();
    else {
      
      //获取签到列表 
    this.httpclient.get(this.getRecord+this.localstorageService.get('orgCode','wrongxx')).then((response)=>{
      // console.log(response)
      this.arrivals=[]
      for(let arr of response['result']){
         if(arr['activityTypeId']==1){
           this.arrivals.push(arr)
         }
        }
      this.arrSize = this.arrivals.length
      this.type=0;
    })
    }
  }
  onearrive(){
    this.arrivetype=1; 
  }
  
   

 
}
