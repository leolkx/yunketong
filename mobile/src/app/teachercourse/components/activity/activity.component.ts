import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular'; 
import { PopoverController } from '@ionic/angular';
import { AddactiveComponent } from '../addactive/addactive.component';
import { ActivedetailComponent } from '../activedetail/activedetail.component';
import {HttpClient} from '@angular/common/http'; 
import { NavController , NavParams } from '@ionic/angular';

import { HttpserviceService } from '../../../service/httpservice.service'; 
import { LocalStorageService } from '../../../service/local-storage.service'; 
@Component({
  selector: 'app-activity',
  templateUrl: './activity.component.html',
  styleUrls: ['./activity.component.scss'],
})
export class ActivityComponent implements OnInit {

  constructor(public modalController: ModalController,
    public popoverController: PopoverController,
    public navCtrl: NavController,
    public httpserviceService:HttpserviceService,
    public localStorageService:LocalStorageService ) {

  }
  public edit:any=0;
  public activity:any={
    antivityName:'',
    activityTypeId:2,		//活动类型，签到为1 作业为2 
    maxscore:5,			//活动分数
    activityDescription:'',//活动描述
    orgCode:this.localStorageService.get('orgCode','xxx'), 			//活动对应的班课号 
    endDate:''
  }
  public createactivityapi:any='/activities'
  public arr:Array<any>=['11','22']
  public modalData:any;
  public type:any=0;
  public getRecord:any='/activities/class/self?page=1&pageSize=1000&orgCode=';
  public getActivityapi:any='/activities/recordsByActivityId?activityId=';
   public arrivals:any=[];
  public arrSize:any=0;
  ngOnInit() {
    //插在前面
    this.arr.unshift('33')
    console.log(this.arr)
     //获取签到列表
     this.httpserviceService.get(this.getRecord+this.localStorageService.get('orgCode','wrongxx')).then((response)=>{
      console.log(response)
      for(let activity of response['result']){
        if(activity['activityTypeId']==2){
          this.arrivals.push(activity)
        }
      }
      this.arrSize = this.arrivals.length
    })
  }

  editActivity(type:any){
    this.edit=type;
    if(type==1){

    }else if(type==0){//提交更新

    }


  }
  async addactive() {
    const modal = await this.modalController.create({
      component: AddactiveComponent
    });
    
    await modal.present();
    this.modalData = await modal.onDidDismiss();
    this.httpserviceService.get(this.getRecord+this.localStorageService.get('orgCode','wrongxx')).then((response)=>{
      this.arrivals=[]
      console.log(response)
      for(let activity of response['result']){
        if(activity['activityTypeId']==2){
          this.arrivals.push(activity)
        }
      }
      this.arrSize = this.arrivals.length
    })
    console.log(this.modalData)
  } 

  async activedetail(event:any) {
    const modal = await this.modalController.create({
      component: ActivedetailComponent
    }); 
    await modal.present();
    // this.modalData = await modal.onDidDismiss();
    // console.log(this.modalData)
  } 

  update(even:any){ 
    //通过类型去拿数据   课程号
    console.log(even)
  }
  delect(del:any){
    console.log(11)
  }

  public arrRecord:any="/activities/records?page=1&pageSize=30&activityId=";
  public getRecordStu:any="/activities/orgParState?activityId="
  public gettheActivityapi:any='/activities/recordsByActivityId?activityId='  
  public getactivyScoreapi:any='/activities/recordsByActivityIdAndUserId?'
  public arrActivity:any;
  public curActivity:any;
  public curActive:any;
  public stuCaseListPar:any=[];
  public stuCaseListNoPar:any=[];
  public Nowdate:any;
  public activityMsg:any;
  public answer:any='';

  activitydetail(activityId:any,isActive:any){
    this.type=1
    this.curActivity = activityId;
    this.curActive=isActive;
    this.fenshu['activityId']=activityId
    this.httpserviceService.get(this.getActivityapi+activityId).then((response)=>{
      console.log(response)
      this.activityMsg=response['result']
      this.activityMsg['activityId']=activityId
    
    })
    
    this.httpserviceService.get(this.getRecordStu+activityId).then((response)=>{
      this.stuCaseListPar=[];
      this.stuCaseListNoPar=[];
      if(response['msg']=='查询成功'){
        for(let stu of response['result']){
          if(stu['isPar']=='yes'){
            this.httpserviceService.get(this.getactivyScoreapi+"activityId="+activityId+'&userId='+stu['userId']).then((respnse)=>{
              console.log('有待分数？？？')
              console.log(respnse)
              if(respnse['result']!=undefined){
               stu['score']=respnse['result']['score']
              }  
               this.stuCaseListPar.push(stu)
               console.log(stu)
            })
          }else{
            this.stuCaseListNoPar.push(stu)
          }
          
        }
      }
      console.log(response)
      console.log(activityId)
    })

  }

  public dafenapi:any='/activities/records/modify'
  public fenshu:any={
    id:0,
    activityId:0,
    activityTypeId:2,
    score:0
  }

  seeAnswer(userId:any,activityId:any,parId:any,score:any){  

    //为了进去打分的时候 分数保持原来的数据
    this.fenshu['score']=score;
    this.type=2
    this.httpserviceService.get(this.getactivyScoreapi+"activityId="+activityId+'&userId='+userId).then((respnse)=>{
      console.log('有待分数？？？')
      console.log(respnse)
      if(respnse['result']!=undefined){
       this.answer=JSON.parse(respnse['result']['submitParam'])['answer']
       this.fenshu['id']=respnse['result']['id']
      }  
    })
  }

  newscore(){ 
    console.log(this.fenshu)
    this.httpserviceService.put(this.dafenapi,this.fenshu).then((response)=>{
      console.log(response)
      this.activitydetail(this.curActivity,this.curActive)
    })
  }
}
