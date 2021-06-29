import { Component, OnInit } from '@angular/core';
import { ModalController, ToastController } from '@ionic/angular'; 
import { PopoverController } from '@ionic/angular';
import { HttpserviceService } from '../../../service/httpservice.service';  
import {Geolocation} from "@ionic-native/geolocation/ngx";  
import { GaoDeLocation,PositionOptions,LocationProtocolEnum,LocationModeEnum,DesiredAccuracyEnum,PositionRes } from '@ionic-native/gao-de-location/ngx';

import { LocalStorageService } from '../../../service/local-storage.service';  
import { StumsgComponent } from '../stumsg/stumsg.component';
@Component({
  selector: 'app-record',
  templateUrl: './record.component.html',
  styleUrls: ['./record.component.scss'],
})
export class RecordComponent implements OnInit {

  constructor(
    public localstorage:LocalStorageService,
    public geolocation:Geolocation,
    public gaoDeLocation: GaoDeLocation, 
    public httpservice:HttpserviceService,
    public modalController: ModalController,
    private toastController: ToastController,
    public popoverController: PopoverController ) {}

  public getRecord:any='/activities/class/self?page=1&pageSize=1000&orgCode=';
  public getRecordapi:any='/activities?orgCode=';
  public dao:any=0;
  public arrList:any=[]
  public isAnswerArrive:any=0;
  async ngOnInit() {
    let toast: any;
      toast = await this.toastController.create({
        duration: 500,
        position: 'middle',
        message: ''
      });
    this.httpservice.get(this.getRecord+this.localstorage.get('orgCode','xxx')).then((response)=>{
      // console.log(response)
      if(response['msg']=='查询成功')
      {
        for (let activity of response['result']) {
          //签到才添加
          if(activity['activityTypeId']==1){

            if(activity['userId']==undefined)
            {
              //没参加
              activity['type']=0
            }else{
              //参加
              activity['type']=1
            }
            this.arrList.push(activity)
          }
        }
      }else{
        toast.message =  '查询失败';
        toast.present();
      } 
    })
  }
  dodao()
  {
    //判断心啊子啊
    this.dao=1;
  }
  public enterRecord:any='/activities/records'
  public enterActivity:any={
    activityId:0, //活动id
    answer:'',   //提交的答案
    latitude:"24.571302",
    longitude:"117.673102"
  }
 
