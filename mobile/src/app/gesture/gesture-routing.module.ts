import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { GesturePage } from './gesture.page';

const routes: Routes = [
  {
    path: '',
    component: GesturePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class GesturePageRoutingModule {}
