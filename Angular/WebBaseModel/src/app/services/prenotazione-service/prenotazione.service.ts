import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConfig } from '../../core/app.config';
import { Prenotazione } from '../../models/Prenotazione';

@Injectable({
  providedIn: 'root'
})
export class PrenotazioneService {

  URL: string = "";

  constructor(private http: HttpClient, private apiconfig: AppConfig) {
    this.URL = apiconfig.getConfig('api_root');
  }

  //chiamata per vedere le prenotazioni di un utente
  getPrenotazioniByUser(idUtente: any): Observable<Object> {
    const params = new HttpParams()
      .set('idUtente', idUtente);
    return this.http.get(this.URL + 'prenotazione/getPrenotazioniByUser', { params });
  }

  //chiamata per prenotare un elemento
  insertPrenotazione(prenotazione: Prenotazione): Observable<Object> {
    return this.http.post(this.URL + 'prenotazione/inserisciPrenotazione', prenotazione);
  }

  //chiamata per eliminare una prenotazione
  eliminaPrenotazione(idPrenotazione: string): Observable<Object> {
    const params = new HttpParams()
      .set('idPrenotazione', idPrenotazione);
    return this.http.delete(this.URL + 'prenotazione/eliminaPrenotazione', { params });
  }

}
