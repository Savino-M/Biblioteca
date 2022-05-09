import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './core/header/header.component';
import { BookListComponent } from './feature/book-list/book-list.component';

import { LayoutComponent } from './core/layout/layout.component';
import { TableComponentBook } from './feature/book-list/table/table.component';
import { TableComponentLoan } from './feature/loan-list/table/table.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { FooterComponent } from './core/footer/footer.component';
import { AddBookComponent } from './feature/add-book/add-book.component';
import { RegisterComponent } from './feature/register/register.component';
import { LoginComponent } from './feature/login/login.component';
import { LoanListComponent } from './feature/loan-list/loan-list.component';
import { ReservationListComponent } from './feature/reservation-list/reservation-list.component';
import { TableReservationComponent } from './feature/reservation-list/table-reservation/table-reservation.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BookListComponent,
    LayoutComponent,
    TableComponentBook,
    NavbarComponent,
    FooterComponent,
    AddBookComponent,
    RegisterComponent,
    LoginComponent,
    LoanListComponent,
    TableComponentLoan,
    ReservationListComponent,
    TableReservationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    HttpClientModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
