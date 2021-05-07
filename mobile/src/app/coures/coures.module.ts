import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CouresPageRoutingModule } from './coures-routing.module';

import { RouterModule } from '@angular/router';
import { CouresPage } from './coures.page';
import {ComponentsModule} from'./components/components.module'  
import { NgxQRCodeModule } from 'ngx-qrcode2';
import { BarcodeScanner } from '@ionic-native/barcode-scanner/ngx';
@NgModule({
  imports: [
    NgxQRCodeModule,
    CommonModule,
    FormsModule,
    IonicModule,
    ComponentsModule,
    RouterModule,
    CouresPageRoutingModule
  ],
  providers:[
    BarcodeScanner
  ],
  declarations: [CouresPage],
  entryComponents:[]
})
export class CouresPageModule {}
