import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { CouresnumberComponent } from './couresnumber.component';

describe('CouresnumberComponent', () => {
  let component: CouresnumberComponent;
  let fixture: ComponentFixture<CouresnumberComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CouresnumberComponent ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(CouresnumberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
