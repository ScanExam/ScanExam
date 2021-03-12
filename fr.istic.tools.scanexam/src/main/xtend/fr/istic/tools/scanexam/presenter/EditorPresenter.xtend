package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.services.ExamEditionService
import fr.istic.tools.scanexam.view.Adapter
import java.io.File
import java.util.Objects

/**
 * Class defining the presenter for the exam creation view(s)
 * and linking with more concrete sub presenters
 * @author Benjamin Danlos
 */
class EditorPresenter implements Presenter
{
	/**
	 * Bidirectional associations with the concrete presenters
	 * and main controller of the view
	 */
	PresenterQRCode presQRCode
	PresenterQuestionZone presQuestionZone
	PresenterMarkingScheme presMarkingScheme
	EditorPresenter editorPresenter
	ExamEditionService service
	PresenterPdf presPdf
	Adapter<EditorPresenter> adapter
	
	new(Adapter<EditorPresenter> adapter,ExamEditionService service) 
	{
		Objects.requireNonNull(service)
		this.service = service
		
		Objects.requireNonNull(adapter)
		this.adapter = adapter
		
		presPdf = new PresenterPdf(service, this)
		presQuestionZone =  new PresenterQuestionZone(service,this)
		presMarkingScheme = new PresenterMarkingScheme(service, this)
	}


	/**
	 * @return current {@link PresenterQRCode} 
	 */
	def getPresenterQRCode(){
		presQRCode
	}
	
	
	/**
	 * @return current {@link PresenterQuestionZone} 
	 */
	def getPresenterQuestionZone(){
		presQuestionZone
	}
	
	
	/**
	 * @return current {@link PresenterMarkingScheme} 
	 */
	override getPresenterMarkingScheme(){
		presMarkingScheme
	}
	
	override getPresenterPdf(){
		presPdf
	}
	
	
	
	def save(String path){
		service.save(path)
	}
	
	def boolean load(String path) {
		return service.open(path)
	}
	
	def close() {
		System.exit(0)
	}
	
	
	
	def getQuestionId() {
		service.getQuestionId()
	}
	
	def int addGradeItem(String name,double points) {
		0 //TODO impl
	}
	
	def updateGradeItem(int gradeItemId,String name,double points) {
		
	}
	
	def removeGradeItem(int gradeItemId) {
		
	}
	
	
	
	
	
}