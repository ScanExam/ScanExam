package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl
import java.io.File
import java.io.FileInputStream

class PresenterStudentSheetExport {
	
	def export(File file, int number) {
		val QRCodeGenerator generator = new QRCodeGeneratorImpl
		//generator.createAllExamCopies(new FileInputStream(file), number)
	}	
	
}