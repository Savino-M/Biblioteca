import { TestBed } from '@angular/core/testing';

import { MessageWindowService } from './message-window-service.service';

describe('MessageWindowService', () => {
  let service: MessageWindowService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MessageWindowService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
