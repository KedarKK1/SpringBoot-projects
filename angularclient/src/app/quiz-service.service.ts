import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Quiz } from './quiz';
import { Observable } from 'rxjs';

// it encapsulates within a reusable component all the functionality required to consume the REST controller API that we implemented before in Spring Boot
// note the use of the @Injectable() metadata marker. This signals that the service should be created and injected via Angular's dependency injectors
@Injectable({
  providedIn: 'root'
})
export class QuizServiceService {

  private quizUrl: string;
  private submitUrl: string;

  constructor(private http: HttpClient) {
    this.quizUrl = 'http://localhost:8765/QUIZ-SERVICE/quiz/get/1';
    this.submitUrl = 'http://localhost:8765/QUESTION-SERVICE/question/getScore';
  }

  public findAll(): Observable<Quiz[]> {
    console.log(" this.http.get<Quiz[]>(this.quizUrl) ", this.http.get<Quiz[]>(this.quizUrl));
    return this.http.get<Quiz[]>(this.quizUrl);
  }

  // public save(quiz: Quiz) {
  //   return this.http.post<Quiz>(this.quizUrl, quiz);
  // }

  public submitQuiz(selectedResponses: any[]): Observable<any> {
    return this.http.post(this.submitUrl, selectedResponses);
  }

  // public submitQuiz(selectedResponses: ): Observable<String> {

  //   return this.http.post(this.submitUrl, selectedResponses)
  //       .subscribe(
  //         response => {
  //           console.log(response); // Handle the response from the server
  //         },
  //         error => {
  //           console.error('Error:', error);
  //         }
  //       );
  // }

}
