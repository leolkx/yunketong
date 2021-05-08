import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ScoredetilPage } from './scoredetil.page';

const routes: Routes = [
  {
    path: '',
    component: ScoredetilPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ScoredetilPageRoutingModule {}
