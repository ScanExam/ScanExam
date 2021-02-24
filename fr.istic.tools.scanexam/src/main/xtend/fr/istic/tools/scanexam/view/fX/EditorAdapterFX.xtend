package fr.istic.tools.scanexam.view.fX

import fr.istic.tools.scanexam.view.EditorAdapter
import fr.istic.tools.scanexam.presenter.Presenter
import fr.istic.tools.scanexam.presenter.PresenterVueCreation

class EditorAdapterFX implements EditorAdapter {
	
	PresenterVueCreation presenter;
	override setPresenter(Presenter presenter) {
		this.presenter = presenter as PresenterVueCreation 
	}
	
}