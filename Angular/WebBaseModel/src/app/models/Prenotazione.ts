import { Elemento } from "./Elemento";
import { Utente } from "./Utente";

export class Prenotazione {

    public id:any;
    public idUtente: any;
    public idElemento: string;
    public elemento: any;

    constructor(idUtente: any, idElemento: string, elemento?: Elemento, id?: string) {

        this.idUtente = idUtente;
        this.idElemento = idElemento;
        if(elemento){
            this.elemento = elemento;
        }
        if(id){
            this.id = id;
        }
        
    }

}