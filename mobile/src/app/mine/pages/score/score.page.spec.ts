import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ScorePage } from './score.page';

describe('ScorePage', () => {
  let component: ScorePage;
  let fixture: ComponentFixture<ScorePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScorePage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ScorePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
