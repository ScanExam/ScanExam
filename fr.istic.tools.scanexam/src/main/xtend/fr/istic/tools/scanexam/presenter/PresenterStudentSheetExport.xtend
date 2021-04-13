package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl
import fr.istic.tools.scanexam.services.ServiceEdition
import java.io.File

class PresenterStudentSheetExport {
	
	val ServiceEdition service
	val PresenterPdf presPdf
	
	new(ServiceEdition edition, PresenterPdf presPdf) {
		this.service = edition
		this.presPdf = presPdf 
	}
	
	def boolean export(File file, int number) {
		val QRCodeGenerator generator = new QRCodeGeneratorImpl
		generator.createAllExamCopies(presPdf.getPdfInputStream, file, service.examName===null?"foo":service.examName, number)
		true
	}	
}