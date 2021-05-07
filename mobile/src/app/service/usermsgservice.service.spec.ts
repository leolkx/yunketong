import { TestBed } from '@angular/core/testing';

import { UsermsgserviceService } from './usermsgservice.service';

describe('UsermsgserviceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UsermsgserviceService = TestBed.get(UsermsgserviceService);
    expect(service).toBeTruthy();
  });
});
