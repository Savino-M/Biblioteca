export class Elemento {

    public id:string;
    public descrizione:string;
    public copertina:any;
    public immaginiList:any[];

    constructor(id:string, descrizione:string, copertina:any, immaginiList:any[]) {
        
        this.id = id;
        this.descrizione = descrizione;
        this.copertina = copertina;
        this.immaginiList = immaginiList;

    }

}