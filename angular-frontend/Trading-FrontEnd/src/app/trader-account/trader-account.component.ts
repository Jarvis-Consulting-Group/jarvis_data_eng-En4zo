import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TraderListService } from '../trader-list.service';
import { Trader } from '../Trader';
import { MatDialog } from '@angular/material/dialog';
import { TraderAccountDepositDialogComponent } from '../trader-account-deposit-dialog/trader-account-deposit-dialog.component';
import { TraderAccountWithDrawDialogComponent } from '../trader-account-with-draw-dialog/trader-account-with-draw-dialog.component'


@Component({
  selector: 'app-trader-account',
  templateUrl: './trader-account.component.html',
  styleUrls: ['./trader-account.component.css']
})
export class TraderAccountComponent implements OnInit{
  trader : Trader | undefined; 

  constructor(private activateRoute : ActivatedRoute, private traderListService : TraderListService, private dialog: MatDialog){}
  
  ngOnInit(): void {
    const id = this.activateRoute.snapshot.paramMap.get("id");
    this.trader = this.traderListService.getTrader(Number(id));
    this.traderListService.setTraderId(Number(id));
  }

  openDepositDialog(){
    const depositDialogRef = this.dialog.open(TraderAccountDepositDialogComponent)

    depositDialogRef.afterClosed().subscribe(reutrn =>
      console.log("Dialog closed")

    )

  }

  openWithDrawDialog(){
    const withDrawDialogRef = this.dialog.open(TraderAccountWithDrawDialogComponent)

    withDrawDialogRef.afterClosed().subscribe(
      result => console.log("Dialog closed")
    )

  }
  

}
