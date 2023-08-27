package com.example.questionservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.questionservice.model.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer>{
	// in JpaRepository you have to mention TableName i.e. Question and primaryKey type i.e. Integer for our id field

	List<Question> findByCategory(String category);
	
	Question getById(Integer id);

	// either create a method like below or make use of Query 
//	static List<Question> findRandomQuestionsByCategory(String category, int numQ) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	// JPA can give you thing upto a point, then you have to use get data by yourself, using JPSQL
	
	@Query(value = "SELECT * FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
	List<Question> findRandomQuestionsByCategory(String category, int numQ);
	
	@Query(value = "SELECT q.id FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
	List<Integer> findRandomQuestionsIdByCategory(String category, int numQ);
	
	// jpql - jpa query language - like in above we are creating a method findByCategory which finds by category as we pass in category, we can create more such simple methods using jpql
	// hql - hybernate query language - for more customization of queries
}
