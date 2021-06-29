import { Component, OnInit,ChangeDetectionStrategy, ChangeDetectorRef,OnDestroy,Input,Output,EventEmitter} from '@angular/core';

import {HttpClient} from '@angular/common/http';
import { HttpserviceService } from '../../../service/httpservice.service';
import { ToastController } from '@ionic/angular';
@Component({
  selector: 'app-get-pass',
  templateUrl: './get-pass.component.html',
  styleUrls: ['./get-pass.component.scss'],
})
export class GetPassComponent implements OnInit {

  @Output() public outer = new EventEmitter<any>();
 
  public timer:any;
  public tab = 'tab1';  
  public phonecodeapi="/phonecode?phone=";
  public passwordapi="/user/password";
  public user:any={
    password:'',
    phone:'',
    verificationCode:''
  }  
  public type:any=1;
  public timelimit:any=60;
  public flag:any = true;
/*   constructor(public ref : ChangeDetectorRef,public http:HttpClient) { } */
  constructor(public ref : ChangeDetectorRef,public httpclient:HttpserviceService, private toastController: ToastController) { }

  ngOnInit() {
  }

  login(){ 
    this.outer.emit(1);
  }

  register(){
    this.outer.emit(2);
  }
  forgetPass()
  {
    this.outer.emit(3);
  }

  //登录
  newpass(){ 
    this.type=2;
    //提交
  }

  // 点击确认修改按钮时调用
  async changepass(){
    let toast: any;
    toast = await this.toastController.create({
      duration: 500,
      position: 'middle',
      message: ''
    });
    
    //提交数据
    // console.log(this.user) 
    this.httpclient.upDataNotoken(this.passwordapi,this.user).then((response)=>{
      console.log(response)
      if(response['msg']=='修改用户密码成功'){
        // alert('修改成功')
        toast.message =  '修改成功';
        toast.present();
        this.outer.emit(1);
      }else{
        toast.message =  response['msg'].split(':')[1];
        toast.present();
      }
      // console.log(response)
    })
  }

/*   // 获取验证码
  get_check_code() {
    var api="http://localhost:8080/test";
    this.http.get(api).subscribe(response=>{
      console.log(response)
    })
    console.log('获取验证码'+this.user.phone)
    //倒计时
    this.flag=false;
    this.timer = setInterval(()=>{
      this.timelimit--;
      if(this.timelimit==0)
      {
        this.timelimit=60;
        this.flag=true;
        clearInterval(this.timer);
      }
      },1000)
    // this.dataService.getCaptchas().subscribe(res => {
    //   this.captchaCodeImg = res.code;
    // });
  } */

    // 获取验证码
    get_check_code() {
      // console.log(this.user['phone'])
      this.httpclient.getNotoken(this.phonecodeapi+this.user['phone']).then((response)=>{
         
      
      //let cookie =response.headers['Set-Cookie']
      // console.log(response)
      })
      
      //倒计时
      this.flag=false;
      this.timer = setInterval(()=>{
        this.timelimit--;
        if(this.timelimit==0)
        {
          this.timelimit=60;
          this.flag=true;
          clearInterval(this.timer);
        }
        },1000)
  
        // let c = setTimeout(function(){
  
        //   console.log(2000)
       
        //   },2000)
      // this.dataService.getCaptchas().subscribe(res => {
      //   this.captchaCodeImg = res.code;
      // });
    }

  //清空数据
  clear(){
    this.user.phone='';
    this.user.password='';
    this.user.verificationCode='';
    this.timelimit=60;
    this.flag=true;
    clearInterval(this.timer);
  }

}
