import { Address } from "./address";

export class User {

    constructor 
    ( //parametri
        public name: string, 
        public surname: string,
        public email: string,
        public password: string,
        public cf: string,
        public address: Address
    ) 

        {

        }

}