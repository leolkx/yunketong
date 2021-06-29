import { Component, OnInit,ChangeDetectionStrategy, ChangeDetectorRef,OnDestroy,Input,Output,EventEmitter} from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

import { AlertController, NavController , NavParams, ToastController } from '@ionic/angular';
import { HttpserviceService } from '../../../service/httpservice.service'; 
import { LocalStorageService } from '../../../service/local-storage.service'; 
//import {Md5} from 'ts-md5';
import { UsermsgserviceService } from '../../../service/usermsgservice.service'; 
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {

  @Output() public outer = new EventEmitter<any>();
 
  public wrong:any=0;
  public timer:any;
  public tab:any = 'tab1';  
  public verimage=this.httpclient.ip+"/verification/code"; 
  public phonecodeapi="/phonecode?phone=";
  public loginapi='/signin'
  public loginbyphoneapi='/signinbyphone'
  public nums:any=1; 
  public verificationCode:any='';
  public user:any={
    username:'',//'b123456',
    password:''//123456',
  }  
  public userPhone:any={
    username:'',//'13290727566',
    verificationCode:''//1234', 
  } 
  public timelimit:any=60;
  public flag:any = true; 
  constructor(private router: Router,public localStorage:LocalStorageService, public ref : ChangeDetectorRef,public usermsg:UsermsgserviceService,public httpclient:HttpserviceService,public navCtrl: NavController,private alertController: AlertController,private toastController: ToastController ) { }

  ngOnInit() {
    console.log(this.verimage)
   
  }
  //不能再onInit里面初始化  因为img是在ngif判断后加入页面的
  ngAfterViewInit(){
    
    /* document.getElementById('vericode').setAttribute('src',this.verimage+'?'+this.nums);
    this.nums++; */
  }


  register(){
    this.outer.emit(2);
  }
  forgetPass()
  {
    this.outer.emit(3);
  }
  
  //登录
  public wrongMsg:any;
  public logUser:any;

  async login(form: NgForm){ 
    let toast: any;
    toast = await this.toastController.create({
      duration: 500,
      position: 'middle',
      message: ''
    });
    
    // console.log(this.tab)
    //验证提交的数据
    this.wrong=0;
    //提交数据
    let signinapi = '';
    if(this.tab=='tab2'){ //手机验证码
      this.logUser=this.userPhone;
      signinapi = this.loginbyphoneapi;
    }
    if(this.tab=='tab1'){ //密码
      this.logUser=this.user;
      signinapi = this.loginapi;
    }
    // this.logUser['verificationCode']=this.verificationCode;
    // console.log(this.logUser)
   // this.user['password'] = Md5.hashStr(this.user["password"]).toString()
    this.httpclient.upDataNotoken(signinapi,this.logUser).then((response)=>{
      //判断返回结果
      // console.log(response)
      if(response['state']=='success')
      {
      // console.log(response);
        //保存token
        this.localStorage.set("token",response['result']['token'])
        //保存userName 需要改
        this.localStorage.set("userName",response['result']['username'])
        //保存userIdionic
       this.localStorage.set("userId",response['result']['id'])
        //对跳转  附带参数
        // console.log(this.localStorage.get("token",'xxx'))
        // this.localStorage.clearAll() 
        // console.log(this.localStorage.get("token",'xxx'))
      //  this.navCtrl.navigateForward('/tabs/coures');
        this.router.navigateByUrl('/tabs/coures')
      }else{
        if(response['msg']=='The user does not exist or the password is wrong'){
          // this.wrongMsg='密码错误'
          toast.message =  '用户不存在或者密码错误';
          toast.present();
        }else{ 
          console.log(response)
          this.wrongMsg=response['msg'].split(':')[1]
          toast.message =  '登录失败';
          toast.present();
        }
        // console.log(response)
        //提示登录信息错误
        this.wrong=1;
      }
    })
    //提交
  }
  // 获取验证码
  // get_check_code() {
    /* var api="http://localhost:8080/test";
    this.httpclient.get(api).then((response)=>{
      console.log(response)
    }) */
/*     console.log('获取验证码'+this.user.phone)
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
      this.httpclient.getNotoken(this.phonecodeapi+this.user['username']).then((response)=>{
         
      
      //let cookie =response.headers['Set-Cookie']
      // console.log(response)
      // console.log(this.user['username'])
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

  newcode()
  {  
    let ranValue = 50 + Math.round(Math.random() * 1000);
    console.log(ranValue)
    document.getElementById('vericode').setAttribute('src',this.verimage+'?'+ranValue);
    //document.getElementById('vericode').src=this.verimage+'?'+this.nums; 
  }
  //清空数据
  clear(){
    this.user.phone='';
    this.user.password='';
    this.user.checkCode='';
    this.timelimit=60;
    this.flag=true;
    clearInterval(this.timer);
  }

}
