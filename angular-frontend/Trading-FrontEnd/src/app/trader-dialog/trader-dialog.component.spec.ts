import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TraderDialogComponent } from './trader-dialog.component';

describe('TraderDialogComponent', () => {
  let component: TraderDialogComponent;
  let fixture: ComponentFixture<TraderDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TraderDialogComponent]
    });
    fixture = TestBed.createComponent(TraderDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
