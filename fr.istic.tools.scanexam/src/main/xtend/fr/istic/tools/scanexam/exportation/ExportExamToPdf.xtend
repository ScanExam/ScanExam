package fr.istic.tools.scanexam.exportation

import fr.istic.tools.scanexam.core.Comment
import fr.istic.tools.scanexam.core.Grade
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.core.TextComment
import fr.istic.tools.scanexam.services.api.ServiceGraduation
import fr.istic.tools.scanexam.utils.ResourcesUtils
import fr.istic.tools.scanexam.utils.Tuple3
import java.awt.Color
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.ArrayList
import java.util.Arrays
import java.util.Collection
import java.util.HashMap
import java.util.List
import java.util.Map
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDStream
import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.apache.pdfbox.multipdf.PDFMergerUtility

class ExportExamToPdf {

	/**
	 * Exports a student's PDF file from the PDF document containing all the exam papers WITHOUT ANNOTATIONS OR GRADE
	 * @param pdf is the complete pdf document of all students
	 * @param sheet is the sheet of the student to export.
	 * @param overwriteFile allow file overwriting
	 * @return null is return if overwrite is needed but @param overwriteFile is false, else return File.
	 */
	def static File exportStudentExamFromCompletePdfToPDF(PDDocument pdf, StudentSheet sheet, File outputPdfFile) {
		var PDDocument document = new PDDocument();

		for (i : sheet.posPage) {
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
	def static InputStream exportStudentExamFromCompletePdfToInputStream(PDDocument pdf, StudentSheet sheet) {
		var PDDocument document = new PDDocument();

		for (i : sheet.posPage) {
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
	def static InputStream exportStudentExamFromCompletePdfToInputStream(InputStream pdf, StudentSheet sheet) {
		var PDDocument document = PDDocument.load(pdf);

		var PDDocument studentDocument = new PDDocument();

		for (i : sheet.posPage) {
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
	def static OutputStream exportToOutputStreamToOutputStream(PDDocument pdf, StudentSheet sheet) {
		var PDDocument document = new PDDocument();

		for (i : sheet.posPage) {
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
	def static File exportToTempFile(InputStream pdfStream, StudentSheet sheet) {

		val pdf = PDDocument.load(pdfStream)

		var PDDocument document = new PDDocument();

		for (i : sheet.posPage) {
			document.addPage(pdf.getPage(i));
		}

		var File file = File.createTempFile(sheet.studentInfo, ".pdf");

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
	 * @param service Service managing the graduation
	 * @param documentInputStream is the PDF of all student exams
	 * @param sheets is they studentSheet of they students
	 * @param folderForSaving is the Folder for save PDF documents
	 * @return collection of temp Files
	 */
	def static void exportExamsOfStudentsToPdfsWithAnnotations(ServiceGraduation service,
		InputStream documentInputStream, Collection<StudentSheet> sheets, File folderForSaving, double originWidht) {
		var File tempExam = File.createTempFile("examTemp", ".pdf");
		val float globalScale = service.getGlobalScale
		exportExamsToAnnotedPdf(documentInputStream, sheets, tempExam, globalScale, originWidht);
		addGradeDetailToPdf(service, sheets, tempExam)

		var PDDocument pdf = PDDocument.load(tempExam);

		for (sheet : sheets) {

			var PDDocument document = new PDDocument();
			for (i : sheet.posPage) {
				document.addPage(pdf.getPage(i));
			}

			document.save(new File(folderForSaving.absolutePath + File.separator + sheet.studentInfo + ".pdf"));
			document.close;
		}
		pdf.close();
	}

	/**
	 * EXPORT a Collection of PDF TEMP files for each student containing all annotations TO selected folder
	 * @param service Service managing the graduation
	 * @param documentInputStream is the PDF of all student exams
	 * @param sheets is they studentSheet of they students
	 * @return map of student's name to temp file 
	 */
	def static Map<String, File> exportExamsOfStudentsToTempPdfsWithAnnotations(ServiceGraduation service,
		InputStream documentInputStream, Collection<StudentSheet> sheets, double originWidht) {
		var Map<String, File> tempExams = new HashMap<String, File>();

		var File tempExam = File.createTempFile("examTemp", ".pdf");

		val float globalScale = service.getGlobalScale
		exportExamsToAnnotedPdf(documentInputStream, sheets, tempExam, globalScale, originWidht);
		addGradeDetailToPdf(service, sheets, tempExam)

		var PDDocument pdf = PDDocument.load(tempExam);

		for (sheet : sheets) {

			var PDDocument document = new PDDocument();
			for (i : sheet.posPage) {
				document.addPage(pdf.getPage(i));
			}
			var File studentExam = File.createTempFile("tempExam" + sheet.studentInfo, ".pdf");
			document.save(studentExam);
			tempExams.put(sheet.studentID, studentExam);
			document.close;
		}
		pdf.close();
		return tempExams;
	}

	/**
	 * EXPORT a PDF TEMP containing all annotations TO temp file
	 * @param service Service managing the graduation
	 * @param studentExamDocument is the PDF file of all students
	 * @param sheet is the studentSheet of the student
	 * @return temp File of annoted PDF.
	 */
	def static File exportExamsToTempAnnotedPdf(ServiceGraduation service, InputStream documentInputStream,
		Collection<StudentSheet> sheets, double originWidht) {
		var File tempExam = File.createTempFile("examTemp", ".pdf");

		val float globalScale = service.getGlobalScale
		exportExamsToAnnotedPdf(documentInputStream, sheets, tempExam, globalScale, originWidht);
		addGradeDetailToPdf(service, sheets, tempExam)
		return tempExam;
	}

	/**
	 * Export a TEMP PDF file containing all annotations for a student
	 * @param service Service managing the graduation
	 * @param studentExamDocument is the PDF file of the student
	 * @param sheet is the studentSheet of the student
	 * @return temp File of annoted PDF.
	 */
	def static void exportStudentExamToPdfWithAnnotations(ServiceGraduation service, InputStream examDocument,
		StudentSheet sheet, File fileForSaving, double originWidht) {

		var File exam = exportExamsToTempAnnotedPdf(service, examDocument,
			new ArrayList<StudentSheet>(Arrays.asList(sheet)), originWidht)

		var PDDocument pdf = PDDocument.load(exam);

		var PDDocument document = new PDDocument();
		for (i : sheet.posPage) {
			document.addPage(pdf.getPage(i));
		}

		document.save(fileForSaving);
		document.close;
		pdf.close();
	}

	/**
	 * Export PDF file containing all annotations for a student
	 * @param service Service managing the graduation
	 * @param studentExamDocument is the PDF file of the student
	 * @param sheet is the studentSheet of the student
	 * @return temp File of annoted PDF.
	 */
	def static Pair<String, File> exportStudentExamToTempPdfWithAnnotations(ServiceGraduation service,
		InputStream examDocument, StudentSheet sheet, double originWidht) {
		var File studentExam = File.createTempFile(sheet.studentInfo, ".pdf");

		var File exam = exportExamsToTempAnnotedPdf(service, examDocument,
			new ArrayList<StudentSheet>(Arrays.asList(sheet)), originWidht)

		var PDDocument pdf = PDDocument.load(exam);

		var PDDocument document = new PDDocument();
		for (i : sheet.posPage) {
			document.addPage(pdf.getPage(i));
		}

		document.save(studentExam);
		document.close;
		pdf.close();

		return Pair.of(sheet.studentInfo + ".pdf", studentExam);
	}

	/**
	 * Add annotations to master PDF that contains all students
	 * @param documentInputStream is the PDF file of the student
	 * @param sheets is they studentSheet of they students
	 * @param fileForSaving is the File for save PDF document
	 */
	def static void exportExamsToAnnotedPdf(InputStream documentInputStream, Collection<StudentSheet> sheets,
		File fileForSaving, float globalScale, double originWidht) {
		var PDDocument document = PDDocument.load(documentInputStream)
		for (sheet : sheets) {
			for (Grade g : sheet.grades) {
				for (Comment c : g.comments) {
					// Write only TextComments
					if (c instanceof TextComment) {
						// Remove newlines not supported by PDFBOX
						var String text = c.text.replace("\n", "").replace("\r", "");
						// Counter of lines
						var int nbLines = 1 + (text.length() / 30);

						var PDPage page = document.getPage(sheet.posPage.get(c.pageId));

						var float pageWidht = page.getMediaBox().getUpperRightX()
						var float pageHeight = page.getMediaBox().getUpperRightY()

						// Instantiating the PDPageContentStream class
						var PDPageContentStream contentStream = new PDPageContentStream(document, page,
							PDPageContentStream.AppendMode.APPEND, true, true);

						// Number of char needed to skip a line
						var int partitionSize = 30;
						// Considered width of a character
						var float charWidth = 3.5f;

						// Considered height of a char
						var float charHeight = 9;

						// Difference between resolution of application and pdf export
						var float resolutiondiff = pageWidht / originWidht.floatValue();

						var float rectangleBottomLeftCornerX;
						var float rectangleBottomLeftCornerY;
						var float rectangleWidth;
						var float rectangleHeight;

						rectangleBottomLeftCornerX = c.x * resolutiondiff;

						// If text size < 30 char
						if (text.length() <= partitionSize) {
							rectangleWidth = text.length() * charWidth;
							rectangleHeight = charHeight;
							rectangleBottomLeftCornerY = pageHeight - c.y * resolutiondiff;
						} // If text size > 30 char
						else {
							rectangleBottomLeftCornerY = pageHeight - c.y * resolutiondiff -
								(charHeight * (nbLines - 1));
							rectangleWidth = partitionSize * charWidth;
							rectangleHeight = charHeight * nbLines;
						}

						// Drawing pointer line
						contentStream.moveTo(c.pointerX * resolutiondiff, pageHeight - c.pointerY * resolutiondiff)
						contentStream.lineTo(c.x * resolutiondiff + (rectangleWidth / 2),
							pageHeight - c.y * resolutiondiff)
						contentStream.setNonStrokingColor(Color.decode("#0093ff"))
						contentStream.stroke()
						contentStream.fill();

						// Draw rectangle
						contentStream.addRect(rectangleBottomLeftCornerX - 2, rectangleBottomLeftCornerY - 2,
							rectangleWidth + 4, rectangleHeight + 4)
						contentStream.setNonStrokingColor(Color.decode("#000000"))
						contentStream.fill();

						contentStream.addRect(rectangleBottomLeftCornerX, rectangleBottomLeftCornerY, rectangleWidth,
							rectangleHeight)
						contentStream.setNonStrokingColor(Color.decode("#ffffff"))
						contentStream.fill();

						contentStream.setNonStrokingColor(Color.decode("#000000"))

						contentStream.setFont(
							PDType0Font.load(document,
								ResourcesUtils.getInputStreamResource("resources_annotation/arial.ttf")), 8);
						contentStream.setLeading(7f);
						contentStream.beginText();
						contentStream.newLineAtOffset(c.x * resolutiondiff, pageHeight - c.y * resolutiondiff);

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
			// Write grade
			var PDPage page = document.getPage(sheet.posPage.get(0));
			var PDPageContentStream contentStream = new PDPageContentStream(document, page,
				PDPageContentStream.AppendMode.APPEND, true, true);
			contentStream.setFont(
				PDType0Font.load(document, ResourcesUtils.getInputStreamResource("resources_annotation/arial.ttf")),
				12);

			contentStream.setNonStrokingColor(Color.decode("#FF0000"))
			contentStream.beginText();
			contentStream.newLineAtOffset(0, page.mediaBox.height - 10);
			contentStream.showText("Note : " + sheet.computeGrade + "/" + globalScale);
			contentStream.endText();
			contentStream.close();
		}
		document.save(fileForSaving);
		document.close();
		documentInputStream.close();
	}

	/**
	 * Ajoute le détail des notes de chaque étudiant à un pdf existant
	 * @author Julien Cochet
	 * @param service Service gérant la correction
	 * @param sheets  Copies des étudiants dont on veut le détail de la note
	 * @param pdfFile Pdf où ajouter le détail des notes
	 */
	private def static void addGradeDetailToPdf(ServiceGraduation service, Collection<StudentSheet> sheets,
		File pdfFile) {
		// Copie du fichier CSS du détail des notes dans le fichier temporaire
		val InputStream resourcesStream = ResourcesUtils.getInputStreamResource("viewResources/gradeDetail.css")
		val Path cssPath = Files.createTempFile("gradeDetail", ".css")
		new File(cssPath.toUri).delete
		Files.copy(resourcesStream, cssPath)
		resourcesStream.close
		val Path resourcesPath = Paths.get(System.getProperty("java.io.tmpdir"))

		// Fichier contenant le détail de toutes les notes
		val Path gradesDetailPath = Files.createTempFile("gradesDetail", ".pdf")
		val File gradesDetailFile = new File(gradesDetailPath.toUri)

		// Nombre de pages dans le pdf contenant toutes les copies étudiantes
		val PDDocument pdfPDDoc = PDDocument.load(pdfFile)
		val pdfNbPage = pdfPDDoc.numberOfPages
		pdfPDDoc.close

		// Création d'un pdf avec le détail de la note de la première copie
		var htmlContent = generateGradeDetailContent(service, sheets.get(0), cssPath.fileName.toString)
		HtmlPdfMerger.createPdfFromHtmlContent(htmlContent, resourcesPath, gradesDetailPath)
		// Ajout de ces pages aux pages lié à l'étudiant
		var initialNbPage = 0
		var PDDocument gradeDetailPDDoc = PDDocument.load(gradesDetailFile)
		var newNbPage = gradeDetailPDDoc.numberOfPages
		gradeDetailPDDoc.close
		for (page : initialNbPage ..< newNbPage) {
			val index = page - initialNbPage
			sheets.get(0).posPage.add(index, page + pdfNbPage)
		}

		// Ajout du détail des autres notes au pdf de détail de note
		for (i : 1 ..< sheets.size) {
			htmlContent = generateGradeDetailContent(service, sheets.get(i), cssPath.fileName.toString)
			HtmlPdfMerger.mergeHtmlContentWithPdf(htmlContent, resourcesPath, gradesDetailFile, gradesDetailPath, false)
			// Ajout de ces nouvelles pages aux pages lié à l'étudiant
			initialNbPage = newNbPage
			gradeDetailPDDoc = PDDocument.load(gradesDetailFile)
			newNbPage = gradeDetailPDDoc.numberOfPages
			gradeDetailPDDoc.close
			for (page : initialNbPage ..< newNbPage) {
				val index = page - initialNbPage
				sheets.get(i).posPage.add(index, page + pdfNbPage)
			}
		}

		// Suppression du CSS temporaire
		new File(cssPath.toUri).delete

		// Ajout des détails des notes au pdf contenant toutes les copies d'élèves
		val Path pdfPath = Paths.get(pdfFile.toURI)
		val PDFMergerUtility merger = new PDFMergerUtility
		merger.addSource(pdfFile)
		merger.addSource(gradesDetailFile)
		merger.setDestinationFileName(pdfPath.toString())
		merger.mergeDocuments(null)
		gradesDetailFile.delete
	}

	/**
	 * Renvoie le détail de la note d'un étudiant sous la forme d'une page html donnée en String
	 * @author Julien Cochet
	 * @param service Service gérant la correction
	 * @param sheet Copie de l'étudiant dont on veut le détail de la note
	 * @param cssName Nom du fichier css, laisser un string vide pour ne pas en utiliser
	 * @return Le détail de la note de l'étudiant sous la forme d'une page html donnée en String
	 */
	private def static String generateGradeDetailContent(ServiceGraduation service, StudentSheet sheet,
		String cssName) {
		// Information globale sur la copie
		service.selectSheet(sheet.id)
		val String examName = service.examName
		val String studentName = sheet.studentID
		val float globalGrade = sheet.computeGrade
		val float globalScale = service.globalScale

		val GradeDetailToHtml gradeDetailToHtml = new GradeDetailToHtml(examName, studentName, globalGrade, globalScale)
		gradeDetailToHtml.cssName = cssName

		// Parcours de chaque question
		for (i : 0 ..< sheet.grades.size) {
			// Détail de la question
			val String qstName = service.getQuestion(i).name
			val double qstGrade = service.getQuestionSelectedGradeEntriesTotalWorth(i)
			val double qstScale = service.getQuestion(i).gradeScale.maxPoint
			val List<Integer> achievedPoints = service.getQuestionSelectedGradeEntries(i)
			val Map<String, Float> achieved = new HashMap
			val Map<String, Float> missed = new HashMap
			val List<Tuple3<Integer, String, Float>> gradeEntries = service.getQuestionGradeEntries(i)
			for (entry : gradeEntries) {
				if (achievedPoints.contains(entry._1)) {
					achieved.put(entry._2, entry._3)
				} else {
					missed.put(entry._2, entry._3)
				}
			}
			gradeDetailToHtml.addQuestion(qstName, qstGrade, qstScale, achieved, missed)
		}
		return gradeDetailToHtml.toHtmlPage
	}

}
