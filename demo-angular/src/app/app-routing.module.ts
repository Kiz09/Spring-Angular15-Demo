import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PaymentsListComponent } from './components/payments-list/payments-list.component';

const routes: Routes = [
  // { path: '', redirectTo: 'payments', pathMatch: 'full' },
  { path: 'payments', component: PaymentsListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
