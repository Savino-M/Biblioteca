import { Component, OnInit, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-table-book',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})

export class TableComponentBook implements OnInit {

  @Input() books!: any[];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  addReservation(isbn:any) {

    this.http.post("http://localhost:8080/reservation/saveReservation?user_id="+document.cookie.split('=')[0]+"&book_isbn="+isbn, null).subscribe(

          res => { //next
            
            if(res==true) {
              alert("Prenotazione inviata");
            } else {
              alert("Prenotazione non riuscita");
            }
          },
          (error) => console.error(error), //error
          () => {}
        );
  }

}
