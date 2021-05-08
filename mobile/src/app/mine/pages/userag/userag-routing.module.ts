import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UseragPage } from './userag.page';

const routes: Routes = [
  {
    path: '',
    component: UseragPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UseragPageRoutingModule {}
