import { Component, OnInit } from '@angular/core';
import { ModalController, ToastController } from '@ionic/angular'; 
import { PopoverController } from '@ionic/angular';

import { HttpserviceService } from '../../../service/httpservice.service';   
import {Geolocation} from "@ionic-native/geolocation/ngx";  
import { GaoDeLocation,PositionOptions,LocationProtocolEnum,LocationModeEnum,DesiredAccuracyEnum,PositionRes } from '@ionic-native/gao-de-location/ngx';

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
