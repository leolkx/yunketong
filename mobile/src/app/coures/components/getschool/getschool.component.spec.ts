import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { GetschoolComponent } from './getschool.component';

describe('GetschoolComponent', () => {
  let component: GetschoolComponent;
  let fixture: ComponentFixture<GetschoolComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GetschoolComponent ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(GetschoolComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
