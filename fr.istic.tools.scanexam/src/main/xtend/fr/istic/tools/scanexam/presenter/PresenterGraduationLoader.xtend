package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl
import fr.istic.tools.scanexam.services.ServiceGraduation

class PresenterGraduationLoader {
	
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
	
	def boolean loadStudentSheets(String path) {
		val QRCodeGenerator generator = new QRCodeGeneratorImpl()
		//generator.createAllExamCopies(service.editionPdfInputStream, new FileOutputStream(new File(path)), service.examName, quantity)
		true
	}
	
}