import { Component, Input, Output, EventEmitter } from '@angular/core';
import { MessageWindowService } from '../../services/message-window-service/message-window-service.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-message-window',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './message-window.component.html',
  styleUrls: ['./message-window.component.css']
})
export class MessageWindowComponent {

  @Input() message!: string;

  constructor(public window: MessageWindowService) { }

  closeWindow() {
    this.window.showWindow(false);
  }

}
