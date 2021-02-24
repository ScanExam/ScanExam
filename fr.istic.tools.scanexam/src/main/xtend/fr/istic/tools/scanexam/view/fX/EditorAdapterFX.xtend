package fr.istic.tools.scanexam.view.fX

import fr.istic.tools.scanexam.view.EditorAdapter
import fr.istic.tools.scanexam.presenter.Presenter
import fr.istic.tools.scanexam.presenter.PresenterVueCreation

class EditorAdapterFX implements EditorAdapter {
	
	PresenterVueCreation presenter;
	override setPresenter(PresenterVueCreation presenter) {
		this.presenter = presenter
	}
	
}