package fr.istic.tools.scanexam.view.fX

import fr.istic.tools.scanexam.presenter.EditorPresenter
import fr.istic.tools.scanexam.view.EditorAdapter

class EditorAdapterFX implements EditorAdapter {
	
	EditorPresenter presenter;
	override setPresenter(EditorPresenter presenter) {
		this.presenter = presenter
	}
	
}