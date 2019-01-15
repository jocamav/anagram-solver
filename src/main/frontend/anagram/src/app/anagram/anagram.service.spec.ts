import { TestBed, inject } from '@angular/core/testing';

import { AnagramService } from './anagram.service';

describe('AnagramService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AnagramService]
    });
  });

  it('should be created', inject([AnagramService], (service: AnagramService) => {
    expect(service).toBeTruthy();
  }));
});
