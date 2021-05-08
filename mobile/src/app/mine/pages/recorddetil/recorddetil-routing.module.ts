import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RecorddetilPage } from './recorddetil.page';

const routes: Routes = [
  {
    path: '',
    component: RecorddetilPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RecorddetilPageRoutingModule {}
