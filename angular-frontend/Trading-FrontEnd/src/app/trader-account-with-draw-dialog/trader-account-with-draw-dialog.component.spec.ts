import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TraderAccountWithDrawDialogComponent } from './trader-account-with-draw-dialog.component';

describe('TraderAccountWithDrawDialogComponent', () => {
  let component: TraderAccountWithDrawDialogComponent;
  let fixture: ComponentFixture<TraderAccountWithDrawDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TraderAccountWithDrawDialogComponent]
    });
    fixture = TestBed.createComponent(TraderAccountWithDrawDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
