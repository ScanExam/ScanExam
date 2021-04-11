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

class Annotation {
	
	def static void main(String[] args) {

		var PDDocument document = PDDocument.load(new File("src/main/resources/resources_annotation/pfo_example.pdf"))
		
		annotationPDF(document,0,0,0,0,0)
		
	}
		
	def static void annotationPDF(PDDocument document,int pageNoted,float lowerLeftX,float lowerLeftY,float upperLeftX,float upperRightY)	{

		var float inch = 72
		var float[] rgb = #[0 / 255f, 0 / 255f, 0 / 255f]
		var PDColor color = new PDColor(rgb, PDDeviceRGB.INSTANCE);

		var PDPage page = document.getPage(pageNoted)

		var List<PDAnnotation> annotations = page.getAnnotations();

		var PDBorderStyleDictionary borderThick = new PDBorderStyleDictionary()
		borderThick.setWidth(inch / 20) // 12th inch
		var PDBorderStyleDictionary borderThin = new PDBorderStyleDictionary()
		borderThin.setWidth(inch / 72) // 1 point
		var PDBorderStyleDictionary borderULine = new PDBorderStyleDictionary()
		borderULine.setStyle(PDBorderStyleDictionary.STYLE_UNDERLINE)
		borderULine.setWidth(inch / 72) // 1 point
		var float pw = page.getMediaBox().getUpperRightX()
		var float ph = page.getMediaBox().getUpperRightY()

		var PDRectangle position = new PDRectangle();

		var float[] snow = #[255 / 255f, 250 / 255f, 250 / 255f]
		var PDColor interieurColor = new PDColor(snow, PDDeviceRGB.INSTANCE);

		var PDAnnotationSquareCircle aSquare = new PDAnnotationSquareCircle(PDAnnotationSquareCircle.SUB_TYPE_SQUARE);
		aSquare.setContents("Square Annotation")
		aSquare.interiorColor = interieurColor
		aSquare.setColor(color) // Outline in red, not setting a fill
		aSquare.setBorderStyle(borderThick)

		// Place the annotation on the page, we'll make this 1" (72points) square
		// 3.5" down, 1" in from the right on the page
		position = new PDRectangle(); // Reuse the variable, but note it's a new object!
		position.setLowerLeftX(pw - (2 * inch)); // 1" in from right, 1" wide
		position.setLowerLeftY(ph - (3.5f * inch) - inch); // 1" height, 3.5" down
		position.setUpperRightX(pw - inch); // 1" in from right
		position.setUpperRightY(ph - (3.5f * inch)); // 3.5" down
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
		position.setLowerLeftX(2 * inch); // 1" in + width of circle
		position.setLowerLeftY(ph - (3.5f * inch) - inch); // 1" height, 3.5" down
		position.setUpperRightX(pw - inch - inch); // 1" in from right, and width of square
		position.setUpperRightY(ph - (3 * inch)); // 3" down (top of circle)
		aLine.setRectangle(position);

		// Now set the line position itself
		var float[] linepos = newFloatArrayOfSize(4)
		linepos.set(0, 2 * inch) // x1 = rhs of circle
		linepos.set(1, ph - (3.5f * inch)) // y1 halfway down circle
		linepos.set(2, pw - (2 * inch)) // x2 = lhs of square
		linepos.set(3, ph - (4 * inch)) // y2 halfway down square
		aLine.setLine(linepos);

		aLine.setBorderStyle(borderThick);
		aLine.setColor(color);

		// add to the annotations on the page
		annotations.add(aLine);
		// Finally all done
		var File file = new File("src/main/resources/resources_annotation/pfo_example_annotation.pdf");

		document.save(file)
		Desktop.getDesktop().open(file);

		document.close()

	}
}
