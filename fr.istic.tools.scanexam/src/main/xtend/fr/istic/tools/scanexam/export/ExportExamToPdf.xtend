package fr.istic.tools.scanexam.export

import fr.istic.tools.scanexam.core.Comment
import fr.istic.tools.scanexam.core.Grade
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.core.TextComment
import fr.istic.tools.scanexam.services.api.Service
import fr.istic.tools.scanexam.utils.ResourcesUtils
import java.awt.Color
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.util.Collection
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDStream
import org.apache.pdfbox.pdmodel.font.PDType0Font
import java.util.ArrayList


class ExportExamToPdf {
	
	/**
	 * Exports a student's PDF file from the PDF document containing all the exam papers WITHOUT ANNOTATIONS OR GRADE
	 * @param pdf is the complete pdf document of all students
	 * @param sheet is the sheet of the student to export.
	 * @param overwriteFile allow file overwriting
	 * @return null is return if overwrite is needed but @param overwriteFile is false, else return File.
	 */
	def static File exportStudentExamFromCompletePdfToPDF(PDDocument pdf,StudentSheet sheet, File outputPdfFile){
		var PDDocument document = new PDDocument();
	
		for(i :sheet.posPage){
			document.addPage(pdf.getPage(i));	
		}
		
		document.save(outputPdfFile);
		document.close;
		return outputPdfFile;
	}	
	
	
	/**
	 * Exports a student's InputStream from the PDF document containing all the exam papers WITHOUT ANNOTATIONS OR GRADE
	 * @param pdf is the complete pdf document of all students
	 * @param sheet is the sheet of the student to export.
	 * @return InputStream of student exam
	 */
	def static InputStream exportStudentExamFromCompletePdfToInputStream(PDDocument pdf, StudentSheet sheet){
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
	 * Exports a student's InputStream from the PDF document containing all the exam papers WITHOUT ANNOTATIONS OR GRADE
	 * @param pdf is the complete pdf document of all students
	 * @param sheet is the sheet of the student to export.
	 * @return InputStream of student exam
	 */
	def static InputStream exportStudentExamFromCompletePdfToInputStream(InputStream pdf, StudentSheet sheet){
		var PDDocument document = PDDocument.load(pdf);
		
        var PDDocument studentDocument = new PDDocument();

        for(i :sheet.posPage){
            studentDocument.addPage(document.getPage(i))
        }
        
        var PDStream ps = new PDStream(studentDocument);
        var InputStream is = ps.createInputStream();
        document.close;
        return is;
    }
    
    /**
	 * Exports a student's OutputStream from the PDF document containing all the exam papers WITHOUT ANNOTATIONS OR GRADE
	 * @param pdf is the complete pdf document of all students
	 * @param sheet is the sheet of the student to export.
	 * @return OutputStream of student exam
	 */
    def static OutputStream exportToOutputStreamToOutputStream(PDDocument pdf, StudentSheet sheet){
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
	 * Exports a student's temp PDF file from the PDF document containing all the exam papers WITHOUT ANNOTATIONS OR GRADE
	 * The name of the file is examName + studentName
	 * @param pdf is the complete pdf document of all students
	 * @param sheet is the sheet of the student to export.
	 * @return temp File of student exam. 
	 */
    def static File exportToTempFile(Service service,InputStream pdfStream,StudentSheet sheet){
		
		val pdf = PDDocument.load(pdfStream)
		
		var PDDocument document = new PDDocument();
		
		for(i :sheet.posPage){
			document.addPage(pdf.getPage(i));	
		}
		
		
		var File file = File.createTempFile(service.examName+sheet.studentName , ".pdf");
		
		document.save(file);
		document.close;
		
		return file;
	}
    
    /**-----------------------------------------------------------------------
     * ------------------------Export with annotations------------------------
     * -----------------------------------------------------------------------
     */

     
     /**
      * EXPORT a PDF file for each student containing all annotations TO selected folder
      * @param documentInputStream is the PDF of all student exams
      * @param sheets is they studentSheet of they students
      * @param folderForSaving is the Folder for save PDF documents
      * @return collection of temp Files
      */
     def static void exportExamsOfStudentsToPdfsWithAnnotations(InputStream documentInputStream,Collection<StudentSheet> sheets, File folderForSaving){
     	var File tempExam = File.createTempFile("examTemp", ".pdf");
     	
     	exportExamsToAnnotedPdf(documentInputStream, sheets, tempExam);
     	
     	var PDDocument pdf = PDDocument.load(tempExam);
     	
     	for(sheet : sheets){
     		
     		var PDDocument document = new PDDocument();
     		for(i :sheet.posPage){
				document.addPage(pdf.getPage(i));	
			}
			
			document.save(new File(folderForSaving.absolutePath + File.separator + sheet.studentName + ".pdf") );
			document.close;
     	}
     	pdf.close();
     }
     
     /**
      * EXPORT a Collection of PDF TEMP files for each student containing all annotations TO selected folder
      * @param documentInputStream is the PDF of all student exams
      * @param sheets is they studentSheet of they students
      * @return collection of temp Files
      */
     def Collection<File> exportExamsOfStudentsToTempPdfsWithAnnotations(InputStream documentInputStream,Collection<StudentSheet> sheets){
     	var Collection<File> tempExams = new ArrayList<File>();
     	
     	var File tempExam = File.createTempFile("examTemp", ".pdf");
     	
     	exportExamsToAnnotedPdf(documentInputStream, sheets, tempExam);
     	
     	var PDDocument pdf = PDDocument.load(tempExam);
     	
     	for(sheet : sheets){
     		
     		var PDDocument document = new PDDocument();
     		for(i :sheet.posPage){
				document.addPage(pdf.getPage(i));	
			}
			var File studentExam = File.createTempFile("tempExam" + sheet.studentName, ".pdf");
			document.save(studentExam);
			tempExams.add(studentExam);
			document.close;
     	}
     	pdf.close();
     	return tempExams;
     }
     
     
     /**
      * EXPORT a PDF TEMP containing all annotations TO temp file
      * @param studentExamDocument is the PDF file of all students
      * @param sheet is the studentSheet of the student
	  * @return temp File of annoted PDF.
      */
     def File exportExamsToTempAnnotedPdf(InputStream documentInputStream,Collection<StudentSheet> sheets){
     	var File tempExam = File.createTempFile("examTemp", ".pdf");
     	exportExamsToAnnotedPdf(documentInputStream,sheets, tempExam);
     	return tempExam;
     }
     
     /**
      * Export PDF file containing all annotations for a student
      * @param studentExamDocument is the PDF file of the student
      * @param sheet is the studentSheet of the student
	  * @return temp File of annoted PDF.
      */
     def File exportToTempPdfWithAnnotations(Service service,InputStream studentExamDocument, StudentSheet sheet){
     	var File file = File.createTempFile(service.examName+sheet.studentName , ".pdf");
     	exportExamToAnnotedPdf(studentExamDocument, sheet, file);
     	return file;
     }
     
     
     /**
      * Add annotations to master PDF that contains all students
      * @param documentInputStream is the PDF file of the student
      * @param sheets is they studentSheet of they students
      * @param fileForSaving is the File for save PDF document
      */
     def static void exportExamsToAnnotedPdf(InputStream documentInputStream,Collection<StudentSheet> sheets, File fileForSaving){
     	var PDDocument document = PDDocument.load(documentInputStream)
		for(sheet : sheets){
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
	     	var PDPage page = document.getPage(sheet.posPage.get(0));
	     	var PDPageContentStream contentStream = new PDPageContentStream(document, page,PDPageContentStream.AppendMode.APPEND, true, true);
	     	contentStream.setFont(PDType0Font.load(document, ResourcesUtils.getInputStreamResource("resources_annotation/arial.ttf")), 12);
	     	
	     	contentStream.setNonStrokingColor(Color.decode("#FF0000"))
	     	contentStream.beginText();
			contentStream.newLineAtOffset(0, page.mediaBox.height-10);
			contentStream.showText("Note : "+sheet.computeGrade);
			contentStream.endText();
			contentStream.close();
		}
		document.save(fileForSaving);
		document.close();
		documentInputStream.close();

     }
     
     
     
     /**
      * Add annotations to PDF exam of a student
      * @param documentInputStream is the PDF file of the student
      * @param sheet is the studentSheet of the student
      * @param fileForSaving is the File for save PDF document
      */
     def static void exportExamToAnnotedPdf(InputStream documentInputStream,StudentSheet sheet, File fileForSaving){
     	
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
     
     
}