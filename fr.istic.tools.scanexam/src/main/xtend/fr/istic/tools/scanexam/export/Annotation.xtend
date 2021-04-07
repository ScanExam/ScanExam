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
	def static void main(String[] args){
		
		
		var PDDocument document = PDDocument.load(new File("/Users/leo/Documents/pfo_example.pdf"))
		
		var float inch = 72
		var float[] rgb =#[0,0,1]
		var PDColor color = new PDColor(rgb, PDDeviceRGB.INSTANCE);

        
		var PDPage page = document.getPage(0)
		
		var  List<PDAnnotation> annotations = page.getAnnotations();
		
        
        var PDBorderStyleDictionary borderThick = new PDBorderStyleDictionary()
    	borderThick.setWidth(inch/12)  // 12th inch
     	var PDBorderStyleDictionary borderThin = new PDBorderStyleDictionary()
      	borderThin.setWidth(inch/72)// 1 point
     	var PDBorderStyleDictionary borderULine = new PDBorderStyleDictionary()
     	borderULine.setStyle(PDBorderStyleDictionary.STYLE_UNDERLINE)
     	borderULine.setWidth(inch/72) // 1 point
     	
     	var float pw = page.getMediaBox().getUpperRightX()
   		var float ph = page.getMediaBox().getUpperRightY()
   		
   		
   	/*	var PDFont font = PDType1Font.HELVETICA_BOLD;
   	
   	 	var PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)
	
		contentStream.beginText()
		contentStream.setFont( font, 18 )
		contentStream.newLineAtOffset( inch, ph-inch-18)
		contentStream.showText( "PDFBox" )
		contentStream.newLineAtOffset( 0,-(inch/2))
		contentStream.showText( "Click Here" )
		
		contentStream.endText()
		
		contentStream.close()
		
		
	
		
		var PDAnnotationTextMarkup txtMark = new PDAnnotationTextMarkup(PDAnnotationTextMarkup.SUB_TYPE_HIGHLIGHT)
		txtMark.setColor(color)
		txtMark.setConstantOpacity(0.2f)
		
		var float textWidth = (font.getStringWidth( "PDFBox" )/1000) * 18;
		var PDRectangle position = new PDRectangle();
		position.setLowerLeftX(inch);
		position.setLowerLeftY( ph-inch-18 );
		position.setUpperRightX(72 + textWidth);
 	    position.setUpperRightY(ph-inch);
 	    txtMark.setRectangle(position);
 	    
 	    var  float[] quads = newFloatArrayOfSize(8)
 	    
 	     quads.set(0,position.getLowerLeftX())
 	     
         quads.set(1,position.getUpperRightY()-2)  // y1
         quads.set(2,position.getUpperRightX()) // x2
         quads.set(3,quads.get(1)) // y2
         quads.set(4,quads.get(0)) // x3
         quads.set(5,position.getLowerLeftY()-2) // y3
         quads.set(6,quads.get(2)) // x4
         quads.set(7,quads.get(5) )// y5


		 txtMark.setQuadPoints(quads);
         txtMark.setContents("Highlighted since it's important");
         
         annotations.add(txtMark);
         
           // Now add the link annotation, so the clickme works
		 var   PDAnnotationLink txtLink = new PDAnnotationLink()
		 txtLink.setBorderStyle(borderULine);
		 
		 // Set the rectangle containing the link
     
         textWidth = (font.getStringWidth( "Click Here" )/1000) * 18;
         position = new PDRectangle();
         position.setLowerLeftX(inch);
         position.setLowerLeftY( ph-(1.5f*inch)-20);  // down a couple of points
         position.setUpperRightX(72 + textWidth);
         position.setUpperRightY(ph-(1.5f*inch));
         txtLink.setRectangle(position);
     
         // add an action
         var PDActionURI action = new PDActionURI();
         action.setURI("http://www.pdfbox.org");
         txtLink.setAction(action);
     
         annotations.add(txtLink);
         
          // Now draw a few more annotations
          */
     	  var PDRectangle position = new PDRectangle();
     
     
          var  PDAnnotationSquareCircle aCircle =
                         new PDAnnotationSquareCircle( PDAnnotationSquareCircle.SUB_TYPE_CIRCLE);
          aCircle.setContents("Circle Annotation");
          aCircle.setColor(color);  // Fill in circle in red
          // aCircle.setColor(color); // The border itself will be blue
          aCircle.setBorderStyle(borderThin);
      
          // Place the annotation on the page, we'll make this 1" round
          // 3" down, 1" in on the page
     
          position = new PDRectangle();
       	  position.setLowerLeftX(inch);
          position.setLowerLeftY(ph-(3*inch)-inch); // 1" height, 3" down
          position.setUpperRightX(2*inch); // 1" in, 1" width
          position.setUpperRightY(ph-(3*inch)); // 3" down
          aCircle.setRectangle(position);
          
           //  add to the annotations on the page
          annotations.add(aCircle);
          
          // Now a square annotation
          
        	 var  PDAnnotationSquareCircle aSquare =
                         new PDAnnotationSquareCircle( PDAnnotationSquareCircle.SUB_TYPE_SQUARE);
                     aSquare.setContents("Square Annotation");
                     aSquare.setColor(color);  // Outline in red, not setting a fill
                     aSquare.setBorderStyle(borderThick);
 	    
          // Place the annotation on the page, we'll make this 1" (72points) square
          // 3.5" down, 1" in from the right on the page
          
          position = new PDRectangle(); // Reuse the variable, but note it's a new object!
                     position.setLowerLeftX(pw-(2*inch));  // 1" in from right, 1" wide
                     position.setLowerLeftY(ph-(3.5f*inch) - inch); // 1" height, 3.5" down
                     position.setUpperRightX(pw-inch); // 1" in from right
                     position.setUpperRightY(ph-(3.5f*inch)); // 3.5" down
                     aSquare.setRectangle(position);
                     
           //  add to the annotations on the page
           annotations.add(aSquare);
           
           // Now we want to draw a line between the two, one end with an open arrow
     
           var PDAnnotationLine aLine = new PDAnnotationLine();
     
                     aLine.setEndPointEndingStyle( PDAnnotationLine.LE_OPEN_ARROW );
                     aLine.setContents("Circle->Square");
                     aLine.setCaption(true);  // Make the contents a caption on the line
                     
            // Set the rectangle containing the line
     
            position = new PDRectangle(); // Reuse the variable, but note it's a new object!
            position.setLowerLeftX(2*inch);  // 1" in + width of circle
            position.setLowerLeftY(ph-(3.5f*inch)-inch); // 1" height, 3.5" down
            position.setUpperRightX(pw-inch-inch); // 1" in from right, and width of square
            position.setUpperRightY(ph-(3*inch)); // 3" down (top of circle)
            aLine.setRectangle(position);
            
            // Now set the line position itself
                  var   float[] linepos = newFloatArrayOfSize(4)
                  linepos.set(0,2*inch)// x1 = rhs of circle
                  linepos.set(1,ph-(3.5f*inch)) // y1 halfway down circle
                  linepos.set(2,pw-(2*inch))  // x2 = lhs of square
                  linepos.set(3,ph-(4*inch)) // y2 halfway down square
                  aLine.setLine(linepos);
                  
                  aLine.setBorderStyle(borderThick);
                  aLine.setColor(color);
                  
            // add to the annotations on the page
             annotations.add(aLine);
             // Finally all done
             var File file = new File("/Users/leo/Documents/pfo_example_annotation.pdf");
             
              document.save(file)
             Desktop.getDesktop().open(file);
              
              document.close()
                  
	}
}