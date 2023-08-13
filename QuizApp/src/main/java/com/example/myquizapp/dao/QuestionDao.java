package com.example.myquizapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myquizapp.Question;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer>{
	// in JpaRepository you have to mention TableName i.e. Question and primaryKey type i.e. Integer for our id field

	List<Question> findByCategory(String category);
	
	Question getById(Integer id);
	
	// jpql - jpa query language - like in above we are creating a method findByCategory which finds by category as we pass in category, we can create more such simple methods using jpql
	// hql - hybernate query language - for more customization of queries
}
