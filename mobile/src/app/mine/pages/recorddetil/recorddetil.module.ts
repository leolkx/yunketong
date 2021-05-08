import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RecorddetilPageRoutingModule } from './recorddetil-routing.module';

import { RecorddetilPage } from './recorddetil.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RecorddetilPageRoutingModule
  ],
  declarations: [RecorddetilPage]
})
export class RecorddetilPageModule {}
