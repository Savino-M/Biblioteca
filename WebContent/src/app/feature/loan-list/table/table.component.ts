import { Component, OnInit, Input } from '@angular/core';


@Component({
  selector: 'app-table-loan',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})

export class TableComponentLoan implements OnInit {

  @Input() loans!: any[];

  constructor() {  }

  ngOnInit(): void { console.log(this.loans) }

}
