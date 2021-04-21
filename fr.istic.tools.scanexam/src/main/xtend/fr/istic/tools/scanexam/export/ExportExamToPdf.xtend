package fr.istic.tools.scanexam.export

import fr.istic.tools.scanexam.core.Comment
import fr.istic.tools.scanexam.core.Grade
import fr.istic.tools.scanexam.core.HandwritingComment
import fr.istic.tools.scanexam.core.Line
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.core.TextComment
import fr.istic.tools.scanexam.services.api.Service
import fr.istic.tools.scanexam.utils.ResourcesUtils
import java.awt.Color
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.util.Collection
import java.util.stream.Collectors
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDStream
import org.apache.pdfbox.pdmodel.font.PDType0Font
import java.io.ByteArrayInputStream

class ExportExamToPdf {
	
	static Service service
	 
	new (Service serv){
	 	service = serv
	 }

	/**
	 * Exports a PDF file to the selected directory.
	 */
	def static File exportToPdf(PDDocument pdf,StudentSheet sheet, File outputPdfFile,Boolean overwriteFile){
		
		if(outputPdfFile.exists && !overwriteFile){
			return null;
		}
		
		var PDDocument document = new PDDocument();
		
		for(i :sheet.posPage){
			document.addPage(pdf.getPage(i));	
		}
		
		document.save(outputPdfFile);
		document.close;
		return outputPdfFile;
	}	
	
	/**
	 * Exports a pdf file to the selected directory even if a file already exists.
	 */
	def static exportToPdfAndOverwriteFile(PDDocument pdf,StudentSheet sheet, File outputPdfFile){
		
		if(outputPdfFile.exists){
			return null;
		}
		
		var PDDocument document = new PDDocument();
		
		for(i :sheet.posPage){
			document.addPage(pdf.getPage(i));	
		}
		
		document.save(outputPdfFile);
		document.close;
	}
	
	
	/**
	 * Returns an InputStream of the exported PDF file
	 */
	def static InputStream exportToInputStream(PDDocument pdf, StudentSheet sheet){
        var PDDocument document = new PDDocument();

        for(i :sheet.posPage){
            document.addPage(pdf.getPage(i))
        }
        
        var PDStream ps = new PDStream(document);
        var InputStream is = ps.createInputStream();

        document.close;
        return is;

    }
    
    /**
     * Returns an OutputStream of the exported PDF file
     */
    def static OutputStream exportToOutputStream(PDDocument pdf, StudentSheet sheet){
        var PDDocument document = new PDDocument();

        for(i :sheet.posPage){
            document.addPage(pdf.getPage(i))
        }
        
        var PDStream ps = new PDStream(document);
        var OutputStream is = ps.createOutputStream();

        document.close;
        return is;

    }
    
    /**
     * Exports a Temp PDF file placed in Temp directory.
     */
    def static File exportToTempFile(PDDocument pdf,StudentSheet sheet){
		
		var PDDocument document = new PDDocument();
		
		for(i :sheet.posPage){
			document.addPage(pdf.getPage(i));	
		}
		
		var File file = File.createTempFile(service.examName+sheet.studentName , ".pdf");
		
		document.save(file);
		
		document.close;
		
		return file;
	}	
    
    
    /**
     * Export Collection of Temp PDF File
     */
    def static Collection<File> exportToCollection(PDDocument pdf,Collection<StudentSheet> sheets){
    	sheets.stream.map(s |exportToTempFile(pdf,s)).collect(Collectors.toList);
    }
    
