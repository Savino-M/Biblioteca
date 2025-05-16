import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root' // Assicurati che il servizio sia iniettato a livello globale
})
export class AppConfig {

  private config: any = null;

  constructor(private http: HttpClient) { }

  public getConfig(key: any) {
    return this.config[key];
  }

  public load() {
    return new Promise((resolve, reject) => {
      this.http.get('./assets/config/env.json').subscribe({
        next: (res: any) => {
          this.config = res;
          resolve(true);
        },
        error: (error) => {
          console.error(error);
          reject(false); // Correggi qui per rifiutare correttamente la promessa
        }
      });
    });
  }
}
