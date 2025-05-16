// encryption.service.ts
import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';

@Injectable({
    providedIn: 'root'
})
export class EncryptionService {

    private key: string = '00112233445566778899aabbccddeeff'; // Usa la stessa chiave del backend

    encrypt(data: string): string {
        const iv = CryptoJS.lib.WordArray.random(16);
        const encrypted = CryptoJS.AES.encrypt(CryptoJS.enc.Utf8.parse(data), CryptoJS.enc.Hex.parse(this.key), {
            iv: iv,
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });

        // Unisci IV e dati cifrati
        const encryptedDataWithIv = iv.concat(encrypted.ciphertext).toString(CryptoJS.enc.Base64);

          // Rendi la stringa URL-safe
        const urlSafeEncryptedData = encryptedDataWithIv
            .replace(/\+/g, '-') // Sostituisci '+' con '-'
            .replace(/\//g, '_') // Sostituisci '/' con '_'
            .replace(/=+$/, ''); // Rimuovi '=' alla fine (opzionale per Base64 URL-safe)
      
        return urlSafeEncryptedData;
    }
}
