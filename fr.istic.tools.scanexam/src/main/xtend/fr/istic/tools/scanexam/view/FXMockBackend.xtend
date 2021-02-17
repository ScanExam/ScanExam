package fr.istic.tools.scanexam.view

import java.util.List
import java.util.ArrayList
import java.util.LinkedList

class FXMockBackend extends ControllerVueCorrection {
	
	var questions = new ArrayList<Question>();
	Question currentQuestion;
	int index;
	
	
	def void showCurrentQuestion(){
		controllerFX.setSelectedQuestion
	}
	
	def void nextQuestion(){
		index++;
		if (index >= questions.size) {
			index = 0;
		}
		currentQuestion = questions.get(index);
		controllerFX.setSelectedQuestion;
	}
	
	def void previousQuestion(){
		index--;
		if (index < 0) {
			index = questions.size - 1;
		}
		currentQuestion = questions.get(index);
		controllerFX.setSelectedQuestion;
	}
	
	def void selectQuestion(String name) {
		
	}
	
	def String selectedName() {
		return currentQuestion.name
	}
	
	def double selectedX(){
		return currentQuestion.x
	}
	def double selectedY(){
		return currentQuestion.y
	}
	def double selectedHeight(){
		return currentQuestion.h
	}
	def double selectedWidth(){
		return currentQuestion.w
	}
	
	
	static class Question {
		String name
		double x
		double y
		double h
		double w
		int page;
		List<Integer> bareme
		new(String name) {
			this.name = name
			x = 0
			y = 0
			h = 0
			w = 0
		}
	}
	
	def void setQuestions(){
		var Q1 = new Question("Q1");
		Q1.x = 0;
		Q1.y = 0;
		Q1.h = 200;
		Q1.w = 100
		var Q2 = new Question("Q2");
		Q2.x = 30;
		Q2.y = 200;
		Q2.h = 520;
		Q2.w = 50;
		var Q3 = new Question("Q3");
		Q3.x = 100;
		Q3.y = 300;
		Q3.h = 400;
		Q3.w = 100;
		var Q4 = new Question("Q4");
		Q4.x = 130;
		Q4.y = 500;
		Q4.h = 170;
		Q4.w = 100;
		questions.add(Q1)
		questions.add(Q2)
		questions.add(Q3)
		questions.add(Q4)
		currentQuestion = questions.get(0);
		var stringList = new LinkedList<String>();
		for (Question q : questions) {
			stringList.add(q.name);
		}
		controllerFX.initQuestions(stringList);
		index = 0;
		showCurrentQuestion
		
	}
	

	
}
