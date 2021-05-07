import { Component, OnInit } from '@angular/core';
import { ModalController, NavParams } from '@ionic/angular';
import { FileChooser } from '@ionic-native/file-chooser/ngx';

import { HttpserviceService } from '../../../service/httpservice.service'; 
import { LocalStorageService } from '../../../service/local-storage.service'; 
@Component({
  selector: 'app-addactive',
  templateUrl: './addactive.component.html',
  styleUrls: ['./addactive.component.scss'],
})
export class AddactiveComponent implements OnInit {

  public type:any='作业';
  public activity:any={
    antivityName:'',
    activityTypeId:2,		//活动类型，签到为1 作业为2 
    maxscore:5,			//活动分数
    activityDescription:'',//活动描述
    orgCode:this.localStorageService.get('orgCode','xxx'), 			//活动对应的班课号 
    endDate:''
  }
  
  public createactivityapi:any='/activities';
  constructor(private fileChooser: FileChooser,
    public modalController: ModalController,
    public navParams: NavParams,
    public httpserviceService:HttpserviceService,
    public localStorageService:LocalStorageService
    ) {
    //this.navParams 通过这个获取父页面的传值
    // console.log(this.navParams);   
}

upfile()
{
  // this.fileChooser.open().then(uri => {
  // 	// uri 文件的路径
  // 	console.log(uri)
  // }).catch(e => console.log(e));
  this.fileChooser.open().then(uri => {
    // uri 文件的路径

// 你会发现通过此插件，选择图片文件跟选择其他文件(.doc/.xlsx/.docx/.ppt ...)，获得的uri是有区别的
// 图片文件路径：content://media/....
// 其他文件路径：file:///storage/....

    // 因此将图片文件转换成实际路径，或者叫绝对路径
    (<any>window).FilePath.resolveNativePath(uri, (result) => {
      // this.util.tip(result);
      // // 调用文件上传(此方法需要自行编写)
      // this.uploadAttachment(result);
    });
  })
  .catch(e => console.log(e)); 
}

formatDate = ( time: any ) => {
  // 格式化日期，获取今天的日期
  const Dates = new Date( time );
  const year: number = Dates.getFullYear();
  const month: any = ( Dates.getMonth() + 1 ) < 10 ? '0' + ( Dates.getMonth() + 1 ) : ( Dates.getMonth() + 1 );
  const day: any = Dates.getDate() < 10 ? '0' + Dates.getDate() : Dates.getDate();
  return year + '-' + month + '-' + day + ' ' + Dates.getHours() + ':' + Dates.getMinutes() + ':' + Dates.getSeconds();
};
  ngOnInit() {}
  creatactive(type:any){

    
    //创建
    if(type==1)
    {
      this.activity['endDate'] = this.formatDate(this.activity['endDate'])
      console.log(this.activity) 
        this.httpserviceService.upData(this.createactivityapi,this.activity).then((response)=>{
          console.log(response)
          this.modalController.dismiss({
            result:'ok'
          });
        })
    }else{
      //取消
      //如果懒加载 这里可以提前获取新的列表  或者把数据返回回去插入
    //提交成功就返回
    this.modalController.dismiss({
      result:'ok'
    });
    }
    //把数据提交

    
  }

}
