import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UsermsgserviceService {

  private account: any = '';
  private orgcode: any = '';
  private modify: any = '';
  private userid: any = '';
  private orgCode: any = '';
  setorgCode(orgCode: any) {
    this.orgCode = orgCode;
  }
  getorgCode() {
    return this.orgCode;
  }
  setaccount(account: any) {
    this.account = account;
  }
  setmod(is: any) {
    this.modify = is;
  }
  getmod() {
    return this.modify;
  }
  setorg(org: any) {
    this.orgcode = org;
  }
  getorg() {
    return this.orgcode;
  }
  getaccount() {
    return this.account;
  }
  setuId(id: any) {
    this.userid = id;
  }
  getuId() {
    return this.userid;
  }
  constructor() { }
}
