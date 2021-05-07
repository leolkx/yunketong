import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { RouterModule } from '@angular/router';
import { IonicModule } from '@ionic/angular';

import { AddcouresComponent } from './addcoures/addcoures.component';
import { CouresnumberComponent } from './couresnumber/couresnumber.component';
import { CreadcourseComponent } from './creadcourse/creadcourse.component';
import { GetschoolComponent } from './getschool/getschool.component';
import { ScanComponent } from './scan/scan.component';

import { NgxQRCodeModule } from 'ngx-qrcode2';
import { BarcodeScanner } from '@ionic-native/barcode-scanner/ngx';
@NgModule({
  declarations: [AddcouresComponent,CouresnumberComponent,ScanComponent,CreadcourseComponent,GetschoolComponent],
  imports: [NgxQRCodeModule,CommonModule,FormsModule,RouterModule,IonicModule],
  providers:[BarcodeScanner],
  exports:[ScanComponent,AddcouresComponent,CouresnumberComponent,CreadcourseComponent,GetschoolComponent],
  entryComponents:[ScanComponent,AddcouresComponent,CouresnumberComponent,CreadcourseComponent,GetschoolComponent]
})
export class ComponentsModule { }
