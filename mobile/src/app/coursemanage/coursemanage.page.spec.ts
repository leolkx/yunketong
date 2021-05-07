import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { CoursemanagePage } from './coursemanage.page';

describe('CoursemanagePage', () => {
  let component: CoursemanagePage;
  let fixture: ComponentFixture<CoursemanagePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CoursemanagePage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(CoursemanagePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
