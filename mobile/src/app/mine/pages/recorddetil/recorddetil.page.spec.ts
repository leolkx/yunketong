import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { RecorddetilPage } from './recorddetil.page';

describe('RecorddetilPage', () => {
  let component: RecorddetilPage;
  let fixture: ComponentFixture<RecorddetilPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecorddetilPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(RecorddetilPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
