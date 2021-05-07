import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { RegUsermsgComponent } from './reg-usermsg.component';

describe('RegUsermsgComponent', () => {
  let component: RegUsermsgComponent;
  let fixture: ComponentFixture<RegUsermsgComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegUsermsgComponent ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(RegUsermsgComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
