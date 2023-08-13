package com.example.myquizapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.myquizapp.Question;
import com.example.myquizapp.dao.QuestionDao;

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
	
	public List<Question> getQuestionsByCategory(String category){
		return questionDao.findByCategory(category);
	}
	
	public Question getQuestionById(Integer id) {
		return questionDao.getById(id);
	}
	
	public String addQuestion(Question question) {
		questionDao.save(question);
		return "successss!!!";
	}
	
	public String deleteQuestionById(Integer id) {
		questionDao.deleteById(id);
		return "Suceess!!";
	}
	
	public String updateQuestion(Question updatedQuestion) {
		questionDao.save(updatedQuestion); // save is used for both creating & updating
		return "Suceeess!!";
	}
	
}
