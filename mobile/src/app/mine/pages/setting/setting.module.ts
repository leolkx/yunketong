import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';

import { SettingPageRoutingModule } from './setting-routing.module';
import { SharedModule } from '../../../shared/shared.module';
import { SettingPage } from './setting.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SettingPageRoutingModule,
    SharedModule,
  ],
  declarations: [SettingPage]
})
export class SettingPageModule {}
