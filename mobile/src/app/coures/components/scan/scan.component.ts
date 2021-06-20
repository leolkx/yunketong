import { Component, OnInit } from '@angular/core';
import { BarcodeScanner } from '@ionic-native/barcode-scanner/ngx';
import { ModalController } from '@ionic/angular'; 
import { PopoverController } from '@ionic/angular';

import { LocalStorageService } from '../../../service/local-storage.service';
@Component({
  selector: 'app-scan',
  templateUrl: './scan.component.html',
  styleUrls: ['./scan.component.scss'],
})
export class ScanComponent implements OnInit {

  constructor(public modalController: ModalController,
    public popoverController: PopoverController,
    public barcodeScanner: BarcodeScanner,
    public localStorageService:LocalStorageService) {

  }
  
  public Code:any;
  ngOnInit() { 
      this.barcodeScanner.scan().then(async barcodeData => {
      // alert(barcodeData['text'])
      this.localStorageService.get('scanText',barcodeData['text']);
      this.localStorageService.get('scanId',this.localStorageService.get('scanText',barcodeData['text']))
  })
  }
}
