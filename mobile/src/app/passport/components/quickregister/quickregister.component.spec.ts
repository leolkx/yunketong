import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { QuickregisterComponent } from './quickregister.component';

describe('QuickregisterComponent', () => {
  let component: QuickregisterComponent;
  let fixture: ComponentFixture<QuickregisterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuickregisterComponent ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(QuickregisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
