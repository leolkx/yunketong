import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { TeachercoursePageRoutingModule } from './teachercourse-routing.module';

import { TeachercoursePage } from './teachercourse.page';
import { ComponentsModule } from './components/components.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ComponentsModule,
    TeachercoursePageRoutingModule
  ],
  declarations: [TeachercoursePage]
})
export class TeachercoursePageModule {}
