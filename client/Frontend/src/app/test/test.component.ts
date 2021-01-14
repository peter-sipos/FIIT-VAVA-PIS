import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';
import { HttpClient } from '@angular/common/http';
import { Event } from '../_models/event';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit {

  events: Event[];
  baseUrl: String = "http://localhost:8080/PIS_exploded/dbAPI";

  constructor(private httpClient: HttpClient) { }

  ngOnInit() {
    this.httpClient.get(this.baseUrl + "/getEvents").subscribe((res : any[])=>{
      this.events = res;
      console.log("Events: " + this.events);
    });
  }

}
