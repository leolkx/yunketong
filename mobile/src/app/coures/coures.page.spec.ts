import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { CouresPage } from './coures.page';

describe('CouresPage', () => {
  let component: CouresPage;
  let fixture: ComponentFixture<CouresPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CouresPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(CouresPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
