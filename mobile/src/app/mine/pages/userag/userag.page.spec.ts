import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { UseragPage } from './userag.page';

describe('UseragPage', () => {
  let component: UseragPage;
  let fixture: ComponentFixture<UseragPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UseragPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(UseragPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
