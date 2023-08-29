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
  selectedAnswers: any = {}; 
  score: string = ""; 

  constructor(private quizService: QuizServiceService) {
    this.quizes = [];
    this.selectedAnswers = {};
    this.score="";
  }

  ngOnInit() {
    this.quizService.findAll().subscribe(data => {
      // console.log("data ", data);
      this.quizes = data;
    });

    // this.submitQuiz().subscribe(data => {
    //   console.log("data ", data)
    //   this.score = data;
    // })

  }

  submitAnswers() {
    const selectedResponses = [];
    for (const quiz of this.quizes) {
      if (this.selectedAnswers[quiz.id]) {
        selectedResponses.push({
          id: quiz.id,
          // response: quiz.response,
          // questionTitle: quiz.questionTitle,
          response: this.selectedAnswers[quiz.id]
        });
      }
    }

    // console.log("selectedResponses ", selectedResponses);
    this.quizService.submitQuiz(selectedResponses).subscribe(
      data => {
        // console.log("Score: ", data);
        this.score = data;
        window.alert(`Your score is: ${this.score}`); 
      },
      error => {
        console.error('Error:', error);
      }
    );
  }


}
