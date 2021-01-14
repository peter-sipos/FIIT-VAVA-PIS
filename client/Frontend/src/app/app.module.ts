import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule, FormsModule }    from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpModule } from '@angular/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavComponent } from './nav/nav.component';

import { AlertModule } from 'ngx-bootstrap';
import { AddEventComponent } from './add-event/add-event.component';
import { LoginComponent } from './login/login.component';
import { JwtInterceptor, ErrorInterceptor } from './_helpers';
import { RegisterComponent } from './register/register.component';

import { fakeBackendProvider } from './_helpers';
import { CalendarComponent } from './calendar/calendar.component';
import { ScannerComponent } from './scanner/scanner.component';
import { EventDetailComponent } from './event-detail/event-detail.component';
import { EmailComponent } from './email/email.component';
import { WebcamModule } from 'ngx-webcam';
import { AttendanceComponent } from './attendance/attendance.component';
import { TestComponent } from './test/test.component';
import { CompareDatesPipe } from './compare-dates.pipe';


@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    AddEventComponent,
    LoginComponent,
    RegisterComponent,
    CalendarComponent,
    ScannerComponent,
    EventDetailComponent,
    EmailComponent,
    AttendanceComponent,
    TestComponent,
    CompareDatesPipe,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AlertModule.forRoot(),
    ReactiveFormsModule,
    HttpClientModule,
    WebcamModule, 
    FormsModule,
    HttpModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },

    // provider used to create fake backend
    fakeBackendProvider
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
