import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { TraderListService } from '../trader-list.service';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-trader-account-deposit-dialog',
  templateUrl: './trader-account-deposit-dialog.component.html',
  styleUrls: ['./trader-account-deposit-dialog.component.css']
})
export class TraderAccountDepositDialogComponent {
  constructor(private traderListService : TraderListService, private dialog: MatDialogRef<TraderAccountDepositDialogComponent>){
    }
  depositForm = new FormGroup({
    amount : new FormControl('')
  })
  
  id = this.traderListService.getTraderId();
  

  onSubmit(){
    console.log("current id is "+ this.id);
    this.traderListService.addAmount(this.id,Number(this.depositForm.get("amount")?.value));
    console.log(this.traderListService.getTrader(this.id)?.amount);
    this.dialog.close();
    
  }

}
