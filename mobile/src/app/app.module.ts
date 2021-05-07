import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {HttpClientModule} from '@angular/common/http'; 
import { FileTransfer, FileTransferObject, FileUploadOptions } from '@ionic-native/file-transfer/ngx';
import { File } from '@ionic-native/file';
import {Geolocation} from "@ionic-native/geolocation/ngx"
import { GaoDeLocation,PositionOptions,LocationProtocolEnum,LocationModeEnum,DesiredAccuracyEnum,PositionRes } from '@ionic-native/gao-de-location/ngx';
import { BarcodeScanner } from '@ionic-native/barcode-scanner/ngx';
import { ElementRef, ViewChild, Renderer2 } from '@angular/core';
import { NgxQRCodeModule } from 'ngx-qrcode2';

// import{GesturePageModule} from '../app/gesture/gesture.module' 
//import {GaoDe} from "gaodelocation-chenyu"
//引入组件
// import { TestComponent } from './components/test/test.component';
// import { LoginComponent } from './login/components/login/login.component';
// import { ExploreContainerComponent } from './explore-container/explore-container.component';
@NgModule({
  declarations: [AppComponent],
  entryComponents: [],
  exports:[],
  imports: [BrowserModule, IonicModule.forRoot(), AppRoutingModule,FormsModule,HttpClientModule],
  providers: [ 
    NgxQRCodeModule, 
    GaoDeLocation,
    FileTransfer, 
    StatusBar,
    SplashScreen, 
    Geolocation,
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
