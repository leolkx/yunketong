import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ChangepwPage } from './changepw.page';

const routes: Routes = [
  {
    path: '',
    component: ChangepwPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ChangepwPageRoutingModule {}
