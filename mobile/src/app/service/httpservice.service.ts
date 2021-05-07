import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders, } from '@angular/common/http';

import { NavController , NavParams } from '@ionic/angular';
import { LocalStorageService } from '../service/local-storage.service'; 
@Injectable({
  providedIn: 'root'
})
export class HttpserviceService {

  public ip:any = 'http://219.229.153.200:8081';//"http://localhost:8080"//"http://47.95.120.250:8081/verification/code";
  constructor(public navCtrl: NavController,public localStorage:LocalStorageService,public http:HttpClient) { }
  public headers:any;
  upData(api:any,data:any){ 
    this.headers= new HttpHeaders({
      'myAuthorization':this.localStorage.get('token',"wrong"),
      'Content-Type':'application/json'})
    return new Promise((resolve, reject) => {
      this.http.post(this.ip+api, data,{withCredentials:true,headers:this.headers}).subscribe((response) => {
        if(response['msg']=='未登录'){
          this.navCtrl.navigateForward('/login');
        }
        resolve(response);
      }, (error) => {
        this.navCtrl.navigateForward('/login');
        reject(error);
      })
    })
  } 
  put(api:any,data:any){
    this.headers= new HttpHeaders({
      'myAuthorization':this.localStorage.get('token',"wrong"),
      'Content-Type':'application/json'})
    console.log(this.ip+api);
    return new Promise((resolve, reject) => {
      this.http.put(this.ip+api,data,{withCredentials:true,headers:this.headers}).subscribe((response) => {
        if(response['msg']=='未登录'){
          this.navCtrl.navigateForward('/login');
        }
        resolve(response);
      }, (error) => {
        this.navCtrl.navigateForward('/login');
        reject(error);
      })
   })
  }
  delete(api:any){
    this.headers= new HttpHeaders({
      'myAuthorization':this.localStorage.get('token',"wrong"),
      'Content-Type':'application/json'})
    return new Promise((resolve, reject) => {
      this.http.delete(this.ip+api,{withCredentials:true,headers:this.headers}).subscribe((response) => {
        if(response['msg']=='未登录'){
          this.navCtrl.navigateForward('/login');
        }
        resolve(response);

      }, (error) => {
        this.navCtrl.navigateForward('/login');
        reject(error);
      })
   })
  }
  get(api:any)
  {  
    this.headers= new HttpHeaders({  
      'myAuthorization':this.localStorage.get('token',"wrong"),
      'Content-Type':'application/json'})
      console.log(this.headers)
    return new Promise((resolve,reject)=>{
      this.http.get(this.ip+api,{withCredentials:true,headers:this.headers}).subscribe((response)=>{
        console.log(1111);
        console.log(response);
        if(response['msg']=='未登录'){
          this.navCtrl.navigateForward('/login');
        }
        console.log('0000');
        resolve(response);
      },(err)=>{
        this.navCtrl.navigateForward('/login');
        reject(err); 
        console.log('big err')
        console.log(JSON.stringify(err))
        console.log('big err------')
      })
    })
  }

  // test()
  // {
  //   var utils = angular.module('Utils', []);
  //   utils.config(['$httpProvider', config]);
  //   function config($httpProvider) {
  //           $httpProvider.defaults.withCredentials = true;
  //   }
  // }

   upDataNotoken(api:any,data:any){ 
    this.headers= new HttpHeaders({ 
      'Content-Type':'application/json'})
    return new Promise((resolve, reject) => {
      this.http.post(this.ip+api, data,{withCredentials:true}).subscribe((response) => {
        if(response['msg']=='未登录'){
          this.navCtrl.navigateForward('/login');
        }
        resolve(response);
      }, (error) => {
        this.navCtrl.navigateForward('/login');
        reject(error);
      })
    })
  } 
  upDatalogin(api:any,data:any){ 
    this.headers= new HttpHeaders({ 
      'Content-Type':'application/json'})
    return new Promise((resolve, reject) => {
      this.http.post(this.ip+api, data).subscribe((response) => {
        if(response['msg']=='未登录'){
          this.navCtrl.navigateForward('/login');
        }
        resolve(response);
      }, (error) => {
        this.navCtrl.navigateForward('/login');
        reject(error);
      })
    })
  } 
  putNotoken(api:any,data:any){
    this.headers= new HttpHeaders({ 
      'Content-Type':'application/json'})
    console.log(this.ip+api);
    return new Promise((resolve, reject) => {
      this.http.put(this.ip+api,data,{withCredentials:true}).subscribe((response) => {
        if(response['msg']=='未登录'){
          this.navCtrl.navigateForward('/login');
        }
        resolve(response);
      }, (error) => {
        this.navCtrl.navigateForward('/login');
        reject(error);
      })
   })
  }
  deleteNotoken(api:any){
    this.headers= new HttpHeaders({ 
      'Content-Type':'application/json'})
    return new Promise((resolve, reject) => {
      this.http.delete(this.ip+api,{withCredentials:true}).subscribe((response) => {
        if(response['msg']=='未登录'){
          this.navCtrl.navigateForward('/login');
        }
        resolve(response);

      }, (error) => {
        this.navCtrl.navigateForward('/login');
        reject(error);
      })
   })
  }
  
  getNotoken(api:any)
  {  
    this.headers= new HttpHeaders({   
      'Content-Type':'application/json'})
      console.log(this.headers)
    return new Promise((resolve,reject)=>{
      this.http.get(this.ip+api,{withCredentials:true}).subscribe((response)=>{
        // console.log(1111);
        console.log(response);
        if(response['msg']=='未登录'){
          this.navCtrl.navigateForward('/login');
        }
        // console.log('0000');
        resolve(response);
      },(err)=>{
        this.navCtrl.navigateForward('/login');
        reject(err); 
        console.log('big err')
        console.log(JSON.stringify(err))
        console.log('big err------')
      })
    })
  }

}
