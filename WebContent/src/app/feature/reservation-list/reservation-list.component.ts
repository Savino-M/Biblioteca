import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-reservation-list',
  templateUrl: './reservation-list.component.html',
  styleUrls: ['./reservation-list.component.css']
})

export class ReservationListComponent implements OnInit {

  reservations:any[]=[];
  role!:string;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {

    this.getRole();
    
    if(this.role=='admin') { // se l'utente è admin, ha accesso a tutte le prenotazioni

      this.http.get("http://localhost:8080/reservation/getAllReservations").subscribe(

        (reservations : any) => { //next
          
          for(let reservation of reservations) { // per ogni prenotazione

            this.http.get("http://localhost:8080/reservation/getUserByReservation?id="+reservation.id).subscribe( //prendo il libro corrispondente

            (user : any) => { //next
              
              this.http.get("http://localhost:8080/reservation/getBookByReservation?id="+reservation.id).subscribe( //prendo l'utente corrispondente

              (book : any) => { //next
                this.reservations.push({reservation,user,book});
              },
        
              (error) => console.error(error), //error
              
              () => {});

            },
      
            (error) => console.error(error), //error
            
            () => {});

          }

        },
  
        (error) => console.error(error), //error
        
        () => {});

    } else {

      this.http.get("http://localhost:8080/reservation/getReservationsByUser?id="+document.cookie.split('=')[0]).subscribe(

        (reservations : any) => { //next
          
          for(let reservation of reservations) { // per ogni prenotazione

            this.http.get("http://localhost:8080/reservation/getUserByReservation?id="+reservation.id).subscribe( //prendo il libro corrispondente

            (user : any) => { //next
              
              this.http.get("http://localhost:8080/reservation/getBookByReservation?id="+reservation.id).subscribe( //prendo il libro corrispondente

              (book : any) => { //next
                this.reservations.push({reservation,user,book});
              },
        
              (error) => console.error(error), //error
              
              () => {});

            },
      
            (error) => console.error(error), //error
            
            () => {});

          }

        },
  
        (error) => console.error(error), //error
        
        () => {});

    }
  }

  getRole() {
    this.role=document.cookie.split('=')[1];
  }

}
