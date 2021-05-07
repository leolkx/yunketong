import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TeachercoursePage } from './teachercourse.page';

const routes: Routes = [
  {
    path: '',
    component: TeachercoursePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TeachercoursePageRoutingModule {}
