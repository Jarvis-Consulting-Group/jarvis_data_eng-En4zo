import { Component,ChangeDetectorRef } from '@angular/core';
import { TraderListService } from '../trader-list.service';
import { OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { TraderDialogComponent } from '../trader-dialog/trader-dialog.component';
import { Trader } from '../Trader'


@Component({
  selector: 'app-trader-list',
  templateUrl: './trader-list.component.html',
  styleUrls: ['./trader-list.component.css']
  
})
export class TraderListComponent implements OnInit {
  dataSource : any;
  propertyColumns: string[] = [];

  constructor(private traderListService : TraderListService, private dialog : MatDialog,){
    
  }

  ngOnInit(): void {
    this.traderListService.getDataSource().subscribe(data => {
      this.dataSource = data;
  
    });
   
    this.propertyColumns = this.traderListService.getColumns();
    this.traderListService.callMethodCalled$.subscribe(value =>{
      if(value.methodName == "update"){
        this.update();
      }
    })
  }

  deleteTrader(id: number ){
    console.log(id)
    this.traderListService.deleteTrader(id);
  
    this.traderListService.getDataSource().subscribe(data => this.dataSource = data);
    
  }

  update(){
    this.traderListService.getDataSource().subscribe(data => this.dataSource = data);
  }



}
