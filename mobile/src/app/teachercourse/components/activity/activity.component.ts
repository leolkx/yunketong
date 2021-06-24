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
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

}
