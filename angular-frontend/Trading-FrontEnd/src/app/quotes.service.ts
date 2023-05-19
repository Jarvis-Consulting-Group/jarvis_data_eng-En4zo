import { Injectable } from '@angular/core';
import { Quote } from './quote';
import { HttpClient } from '@angular/common/http'
import { Observable, of } from'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QuotesService {
  quoteLists : Quote[] = [
    {
        ticker: "FB",
        lastPrice: 319.48,
        bidPrice: 0,
        bidSize: 13,
        askPrice: 13,
        askSize: 400
    },
    {
        ticker: "AAPL",
        lastPrice: 500.23,
        bidPrice: 0,
        bidSize: 18,
        askPrice: 18,
        askSize: 100
    },
    {
        ticker: "MSFT",
        lastPrice: 100.53,
        bidPrice: 0,
        bidSize: 25,
        askPrice: 25,
        askSize: 200
    },
    {
        ticker: "GOOGL",
        lastPrice: 500.99,
        bidPrice: 0,
        bidSize: 30,
        askPrice: 10,
        askSize: 400
    },
    {
        ticker: "AMZN",
        lastPrice: 85.50,
        bidPrice: 0,
        bidSize: 16,
        askPrice: 16,
        askSize: 150
    }
];

constructor() { }

getColumns() : string[]{
  return ['Ticker','Last Price','Bid Price', 'Bid Size', 'Ask Price', 'Ask Size']
}

getDataSource(): Observable<Quote[]>{
  return of(this.quoteLists);
}

}
