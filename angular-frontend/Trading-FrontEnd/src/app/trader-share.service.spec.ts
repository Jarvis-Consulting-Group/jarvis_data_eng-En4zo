import { TestBed } from '@angular/core/testing';

import { TraderShareService } from './trader-share.service';

describe('TraderShareService', () => {
  let service: TraderShareService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TraderShareService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
