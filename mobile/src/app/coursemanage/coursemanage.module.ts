import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { CoursemanagePageRoutingModule } from './coursemanage-routing.module';

import { CoursemanagePage } from './coursemanage.page';

import { ComponentsModule } from './components/components.module';
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ComponentsModule,
    CoursemanagePageRoutingModule
  ],
  declarations: [CoursemanagePage],
  entryComponents:[CoursemanagePage]
})
export class CoursemanagePageModule {}
