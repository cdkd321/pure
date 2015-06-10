package com.mygame.pure;

import java.util.List;

public class Question {
	String question;
	String quesionId; 
	List<String> answers;
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getQuesionId() {
		return quesionId;
	}
	public void setQuesionId(String quesionId) {
		this.quesionId = quesionId;
	}
	public List<String> getQuestions() {
		return answers;
	}
	public void setQuestions(List<String> questions) {
		this.answers = questions;
	}
}
