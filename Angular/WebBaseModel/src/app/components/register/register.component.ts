import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Indirizzo } from '../../models/Indirizzo';
import { Ruolo } from '../../models/Ruolo';
import { Utente } from '../../models/Utente';
import { MessageWindowService } from '../../services/message-window-service/message-window-service.service';
import { RegisterService } from '../../services/register-service/register.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  utente!: Utente;
  indirizzo!: Indirizzo;
  ruolo!: Ruolo;
  accepted: any = false;

  constructor(private service: RegisterService, private router: Router, private messageWindowService: MessageWindowService) { }

  registerUser(nome: string, cognome: string, cf: string, indirizzo: string, cap: string, citta: string, email: string, password: string) {

    this.indirizzo = new Indirizzo(citta, cap, indirizzo);
    this.utente = new Utente(nome, cognome, email, password, cf, this.ruolo, this.indirizzo);

    this.service.registerUser(this.utente).subscribe({
      next: (res: any) => {
        this.accepted = res.valueOf();
        if (this.accepted) {
          this.router.navigate(['/login']);
        }
      },
      error: (error) => {
        console.error(error);
        this.messageWindowService.showWindow(true, "Errore nella registrazione");
      }
    });

  }

}
