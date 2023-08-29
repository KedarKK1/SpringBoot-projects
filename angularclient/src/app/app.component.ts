import { Component } from '@angular/core';

// @Component metadata marker or decorator, which defines three elements:

// selector – the HTML selector used to bind the component to the HTML template file
// templateUrl – the HTML template file associated with the component
// styleUrls – one or more CSS files associated with the component

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  // title = 'angularclient';
  title: string;

  constructor() {
    // constructor initializes the field with a string value
    this.title = 'Quiz App - Spring Boot - Angular Application';
  }

}
