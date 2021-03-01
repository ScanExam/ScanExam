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
		
	}
	
	def void removeBox(Box box) 
	{ //removes a box from the model via the presenter
		
	}
	
	def void updateBox(Box box) {//updates a box in the model via the presenter
		
	}
	
	def void setControllerFXCreator(ControllerFXCreator controller) {
		this.controller = controller;
	}
	
	override getPresenter()
	{
	}
	
}