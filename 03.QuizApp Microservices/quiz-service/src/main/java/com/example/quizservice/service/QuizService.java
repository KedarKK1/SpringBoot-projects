package com.example.quizservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.quizservice.dao.QuizDao;
import com.example.quizservice.feign.QuizInterface;
import com.example.quizservice.model.Question;
import com.example.quizservice.model.QuestionWrapper;
import com.example.quizservice.model.Quiz;
import com.example.quizservice.model.Response;

@Service
public class QuizService {
	// This will be Many-to-Many model as one qn can be in multipule Quizes and one quiz can have multiple qns.
	
	@Autowired
	QuizDao quizDao;
	
	@Autowired
	QuizInterface quizInterface;

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		
		// call the url from questionService Microservice - RestTemplate - http://localhost:8080/question/generate
		// Feign is like declarative way of requesting other services
		// service discovery - Eureka (Netflix 	eureka server connected using Eureka client), is where all services are registered to each other,
		// by using Eureka, we dont have to much worry about localhost (hostName) and ports on which other services are exposed!
		
		List<Integer> questions = quizInterface.getQuestionsForQuiz(category, String.valueOf(numQ)).getBody();
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestions(questions);
		
		quizDao.save(quiz);
		
		return new ResponseEntity<>	("Success", HttpStatus.CREATED);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		
		Quiz quiz = quizDao.findById(id).get();
		List<Integer> questionIds= quiz.getQuestions(); // or getQuestionIds
		
		ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);
		
		return questions;
	}

	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

		ResponseEntity<Integer> score = quizInterface.getScore(responses);
		
		return score;
	}
}
