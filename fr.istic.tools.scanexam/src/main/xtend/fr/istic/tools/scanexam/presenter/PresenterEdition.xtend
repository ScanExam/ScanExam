package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.services.ServiceEdition
import fr.istic.tools.scanexam.view.Adapter
import java.io.File
import java.util.Objects

/**
 * Class defining the presenter for the exam creation view(s)
 * and linking with more concrete sub presenters
 * @author Benjamin Danlos
 */
class PresenterEdition implements Presenter
{
	/**
	 * Bidirectional associations with the concrete presenters
	 * and main controller of the view
	 */
	PresenterQuestionZone presQuestionZone
	ServiceEdition service
	PresenterPdf presPdf
	PresenterTemplateCreator templateCreatorPresenter
	PresenterStudentSheetExport sheetExport
	Adapter<PresenterEdition> adapter
	
	private new(ServiceEdition service)
	{
	 	Objects.requireNonNull(service)
		this.service = service
		
		presPdf = new PresenterPdf(service, this)
		templateCreatorPresenter = new PresenterTemplateCreator(this)
		presQuestionZone =  new PresenterQuestionZone(service,this)
		sheetExport = new PresenterStudentSheetExport(service, presPdf)
	}
	new(Adapter<PresenterEdition> adapter,ServiceEdition service) 
	{
		this(service)
		Objects.requireNonNull(adapter)
		this.adapter = adapter

		//Verification Switch Service
		/*if(this.service.getExamInstance() === null){
			if(!EditorGraduationSwitchVerification.saveExamInstance(this.service.getExamInstance()).equals(null)){
				this.service.setExamInstance(EditorGraduationSwitchVerification.loadExamInstance)
			}
		}else{
			EditorGraduationSwitchVerification.saveExamInstance(this.service.getExamInstance())
		}*/
	}
	
	/**
	 * @return current {@link PresenterQuestionZone} 
	 */
	def getPresenterQuestionZone(){
		presQuestionZone
	}
	
	/**
	 * @return current {@link PresenterTemplateCreator} 
	 */
	def PresenterTemplateCreator getPresenterTemplateCreator() {
		templateCreatorPresenter
	}
	
	/**
	 * @return current {@link StudentSheetExport}
	 */
	def getPresenterStudentSheetExport() {
		return sheetExport
	}
	
	
	override getPresenterPdf(){
		presPdf
	}

	
	def save(File path)
	{
		val outputStream = presPdf.getPdfOutputStream();
		service.save(outputStream,path);
	}
	
	def boolean load(String path)
	{
		val stream = service.open(path)
		
		if (stream.isPresent)
		{
			presPdf.create(stream.get);
		}
		
		return stream.isPresent;
	}
	
	def close() {
		System.exit(0)
	}
	
	
	
	def getQuestionId() {
		service.getQuestionId()
	}
}