  //获取当前位置
  public positionRes: PositionRes; 
async godao()
  {
    let toast: any;
      toast = await this.toastController.create({
        duration: 2000,
        position: 'middle',
        message: ''
      });
    
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
      this.enterActivity['latitude']=positionRes['latitude']
      this.enterActivity['longitude']=positionRes['longitude']
      // console.log('longitude'+positionRes['longitude'])
      // alert('longitude'+positionRes['longitude'])
      toast.message = 'longitude'+positionRes['longitude']
      toast.present(); 
      // toast.message =  'latitude'+positionRes['latitude']+'longitude'+positionRes['longitude'];
      if(positionRes['latitude']==null){
        toast.message =  '未获取到地址，请稍后重试'
      }
      else{
        toast.message = '定位成功'
        toast.present();
        //这里有签到方式 
      // console.log(this.getRecord+this.localstorage.get('orgCode','xxx'))
      this.httpservice.get(this.getRecord+this.localstorage.get('orgCode','xxx')).then((response)=>{
        let mark:any=0;
        console.log(response)
        for (let activity of response['result']) {
          // console.log(activity); // "0", "1", "2",
          //判断是否有签到
          if(activity['activityTypeId']=='1'&&activity['isActive']==1)
          {
          // alert('有正在进行中的签到') 
            //一键签到 则直接签到
            if(activity['answerLength']==0){
              
              this.enterActivity['activityId']=activity['activityId']
              // console.log(activity)
              //先用
              this.httpservice.upData(this.enterRecord,this.enterActivity).then((response)=>{
                if(response['msg']=='参与成功'){
                  // alert('已成功签到')
                  toast.message =  '已成功签到';
                  toast.present();
                }else if(response['msg']=='距离活动源距离超出限制')//这里内容可能错
                {
                  // alert('超出距离，不能签到')
                  toast.message =  '超出距离，不能签到';
                  toast.present();
                }else if(response['msg']=='已经参加过该活动'){
                  // alert('已经成功签到')
                  toast.message =  '已经参加过该活动';
                  toast.present();
                  //更新签到列表
                }else if(response['msg']=='已经结束的活动'){
                  // alert('已经成功签到')
                  toast.message =  '限时签到已结束';
                  toast.present();
                  //更新签到列表
                }else{
                  toast.message =  '签到失败';
                  toast.present();
                } 
              })
              //跟新列表
              this.httpservice.get(this.getRecord+this.localstorage.get('orgCode','xxx')).then((response)=>{
                // console.log(response)
                this.arrList=[];
                if(response['msg']=='查询成功')
                { 
                  for (let activity of response['result']) {
                    //签到才添加
                    if(activity['activityTypeId']==1){
          
                      if(activity['userId']==undefined)
                      {
                        //没参加
                        activity['type']=0
                      }else{
                        //参加
                        activity['type']=1
                      }
                      this.arrList.push(activity)
                    }
                  }
                } 
              })

            }else{
              //密码签到
              this.isAnswerArrive=1;
              this.enterActivity['activityId']=activity['activityId']
              // console.log(activity) 
            }
            
            mark=1
            break;
          } 
        }
          if(mark==0){
            // alert('目前没有签到')
            toast.message =  '目前没有签到';
            toast.present(); 
          }
      //  console.log(this.arrList)
      })
      }
    // alert(JSON.stringify(positionRes))
    }else{
      // alert('未获取到地址')
      toast.message =  '未获取到地址，请稍后重试';
      toast.present();
    }
      
  }

    async answerArrive(){
    let toast: any;
        toast = await this.toastController.create({
          duration: 500,
          position: 'middle',
          message: ''
        });
    this.httpservice.upData(this.enterRecord,this.enterActivity).then((response)=>{
    //  alert(JSON.stringify(response))
      if(response['msg']=='参与成功'){
        // alert('签到成功')
        toast.message =  '签到成功';
        toast.present(); 
        //跟新列表
        this.httpservice.get(this.getRecord+this.localstorage.get('orgCode','xxx')).then((response)=>{
          // console.log(response)
          this.arrList=[];
          if(response['msg']=='查询成功')
          {
            
            for (let activity of response['result']) {
              //签到才添加
              if(activity['activityTypeId']==1){
    
                if(activity['userId']==undefined)
                {
                  //没参加
                  activity['type']=0
                }else{
                  //参加
                  activity['type']=1
                }
                this.arrList.push(activity)
              }
            }
          } 
        })
      }else if(response['msg']=='距离活动源距离超出限制')//这里内容可能错
      {
        // alert('超出距离，不能签到')
        toast.message =  '超出距离，不能签到';
        toast.present();
      }else if(response['msg']=='已经参加过该活动'){
        // alert('已经参加过签到')
        toast.message =  '已经参加过签到';
        toast.present();
        //更新签到列表
      } else if(response['msg']=='答案错误'){
        // alert('签到密码错误')
        toast.message =  '签到密码错误';
        toast.present();
      }
      
      this.isAnswerArrive=0
      this.enterActivity['answer']='';
    })
  }
  dismiss(){
    // console.log(111)
    this.modalController.dismiss();
    // if(this.type==0)
    //   this.modalController.dismiss();
    // else {
      
    //   //获取签到列表
    // this.httpclient.get(this.getactivityspai+this.localstorageService.get('orgCode','wrongxx')).then((response)=>{
    //   console.log(response)
    //   this.arrivals=response['result']
    //   this.arrSize = this.arrivals.length
    //   this.type=0;
    // })
    // }
  }
}
