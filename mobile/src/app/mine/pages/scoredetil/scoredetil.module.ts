import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ScoredetilPageRoutingModule } from './scoredetil-routing.module';

import { ScoredetilPage } from './scoredetil.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ScoredetilPageRoutingModule
  ],
  declarations: [ScoredetilPage]
})
export class ScoredetilPageModule {}
