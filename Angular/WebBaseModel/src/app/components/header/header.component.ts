import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  private date: Date = new Date();
  currentTime: number = 0;

  user: String = "Mario rossi";

  ngOnInit(): void {
    this.setCurrentTimeInterval();
  }

  private setCurrentTimeInterval() {
    setInterval(() => {
      this.date.setSeconds(this.date.getSeconds() + 1);
      this.currentTime = this.date.getTime();
    }, 1000);
  }



}
