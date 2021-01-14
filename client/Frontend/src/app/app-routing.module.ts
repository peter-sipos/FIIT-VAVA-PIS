import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddEventComponent } from './add-event/add-event.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuard } from './_guards';
import { CalendarComponent } from './calendar/calendar.component';
import { ScannerComponent } from './scanner/scanner.component';
import { EventDetailComponent } from './event-detail/event-detail.component';
import { EmailComponent } from './email/email.component';
import { AttendanceComponent } from './attendance/attendance.component';
import { TestComponent } from './test/test.component';

const routes: Routes = [
  { path: '', component: CalendarComponent, canActivate: [AuthGuard]},
  { path: 'add-event', component: AddEventComponent, canActivate: [AuthGuard]},
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'scanner', component: ScannerComponent },
  { path: 'event-detail', component: EventDetailComponent },
  { path: 'email', component: EmailComponent, canActivate: [AuthGuard] },
  { path: 'attendance', component: AttendanceComponent, canActivate: [AuthGuard] },
  { path: 'test', component: TestComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: '' }    // all other roots redirected to homepage

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
