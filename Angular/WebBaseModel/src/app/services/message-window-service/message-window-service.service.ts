import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessageWindowService {

  private showWindowMessage: boolean = false;
  private windowMessage: string | undefined;

  constructor() { }

  showWindow(show: boolean, message?: string) {
    this.showWindowMessage = show;
    this.windowMessage = message;
  }

  getShow(): boolean {
    return this.showWindowMessage;
  }

  getMessage(): string | undefined {
    return this.windowMessage;
  }

}
