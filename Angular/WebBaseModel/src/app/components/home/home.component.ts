import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Elemento } from '../../models/Elemento';
import { Prenotazione } from '../../models/Prenotazione';
import { Utente } from '../../models/Utente';
import { HomeService } from '../../services/home-service/home-service';
import { MessageWindowService } from '../../services/message-window-service/message-window-service.service';
import { PrenotazioneService } from '../../services/prenotazione-service/prenotazione.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  utente!: Utente;
  elementsList!: Elemento[];
  prenotazione!: Prenotazione;
  accepted: any = false;
  element!: Elemento;

  constructor(private homeService: HomeService, private prenotazioneService: PrenotazioneService, private router: Router, private messageWindowService: MessageWindowService) { }

  ngOnInit() {

    this.homeService.getAllElements(10, 1).subscribe({
      next: (elementsList: any) => {
        this.elementsList = elementsList.content;
      },
      error: (error) => {
        console.error(error);
        this.messageWindowService.showWindow(true, error.error);
      }
    });

  }

  prenotaElemento(idElemento: string) {

    const index = this.elementsList.findIndex((x: { id: string; }) => x.id === idElemento);
    this.element = this.elementsList[index];

    this.prenotazione = new Prenotazione(sessionStorage.getItem("IdUtente"), this.element.id);

    this.prenotazioneService.insertPrenotazione(this.prenotazione).subscribe({
      next: (res: any) => {
        this.messageWindowService.showWindow(true, "Prenotazione effettuata");
      },
      error: (error: any) => {
        console.error(error);
        const errorMessage = error.status
          ? `Errore ${error.status}: ${error.error || 'Si è verificato un errore'}`
          : 'Errore sconosciuto';
        this.messageWindowService.showWindow(true, errorMessage);
      }
    });

  }

  visualizzaImmagini(idElemento: string) {

    const index = this.elementsList.findIndex((x: { id: string; }) => x.id === idElemento);
    this.element = this.elementsList[index];

  }

}