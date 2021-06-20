import { Component, OnInit,ChangeDetectionStrategy, ChangeDetectorRef,OnDestroy,Input,Output,EventEmitter} from '@angular/core';
import { ToastController } from '@ionic/angular';
import { HttpserviceService } from '../../../service/httpservice.service';
//import {Md5} from 'ts-md5';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {

  @Output() public outer = new EventEmitter<any>();
 
  public verimage=this.httpclient.ip+"/verification/code";
  public phonecodeapi="/phonecode?phone=";
  public signupapi="/signup";
  public timer:any;
  public tab = 'tab1';  
  public mail:any='';
  public user:any={
    username:'',
    password:'',
    email:'',
    phone:'',
    mailVerificationCode:'',
    verificationCode:''
  }  
  public timelimit:any=60;
  public flag:any = true;
  public nums:any=5; 
  constructor(public ref : ChangeDetectorRef,public httpclient:HttpserviceService, private toastController: ToastController) { }

  ngOnInit() {
    console.log(this.verimage)
    /* document.getElementById('vericode').setAttribute('src',this.verimage+'?'+this.nums);
    this.nums++; */
  }

  quickregister()
  {
    this.outer.emit(5);
  }
  
  /* 更新图片验证码 */
  newcode()
  {  
    document.getElementById('vericode').setAttribute('src',this.verimage+'?'+this.nums);
    this.nums++;
  }
  usermsg()
  {
    //提交数据并返回

    //跳转到填写信息页面
    this.outer.emit(4);
  }
  dismiss(){ 
    this.outer.emit(1);
  }
  async register(){
    let toast: any;
    toast = await this.toastController.create({
      duration: 500,
      position: 'middle',
      message: ''
    });
    //提交数据 注册
    //加密用户数据
   // this.user['password'] = Md5.hashStr(this.user["password"]).toString()
    console.log(this.user) 
    this.httpclient.upDataNotoken(this.signupapi,this.user).then((response)=>{
      if(response['msg']=='register success'){
        // alert('注册成功')
        toast.message =  '注册成功';
        toast.present();
        this.outer.emit(1);
      }else{
        // alert(response['msg'].split(':')[1])
        toast.message =  response['msg'].split(':')[1];
        toast.present();
      }
      console.log(response)
      //注册完去登录  这里没提示 后面再优化体验
      //判断成功才可以跳去登录
    })
  }
  
  forgetPass()
  {
    this.outer.emit(3);
  }
  //登录
  login(type:any){
    if(type==1)
    {
      this.user.type=1;
    }else{
      this.user.type=2;
    }
    console.log(this.user)
  }
  // 获取验证码
  get_check_code() {
    this.httpclient.getNotoken(this.phonecodeapi+this.user['phone']).then((response)=>{

    //let cookie =response.headers['Set-Cookie']
    console.log(response)
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
}
