import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableComponentLoan } from './table.component';

describe('TableComponent', () => {
  let component: TableComponentLoan;
  let fixture: ComponentFixture<TableComponentLoan>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TableComponentLoan ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TableComponentLoan);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
