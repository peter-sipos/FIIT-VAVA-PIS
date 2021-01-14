import { Component, OnInit } from '@angular/core';
import { Event } from '../_models/event';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-attendance',
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.css']
})
export class AttendanceComponent implements OnInit {

  event: Event;
  scann: Boolean = false;
  searchForm: FormGroup;
  attendanceForm: FormGroup;
  submitted = false;
  success = false;
  submittedSearch = false;
  successSearch = false;
  id_clen: number = -1;
  baseUrl: String = `${window.location.protocol}//${window.location.hostname}:8080/PIS_exploded/dbAPI`;
  karty: Number[];
  image: String;

  constructor(private route: ActivatedRoute, private formBuilder: FormBuilder, private httpClient: HttpClient) {
    this.searchForm = this.formBuilder.group({
      meno_ucastnika: ['', Validators.required],
      priezvisko_ucastnika: ['', Validators.required],
    });

    this.attendanceForm = this.formBuilder.group({
      isic: Boolean(false),
      vlaky: Boolean(false),
      poistenca: Boolean(false),
      poplatok: [Number, Validators.required],
      prihlaska: Boolean(false),
    });

   }

  ngOnInit() {
    this.event = JSON.parse(localStorage.getItem('event-detail'));
  }

  openCamera() {
    this.scann = true;
  }

  receiveId($event) {
    this.id_clen = $event;
    document.getElementById("top").style.display = "none";
    document.getElementById("bottom").style.display = "block";
  }

  ngOnDestroy() {
    localStorage.removeItem('event-detail');
  }

  postAttendance() {
    this.httpClient.post(this.baseUrl + "/pridajDochadzku", { 

      idAkcie: this.event.id,
      idClena: this.id_clen,
      poplatok: this.attendanceForm.controls.poplatok.value,
      isic: this.attendanceForm.controls.isic.value,
      vlaky: this.attendanceForm.controls.vlaky.value,
      poistenca: this.attendanceForm.controls.poistenca.value,
      prihlaska: this.attendanceForm.controls.prihlaska.value,

     }).subscribe((res : number)=>{
      document.getElementById("top").style.display = "block";
      document.getElementById("bottom").style.display = "none";
    });
  }

  onSubmit() {
    this.submitted = true;

    if (this.attendanceForm.invalid) return;
    
    this.postAttendance();
    
    this.success = true;
  }

  postSearch() {
    this.httpClient.post(this.baseUrl + "/vratIdClena", { 
      meno: this.searchForm.controls.meno_ucastnika.value,
      priezvisko: this.searchForm.controls.priezvisko_ucastnika.value,
     }).subscribe((res : number)=>{
      this.id_clen = res;
    });
  }

  onSubmitSearch() {
    this.submittedSearch = true;
    if (this.searchForm.invalid) return;
    
    this.postSearch();
    
    this.successSearch = true;
  }

}