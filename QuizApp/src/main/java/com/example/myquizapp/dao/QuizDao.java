package com.example.myquizapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myquizapp.model.Quiz;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer> {
	

}