    /**-----------------------------------------------------------------------
     * ------------------------Export with annotations------------------------
     * -----------------------------------------------------------------------
     */
     /*
      * Export pdf with annotations and Grade
      */
     def static void exportToPdfWithAnnotations(InputStream documentInputStream,StudentSheet sheet, File fileForSaving){
     	
		var PDDocument document = PDDocument.load(documentInputStream)

     	for(Grade g : sheet.grades){
     		for(Comment c : g.comments){
				//Write only TextComments
     			if(c instanceof TextComment){
     				//Remove newlines not supported by PDFBOX
     				var String text = c.text.replace("\n", "").replace("\r", "");
     				// Counter of lines
					var int nbLines = 1 + (text.length() / 30);
     			
     			
	     			var PDPage page = document.getPage(c.pageId);
	     			
	     			var float pageWidht = page.getMediaBox().getUpperRightX()
					var float pageHeight = page.getMediaBox().getUpperRightY()
			
					// Instantiating the PDPageContentStream class
					var PDPageContentStream contentStream = new PDPageContentStream(document, page,PDPageContentStream.AppendMode.APPEND, true, true);
					
					
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
					
					rectangleBottomLeftCornerX = c.x*pageWidht;
	
					// If text size < 30 char
					if (text.length() <= partitionSize) {
						rectangleWidth = text.length() * charWidth;
						rectangleHeight = charHeight;
						rectangleBottomLeftCornerY = pageHeight-pageHeight*c.y;
					} // If text size > 30 char
					else {
						rectangleBottomLeftCornerY = pageHeight-pageHeight*c.y - (charHeight * (nbLines - 1));
						rectangleWidth = partitionSize * charWidth;
						rectangleHeight = charHeight * nbLines;
					}
			
					// Drawing pointer line
					contentStream.moveTo(pageWidht*c.pointerX, pageHeight-pageHeight*c.pointerY)
					contentStream.lineTo(pageWidht*c.x + (rectangleWidth / 2), pageHeight-pageHeight*c.y)
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
					
					contentStream.setFont(PDType0Font.load(document, ResourcesUtils.getInputStreamResource("resources_annotation/arial.ttf")), 8);
					contentStream.setLeading(7f);
					contentStream.beginText();
					contentStream.newLineAtOffset(pageWidht*c.x, pageHeight-pageHeight*c.y);
			
					// Newline for text every "partitionSize" char
					for (var int i = 0; i < text.length(); i += partitionSize) {
						contentStream.showText(text.substring(i, Math.min(text.length(), i + partitionSize)));
						if (i < text.length()) {
							contentStream.newLine();
						}
					}
			
					contentStream.endText();
					contentStream.fill();
					contentStream.close();
					// File auto open
				}
     		}
     	}
     	

     	
     	//Write grade
     	var PDPage page = document.getPage(0);
     	var PDPageContentStream contentStream = new PDPageContentStream(document, page,PDPageContentStream.AppendMode.APPEND, true, true);
     	contentStream.setFont(PDType0Font.load(document, ResourcesUtils.getInputStreamResource("resources_annotation/arial.ttf")), 12);
     	
     	contentStream.setNonStrokingColor(Color.decode("#FF0000"))
     	contentStream.beginText();
		contentStream.newLineAtOffset(0, page.mediaBox.height-10);
		contentStream.showText("Note : "+sheet.computeGrade);
		contentStream.endText();
		contentStream.close();
		
		
		document.save(fileForSaving);
		document.close();
		documentInputStream.close();
		
     	
     }
     
     /*
	 * Draw lines annotations of fileForSaving
	 */
	def static void annotationDrawLinePDF(PDDocument document, HandwritingComment[] listLine, File fileForSaving) {
		
		for(HandwritingComment handwritingComment : listLine){
			
			var PDPage page = document.getPage(handwritingComment.pageId);
			var PDPageContentStream contentStream = new PDPageContentStream(document, page,
				PDPageContentStream.AppendMode.APPEND, true, true)
	
			for (Line l : handwritingComment.lines) {
				contentStream.moveTo(l.x1, l.y1)
				contentStream.lineTo(l.x2, l.y2)
				contentStream.setNonStrokingColor(Color.decode(l.color))
				contentStream.stroke()
				contentStream.fill();
			}
	
			contentStream.close();
			document.save(fileForSaving);
		}
	}
     
     
}