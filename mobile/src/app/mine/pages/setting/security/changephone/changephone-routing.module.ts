import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ChangephonePage } from './changephone.page';

const routes: Routes = [
  {
    path: '',
    component: ChangephonePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ChangephonePageRoutingModule {}
