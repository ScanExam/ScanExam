package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.core.Question
import fr.istic.tools.scanexam.presenter.PresenterGraduation
import fr.istic.tools.scanexam.view.AdapterGraduation
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation
import java.io.File

class AdapterFxGraduation implements AdapterGraduation {
	
	PresenterGraduation presenter;
	ControllerFxGraduation corrector;
	
	def void setController(ControllerFxGraduation controller){
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