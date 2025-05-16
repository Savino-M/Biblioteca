import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Prenotazione } from '../../models/Prenotazione';
import { MessageWindowService } from '../../services/message-window-service/message-window-service.service';
import { PrenotazioneService } from '../../services/prenotazione-service/prenotazione.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-prenotazioni',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './prenotazioni.component.html',
  styleUrls: ['./prenotazioni.component.css']
})
export class PrenotazioniComponent {

  prenotazioniList!: Prenotazione[];

  constructor(private service: PrenotazioneService, private router: Router, private messageWindowService: MessageWindowService) { }

  ngOnInit() {

    this.service.getPrenotazioniByUser(sessionStorage.getItem("IdUtente")).subscribe({
      next: (elementsList: any) => {
        this.prenotazioniList = elementsList;
      },
      error: (error) => {
        console.error(error);
        this.messageWindowService.showWindow(true, error.error);
      }
    });

  }

  eliminaPrenotazione(idPrenotazione: string) {

    this.service.eliminaPrenotazione(idPrenotazione).subscribe({
      next: (res: any) => {
        this.messageWindowService.showWindow(true, "Prenotazione annullata");
        window.location.reload();
      },
      error: (error) => {
        console.error(error);
        this.messageWindowService.showWindow(true, error.error);
      }
    });

  }

}
