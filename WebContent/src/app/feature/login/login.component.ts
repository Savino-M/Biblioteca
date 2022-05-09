import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user!: any;
  role: any=null;
  
  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  login(email:string, password:string) {
    
    this.http.get("http://localhost:8080/user/getUserByMailAndPassword?mail="+email+"&password="+password).subscribe(

      (user : any) => { //restituisce l'utente
        this.user=user; 

        if(this.user!=null) { //se le credenziali sono corrette
                  
          this.http.get("http://localhost:8080/user/getUserRole?id="+this.user.id).subscribe( //ricavo il ruolo dell'utente
    
            (role : any) => { //next
              this.role=role;  
              document.cookie=this.user.id+"="+this.role.roleName;
              window.location.href="/book"; // vado nella pagina book 
            },
    
            (error) => console.error(error), //error
    
            () => {}
          );   
                      
        } else {
          alert("Credenziali non valide");
        } 
             
      },
      (error) => console.error(error), //error
      () => {}
    );
}

}
