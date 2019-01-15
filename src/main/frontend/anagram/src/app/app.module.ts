import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';

//Prime NG Components
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { MessagesModule } from 'primeng/messages';
import { ProgressBarModule } from 'primeng/progressbar';

//App components
import { AppComponent } from './app.component';
import { AnagramComponent } from './anagram/anagram.component';


import { AnagramService } from './anagram/anagram.service';

@NgModule({
  declarations: [
    AppComponent,
    AnagramComponent
  ],
  imports: [
    BrowserModule, FormsModule, BrowserAnimationsModule, HttpClientModule,
    InputTextModule, ButtonModule, TableModule, MessagesModule, ProgressBarModule
  ],
  providers: [AnagramService],
  bootstrap: [AppComponent]
})
export class AppModule { }
