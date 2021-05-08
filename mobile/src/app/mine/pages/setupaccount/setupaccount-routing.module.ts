import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SetupaccountPage } from './setupaccount.page';

const routes: Routes = [
  {
    path: '',
    component: SetupaccountPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SetupaccountPageRoutingModule {}
