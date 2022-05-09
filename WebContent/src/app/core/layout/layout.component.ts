import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})

export class LayoutComponent implements OnInit {

  links = [
    { label: "Book List", url: "book" },
    { label: "Reservation List", url: "getReservations" },
    { label: "Loan List", url: "getLoans" }
  ]; 

  constructor() { }

  ngOnInit(): void {
  }

}
