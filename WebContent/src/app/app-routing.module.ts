import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookListComponent } from './feature/book-list/book-list.component';
import { RegisterComponent } from './feature/register/register.component';
import { LoginComponent } from './feature/login/login.component';
import { LoanListComponent } from './feature/loan-list/loan-list.component';
import { ReservationListComponent } from './feature/reservation-list/reservation-list.component';

const routes: Routes = [ //definisci i percorsi del sito
  {path: 'book', component: BookListComponent }, //se vai in book, viene caricato il componente corrispondente
  {path: 'getLoans', component: LoanListComponent},
  {path: 'getReservations', component: ReservationListComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: '', redirectTo: '/login', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
