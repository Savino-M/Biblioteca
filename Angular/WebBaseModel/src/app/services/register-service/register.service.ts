import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Utente } from '../../models/Utente';
import { AppConfig } from '../../core/app.config';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  URL: string = "";

  constructor(private http: HttpClient, private apiconfig: AppConfig) {
    this.URL = apiconfig.getConfig('api_root');
  }

  //chiamata per registrare un nuovo utente
  registerUser(utente: Utente): Observable<Object> {
    return this.http.post(this.URL + 'user/registraUtente', utente);
  }
}
