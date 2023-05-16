import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ToDoPageComponent } from './to-do-page.component';

describe('ToDoPageComponent', () => {
  let component: ToDoPageComponent;
  let fixture: ComponentFixture<ToDoPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ToDoPageComponent]
    });
    fixture = TestBed.createComponent(ToDoPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
