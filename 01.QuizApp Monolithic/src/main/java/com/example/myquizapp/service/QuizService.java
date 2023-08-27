package com.example.myquizapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.myquizapp.dao.QuestionDao;
import com.example.myquizapp.dao.QuizDao;
import com.example.myquizapp.model.Question;
import com.example.myquizapp.model.QuestionWrapper;
import com.example.myquizapp.model.Quiz;
import com.example.myquizapp.model.Response;

@Service
public class QuizService {
	// This will be Many-to-Many model as one qn can be in multipule Quizes and one quiz can have multiple qns.
	
	@Autowired
	QuizDao quizDao;
	
	@Autowired
	QuestionDao questionDao;

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		
		List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
		
		Quiz quiz = new Quiz();
		
		quiz.setTitle(title);
		quiz.setQuestions(questions);
		quizDao.save(quiz);
		
		return new ResponseEntity<>	("Success", HttpStatus.CREATED);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		Optional<Quiz> quiz = quizDao.findById(id);
		List<Question> questionsFromDB = quiz.get().getQuestions();
		List<QuestionWrapper> questionsForUser = new ArrayList<>();
		for(Question q: questionsFromDB) {
			QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
			questionsForUser.add(qw);
		}
		
		return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
		
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		// either use Optional<Quiz> quiz = quizDao.findById(id); or use below method using get()
		Quiz quiz = quizDao.findById(id).get(); 
		List<Question> questions = quiz.getQuestions();
		
		int right = 0;		
//		int i = 0;
//		for(Response response : responses) {
////			if(response.getResponse().equals(questions.get(i).getRightAnswer())) {
////				right = right + 1;
////				}
////			i = i + 1;
//		}

		for (int i = 0; i < responses.size(); i++) {
			Response response = responses.get(i);
//			System.out.println("Response: " + response.getResponse());
//		    System.out.println("Expected: " + questions.get(i).getRightAnswer());

			if (response.getResponse().equals(questions.get(i).getRightAnswer())) {
		        right = right + 1;
		    }
		}
		
		return new ResponseEntity<Integer>(right, HttpStatus.OK);
	}
}
