import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SecurityPage } from './security.page';

const routes: Routes = [
  {
    path: '',
    component: SecurityPage
  },
  {
    path: 'changephone',
    loadChildren: () => import('./changephone/changephone.module').then( m => m.ChangephonePageModule)
  },
  {
    path: 'changepw',
    loadChildren: () => import('./changepw/changepw.module').then( m => m.ChangepwPageModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SecurityPageRoutingModule {}
