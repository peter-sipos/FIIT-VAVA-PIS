import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import { Event } from '../_models/event';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css'],
})

export class CalendarComponent implements OnInit {

  public events: Event[];
  baseUrl: String = `${window.location.protocol}//${window.location.hostname}:8080/PIS_exploded/dbAPI`;
  loading: Boolean = false;

  constructor(private router: Router, private httpClient: HttpClient) {}

  ngOnInit() {
    this.httpClient.get(this.baseUrl + "/getAkcie").subscribe((res : any[])=>{
      this.events = res;
    });
  }

  showDetail(i: number) {
    localStorage.setItem('event-detail', JSON.stringify(this.events[i]));
    this.router.navigate(['../event-detail']);
  }

  markAttendance(i: number) {
    localStorage.setItem('event-detail', JSON.stringify(this.events[i]));
    this.router.navigate(['../attendance']);
  }

  future(i: number): boolean {
    let tmp = this.events[i].datum.valueOf();
    var today = new Date();
    let eventDate = new Date(tmp[6]+tmp[7]+tmp[8]+tmp[9]+','+tmp[3]+tmp[4]+','+tmp[0]+tmp[1]);

    if (eventDate >= today) return true;
    else return false;
  }

  send_mail(i: number) {
    localStorage.setItem('event-email', JSON.stringify(this.events[i]));
    this.router.navigate(['../email']);
  }
}
