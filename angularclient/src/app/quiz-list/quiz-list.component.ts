import { Component, OnInit } from '@angular/core';
import { Quiz } from '../quiz';
import { QuizServiceService } from '../quiz-service.service';

@Component({
  selector: 'app-quiz-list',
  templateUrl: './quiz-list.component.html',
  styleUrls: ['./quiz-list.component.css']
})
export class QuizListComponent implements OnInit {
  quizes: Quiz[];

  constructor(private quizService: QuizServiceService) {
    this.quizes = [];
  }

  ngOnInit() {
    this.quizService.findAll().subscribe(data => {
      console.log("data ", data)
      this.quizes = data;
    });
  }

}
