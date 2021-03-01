package fr.istic.tools.scanexam.view.fX

import fr.istic.tools.scanexam.presenter.EditorPresenter
import fr.istic.tools.scanexam.view.EditorAdapter


class EditorAdapterFX implements EditorAdapter {
	
	EditorPresenter presenter;
	ControllerFXCreator controller;
	
	override setPresenter(EditorPresenter presenter)
	{
		this.presenter = presenter
	}
	
	

	
	def void addBox(Box box) 
	{
		box.boxId = presenter.presenterQuestionZone.createQuestion(box.x,box.y,box.height,box.width)
	}
	
	def void removeBox(Box box) 
	{ 
		presenter.presenterQuestionZone.removeQuestion(box.boxId);
	}
	
	def void updateBox(Box box) {//updates a box in the model via the presenter
		
	}
	
	def void setControllerFXCreator(ControllerFXCreator controller) {
		this.controller = controller;
	}
	
	override getPresenter()
	{
		presenter;
	}
	
}