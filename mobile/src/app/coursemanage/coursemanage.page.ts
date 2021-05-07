import { Component, OnInit } from '@angular/core';

import { LocalStorageService } from '../service/local-storage.service';
@Component({
  selector: 'app-coursemanage',
  templateUrl: './coursemanage.page.html',
  styleUrls: ['./coursemanage.page.scss'],
})
export class CoursemanagePage implements OnInit {

  public show:any='staff';
  public type:any=1;
  constructor(public localStorageService:LocalStorageService) { }
  public orgName = this.localStorageService.get('orgName','班课信息')
  ngOnInit() {
  }
  changetype(type:any)
  {
    this.type=type;
  }

}
