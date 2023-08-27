package com.example.questionservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questionservice.model.Question;
import com.example.questionservice.model.QuestionWrapper;
import com.example.questionservice.model.Response;
import com.example.questionservice.service.QuestionService;

@RestController
@RequestMapping("question")
public class QuestionController {
	
	//@Autowired automatically links it to 
	
	@Autowired
	QuestionService questionService;
	
//	@Autowired
//	Environment environment;
	
	// url => http://localhost:8080/question/allQuestions
	@GetMapping("allQuestions")
	public ResponseEntity<List<Question>> getAllQuestion() {
		// either we can do exception handling in controller or in service, as per your wish!
		//	return "Hi, these are your all questions2";
		try {			
			return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("category/{category}")
	public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
		// if multiple pathvariables, eg. category/{mycategory}/{mydifficulty} then -
		// public List<Question> getQuestionsByCategory(@PathVariable("mycategory") String category, @PathVariable("mydifficulty") String difficulty){
		
		// in this example, exception handling is done in service instead of inside controller
		return questionService.getQuestionsByCategory(category);
	}
	
	@PostMapping("add")
	public ResponseEntity<String> addQuestion(@RequestBody Question question) { // accepting JSON from client
		return questionService.addQuestion(question);
	}
	
	@DeleteMapping("delete/{qid}")
	public String deleteQuestion(@PathVariable("qid") String id) {
		questionService.deleteQuestionById(Integer.parseInt(id));
		return "YEsss!";
	}
	
	@PutMapping("update/{qid}")
	public String updateQuestion(@PathVariable("qid") String id, @RequestBody Question questionData) {
	    int questionId = Integer.parseInt(id);
	    Question existingQuestion = questionService.getQuestionById(questionId);

	    if (existingQuestion != null) {
	        // Update the fields of the existing question with the new data
//	        existingQuestion.setQuestionTitle(questionData.getQuestionTitle());
//	        existingQuestion.setOption1(questionData.getOption1());
//	        existingQuestion.setOption2(questionData.getOption2());
//	        existingQuestion.setOption3(questionData.getOption3());
//	        existingQuestion.setOption4(questionData.getOption4());
//	        existingQuestion.setRightAnswer(questionData.getRightAnswer());
//	        existingQuestion.setDifficultylevel(questionData.getDifficultylevel());
//	        existingQuestion.setCategory(questionData.getCategory());

	        // Call the service method to update the question
//	        questionService.updateQuestion(existingQuestion);
	        
	        questionService.updateQuestion(questionData);

	        return "Question updated successfully!";
	    } else {
	        return "Question with ID " + id + " not found.";
	    }
	}
	
	// FOR question-service MICROSERVICES
	
	// generate questions for quiz
	// give them questions based on qn id getQuestions(questionid)
	// getScores
	
	@GetMapping("generate")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam String numQuestions){
		return questionService.getQuestionsForQuizMicroservice(categoryName, Integer.parseInt(numQuestions));
	}
	
	@PostMapping("getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
//		System.out.println(environment.getProperty("local.server.port"));
		return questionService.getQuestionsFromIds(questionIds);
	}
	
	@PostMapping("getScore")
	public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
		return questionService.getScore(responses);
	}

}
