import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ScoredetilPage } from './scoredetil.page';

describe('ScoredetilPage', () => {
  let component: ScoredetilPage;
  let fixture: ComponentFixture<ScoredetilPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScoredetilPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ScoredetilPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
