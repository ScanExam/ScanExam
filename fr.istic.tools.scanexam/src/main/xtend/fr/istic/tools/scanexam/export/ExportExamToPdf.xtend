package fr.istic.tools.scanexam.export

import java.io.File
import org.apache.pdfbox.pdmodel.PDDocument
import fr.istic.tools.scanexam.core.StudentSheet

class ExportExamToPdf {

	def static exportToPdf(PDDocument pdf,StudentSheet sheet, File outputPdfFile){
		
		if(outputPdfFile.exists){
			return
		}
		
		
		var document = new PDDocument();
		
		for(i :sheet.posPage){
			document.addPage(pdf.getPage(i))	
		}
		
	
		
		document.save(outputPdfFile)
		document.close		
	}	
	
	
}