package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl
import fr.istic.tools.scanexam.services.ServiceEdition
import java.io.File
import java.io.FileOutputStream

class PresenterStudentSheetExport {
	
	val ServiceEdition service
	
	new(ServiceEdition edition) {
		service = edition
	}
	
	def boolean export(File file, int number) {
		val QRCodeGenerator generator = new QRCodeGeneratorImpl
		generator.createAllExamCopies(service.editionPdfInputStream, new FileOutputStream(file), service.examName, number)
		true
	}	
}