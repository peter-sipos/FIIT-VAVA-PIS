import { Component, HostListener, LOCALE_ID, Inject } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from './_services';
import { User } from './_models';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  currentUser: User;
  screenWidth: any;
  languageList = [
    { code: 'en', label: 'English' },
    { code: 'sk', label: 'Slovak' }

  ];

    constructor(
        private router: Router,
        private authenticationService: AuthenticationService,
        @Inject(LOCALE_ID) protected localeId: string
    ) {
        this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    }

    logout() {
        this.authenticationService.logout();
        this.router.navigate(['/login']);
    }

    ngOnInit() {
      this.screenWidth = window.innerWidth;
    }

    @HostListener('window:resize', ['$event'])
    onResize(event) {
      this.screenWidth = window.innerWidth;
    }
}
