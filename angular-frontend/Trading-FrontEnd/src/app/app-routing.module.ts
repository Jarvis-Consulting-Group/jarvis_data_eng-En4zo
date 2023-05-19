import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { QuoteListComponent } from './quote-list/quote-list.component';
import { TraderAccountComponent } from './trader-account/trader-account.component';


const routes: Routes = [
  {path : 'traderAccount/:id', component : TraderAccountComponent},
  {path : 'quotes', component : QuoteListComponent},
  {path : 'dashboard' ,component : DashboardComponent},
  {path : '',component: DashboardComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
