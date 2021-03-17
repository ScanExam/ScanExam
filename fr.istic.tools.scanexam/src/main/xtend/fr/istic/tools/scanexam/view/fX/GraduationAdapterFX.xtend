package fr.istic.tools.scanexam.view.fX

import fr.istic.tools.scanexam.presenter.GraduationPresenter
import fr.istic.tools.scanexam.view.GraduationAdapter
import java.io.File
import fr.istic.tools.scanexam.core.Question
import fr.istic.tools.scanexam.view.fX.corrector.ControllerFXCorrector

class GraduationAdapterFX implements GraduationAdapter {
	
	GraduationPresenter presenter;
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
	
	override setPresenter(GraduationPresenter presenter) {
		this.presenter = presenter
	}
	override getPresenter() {
		presenter
	}
	
}