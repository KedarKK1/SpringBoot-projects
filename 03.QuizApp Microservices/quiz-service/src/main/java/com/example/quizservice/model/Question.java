package com.example.quizservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data; // this reduces gettersAnsSetters that we'll have to write for each field. eg. getQuestioNTitle and setQuestionTitle reduced by @Data
// if in Database we have different entity (name) use @Entity("newName")

@Data
@Entity
public class Question {
	
	// below ordering of declaration is what we get when we request through API, so order it properly 
	
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
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getQuestionTitle() {
		return questionTitle;
	}
	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	public String getOption3() {
		return option3;
	}
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	public String getOption4() {
		return option4;
	}
	public void setOption4(String option4) {
		this.option4 = option4;
	}
	public String getRightAnswer() {
		return rightAnswer;
	}
	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}
	public String getDifficultylevel() {
		return difficultylevel;
	}
	public void setDifficultylevel(String difficultylevel) {
		this.difficultylevel = difficultylevel;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public Question(Integer id, String questionTitle, String option1, String option2, String option3, String option4,
			String rightAnswer, String difficultylevel, String category) {
		super();
		this.id = id;
		this.questionTitle = questionTitle;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.rightAnswer = rightAnswer;
		this.difficultylevel = difficultylevel;
		this.category = category;
	}
	
	public Question() {
        // Default constructor for JPA
    }

}
