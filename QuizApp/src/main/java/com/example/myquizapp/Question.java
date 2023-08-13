package com.example.myquizapp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data; // this reduces gettersAnsSetters that we'll have to write for each field. eg. getQuestioNTitle and setQuestionTitle reduced by @Data
// if in Database we have different entity (name) use @Entity("newName")

@Data
@Entity
public class Question {
	
	// in sql - snake_casing in java - camelCasing

	// in java ORM, _ is converted to capital letter. i.e. in database question_title is here questionTitle
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	// @GeneratedValue(strategy = GenerationType.IDENTITY) makes sure about autoIncrement, so in post object for creation, we dont need any id value
	
	public String questionTitle;
	public String option1;
	public String option2;
	public String option3;
	public String option4;
	public String rightAnswer;
	public String difficultylevel;
	public String category;
}
