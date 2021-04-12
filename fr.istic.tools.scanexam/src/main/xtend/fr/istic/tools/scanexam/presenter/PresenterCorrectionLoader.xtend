package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.services.ServiceGraduation

class PresenterCorrectionLoader {
	
	val ServiceGraduation service
	
	new(ServiceGraduation graduation) {
		service = graduation
	}
	
	def boolean hasTemplateLoaded() {
		return service.hasExamLoaded
	}
	
	def boolean loadTemplate(String path) {
		service.openCreationTemplate(path)
	}
	
	def boolean loadCorrection(String path) {
		true
	}
	
}