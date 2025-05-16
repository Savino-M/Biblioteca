import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Utente } from '../../models/Utente';
import { LoginService } from '../../services/login-service/login.service';
import { MessageWindowService } from '../../services/message-window-service/message-window-service.service';
import { MessageWindowComponent } from '../message-window/message-window.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  utente!: Utente;

  constructor(private service: LoginService, private router: Router, private messageWindowService: MessageWindowService) { }

  login(email: string, password: string) {

    this.service.login(email, password).subscribe({
      next: (response: any) => {
        this.utente = response.utente;
        if (this.utente != null) {
          sessionStorage.setItem("IdUtente", this.utente.id);
          sessionStorage.setItem("Ruolo", this.utente.ruolo.descrizione);
          sessionStorage.setItem("Token", response.token);
          this.router.navigate(['/home']);
        }
      },
      error: (error) => {
        console.error(error);
        this.messageWindowService.showWindow(true, "Errore nel login");
      }
    });

  }

  loginWithGoogle() {
    window.location.href = 'https://localhost:8080/oauth2/authorization/google';
  }

}
