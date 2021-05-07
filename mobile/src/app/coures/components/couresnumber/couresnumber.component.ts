import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';  
import { PopoverController } from '@ionic/angular';
import { NavController , NavParams } from '@ionic/angular'; 

import { LocalStorageService } from '../../../service/local-storage.service';
import { HttpserviceService } from '../../../service/httpservice.service';
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
  constructor(public localStorageService:LocalStorageService,public modalCtrl:ModalController,public httpservice:HttpserviceService) { }
  public orgCode:any='';
  public coursemsg:any;
  ngOnInit() {
    //通过扫描进来的
    if(this.localStorageService.get('scanText','xxx')!='xxx'){ 
      this.type=2;
      this.orgCode=this.localStorageService.get('scanText','xxx');
      this.localStorageService.remove('scanText')
      this.httpservice.get(this.addcourseapi+this.orgCode).then((response)=>{
        console.log(response)
        if(response['state']=='success')
        {
          this.coursemsg=response['result']['classCloud'];
        }else{
          //后面优化
          alert('没有该课程');
        }
      })
    }
  }
  nextadd(){
    this.httpservice.get(this.addcourseapi+this.orgCode).then((response)=>{
      console.log(response)
      if(response['state']=='success')
      {

        this.coursemsg=response['result']['classCloud'];
        this.type=2;
      }else{
        //后面优化
        alert('没有该课程');
      }
    })
    //通过班课号获取班课信息，展示在页面上
  } 
  addcourse(){
    this.httpservice.upData(this.enterclassapi+this.orgCode,'').then((response)=>{
      console.log(response)
      //alert(JSON.stringify(response))
      if(response['state']=='success')
      {
        alert('成功添加')
         location.reload();
      }else{
        alert(response['msg'].split(':'))[1]
        //后面优化
        //alert('出错');
        
      }
    })
    
  
  }
  concel(){
      this.modalCtrl.dismiss({
      'dismissed': true
    })
    }
} 

