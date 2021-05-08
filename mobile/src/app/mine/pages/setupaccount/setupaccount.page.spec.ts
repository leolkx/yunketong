import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { SetupaccountPage } from './setupaccount.page';

describe('SetupaccountPage', () => {
  let component: SetupaccountPage;
  let fixture: ComponentFixture<SetupaccountPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SetupaccountPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(SetupaccountPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
