import { Component } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { TraderDialogComponent } from '../trader-dialog/trader-dialog.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  constructor(public dialog: MatDialog){}
  

  openDialog(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.position= {
      // left : "100px",
      // top : "100px"


    }
    const dialogRef = this.dialog.open(TraderDialogComponent,dialogConfig);

    dialogRef.afterClosed().subscribe(result =>
      console.log("Dialog Closed.")
      );
  }

  
}
