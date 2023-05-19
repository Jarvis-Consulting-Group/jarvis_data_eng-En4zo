import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { TraderListService } from '../trader-list.service';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-trader-account-with-draw-dialog',
  templateUrl: './trader-account-with-draw-dialog.component.html',
  styleUrls: ['./trader-account-with-draw-dialog.component.css']
})
export class TraderAccountWithDrawDialogComponent {
  constructor(private traderListService : TraderListService, private dialog : MatDialogRef<TraderAccountWithDrawDialogComponent>){}
  withDrawForm = new FormGroup({
    amount : new FormControl(''),
  })

  id = this.traderListService.getTraderId();

  onSubmit(){
    this.traderListService.deleteAmount(this.id,Number(this.withDrawForm.get("amount")?.value));
    this.dialog.close();

  }


}
