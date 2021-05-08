import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { GesturePage } from './gesture.page';

describe('GesturePage', () => {
  let component: GesturePage;
  let fixture: ComponentFixture<GesturePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GesturePage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(GesturePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
