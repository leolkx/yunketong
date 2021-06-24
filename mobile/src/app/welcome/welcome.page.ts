import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import { LocalStorageService } from '../shared/services/local-storage.service';
import { IonSlides } from '@ionic/angular';
import { Router } from '@angular/router';
export const App = 'App';
@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.page.html',
  styleUrls: ['./welcome.page.scss'],
  encapsulation: ViewEncapsulation.None
})
export class WelcomePage implements OnInit {
  showSkip = true;
  @ViewChild('slides', {static: false}) slides: IonSlides;
  constructor(private localStorageService:LocalStorageService, private router: Router) { }

  ngOnInit() {
  }
  onSlideWillChange(event) {
    // console.log(event);
    this.slides.isEnd().then((end) => {
      this.showSkip = !end;
    });
  }
  onSkip(){
    this.router.navigateByUrl('\login');
  }
}
