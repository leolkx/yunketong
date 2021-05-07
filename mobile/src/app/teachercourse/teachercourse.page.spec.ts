import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { TeachercoursePage } from './teachercourse.page';

describe('TeachercoursePage', () => {
  let component: TeachercoursePage;
  let fixture: ComponentFixture<TeachercoursePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TeachercoursePage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(TeachercoursePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
