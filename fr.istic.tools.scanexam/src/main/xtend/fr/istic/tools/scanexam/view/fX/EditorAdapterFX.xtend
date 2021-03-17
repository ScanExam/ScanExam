package fr.istic.tools.scanexam.view.fX

import fr.istic.tools.scanexam.presenter.EditorPresenter
import fr.istic.tools.scanexam.view.EditorAdapter
import fr.istic.tools.scanexam.view.fX.editor.ControllerFXEditor
import fr.istic.tools.scanexam.view.fX.editor.Box

class EditorAdapterFX implements EditorAdapter {
	
	EditorPresenter presenter;
	ControllerFXEditor controller;
	
	override setPresenter(EditorPresenter presenter)
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