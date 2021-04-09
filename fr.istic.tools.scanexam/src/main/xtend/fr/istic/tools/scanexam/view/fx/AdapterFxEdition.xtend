package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition
import fr.istic.tools.scanexam.presenter.PresenterEdition
import fr.istic.tools.scanexam.view.AdapterEdition

class AdapterFxEdition implements AdapterEdition {
	
	PresenterEdition presenter;
	ControllerFxEdition controller;
	
	override setPresenter(PresenterEdition presenter)
	{
		this.presenter = presenter
	}	
	
	def void setControllerFXCreator(ControllerFxEdition controller) {
		this.controller = controller;
	}
	
	
	override getPresenter()
	{
		presenter;
	}
	
	
	
}