import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/core/app.component';
import { AppConfig } from './app/core/app.config';
import { HTTP_INTERCEPTORS, HttpHandlerFn, HttpRequest, provideHttpClient, withInterceptors, withInterceptorsFromDi } from '@angular/common/http';
import { provideZoneChangeDetection, APP_INITIALIZER, inject } from '@angular/core';
import { provideRouter, Router } from '@angular/router';
import { routes } from './app/core/app.routes';
import { Observable, switchMap, of, catchError, throwError, finalize, map } from 'rxjs';
import { LoginService } from './app/services/login-service/login.service';
import { EncryptionService } from './app/core/app.encryptor';
import { LoaderService } from './app/services/loader-service/loader.service';

function initializeApp(appConfig: AppConfig) {
  return () => appConfig.load()
}

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(withInterceptors([
      (req, next) => {

        return intercept(req, next);

      }
    ])),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    AppConfig,
    {
      provide: APP_INITIALIZER,
      useFactory: initializeApp,
      deps: [AppConfig],
      multi: true
    }
  ]
}).catch((err) => console.error(err));

function intercept(req: HttpRequest<unknown>, next: HttpHandlerFn) {

  // Iniettare i servizi necessari
  const loadingService = inject(LoaderService);
  const encryptionService = inject(EncryptionService);
  const router = inject(Router);

  if (req.url.endsWith('env.json') || req.url.includes('aggiornaToken')) {
    return next(req); // Non fare nulla
  }

  // Mostra loader
  loadingService.showLoader(true);

  const loginService = inject(LoginService);

  // Aggiungo l'authorization bearer token alla request
  return addBearerToken(req, loginService, router).pipe(
    switchMap((updatedReq: HttpRequest<any>) => {
      const encryptedReq = encryptRequestParameters(updatedReq, encryptionService); // Cripto i parametri della request
      return next(encryptedReq);
    }),
    finalize(() => {
      loadingService.showLoader(false);
    }),
    catchError((error) => {
      console.error(error);
      router.navigate(['/login']);
      return throwError(() => new Error(error));
    })
  );

}

function encryptRequestParameters(req: HttpRequest<any>, encryptionService: EncryptionService): HttpRequest<any> {

  const paramsToEncrypt = ['idElemento', 'idUtente', 'idPrenotazione', 'email', 'password'];
  let modifiedUrl = req.url;

  // Controlla se ci sono query params nella URL 
  if (req.urlWithParams.includes('?')) {

    const [baseUrl, queryParams] = req.urlWithParams.split('?');
    const params = new URLSearchParams(queryParams);
    const transformedParts: string[] = [];

    // Itera sui parametri nella query string nell'ordine originale 
    params.forEach((value, key) => {
      if (paramsToEncrypt.includes(key)) {
        // Crittografa e aggiungi ai transformedParts 
        transformedParts.push(encryptionService.encrypt(value));
      } else {
        transformedParts.push(value);
      }
    });

    // Ricostruisci la URL con i parametri crittografati e quelli rimanenti 
    modifiedUrl = `${baseUrl}/${transformedParts.join('/')}`;

  }

  // Clona la richiesta con la URL modificata 
  const clonedRequest = req.clone({ url: modifiedUrl });
  return clonedRequest;

}

function addBearerToken(req: HttpRequest<any>, loginService: LoginService, router: Router): Observable<HttpRequest<any>> {

  var idUtente: string | null = sessionStorage.getItem("IdUtente");

  // Verifico se esiste il token in sessionStorage
  const token = sessionStorage.getItem("Token");

  if (token != null) {
    if (loginService.isTokenExpiring()) { // Se il token è quasi o del tutto espirato
      if (idUtente != null) {
        loginService.aggiornaToken(idUtente).pipe( // Aggiorna token
          map((response: any) => {
            sessionStorage.setItem("Token", response);
          }),
          catchError((error) => {
            console.error(error);
            router.navigate(['/login']);
            return throwError(() => new Error(error));
          }));
      }
    }
  } else {
    return of(req); // Se non esiste non faccio nulla, vuol dire che sto facendo il login per la prima volta
  }

  const updatedReq = req.clone({ setHeaders: { 'Authorization': "Bearer " + token } }); // Se esiste, lo aggiungo alla request
  return of(updatedReq);

}