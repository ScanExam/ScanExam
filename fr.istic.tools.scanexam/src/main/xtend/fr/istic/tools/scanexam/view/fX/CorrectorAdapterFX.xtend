package fr.istic.tools.scanexam.view.fX

import fr.istic.tools.scanexam.presenter.PresenterVueCorrection
import fr.istic.tools.scanexam.view.CorrectorAdapter
import java.io.File

class CorrectorAdapterFX implements CorrectorAdapter {
	
	PresenterVueCorrection presenter;
	def void loadFile(File file) {
		
	}
	
	override setPresenter(PresenterVueCorrection presenter) {
		this.presenter = presenter
	}
	
	def void nextQuestion(){
		
	}
	
	def void previousQuestion(){
		
	}
	
}