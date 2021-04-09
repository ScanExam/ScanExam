package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.view.Adapter
import java.util.Objects
import java.io.File
import fr.istic.tools.scanexam.services.ServiceEdition

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
	PresenterQRCode presQRCode
	PresenterQuestionZone presQuestionZone
	PresenterGradeScale presMarkingScheme
	ServiceEdition service
	PresenterPdf presPdf
	Adapter<PresenterEdition> adapter
	
	private new(ServiceEdition service)
	{
	 	Objects.requireNonNull(service)
		this.service = service
		
		presPdf = new PresenterPdf(service, this)
		presQuestionZone =  new PresenterQuestionZone(service,this)
		presMarkingScheme = new PresenterGradeScale(service, this)
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