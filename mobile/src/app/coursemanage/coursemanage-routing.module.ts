import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CoursemanagePage } from './coursemanage.page';

const routes: Routes = [
  {
    path: '',
    component: CoursemanagePage,
    children: [
      {
        path: 'staff',
        children: [
          {
            path: '',
            loadChildren: () =>
              import('../mine/mine.module').then(m => m.MinePageModule)
          }
        ]
      },
      {
        path: 'active',
        children: [
          {
            path: '',
            loadChildren: () =>
              import('../coures/coures.module').then(m => m.CouresPageModule)
          }
        ]
      },
      {
        path: 'course',
        children: [
          {
            path: '',
            loadChildren: () =>
              import('../mine/mine.module').then(m => m.MinePageModule)
          }
        ]
      },
      {
        path: '',
        redirectTo: '/coursemanage/staff',
        pathMatch: 'full'
      }
    ]
  },
  {
    path: '',
    component: CoursemanagePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CoursemanagePageRoutingModule {}
