import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { HeaderComponent } from '../components/header/header.component';
import { FooterComponent } from '../components/footer/footer.component';
import { LoaderComponent } from '../components/loader/loader.component';
import { MessageWindowComponent } from '../components/message-window/message-window.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, HeaderComponent, FooterComponent, LoaderComponent, MessageWindowComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  showHeader: any;

  constructor(router: Router) {

    router.events.subscribe(event => {
      if (router.url === '/login' || router.url === '/register') {
        this.showHeader = false;
      } else {
        this.showHeader = true;
      }
    });

  }
}
