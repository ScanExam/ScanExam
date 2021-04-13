package fr.istic.tools.scanexam.export

import java.io.File
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import java.awt.Desktop
import java.util.ArrayList
import java.awt.Color
import fr.istic.tools.scanexam.core.Line
import org.apache.pdfbox.pdmodel.font.PDType0Font
import fr.istic.tools.scanexam.utils.ResourcesUtils

class ExportPdfWithAnnotations {
	def static void main(String[] args) {
		
		

		var PDDocument document = PDDocument.load(new File("src/main/resources/resources_annotation/pfo_example.pdf"));

		textAnnotationWithArrowAbsoluteCoords(document, 0, 100, 350, 400, 400, "ffffffff", "10/20")

		// Closing the document
		document.close();
	}

	def static void textAnnotationWithArrowAbsoluteCoords(PDDocument document, int nbPage, float pointerAbsoluteX,float pointerAbsoluteY, float textAbsoluteX, float textAbsoluteY, String t, String note) {
		// Remove Newlines

		var String text = t.replace("\n", "").replace("\r", "");

		// Retrieving a page of the PDF Document
		var PDPage page = document.getPage(nbPage);
		//var float pageWidht = page.getMediaBox().getUpperRightX()
		//var float pageHeight = page.getMediaBox().getUpperRightY()

		// Instantiating the PDPageContentStream class
		var PDPageContentStream contentStream = new PDPageContentStream(document, page,
			PDPageContentStream.AppendMode.APPEND, true, true)

		// Counter of lines
		var int nbLines = 1 + (text.length() / 30);
		// Number of char needed to skip a line
		var int partitionSize = 30;
		// Considered width of a character
		var float charWidth = 3.5f;

		// Considered height of a char
		var float charHeight = 9;

		var float rectangleBottomLeftCornerX;
		var float rectangleBottomLeftCornerY;
		var float rectangleWidth;
		var float rectangleHeight;

		rectangleBottomLeftCornerX = textAbsoluteX;

		// If text size < 30 char
		if (text.length() <= partitionSize) {
			rectangleWidth = text.length() * charWidth;
			rectangleHeight = charHeight;
			rectangleBottomLeftCornerY = textAbsoluteY;
		} // If text size > 30 char
		else {
			rectangleBottomLeftCornerY = textAbsoluteY - (charHeight * (nbLines - 1));
			rectangleWidth = partitionSize * charWidth;
			rectangleHeight = charHeight * nbLines;
		}

		// Drawing pointer line
		contentStream.moveTo(pointerAbsoluteX, pointerAbsoluteY)
		contentStream.lineTo(textAbsoluteX + (rectangleWidth / 2), textAbsoluteY)
		contentStream.setNonStrokingColor(Color.decode("#0093ff"))
		contentStream.stroke()
		contentStream.fill();

		// Draw rectangle
		contentStream.addRect(rectangleBottomLeftCornerX - 2, rectangleBottomLeftCornerY - 2, rectangleWidth + 4,
			rectangleHeight + 4)
		contentStream.setNonStrokingColor(Color.decode("#000000"))
		contentStream.fill();

		contentStream.addRect(rectangleBottomLeftCornerX, rectangleBottomLeftCornerY, rectangleWidth, rectangleHeight)
		contentStream.setNonStrokingColor(Color.decode("#ffffff"))
		contentStream.fill();

		contentStream.setNonStrokingColor(Color.decode("#000000"))
		//contentStream.setFont(PDType1Font.TIMES_ROMAN, 8);
		
		contentStream.setFont(PDType0Font.load(document, ResourcesUtils.getInputStreamResource("resources_annotation/arial.ttf")), 8);
		contentStream.setLeading(7f);
		contentStream.beginText();
		contentStream.newLineAtOffset(textAbsoluteX, textAbsoluteY);

		// Newline for text every "partitionSize" char
		for (var int i = 0; i < text.length(); i += partitionSize) {
			contentStream.showText(text.substring(i, Math.min(text.length(), i + partitionSize)));
			if (i < text.length()) {
				contentStream.newLine();
			}
		}

		contentStream.endText();
		contentStream.fill();
		
		contentStream.beginText();
		contentStream.newLineAtOffset(0, page.mediaBox.height-10);
		contentStream.showText(note);
		contentStream.endText();
		contentStream.close();

		var File file = new File("src/main/resources/resources_annotation/pfo_example_annotation.pdf");
		document.save(file);
		// File auto open
		Desktop.getDesktop().open(file);

	}
	
	def static float[] convertHexaToRGBFloat(String hexaColor){
		var Color color = Color.decode(hexaColor)
		#[color.getRed() as float/255,color.getGreen() as float/255,color.getBlue() as float/255]
	}
	
	
	
	

	def static void annotationDrawLinePDF(PDDocument document, int nbPage, ArrayList<Line> listLine) {

		var PDPage page = document.getPage(nbPage);
		var PDPageContentStream contentStream = new PDPageContentStream(document, page,
			PDPageContentStream.AppendMode.APPEND, true, true)

		for (i : 0 ..< listLine.size) {
			var Line lineTMP = listLine.get(i)
			var float originAbsoluteX = lineTMP.x1
			var float originAbsoluteY = lineTMP.y1
			var float destinationAbsoluteX = lineTMP.x2
			var float destinationAbsoluteY = lineTMP.y2

			contentStream.moveTo(originAbsoluteX, originAbsoluteY)
			contentStream.lineTo(destinationAbsoluteX, destinationAbsoluteY)
			contentStream.setNonStrokingColor(Color.decode("#0093ff"))
			contentStream.stroke()
			contentStream.fill();
		}

		contentStream.close();
		var File file = new File("src/main/resources/resources_annotation/pfo_example_annotation.pdf");

		document.save(file);
		Desktop.getDesktop().open(file);

	}

	
}
