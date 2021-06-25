package fr.istic.tools.scanexam.qrCode.writer

import fr.istic.tools.scanexam.utils.ResourcesUtils
import java.io.File
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import java.util.List
import java.util.ArrayList
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.DataFormatter
import java.io.IOException

/**
 * Classe pour générer un document contenant le nom, prénom de chaque élève accompagné d'un qr code.
 * Le qr code contient des informations pour pouvoir identifier par la suite à qui appartient sa copie.
 * @author Julien Cochet
 */
class StudentsQrCodeDocGenerator {

	// ----------------------------------------------------------------------------------------------------
	/*
	 * VARIABLES
	 */
	// ----------------------------------------------------------------------------------------------------
	/** Nombre de qr codes par ligne */
	val int qrCodeByLine = 3
	/** Marge autour des qr codes en pixel */
	val int qrCodeMargin = 0
	/** Limite de caractères par ligne */
	val int lineLimit = 32

	// ----------------------------------------------------------------------------------------------------
	/*
	 * MÉTHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Produit le document contenant les qr codes d'identification d'élèves à partir d'un fichier XLS
	 * @param file Fichier XLS contenant les identifiants des étudiants
	 * @param outputFile Fichier pdf où enregistrer le pdf généré
	 */
	def void generateDocument(File file, File outputFile) {
		val List<String> students = loadStudentsFromFile(file)
		val PDDocument document = new PDDocument
		var studentIndex = 0
		while (studentIndex < students.length) {
			studentIndex = addPage(document, students, studentIndex)
		}
		document.save(outputFile)
		document.close
	}

	/**
	 * Produit le document contenant les qr codes d'identification d'élèves à partir d'une liste d'étudiants
	 * @param students Noms/identifiants des élèves
	 * @param outputFile Fichier pdf où enregistrer le pdf généré
	 */
	def void generateDocument(List<String> students, File outputFile) {
		val PDDocument document = new PDDocument
		var studentIndex = 0
		while (studentIndex < students.length) {
			studentIndex = addPage(document, students, studentIndex)
		}
		document.save(outputFile)
		document.close
	}

	/**
	 * Charge la liste des étudiants à partir d'un fichier XLS
	 * @param file Fichier XLS contenant les identifiants des étudiants
	 * @return Identifiants des étudiants sous forme de liste
	 */
	private def List<String> loadStudentsFromFile(File file) {
		val List<String> students = new ArrayList
		try(val Workbook workbook = WorkbookFactory.create(new FileInputStream(file))) {
			val Sheet sheet = workbook.getSheetAt(0)
			var int rowIndex = 0
			var Row row = sheet.getRow(rowIndex)
			val formatter = new DataFormatter
			while (row !== null) {
				val cell = row.getCell(0)
				val student = formatter.formatCellValue(cell)
				students.add(student)
				rowIndex++
				row = sheet.getRow(rowIndex)
			}
		} catch (IOException e) {
			e.printStackTrace
		}
		return students
	}

	/**
	 * Ajoute à un document, une page contenant les noms, prénoms et qr codes des élèves
	 * @param document Document auquel ajouter la page
	 * @param students Noms/identifiants des élèves à inscrire sur cette page
	 * @param firstStudent Index de l'étudiant par lequel commencer
	 * @return Si tout les qr codes n'ont pas pu être mis, index du prochain élève à insérer ; taille de la collection d'élèves sinon
	 */
	private def int addPage(PDDocument document, List<String> students, int firstStudent) {
		val PDPage page = new PDPage
		document.addPage(page)
		val PDPageContentStream contentStream = new PDPageContentStream(document, page)

		val int qrCodeSize = (page.mediaBox.width / (qrCodeByLine * 2)).intValue
		var int qrCodePosY = page.mediaBox.height.intValue - qrCodeSize
		var studentIndex = firstStudent
		while (qrCodePosY >= 0 && studentIndex < students.length) {
			var qrCodeOnLine = 0
			while (qrCodeOnLine < qrCodeByLine && studentIndex < students.length) {
				val int qrCodePosX = qrCodeOnLine * 2 * qrCodeSize
				val String data = students.get(studentIndex)
				insertQrCode(contentStream, document, qrCodePosX, qrCodePosY, qrCodeSize, data)
				insertText(contentStream, document, qrCodePosX + qrCodeSize,
					qrCodePosY + (qrCodeSize * 0.8f).intValue, data)
				qrCodeOnLine++
				studentIndex++
			}
			qrCodePosY -= qrCodeSize
		}
		contentStream.close
		return studentIndex
	}

	/**
	 * Insère un qr code sur une page
	 * @param contentStream Stream entre le document et la page où insérer le qr code
	 * @param document Document auquel ajouter le qr code
	 * @param posX Position du qr code sur l'axe X
	 * @param posX Position du qr code sur l'axe Y
	 * @param size Hauteur/largeur des qr codes en pixel
	 * @param data Information à inscrire dans le qr code
	 */
	private def void insertQrCode(PDPageContentStream contentStream, PDDocument document, int posX, int posY, int size,
		String data) {
		if (data !== "") {
			val File qrCodeFile = File.createTempFile("qrcode", ".png")
			val qrCodeGen = new QRCodeGeneratorImpl
			qrCodeGen.generateQRCodeImage(data, size, size, qrCodeMargin, qrCodeFile.path)
			val PDImageXObject qrCode = PDImageXObject.createFromFile(qrCodeFile.path, document)
			contentStream.drawImage(qrCode, posX, posY)
			qrCodeFile.delete
		}
	}

	/**
	 * Insère du texte sur une page
	 * @param contentStream Stream entre le document et la page où insérer le texte
	 * @param document Document auquel ajouter le texte
	 * @param posX Position du texte sur l'axe X
	 * @param posX Position du texte sur l'axe Y
	 * @param text Texte à écrire
	 */
	private def void insertText(PDPageContentStream contentStream, PDDocument document, int posX, int posY,
		String text) {
		contentStream.beginText
		contentStream.setFont(
			PDType0Font.load(document, ResourcesUtils.getInputStreamResource("resources_annotation/arial.ttf")), 6)
		contentStream.setLeading(8.0f)
		contentStream.newLineAtOffset(posX, posY)
		val String[] splitedTexts = splitString(text, lineLimit)
		for (splitedText : splitedTexts) {
			contentStream.showText(splitedText)
			contentStream.newLine
		}
		contentStream.endText
	}

	/**
	 * Sépare un texte avec un nombre maximal de caractères à la suite
	 * @param text Texte à séparer
	 * @param charByLine Nombre maximal de caractères par string
	 * @return Le texte séparé sous forme de liste
	 */
	private def List<String> splitString(String text, int charByLine) {
		val String[] words = text.split(" ")
		val List<String> texts = new ArrayList
		var String tmpText = ""
		for (i : 0 ..< words.length) {
			tmpText += words.get(i) + " "
			if ((i + 1 >= words.length) || (tmpText.length + words.get(i + 1).length > charByLine)) {
				texts.add(tmpText)
				tmpText = ""
			}
		}
		return texts
	}
}
