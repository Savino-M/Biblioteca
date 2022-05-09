import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableComponentBook } from './table.component';

describe('TableComponent', () => {
  let component: TableComponentBook;
  let fixture: ComponentFixture<TableComponentBook>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TableComponentBook ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TableComponentBook);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
