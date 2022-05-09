import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-loan-list',
  templateUrl: './loan-list.component.html',
  styleUrls: ['./loan-list.component.css']
})

export class LoanListComponent implements OnInit {

  loans:any[]=[];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {

    this.http.get("http://localhost:8080/loan/getLoansByUser?id="+document.cookie.split('=')[0]).subscribe(

      (loans : any) => { //ricavo i prestiti dell'utente

        for (let loan of loans) { //per ogni prestito
          
          this.http.get("http://localhost:8080/loan/getBook?id="+loan.id).subscribe( //prendo il libro corrispondente

            (book : any) => { //next
              this.loans.push({loan,book});
            },
      
            (error) => console.error(error), //error
            
            () => {});

      }

      },

      (error) => console.error(error), //error
      
      () => {});

  }

}
