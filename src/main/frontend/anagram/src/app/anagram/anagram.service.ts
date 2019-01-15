import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';

@Injectable()
export class AnagramService {

  private anagramsUrl = 'http://localhost:8080/api/anagrams';  // URL to web api

  constructor(
    private http: HttpClient
  ) { }

  getAnagrams(word: String): Observable<String[]> {
    if (word === undefined) {
      return of([]);
    }
    return this.http.get<String[]>(this.anagramsUrl + '?word=' + word)
  }

}
