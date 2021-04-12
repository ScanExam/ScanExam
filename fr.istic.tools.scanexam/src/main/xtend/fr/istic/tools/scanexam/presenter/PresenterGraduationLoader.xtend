package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.services.ServiceGraduation

class PresenterGraduationLoader {
	
	val ServiceGraduation service
	
	new(ServiceGraduation graduation) {
		service = graduation
	}
	
	def boolean hasTemplateLoaded() {
		return service.hasExamLoaded
	}
	
	
	def getPdfInputStream(){
		return service.getEditionPdfInputStream();
	}
	
	def boolean loadTemplate(String path) {
		service.openCreationTemplate(path)
	}
	
	def boolean loadStudentSheets(String path) {
		
		
		true
	}
	
}