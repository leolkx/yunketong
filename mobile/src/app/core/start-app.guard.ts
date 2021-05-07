import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { LocalStorageService } from '../shared/services/local-storage.service';
import {App} from '../welcome/welcome.page';
@Injectable({
  providedIn: 'root'
})
export class StartAppGuard implements CanActivate {

  constructor(private localStorageService: LocalStorageService, private router: Router) { }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const appConfig: any = this.localStorageService.get(App, {
      hasRun: false,
      version: '1.0.0'
    });
    if ( appConfig.hasRun === false ) {
      appConfig.hasRun = true;
      this.localStorageService.set(App, appConfig);
      return true;
    } else {
      this.router.navigateByUrl('login');
      return false;
    }
  }
}
