import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { RouterModule } from '@angular/router';
import { IonicModule } from '@ionic/angular';

import { ActivityComponent } from './activity/activity.component';

import { CoursemsgComponent } from './coursemsg/coursemsg.component';
import { StafflistComponent } from './stafflist/stafflist.component';

import { RecordComponent } from './record/record.component';

import { StumsgComponent } from './stumsg/stumsg.component';

import { AddactiveComponent } from './addactive/addactive.component';
import { GestureComponent } from './gesture/gesture.component';
import { FileChooser } from '@ionic-native/file-chooser/ngx';
import { ActivedetailComponent } from './activedetail/activedetail.component'; 
@NgModule({
  declarations: [GestureComponent,StafflistComponent,CoursemsgComponent,ActivityComponent,RecordComponent,ActivedetailComponent,StumsgComponent,AddactiveComponent],
  imports: [CommonModule,FormsModule,RouterModule,IonicModule],
  providers:[FileChooser],
  exports:[GestureComponent,StafflistComponent,CoursemsgComponent,ActivityComponent,RecordComponent,ActivedetailComponent,StumsgComponent,AddactiveComponent],
  entryComponents:[GestureComponent,RecordComponent,StumsgComponent,AddactiveComponent,ActivedetailComponent]
})
export class ComponentsModule { }
