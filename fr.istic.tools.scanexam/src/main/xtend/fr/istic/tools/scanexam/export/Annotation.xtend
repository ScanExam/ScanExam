package fr.istic.tools.scanexam.export

import org.apache.pdfbox.pdmodel.PDDocument
import java.io.File
import org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary
import org.apache.pdfbox.pdmodel.PDPage
//import org.apache.pdfbox.pdmodel.font.PDFont
//import org.apache.pdfbox.pdmodel.font.PDType1Font
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.graphics.color.PDColor
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB
import java.util.List
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationSquareCircle
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLine
import java.awt.Desktop
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText

class Annotation {
	static var PDColor WHITEPDFBOX = new PDColor(#[255 / 255f, 255 / 255f, 255 / 255f], PDDeviceRGB.INSTANCE);
	static var PDColor REDPDFBOX = new PDColor(#[255 / 255f, 0 / 255f, 0 / 255f], PDDeviceRGB.INSTANCE);
	static var PDColor GREENPDFBOX = new PDColor(#[0 / 255f, 200 / 255f, 0 / 255f], PDDeviceRGB.INSTANCE);
	static var PDColor BLUEPDFBOX = new PDColor(#[0 / 255f, 0 / 255f, 255 / 255f], PDDeviceRGB.INSTANCE);
	static var PDColor BLACKPDFBOX = new PDColor(#[0 / 255f, 0 / 255f, 0 / 255f], PDDeviceRGB.INSTANCE);
	static var float INCH = 72
	
	
	def static void main(String[] args) {

		var PDDocument document = PDDocument.load(new File("src/main/resources/resources_annotation/pfo_example.pdf"))
		
		annotationPDFWithArrow(document,0,0,0,0,0)
		
	}
		
	def static void annotationPDFWithArrow(PDDocument document,int pageNoted,float lowerLeftX,float lowerLeftY,float upperLeftX,float upperRightY)	{

		var PDPage page = document.getPage(pageNoted)

		var List<PDAnnotation> annotations = page.getAnnotations();

		var PDBorderStyleDictionary borderThick = new PDBorderStyleDictionary()
		borderThick.setWidth(INCH / 20) // 12th inch
		var PDBorderStyleDictionary borderThin = new PDBorderStyleDictionary()
		borderThin.setWidth(INCH / 72) // 1 point
		var PDBorderStyleDictionary borderULine = new PDBorderStyleDictionary()
		borderULine.setStyle(PDBorderStyleDictionary.STYLE_UNDERLINE)
		borderULine.setWidth(INCH / 72) // 1 point
		var float pw = page.getMediaBox().getUpperRightX()
		var float ph = page.getMediaBox().getUpperRightY()

		var PDRectangle position = new PDRectangle();

		var PDAnnotationSquareCircle aSquare = new PDAnnotationSquareCircle(PDAnnotationSquareCircle.SUB_TYPE_SQUARE);
		aSquare.setContents("Square Annotation")
		aSquare.interiorColor = WHITEPDFBOX
		aSquare.setColor(BLACKPDFBOX) // Outline in red, not setting a fill
		aSquare.setBorderStyle(borderThick)

		// Place the annotation on the page, we'll make this 1" (72points) square
		// 3.5" down, 1" in from the right on the page
		position = new PDRectangle(); // Reuse the variable, but note it's a new object!
		position.setLowerLeftX(pw - (2 * INCH)); // 1" in from right, 1" wide
		position.setLowerLeftY(ph - (3.5f * INCH) - INCH); // 1" height, 3.5" down
		position.setUpperRightX(pw - INCH); // 1" in from right
		position.setUpperRightY(ph - (3.5f * INCH)); // 3.5" down
		aSquare.setRectangle(position);

		// add to the annotations on the page
		annotations.add(aSquare);

		// Now we want to draw a line between the two, one end with an open arrow
		var PDAnnotationLine aLine = new PDAnnotationLine();

		aLine.setEndPointEndingStyle(PDAnnotationLine.LE_OPEN_ARROW);
		aLine.setContents("Circle->Square");
		aLine.setCaption(true); // Make the contents a caption on the line
		// Set the rectangle containing the line
		position = new PDRectangle(); // Reuse the variable, but note it's a new object!
		position.setLowerLeftX(2 * INCH); // 1" in + width of circle
		position.setLowerLeftY(ph - (3.5f * INCH) - INCH); // 1" height, 3.5" down
		position.setUpperRightX(pw - INCH - INCH); // 1" in from right, and width of square
		position.setUpperRightY(ph - (3 * INCH)); // 3" down (top of circle)
		aLine.setRectangle(position);

		// Now set the line position itself
		var float[] linepos = newFloatArrayOfSize(4)
		linepos.set(0, 2 * INCH) // x1 = rhs of circle
		linepos.set(1, ph - (3.5f * INCH)) // y1 halfway down circle
		linepos.set(2, pw - (2 * INCH)) // x2 = lhs of square
		linepos.set(3, ph - (4 * INCH)) // y2 halfway down square
		aLine.setLine(linepos);

		aLine.setBorderStyle(borderThick);
		aLine.setColor(BLACKPDFBOX);

		// add to the annotations on the page
		annotations.add(aLine);
		// Finally all done
		var File file = new File("src/main/resources/resources_annotation/pfo_example_annotation.pdf");

		document.save(file)
		Desktop.getDesktop().open(file);

		document.close()

	}
	
	def static void annotationTextPDF(PDDocument document,int pageNoted,float lowerLeftX,float lowerLeftY,float upperLeftX,float upperRightY){
		var PDPage page = document.getPage(pageNoted)
		var List<PDAnnotation> annotations = page.getAnnotations();
		
		var float inch = 72
		var PDAnnotationText text = new PDAnnotationText();
		
		var PDBorderStyleDictionary borderThick = new PDBorderStyleDictionary()
		borderThick.setWidth(inch / 20) // 12th inch
		var PDBorderStyleDictionary borderThin = new PDBorderStyleDictionary()
		borderThin.setWidth(inch / 72) // 1 point
		var PDBorderStyleDictionary borderULine = new PDBorderStyleDictionary()
		borderULine.setStyle(PDBorderStyleDictionary.STYLE_UNDERLINE)
		borderULine.setWidth(inch / 72) // 1 point
		var float pw = page.getMediaBox().getUpperRightX()
		var float ph = page.getMediaBox().getUpperRightY()
		
		text.setContents("test");
		text.setName("test");
		var float[] rgb = #[0 / 255f, 0 / 255f, 0 / 255f]
		var PDColor color = new PDColor(rgb, PDDeviceRGB.INSTANCE);
		text.setColor(color)
		
		var PDRectangle position = new PDRectangle(); // Reuse the variable, but note it's a new object!
		position.setLowerLeftX(pw - (2 * inch)); // 1" in from right, 1" wide
		position.setLowerLeftY(ph - (3.5f * inch) - inch); // 1" height, 3.5" down
		position.setUpperRightX(pw - inch); // 1" in from right
		position.setUpperRightY(ph - (3.5f * inch)); // 3.5" down
		text.setRectangle(position);
		
		annotations.add(text);
		
		var File file = new File("src/main/resources/resources_annotation/pfo_example_annotation.pdf");

		document.save(file)
		Desktop.getDesktop().open(file);

		document.close()
	}
	
	
	
	
}
