import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Book } from './book';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})

export class BookListComponent implements OnInit {

  revealForm:boolean=false;

  books: Book[] = []; 
  role!:string;

  constructor (private http: HttpClient) { 
    
  }

  ngOnInit(): void {
    
    this.getRole();

    this.http.get("http://localhost:8080/book/getAllBooks").subscribe(

      (books : any) => { //next
        this.books=books;
      },

      (error) => console.error(error), //error
      
      () => {});
  }

  changeReveal(){
    this.revealForm=!this.revealForm;
  }

  getRole() {
    this.role=document.cookie.split('=')[1];
  }

}
