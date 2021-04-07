package fr.istic.tools.scanexam.export

import java.io.File
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import java.awt.Desktop

class ExportPdfWithAnnotations {
	def static void main(String[] args){
		var PDDocument document = PDDocument.load(new File("/Users/leo/Documents/pfo_example.pdf"));
		
		//Retrieving a page of the PDF Document
		var PDPage page = document.getPage(0);
				
		//Instantiating the PDPageContentStream class
		var PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)

				
		var int startX = 200	
		var int startY = 350		
			
		var int bullX = 400
		var int bullY = 400
		var int weight = 100
		var int height = 100		
					
				
				

		//Drawing a rectangle 
		
		contentStream.addRect(bullX-2, bullY-2, weight+4, height+4)
		contentStream.setNonStrokingColor(36/255f, 35/255f, 35/255f)
		contentStream.fill();
		
		
		contentStream.addRect(bullX, bullY, weight, height)
		contentStream.setNonStrokingColor(248/255f, 244/255f, 243/255f)
		contentStream.fill();
		
		//Drawing a line
		contentStream.moveTo(startX,startY)
		contentStream.lineTo(bullX,bullY)
		contentStream.stroke()
		contentStream.fill();
		
		contentStream.setNonStrokingColor(36/255f, 35/255f, 35/255f)
		contentStream.setFont(PDType1Font.TIMES_ROMAN,12);
		contentStream.beginText();
		contentStream.newLineAtOffset(bullX,bullY+weight/2);
		contentStream.showText("Here is a test");
		contentStream.endText();


		contentStream.fill();
				
		//Closing the ContentStream object
		contentStream.close();
				
		//Saving the document
		var File file1 = new File("/Users/leo/Documents/pfo_example_annotation.pdf");
		document.save(file1);
		Desktop.getDesktop().open(file1);
				
		//Closing the document
		document.close();
		}
	}