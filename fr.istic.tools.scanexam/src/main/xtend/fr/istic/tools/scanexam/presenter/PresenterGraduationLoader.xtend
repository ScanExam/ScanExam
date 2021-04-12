package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl
import fr.istic.tools.scanexam.services.ServiceEdition
import fr.istic.tools.scanexam.services.ServiceGraduation
import java.io.File
import java.io.FileOutputStream

class PresenterGraduationLoader {
	
	val ServiceGraduation service
	val ServiceEdition edition
	
	new(ServiceGraduation graduation, ServiceEdition edition) {
		service = graduation
		this.edition = edition
	}
	
	def boolean hasTemplateLoaded() {
		return service.hasExamLoaded
	}
	
	def boolean loadTemplate(String path) {
		service.openCreationTemplate(path)
	}
	
	def boolean loadStudentSheets(String path, int quantity) {
		val QRCodeGenerator generator = new QRCodeGeneratorImpl()
		generator.createAllExamCopies(null, new FileOutputStream(new File(path)), service.examName, quantity)
		true
	}
	
}