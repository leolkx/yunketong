import { NgModule } from '@angular/core';
import { StartAppGuard } from './core/start-app.guard';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'welcome', 
    pathMatch: 'full'
  },
  { path: 'welcome', loadChildren: './welcome/welcome.module#WelcomePageModule', canActivate: [StartAppGuard] },
  {
    path: '',
    loadChildren: () => import('./tabs/tabs.module').then( m => m.TabsPageModule)
  },
  {
    path: 'mine',
    loadChildren: () => import('./mine/mine.module').then( m => m.MinePageModule)
  },
  {
    path: 'coures',
    loadChildren: () => import('./coures/coures.module').then( m => m.CouresPageModule)
  },
  {
    path: 'login',
    loadChildren: () => import('./passport/passport.module').then( m => m.PassportPageModule)
  },
  {
    path: 'coursemanage',
    loadChildren: () => import('./coursemanage/coursemanage.module').then( m => m.CoursemanagePageModule)
  },
  {
    path: 'teachercourse',
    loadChildren: () => import('./teachercourse/teachercourse.module').then( m => m.TeachercoursePageModule)
  },
  {
    path: 'gesture',
    loadChildren: () => import('./gesture/gesture.module').then( m => m.GesturePageModule)
  }
];
@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
