package fr.istic.tools.scanexam.view.fx

import java.io.File
import fr.istic.tools.scanexam.core.Question
import fr.istic.tools.scanexam.view.fx.corrector.ControllerFXCorrector
import fr.istic.tools.scanexam.presenter.PresenterGraduation
import fr.istic.tools.scanexam.view.AdapterGraduation

class GraduationAdapterFX implements AdapterGraduation {
	
	PresenterGraduation presenter;
	ControllerFXCorrector corrector;
	
	def void setController(ControllerFXCorrector controller){
		corrector = controller
	}
	def void loadFile(File file) {
		
	}

	
	override void nextQuestion(){
		
	}
	
	override void previousQuestion(){
		
	}
	
	override questionNames() {
		
	}
	
	override thisQuestion(int index) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	def void showQuestion(Question question){
		
	}
	
	override setPresenter(PresenterGraduation presenter) {
		this.presenter = presenter
	}
	override getPresenter() {
		presenter
	}
	
}