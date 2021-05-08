import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ChangepwPageRoutingModule } from './changepw-routing.module';

import { ChangepwPage } from './changepw.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ChangepwPageRoutingModule
  ],
  declarations: [ChangepwPage]
})
export class ChangepwPageModule {}
