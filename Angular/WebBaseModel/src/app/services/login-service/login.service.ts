import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConfig } from '../../core/app.config';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  URL: string = "";

  constructor(private http: HttpClient, private apiconfig: AppConfig, private cookieService: CookieService) {
    this.URL = apiconfig.getConfig('api_root');
  }

  //chiamata per il login
  login(email: string, password: string): Observable<Object> {
    const params = new HttpParams()
      .set('email', email)
      .set('password', password);
    return this.http.get(this.URL + 'user/login', { params });
  }

  //chiamata per il ricavare il ruolo dell'utente
  getUserRole(id: string): Observable<Object> {
    const params = new HttpParams().set('id', id);
    return this.http.get(this.URL + 'user/getUserRole', { params });
  }

  //chiamata per il ricavare il ruolo dell'utente
  aggiornaToken(idUtente: string): Observable<Object> {
    const params = new HttpParams().set('idUtente', idUtente);
    return this.http.get(this.URL + 'user/aggiornaToken', { params });
  }

  isTokenExpiring(): boolean {

    var tokenFromCookie: string = '';

    const token = sessionStorage.getItem("Token");
    if (!token) {

      tokenFromCookie = this.cookieService.get('Token');

      if (!tokenFromCookie) {

        return false; // Nessun token disponibile

      } else {

        const expiryThreshold = 300; // 300 secondi (5 minuti) prima della scadenza
        const tokenPayload = this.decodeToken(tokenFromCookie);

        const currentTime = Math.floor(Date.now() / 1000); // Tempo corrente in secondi

        if (tokenPayload.exp) {
          return (tokenPayload.exp - currentTime) < expiryThreshold;
        }

      }

    } else {

      const expiryThreshold = 300; // 300 secondi (5 minuti) prima della scadenza
      const tokenPayload = this.decodeToken(token);

      const currentTime = Math.floor(Date.now() / 1000); // Tempo corrente in secondi

      if (tokenPayload.exp) {
        return (tokenPayload.exp - currentTime) < expiryThreshold;
      }

    }

    return false;

  }

  private decodeToken(token: string): any {
    // Decodifica il token JWT senza verificarlo
    const payloadBase64 = token.split('.')[1];
    const decodedPayload = atob(payloadBase64);
    return JSON.parse(decodedPayload);
  }

}
