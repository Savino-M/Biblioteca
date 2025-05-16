import { Indirizzo } from "./Indirizzo";
import { Ruolo } from "./Ruolo";

export class Utente {

    public id: any;
    public nome: string;
    public cognome: string;
    public email: string;
    public password: string;
    public codiceFiscale: string;
    public ruolo: Ruolo;
    public indirizzo: Indirizzo;

    constructor(nome: string, cognome: string, email: string, password: string, codiceFiscale: string, ruolo: Ruolo, indirizzo: Indirizzo, id?: string) {

        if(id){
            this.id = id;
        }
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.codiceFiscale = codiceFiscale;
        this.ruolo = ruolo;
        this.indirizzo = indirizzo;

    }

}