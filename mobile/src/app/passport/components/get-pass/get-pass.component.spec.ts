import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { GetPassComponent } from './get-pass.component';

describe('GetPassComponent', () => {
  let component: GetPassComponent;
  let fixture: ComponentFixture<GetPassComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GetPassComponent ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(GetPassComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
