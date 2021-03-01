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
		
	}


	/**
	 * setter for the PresenterQRCode attribute
	 * @param {@link PresenterQRCode} pres instance of the presenter (not null) 
	 */
	def setPresenterQRCode(PresenterQRCode pres){
		Objects.requireNonNull(pres)
		presQRCode = pres
	}
	/**
	 * @return current {@link PresenterQRCode} 
	 */
	def getPresenterQRCode(){
		presQRCode
	}
	
	/**
	 * Setter for {@link PresenterQuestionZone} attribute
	 * @param {@link PresenterQuestionZone} pres an instance (not null)
	 */
	def setPresenterQuestionZone(PresenterQuestionZone pres){
		Objects.requireNonNull(pres)
		presQuestionZone = pres
	}
	/**
	 * @return current {@link PresenterQuestionZone} 
	 */
	def getPresenterQuestionZone(){
		presQuestionZone
	}
	
	/**
	 * Setter for {@link PresenterMarkingScheme} attribute
	 * @param {@link PresenterMarkingScheme} pres an instance (not null)
	 */
	def setPresenterMarkingScheme(PresenterMarkingScheme pres){
		Objects.requireNonNull(pres)
		presMarkingScheme = pres
	}
	/**
	 * @return current {@link PresenterMarkingScheme} 
	 */
	def getPresenterMarkingScheme(){
		presMarkingScheme
	}
	
	/**
	 * Sets a {@link ControllerVueCreation} the link with the view
	 * @param {@link ControllerVueCreation} contr an instance (not null)
	 */
	def setControllerVueCreation(EditorPresenter contr){
		Objects.requireNonNull(contr)
		editorPresenter = contr
	}
	/**
	 * @return current {@link ControllerVueCreation} 
	 */
	def getControllerVueCreation(){
		editorPresenter
	}
	
	override getCurrentPdfPage()
	{
		return service.getCurrentPdfPage
	}
	
	def void choosePdfPage(int pageNumber) {
		
	}
	def void nextPdfPage(){
		service.nextPage
	}
	def void previousPdfPage(){
		service.previousPage
	}
	
	def int getTotalPdfPageNumber(){
		service.pageNumber
	}
	def int getCurrentPdfPageNumber(){
		service.currentPageNumber
	}
	
	override create(File file)
	{
		 service.create(file);
	}
	
	def getDocument() 
	{
		service.document
	}
	
	
}