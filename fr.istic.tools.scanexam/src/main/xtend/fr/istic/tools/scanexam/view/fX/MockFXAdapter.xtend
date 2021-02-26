package fr.istic.tools.scanexam.view.fX

import fr.istic.tools.scanexam.core.Question
import java.util.ArrayList
import java.util.LinkedList
import fr.istic.tools.scanexam.api.DataFactory
import fr.istic.tools.scanexam.view.fX.ControllerFX
import java.util.List

class MockFXAdapter extends GraduationAdapterFX {
	
	var questions = new ArrayList<Question>();
	Question currentQuestion;
	int index;
	
	
	public ControllerFX controller;
	override List<String> questionNames(){
		var stringList = new LinkedList<String>();
		for (Question q : questions) {
			stringList.add(q.name);
		}
		stringList
	}
	override void nextQuestion(){
		index++;
		if (index >= questions.size) {
			index = 0;
		}
		currentQuestion = questions.get(index);
		controller.showQuestion(currentQuestion);
	}
	
	override void previousQuestion(){
		index--;
		if (index < 0) {
			index = questions.size - 1;
		}
		currentQuestion = questions.get(index);
		controller.showQuestion(currentQuestion);
	}
	
	def void selectQuestion(String name) {
		
	}
	
	def String selectedName() {
		return currentQuestion.name
	}

	
	def void setQuestions(){
		var df = new DataFactory();
		var Q1 = df.createQuestion(0);
		Q1.zone = df.createRectangle(0,0,210,100);
		Q1.name = "Question 1";
		var Q2 = df.createQuestion(2);
		Q2.zone = df.createRectangle(30,200,520,50);
		Q2.name = "Question 2";
		var Q3 = df.createQuestion(3);
		Q3.zone = df.createRectangle(100,300,400,100);
		Q3.name = "Question 3";
		var Q4 = df.createQuestion(4);
		Q4.zone = df.createRectangle(130,500,170,100);
		Q4.name = "Question 4";
		
		
		questions.add(Q1)
		questions.add(Q2)
		questions.add(Q3)
		questions.add(Q4)
		currentQuestion = questions.get(0);
		var stringList = new LinkedList<String>();
		for (Question q : questions) {
			stringList.add(q.name);
		}
		controller.initQuestionNames(stringList)
		index = 0;
		controller.showQuestion(questions.get(0));
		
	}
	
	override thisQuestion(int index) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override getPresenter() {
		presenter
	}
	

	
}
