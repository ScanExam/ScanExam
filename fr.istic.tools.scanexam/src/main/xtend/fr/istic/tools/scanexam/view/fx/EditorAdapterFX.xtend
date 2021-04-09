package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.view.fx.editor.ControllerFXEditor
import fr.istic.tools.scanexam.presenter.PresenterEdition
import fr.istic.tools.scanexam.view.AdapterEdition

class EditorAdapterFX implements AdapterEdition {
	
	PresenterEdition presenter;
	ControllerFXEditor controller;
	
	override setPresenter(PresenterEdition presenter)
	{
		this.presenter = presenter
	}	
	
	def void setControllerFXCreator(ControllerFXEditor controller) {
		this.controller = controller;
	}
	
	
	override getPresenter()
	{
		presenter;
	}
	
	
	
}