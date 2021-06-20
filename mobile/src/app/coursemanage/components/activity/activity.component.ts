import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular'; 
import { PopoverController } from '@ionic/angular';

import { HttpserviceService } from '../../../service/httpservice.service';   
import {Geolocation} from "@ionic-native/geolocation/ngx";  
import { GaoDeLocation,PositionOptions,LocationProtocolEnum,LocationModeEnum,DesiredAccuracyEnum,PositionRes } from '@ionic-native/gao-de-location/ngx';

import { LocalStorageService } from '../../../service/local-storage.service';  
@Component({
  selector: 'app-activity',
  templateUrl: './activity.component.html',
  styleUrls: ['./activity.component.scss'],
})
export class ActivityComponent implements OnInit {

  constructor(
    public localstorage:LocalStorageService,
    public geolocation:Geolocation,
    public gaoDeLocation: GaoDeLocation, 
    public httpservice:HttpserviceService,
    public modalController: ModalController,
    public popoverController: PopoverController ) {}

    public activitySize:any=0;
    public type:any=0;
    public getActivityapi:any='/activities/class/self?page=1&pageSize=1000&orgCode=';
    //public getRecordapi:any='/activities?orgCode='; 
    public arrList:any=[]
    public tmpScore:any=0;
    ngOnInit() {
      this.httpservice.get(this.getActivityapi+this.localstorage.get('orgCode','xxx')).then((response)=>{
        // console.log("有返回分数嘛？")
        // console.log(response)
        if(response['msg']=='查询成功')
        {
          for (let activity of response['result']) { 
            //2 表示作业
            if(activity['activityTypeId']==2){
  
              if(activity['userId']==undefined)
              {
                //没参加
                activity['type']=0
                activity['score']=0
              }else{
                this.httpservice.get(this.getactivyScoreapi+"activityId="+activity['activityId']+'&userId='+activity['userId']).then((respnse)=>{
                  activity['score'] =  respnse['result']['score'] 
                })
                //参加
                activity['type']=1
              }
              this.arrList.push(activity)
            }
          }
          this.activitySize = this.arrList.length
        }
      })
    }

    public activityMsg:any={
      activityDescription: "",
      activityName: "",
      answer: '' ,
      activityId:0,
      maxscore:0
    };
    public activity:any={
      antivityId:'',
      answer:''
    }
    public jiaozuoyeapi:any='/activities/records';
    public getactivyScoreapi:any='/activities/recordsByActivityIdAndUserId?'
    public gettheActivityapi:any='/activities/recordsByActivityId?activityId=';
    goanswer(){
      this.httpservice.upData(this.jiaozuoyeapi,this.activity).then((response)=>{
        console.log(response)
        if(response['msg']=='参与成功'){
          this.httpservice.get(this.getActivityapi+this.localstorage.get('orgCode','xxx')).then((response)=>{
            this.arrList=[]
            console.log(response)
            if(response['msg']=='查询成功')
            {
               
              for (let activity of response['result']) {
                //2 表示作业
                if(activity['activityTypeId']==2){
      
                  if(activity['userId']==undefined)
                  {
                    //没参加
                    activity['type']=0
                  }else{
                    this.httpservice.get(this.getactivyScoreapi+"activityId="+activity['activityId']+'&userId='+activity['userId']).then((respnse)=>{
                      activity['score'] =  respnse['result']['score'] 
                    })
                    //参加
                    activity['type']=1
                  }
                  this.arrList.push(activity)
                }
              }
            }
            
            this.activitySize = this.arrList.length
          })
          this.type=0;
        }
        else{
          alert('已经提交过，不可重复提交')
        }
      })
      console.log(this.activityMsg)
      console.log('提交成功')

    }

    //提交作业
    upanswer(activityId:any){ 
      //获取作业内容
    //获取作业内容
    this.httpservice.get(this.gettheActivityapi+activityId).then((response)=>{ 
      
      this.httpservice.get(this.getactivyScoreapi+"activityId="+activityId+'&userId='+this.localstorage.get('userId','xxx')).then((respnse)=>{
        console.log('有待分数？？？')
        console.log(respnse)
        if(respnse['result']!=undefined){
          this.activity['answer']=JSON.parse(respnse['result']['submitParam'])['answer']
        }  
      })
      this.activityMsg=response['result']
      this.activityMsg['activityId']=activityId
      this.activity['activityId']=activityId
      console.log(this.activityMsg)
      this.type=1;
    })

    }
}
