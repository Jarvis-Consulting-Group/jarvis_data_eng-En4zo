import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { QuotesService } from '../quotes.service';

@Component({
  selector: 'app-quote-list',
  templateUrl: './quote-list.component.html',
  styleUrls: ['./quote-list.component.css']
})
export class QuoteListComponent implements OnInit{
  constructor(private quotesService : QuotesService){}

  datasource : any;
  propertyColumns : string[] = [];

  

  ngOnInit(): void {
    this.quotesService.getDataSource().subscribe(data => {
      this.datasource = data;  
    })

    this.propertyColumns = this.quotesService.getColumns();
  }


}
