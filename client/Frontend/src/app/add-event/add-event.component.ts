import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { post } from 'selenium-webdriver/http';
import { Event } from '../_models/event';

@Component({
  selector: 'app-add-event',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.css']
})

export class AddEventComponent implements OnInit {

  event: Event = new Event();
  eventForm: FormGroup;
  submitted = false;
  success = false;
  baseUrl: String = `${window.location.protocol}//${window.location.hostname}:8080/PIS_exploded/dbAPI`;

  constructor(private formBuilder: FormBuilder, private httpClient: HttpClient ) { 
    this.eventForm = this.formBuilder.group({
      nazov: ['', Validators.required],
      zodpovedna_osoba_meno: ['', Validators.required],
      zodpovedna_osoba_priezvisko: ['', Validators.required],
      miesto_konania: ['', Validators.required],
      datum: ['', Validators.required],
      oddiel: ['', Validators.required],
      popis: ['', Validators.required],
    })
  }

  postEvent() {
    this.httpClient.post(this.baseUrl + "/pridajAkciu", { 
      nazov: this.eventForm.controls.nazov.value,
      popis: this.eventForm.controls.popis.value,
      zodpovednaOsobaMeno: this.eventForm.controls.zodpovedna_osoba_meno.value,
      zodpovednaOsobaPriezvisko: this.eventForm.controls.zodpovedna_osoba_priezvisko.value,
      miestoKonania: this.eventForm.controls.miesto_konania.value,
      oddiel: this.eventForm.controls.oddiel.value,
      datum: this.eventForm.controls.datum.value,

     }).subscribe((res : any[])=>{
      // console.log(res);
    });
  }

  onSubmit() {
    this.submitted = true;

    if (this.eventForm.invalid) return;
    
    this.postEvent();
    
    this.success = true;
  }

  ngOnInit() {
  }

}
