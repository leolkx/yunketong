import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CouresPage } from './coures.page';

const routes: Routes = [
  {
    path: '',
    component: CouresPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CouresPageRoutingModule {}
