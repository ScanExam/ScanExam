package fr.istic.tools.scanexam.export

import java.io.File
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType1Font
import java.awt.Desktop

class ExportPdfWithAnnotations {
	def static void main(String[] args){

		var PDDocument document = PDDocument.load(new File("src/main/resources/resources_annotation/pfo_example.pdf"));

		textAnnotationWithArrowAbsoluteCoords(document,0,0,350,400,400,"fffffffffffffffffffffffffffffffffffffff")

		//Closing the document
		document.close();
	}





	def static void textAnnotationWithArrowAbsoluteCoords(PDDocument document,int nbPage,float pointerAbsoluteX,float pointerAbsoluteY,float textAbsoluteX,float textAbsoluteY,String t){
		//Remove Newlines
		var String text = t.replace("\n", "").replace("\r", "");

		//Retrieving a page of the PDF Document
		var PDPage page = document.getPage(nbPage);
		var float pageWidht = page.getMediaBox().getUpperRightX()
		var float pageHeight = page.getMediaBox().getUpperRightY()

		//Instantiating the PDPageContentStream class
		var PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)

		//Counter of lines
		var int nbLines=1+(text.length()/30);
		//Number of char needed to skip a line
		var int partitionSize = 30;
		//Considered width of a character
		var float charWidth = 3.5f;

		//Considered height of a char
		var float charHeight = 9;


		var float rectangleBottomLeftCornerX;
		var float rectangleBottomLeftCornerY;
		var float rectangleWidth;
		var float rectangleHeight;


		rectangleBottomLeftCornerX = textAbsoluteX;

		//If text size < 30 char
		if(text.length()<=partitionSize){
			rectangleWidth = text.length()*charWidth;
			rectangleHeight = charHeight;
			rectangleBottomLeftCornerY = textAbsoluteY;
		}
		//If text size > 30 char
		else{
			rectangleBottomLeftCornerY = textAbsoluteY-(charHeight*(nbLines-1));
			rectangleWidth=partitionSize*charWidth;
			rectangleHeight = charHeight*nbLines;
		}



		//Drawing pointer line
		contentStream.moveTo(pointerAbsoluteX,pointerAbsoluteY)
		contentStream.lineTo(textAbsoluteX+(rectangleWidth/2),textAbsoluteY)
		contentStream.stroke()
		contentStream.fill();

		//Draw rectangle
		contentStream.addRect(rectangleBottomLeftCornerX-2, rectangleBottomLeftCornerY-2, rectangleWidth+4, rectangleHeight+4)
		contentStream.setNonStrokingColor(36/255f, 35/255f, 35/255f)
		contentStream.fill();


		contentStream.addRect(rectangleBottomLeftCornerX, rectangleBottomLeftCornerY, rectangleWidth, rectangleHeight)
		contentStream.setNonStrokingColor(248/255f, 244/255f, 243/255f)
		contentStream.fill();


		contentStream.setNonStrokingColor(36/255f, 35/255f, 35/255f)
		contentStream.setFont(PDType1Font.TIMES_ROMAN,8);
		contentStream.setLeading(7f);
		contentStream.beginText();
		contentStream.newLineAtOffset(textAbsoluteX,textAbsoluteY);

 		//Newline for text every "partitionSize" char
    	for (var int i = 0; i < text.length(); i += partitionSize) {
      		contentStream.showText(text.substring(i, Math.min(text.length(), i + partitionSize)));
      		if(i < text.length()){
      			contentStream.newLine();
      		}
    	}

		contentStream.endText();
		contentStream.fill();


		contentStream.close();

		var File file1 = new File("src/main/resources/resources_annotation/pfo_example_annotation.pdf");
		document.save(file1);
		//File auto open
		Desktop.getDesktop().open(file1);


	}


	def static void annotationDrawLinePDF(PDDocument document,int nbPage,float originAbsoluteX,float originAbsoluteY,float destinationAbsoluteX,float destinationAbsoluteY){

			var PDPage page = document.getPage(nbPage);
			var PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)

			contentStream.moveTo(originAbsoluteX,originAbsoluteY)
			contentStream.lineTo(destinationAbsoluteX,destinationAbsoluteY)
			contentStream.stroke()
			contentStream.fill();
			contentStream.close();
	}

}
