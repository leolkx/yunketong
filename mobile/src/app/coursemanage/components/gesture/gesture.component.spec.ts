import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { GestureComponent } from './gesture.component';

describe('GestureComponent', () => {
  let component: GestureComponent;
  let fixture: ComponentFixture<GestureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestureComponent ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(GestureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
