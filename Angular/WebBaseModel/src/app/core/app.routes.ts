import { Routes } from '@angular/router';
import { HomeComponent } from '../components/home/home.component';
import { LoginComponent } from '../components/login/login.component';
import { PrenotazioniComponent } from '../components/prenotazioni/prenotazioni.component';
import { RegisterComponent } from '../components/register/register.component';

export const routes: Routes = [ //definisci i percorsi del sito
    { path: 'register', component: RegisterComponent },
    { path: 'login', component: LoginComponent },
    { path: 'home', component: HomeComponent },
    { path: 'prenotazioni', component: PrenotazioniComponent },
    { path: '', redirectTo: '/login', pathMatch: 'full' }
];
