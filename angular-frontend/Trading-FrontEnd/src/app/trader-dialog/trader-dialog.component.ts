import { Component, ViewChild } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { TraderListService } from '../trader-list.service';
import { Trader } from '../Trader';
import { TraderListComponent } from '../trader-list/trader-list.component';




@Component({
  selector: 'app-trader-dialog',
  templateUrl: './trader-dialog.component.html',
  styleUrls: ['./trader-dialog.component.css']
})
export class TraderDialogComponent {
constructor(public dialog : MatDialogRef<TraderDialogComponent>, private traderListService : TraderListService){}

  firstName : string = "";
  lastName: string = "";
  email: string = "";
  country: string = "";
  dateOfBirth!: Date ;
  key : string = this.traderListService.getKeyAndId.toString();
  id : number = this.traderListService.getKeyAndId();


  onCancel(): void{
    this.dialog.close();
  }

  addTrader(){
    let newTrader : Trader = {
      key : this.key,
      id: this.id,
      firstName: this.firstName,
      lastName: this.lastName,
      dob: this.dateOfBirth.toString(),
      country: this.country,
      email: this.email,
      actions:"<button (click)='deleteTrader'>Delete Trader</button>" ,
      amount: 0,
    }
    console.log("addtrader!")
    this.traderListService.addTrader(newTrader);
    this.traderListService.callMethod("update");
    this.dialog.close();
  }


}
