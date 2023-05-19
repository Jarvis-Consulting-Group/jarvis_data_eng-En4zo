import { Injectable } from '@angular/core';
import {Trader} from "./Trader"
import { Subject, Observable, of  } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TraderListService {

  traderList: Trader[] = [
    {
    key: "1",
    id: 1,
    firstName: "Mike",
    lastName: "Spencer",
    dob: "1990-01-01",
    country: "Canada",
    email: "mike@test.com",
    amount: 0,
    actions: "<button (click)='deleteTrader'>Delete Trader</button>"
  },
  {
      key: "2",
      id: 2,
      firstName: "Hellen",
      lastName: "Miller",
      dob: "1990-01-01",
      country: "Austria",
      email: "hellen@test.com",
      actions: "<button (click)='deleteTrader'>Delete Trader</button>",
      amount: 0,
  },
  {
      key: "3",
      id: 3,
      firstName: "Jack",
      lastName: "Reed",
      dob: "1990-01-01",
      country: "United Kingdom",
      email: "jack@test.com",
      actions: "<button (click)='deleteTrader'>Delete Trader</button>",
      amount: 0,
  },
  
]
private traderSubject = new Subject<{methodName : String}>();
callMethodCalled$ = this.traderSubject.asObservable();

  constructor() { }

  id ?: number; 

  getDataSource(): Observable<Trader[]>{
    return of(this.traderList);
  }

  getColumns(): string[]{
    return ["First name", "Last Name", "Email","DateOfBirth", "Country","Actions"]
  }

  deleteTrader(id : number){
    const deleteIndex : number = this.traderList.findIndex(trader => trader.id === id);
    console.log(deleteIndex);
    
    if(deleteIndex > -1){ 
      this.traderList = this.traderList.filter(trader => trader.id !== id); 
      console.log(this.traderList);
    }
  }

  addTrader(trader: Trader){
    const index : number = this.traderList.findIndex(trader2 => trader.id === trader2.id);
    console.log(index);
    if(index === -1){
      this.traderList= [...this.traderList,trader];

      console.log(this.traderList);
    }
  }

  getKeyAndId(): number{
    return this.traderList.length + 1;
  }

  callMethod(methodName : string){
    this.traderSubject.next({methodName: methodName});
  }

  getTrader(id: number | undefined): Trader | undefined{
    return this.traderList.find(t => t.id === id);
  }

  addAmount(id : number | undefined, amount : number): void{
    let trader = this.getTrader(id);
    if(trader){
      trader.amount += amount;
    } else{
      console.log("Trader not found")
    }

  }

  deleteAmount(id : number | undefined, amount : number) : void{
    let trader = this.getTrader(id);
    if(trader){
      if(trader.amount >= amount){
        trader.amount -= amount;
      }else{
        trader.amount = 0;
      }
    }else{
      console.log("Trader not found")
    }

  }

  setTraderId(id : number): void{
    this.id = id;
  }
  getTraderId(): number | undefined {
    return this.id;
  }
}
