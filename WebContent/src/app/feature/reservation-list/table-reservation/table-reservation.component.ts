import { Component, OnInit, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-table-reservation',
  templateUrl: './table-reservation.component.html',
  styleUrls: ['./table-reservation.component.css']
})
export class TableReservationComponent implements OnInit {

  @Input() reservations!: any[];
  role!:string;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.getRole();
  }

  acceptReservation(id:any) {

    this.http.post("http://localhost:8080/reservation/acceptReservation?id="+id,null).subscribe(

      res => { //next

        if(res==true) {
          alert("La prenotazione è stata accettata");
        } else {
          alert('Non è stato possibile accettare la prenotazione');
        }
      },

      (error) => console.error(error), //error
      
      () => {});

  }

  cancelReservation(id:any) {

    this.http.post("http://localhost:8080/reservation/cancelReservation?id="+id,null).subscribe(

      res => { //next

        if(res==true) {
          alert("La prenotazione è stata annullata");
        } else {
          alert('Non è stato possibile annullare la prenotazione');
        }
      },

      (error) => console.error(error), //error
      
      () => {});
  }

  getRole() {
    this.role=document.cookie.split('=')[1];
  }

}
