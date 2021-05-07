import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { RouterModule } from '@angular/router';
import { IonicModule } from '@ionic/angular';

import { ActivityComponent } from './activity/activity.component';

import { CoursemsgComponent } from './coursemsg/coursemsg.component';
import { StafflistComponent } from './stafflist/stafflist.component';
import { GestureComponent } from './gesture/gesture.component';
import { RecordComponent } from './record/record.component';

import { StumsgComponent } from './stumsg/stumsg.component';
@NgModule({
  declarations: [GestureComponent,StafflistComponent,CoursemsgComponent,ActivityComponent,RecordComponent,StumsgComponent],
  imports: [CommonModule,FormsModule,RouterModule,IonicModule],
  exports:[GestureComponent,StafflistComponent,CoursemsgComponent,ActivityComponent,RecordComponent,StumsgComponent],
  entryComponents:[GestureComponent,RecordComponent,StumsgComponent]
})
export class ComponentsModule { }
