import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Event } from '../_models/event';
import { Email_object } from '../_models/email_object';


@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent implements OnInit {

  emailForm: FormGroup;
  email_object: Email_object = new Email_object();
  event: Event = null;
  submitted = false;
  success = false;
  qr_wanted: boolean = false;
  baseUrl: String = `${window.location.protocol}//${window.location.hostname}:8080/PIS_exploded`;

  constructor(private formBuilder: FormBuilder, private httpClient: HttpClient ) { 
    this.emailForm = this.formBuilder.group({
      skauti: [Boolean],
      skautky: [Boolean],
      vlcata: [Boolean],
      vcielky: [Boolean],
      subject: ['', Validators.required],
      message: ['', Validators.required],
      event_name: [''],
      qr_code: [Boolean],
    })
  }

  ngOnInit() {
    if (localStorage.getItem('event-email')) {
      this.event = JSON.parse(localStorage.getItem('event-email'));
      this.qr_wanted = true;
    }
    else {
      this.qr_wanted = false;
    }

  }

  pick_event() {
    this.qr_wanted = true;
  }

  fill_email_object() {
    if (this.emailForm.controls.skauti.value)
      this.email_object.skauti = 1;
    else this.email_object.skauti = 0;

    if (this.emailForm.controls.skautky.value)
      this.email_object.skautky = 1;
    else this.email_object.skautky = 0;

    if (this.emailForm.controls.vlcata.value)
      this.email_object.vlcata = 1;
    else this.email_object.vlcata = 0;

    if (this.emailForm.controls.vcielky.value)
      this.email_object.vcielky = 1;
    else this.email_object.vcielky = 0;

    this.email_object.predmet = this.emailForm.controls.subject.value;
    this.email_object.sprava = this.emailForm.controls.message.value;

    if (this.event) this.email_object.id_akcie = this.event.id;
    else this.email_object.id_akcie = 0;

    if (this.emailForm.controls.qr_code.value)
      this.email_object.qr_code = 1;
    else this.email_object.qr_code = 0;
  }

  postEvent() {
    this.httpClient.post(this.baseUrl + "/emailAPI/posliEmail", { 
      body: JSON.stringify(this.email_object)
     }).subscribe((res : any[])=>{
    });
  }

  onSubmit() {
    this.submitted = true;

    if (this.emailForm.invalid) return;   
    
    this.fill_email_object();
    this.postEvent();

    this.success = true;

    this.submitted = false;
    this.success = false;
    this.qr_wanted = false;
    this.emailForm.reset();
    if (localStorage.getItem('event-email'))
      localStorage.removeItem('event-email');

    window.alert("Email poslaný");

  }

  ngOnDestroy() {
    localStorage.removeItem('event-email');
    this.qr_wanted = false;
  }

}