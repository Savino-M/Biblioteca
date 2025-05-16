import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConfig } from '../../core/app.config';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  URL: string = "";

  constructor(private http: HttpClient, private apiconfig: AppConfig) {
    this.URL = apiconfig.getConfig('api_root');
  }

  //chiamata per reperire la lista degli elementi da mostrare
  getAllElements(pagina: number, elementiPerPagina: number): Observable<Object> {
    const params = new HttpParams().set('pagina', pagina).set('elementiPerPagina', elementiPerPagina);
    return this.http.get(this.URL + 'element/getAllElements', { params });
  }

  //chiamata per aggiornare il token di sicurezza
  refreshToken(idUtente: string): Observable<Object> {
    const params = new HttpParams()
      .set('idUtente', idUtente);
    return this.http.get(this.URL + 'user/aggiornaToken', { params });
  }

}
