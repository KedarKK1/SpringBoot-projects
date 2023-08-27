package com.example.questionservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.questionservice.dao.QuestionDao;
import com.example.questionservice.model.Question;
import com.example.questionservice.model.QuestionWrapper;
import com.example.questionservice.model.Response;

@Service
public class QuestionService {
	
	@Autowired
	QuestionDao questionDao;
	
	public List<Question> getAllQuestions(){
		// service is not doing any processing, it's just fetching data form DAO
		return questionDao.findAll();
		
		// Alternatively, you can also use the @Query annotation to apply your query.
		// @Query("select p from Pet p where p.age = ?1")
		// List<Pet> findAllByAge(Integer age);
	}
	
	public ResponseEntity<List<Question>> getQuestionsByCategory(String category){
		// no need to do exception handling in QuestionService as we have already done in QuestionController, here we are just returning data!
		try {			
			return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}
	
	public Question getQuestionById(Integer id) {		
		return questionDao.getById(id);
	}
	
	public ResponseEntity<String> addQuestion(Question question) {
		
		questionDao.save(question);
		return new ResponseEntity<>("successss!!!", HttpStatus.CREATED);
	}
	
	public String deleteQuestionById(Integer id) {
		questionDao.deleteById(id);
		return "Suceess!!";
	}
	
	public String updateQuestion(Question updatedQuestion) {
		questionDao.save(updatedQuestion); // save is used for both creating & updating
		return "Suceeess!!";
	}

	public ResponseEntity<List<Integer>> getQuestionsForQuizMicroservice(String categoryName, Integer numQuestions) {
		
		List<Integer> questions = questionDao.findRandomQuestionsIdByCategory(categoryName, numQuestions);
		
		return new ResponseEntity<>	(questions, HttpStatus.CREATED);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromIds(List<Integer> questionIds) {
		List<QuestionWrapper> wrappers = new ArrayList<>();
		List<Question> questions = new ArrayList<>(); 
		
		for(Integer id: questionIds) {
			questions.add(questionDao.findById(id).get());
		}
		for(Question question: questions) {
			QuestionWrapper wrapper = new QuestionWrapper();
			wrapper.setId(question.getId());
			wrapper.setQuestionTitle(question.getQuestionTitle());
			wrapper.setOption1(question.getOption1());
			wrapper.setOption2(question.getOption2());
			wrapper.setOption3(question.getOption3());
			wrapper.setOption4(question.getOption4());
			
			wrappers.add(wrapper);
			
			
		}
		
		return new ResponseEntity<>(wrappers, HttpStatus.OK);
	}

	public ResponseEntity<Integer> getScore(List<Response> responses) {
		// either use Optional<Quiz> quiz = quizDao.findById(id); or use below method using get()
				
		int right = 0;		
		for(Response response : responses) {
			Question question = questionDao.findById(response.getId()).get();
			if(response.getResponse().equals(question.getRightAnswer())) {
				right = right + 1;
			}
		}

//				for (int i = 0; i < responses.size(); i++) {
//					Response response = responses.get(i);
////					System.out.println("Response: " + response.getResponse());
////				    System.out.println("Expected: " + questions.get(i).getRightAnswer());
//
//					if (response.getResponse().equals(questions.get(i).getRightAnswer())) {
//				        right = right + 1;
//				    }
//				}
				
		return new ResponseEntity<Integer>(right, HttpStatus.OK);
	}
	
}
