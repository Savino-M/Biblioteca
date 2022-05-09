import { Component, OnInit } from '@angular/core';
import { Address } from './address';
import { User } from './user';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  user!:User;
  address!:Address;
  accepted: any=false;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  addUser(name:string, surname:string, cf:string, address:string, cap:string, city:string, mail:string, password:string) {
    
    this.address=new Address(city,cap,address);
    this.user=new User(name,surname,mail,password,cf,this.address);

    this.http.post("http://localhost:8080/user/saveUser",this.user).subscribe(

      res => { //next
        this.accepted=res.valueOf();
        
        if(this.accepted){
          window.location.href="/login";
        }
      },
      (error) => console.error(error), //error
      () => {}
    );



  }

}
