import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { StumsgComponent } from './stumsg.component';

describe('StumsgComponent', () => {
  let component: StumsgComponent;
  let fixture: ComponentFixture<StumsgComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StumsgComponent ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(StumsgComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
