package fr.istic.tools.scanexam.export

import org.apache.pdfbox.pdmodel.PDDocument

import java.io.File
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDFont
//import org.apache.pdfbox.pdmodel.font.PDType1Font

class AddingTextPdf {
	
		def AddingTextPdf(){
			
		}
	
		def addingText(File file,String text,int x,int y,PDFont font, int size){
			
			var PDDocument doc = PDDocument.load(file)
			var PDPage page = doc.getPage(1)
			var PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true)
			
			
			contentStream.beginText()
			
			contentStream.newLineAtOffset(x,y)
			contentStream.setFont(font , size)
			contentStream.showText(text)
			
			contentStream.endText()
			contentStream.close()
			
			System.out.println("J'ai reussi")
			doc.save("/Users/leo/Desktop/test.pdf")
			doc.close()	
			
		}
		
	
	
	
	
}