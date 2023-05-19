import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TraderAccountDepositDialogComponent } from './trader-account-deposit-dialog.component';

describe('TraderAccountDepositDialogComponent', () => {
  let component: TraderAccountDepositDialogComponent;
  let fixture: ComponentFixture<TraderAccountDepositDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TraderAccountDepositDialogComponent]
    });
    fixture = TestBed.createComponent(TraderAccountDepositDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
