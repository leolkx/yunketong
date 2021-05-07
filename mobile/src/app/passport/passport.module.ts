import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';
import { RouterModule } from '@angular/router';
import { PassportPageRoutingModule } from './passport-routing.module'; 
import { PassportPage } from './passport.page';
import { SharedModule } from '../shared/shared.module';

//导入自定义模块
//import { LoginModule } from './components/login/login.module'
import { ComponentsModule } from './components/components.module';
@NgModule({
  imports: [
    SharedModule,
    CommonModule,
    FormsModule,
    IonicModule,
    ComponentsModule,
    PassportPageRoutingModule
  ],
  declarations: [PassportPage]
})
export class PassportPageModule {}
