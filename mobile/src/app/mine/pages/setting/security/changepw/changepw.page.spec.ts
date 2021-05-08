import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ChangepwPage } from './changepw.page';

describe('ChangepwPage', () => {
  let component: ChangepwPage;
  let fixture: ComponentFixture<ChangepwPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangepwPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ChangepwPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
