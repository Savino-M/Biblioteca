import { Component, OnInit } from '@angular/core';
import { Book } from '../book-list/book';
import { Output, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css']
})

export class AddBookComponent implements OnInit {

  item!:Book;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

 addBook(isbn:string, title:string, author:string, date:string, genre:string) {

        this.item=new Book(isbn,title,author,new Date,genre);

        this.http.post("http://localhost:8080/book/saveBook",this.item).subscribe(

          res => { //next
            console.log(res);
          },
          (error) => console.error(error), //error
          () => {}
        );
 }

}
