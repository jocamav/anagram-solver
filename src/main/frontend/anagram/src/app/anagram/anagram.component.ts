import { Component, OnInit } from '@angular/core';

import { AnagramService } from './anagram.service';
import { error } from 'selenium-webdriver';
import { Message } from 'primeng/components/common/api';

@Component({
  selector: 'app-anagram',
  templateUrl: './anagram.component.html',
  styleUrls: ['./anagram.component.css']
})
export class AnagramComponent implements OnInit {

  word: String;
  anagrams: String[];
  msgs: Message[] = [];
  loading: boolean = false;

  constructor(private anagramService: AnagramService) { }

  ngOnInit() {
  }

  searchAnagrams(): void {
    this.anagrams = null;
    this.msgs = [];
    this.loading = true;
    this.anagramService.getAnagrams(this.word)
      .subscribe(
      anagrams => this.anagrams = anagrams,
      error => {
        this.msgs.push({ severity: 'error', summary: 'ERROR', detail: 'Error getting the anagrams' });
        this.loading = false;
      },
      () => {
        this.loading = false;
      }
      );
  }

}
