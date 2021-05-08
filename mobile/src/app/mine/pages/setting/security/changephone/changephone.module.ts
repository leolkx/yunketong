import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ChangephonePageRoutingModule } from './changephone-routing.module';

import { ChangephonePage } from './changephone.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ChangephonePageRoutingModule
  ],
  declarations: [ChangephonePage]
})
export class ChangephonePageModule {}
