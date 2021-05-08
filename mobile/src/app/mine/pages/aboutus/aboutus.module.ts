import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AboutusPageRoutingModule } from './aboutus-routing.module';
import { SharedModule } from '../../../shared/shared.module';
import { AboutusPage } from './aboutus.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AboutusPageRoutingModule,
    SharedModule
  ],
  declarations: [AboutusPage]
})
export class AboutusPageModule {}
