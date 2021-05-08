import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { UseragPageRoutingModule } from './userag-routing.module';

import { UseragPage } from './userag.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    UseragPageRoutingModule
  ],
  declarations: [UseragPage]
})
export class UseragPageModule {}
