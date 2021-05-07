import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { CoursemsgComponent } from './coursemsg.component';

describe('CoursemsgComponent', () => {
  let component: CoursemsgComponent;
  let fixture: ComponentFixture<CoursemsgComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CoursemsgComponent ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(CoursemsgComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
