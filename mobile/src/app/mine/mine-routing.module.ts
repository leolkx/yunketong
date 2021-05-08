import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MinePage } from './mine.page';

const routes: Routes = [
  {
    path: '',
    component: MinePage
  },
  {
    path: 'setupaccount',
    loadChildren: () => import('./pages/setupaccount/setupaccount.module').then( m => m.SetupaccountPageModule)
  },
  {
    path: 'setting',
    loadChildren: () => import('./pages/setting/setting.module').then( m => m.SettingPageModule)
  },
  {
    path: 'aboutus',
    loadChildren: () => import('./pages/aboutus/aboutus.module').then( m => m.AboutusPageModule)
  },
  {
    path: 'score',
    loadChildren: () => import('./pages/score/score.module').then( m => m.ScorePageModule)
  },
  {
    path: 'record',
    loadChildren: () => import('./pages/record/record.module').then( m => m.RecordPageModule)
  },
  {
    path: 'gesture',
    loadChildren: () => import('./pages/gesture/gesture.module').then( m => m.GesturePageModule)
  },  {
    path: 'scoredetil',
    loadChildren: () => import('./pages/scoredetil/scoredetil.module').then( m => m.ScoredetilPageModule)
  },
  {
    path: 'recorddetil',
    loadChildren: () => import('./pages/recorddetil/recorddetil.module').then( m => m.RecorddetilPageModule)
  },
  {
    path: 'userag',
    loadChildren: () => import('./pages/userag/userag.module').then( m => m.UseragPageModule)
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MinePageRoutingModule {}
