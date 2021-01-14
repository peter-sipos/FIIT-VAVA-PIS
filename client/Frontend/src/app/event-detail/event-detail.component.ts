import { Component, OnInit, ElementRef } from '@angular/core';
import { Event } from '../_models/event';
import { ActivatedRoute } from '@angular/router';
import { Scout } from '../_models';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.css']
})
export class EventDetailComponent implements OnInit {

  event: Event;
  scouts: Scout[] = null;
  show: boolean = false;
  modal_scout: Scout;
  baseUrl: String = `${window.location.protocol}//${window.location.hostname}:8080/PIS_exploded/dbAPI`;


  constructor(private route: ActivatedRoute, private httpClient: HttpClient ) {}

  ngOnInit() {
    this.event = JSON.parse(localStorage.getItem('event-detail'));
    this.show = false;

    this.httpClient.post(this.baseUrl + "/getUcast", { 
      body: JSON.stringify(this.event)
     }).subscribe((res : any[])=>{
      this.scouts = res;

      if (this.scouts.length == 0)
        this.scouts = null;
    });
    
  }

  ngOnDestroy() {
    localStorage.removeItem('event-detail');
  }

  showScout(i: number) {
    this.modal_scout = this.scouts[i];
    this.show = true;
  }

  close() {
    this.show = false;
    this.modal_scout = null;
  }

}
