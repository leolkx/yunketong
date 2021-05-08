import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SetupaccountPageRoutingModule } from './setupaccount-routing.module';

import { SetupaccountPage } from './setupaccount.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SetupaccountPageRoutingModule
  ],
  declarations: [SetupaccountPage]
})
export class SetupaccountPageModule {}
