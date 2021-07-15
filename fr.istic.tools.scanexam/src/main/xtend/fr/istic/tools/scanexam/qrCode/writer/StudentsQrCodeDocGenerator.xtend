package fr.istic.tools.scanexam.qrCode.writer

import fr.istic.tools.scanexam.importation.StudentDataManager
import fr.istic.tools.scanexam.qrCode.QrCodeType
import fr.istic.tools.scanexam.utils.ResourcesUtils
import java.io.File
import java.util.ArrayList
import java.util.Collections
import java.util.Comparator
import java.util.List
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.font.PDType0Font
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject

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
	/** Marge autour des qr codes en pixel */
	val int qrCodeMargin = 2

	// ----------------------------------------------------------------------------------------------------
	/*
	 * MÉTHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Produit le document contenant les qr codes d'identification d'élèves à partir d'un fichier XLS
	 * @param file Fichier XLS contenant les identifiants des étudiants
	 * @param firstCell Cellule la plus en haut à gauche à prendre en compte
	 * @param labelWidth Largeur des étiquettes en mm
	 * @param labelHeight Hauteur des étiquettes en mm
	 * @param alphabeticalOrder Indique si les étudiants doivent être mis par ordre alphabetique
	 * @param outputFile Fichier pdf où enregistrer le pdf généré
	 */
	def void generateDocument(File file, String firstCell, float labelWidth, float labelHeight, boolean alphabeticalOrder,
		File outputFile) {
		val List<List<String>> studentsData = StudentDataManager.loadData(file, firstCell)
		if (alphabeticalOrder) {
			Collections.sort(studentsData, new StudentDataComparator)
		}
		val PDDocument document = new PDDocument
		var studentIndex = 0
		while (studentIndex < studentsData.length) {
			studentIndex = addPage(document, studentsData, studentIndex, labelWidth, labelHeight)
		}
		document.save(outputFile)
		document.close
	}

	/**
	 * Produit le document contenant les qr codes d'identification d'élèves à partir d'une liste d'étudiants
	 * @param studentsData Informations (id, nom, prénom) des élèves
	 * @param labelWidth Largeur des étiquettes en mm
	 * @param labelHeight Hauteur des étiquettes en mm
	 * @param outputFile Fichier pdf où enregistrer le pdf généré
	 */
	def void generateDocument(List<List<String>> studentsData, float labelWidth, float labelHeight, File outputFile) {
		val PDDocument document = new PDDocument
		var studentIndex = 0
		while (studentIndex < studentsData.length && studentIndex != -1) {
			studentIndex = addPage(document, studentsData, studentIndex, labelWidth, labelHeight)
		}
		document.save(outputFile)
		document.close
	}

	/**
	 * Ajoute à un document, une page contenant les noms, prénoms et qr codes des élèves
	 * @param document Document auquel ajouter la page
	 * @param studentsData Informations (id, nom, prénom) des élèves à inscrire sur cette page
	 * @param firstStudent Index de l'étudiant par lequel commencer
	 * @param labelWidth Largeur des étiquettes en mm
	 * @param labelHeight Hauteur des étiquettes en mm
	 * @return Si aucun qr code n'a été mis, -1 ; si tout les qr codes n'ont pas pu être mis, index du prochain élève à insérer ; taille de la collection d'élèves sinon
	 */
	private def int addPage(PDDocument document, List<List<String>> studentsData, int firstStudent, float labelWidth,
		float labelHeight) {
		val PDPage page = new PDPage
		document.addPage(page)
		val PDPageContentStream contentStream = new PDPageContentStream(document, page)

		val int pageWidthPixel = page.mediaBox.width.intValue
		val int pageHeightPixel = page.mediaBox.height.intValue
		val int labelWidthPixel = (labelWidth * pageWidthPixel / 210).intValue
		val int labelHeightPixel = (labelHeight * pageHeightPixel / 297).intValue
		val int xMarginPixel = (pageWidthPixel % labelWidthPixel) / 2
		val int yMarginPixel = (pageHeightPixel % labelHeightPixel) / 2

		val int qrCodeSize = labelWidthPixel < labelHeightPixel ? labelWidthPixel : labelHeightPixel
		var int posX = xMarginPixel
		var int posY = pageHeightPixel - yMarginPixel - labelHeightPixel

		var studentIndex = firstStudent

		while (posY >= yMarginPixel && studentIndex < studentsData.length) {
			while ((posX + (labelWidthPixel * 2)) <= (pageHeightPixel - xMarginPixel) &&
				studentIndex < studentsData.length) {
				val String id = studentsData.get(studentIndex).get(0)
				val String lastName = studentsData.get(studentIndex).get(1)
				val String firstName = studentsData.get(studentIndex).get(2)
				insertQrCode(contentStream, document, posX + ((labelWidthPixel - qrCodeSize) / 2), posY, qrCodeSize,
					QrCodeType.STUDENT + "_" + id + "_" + lastName + "_" + firstName)
				insertText(contentStream, document, posX + labelWidthPixel + 4,
					posY + (labelHeightPixel * 0.8f).intValue, labelWidthPixel / 5, id)
				posX += (labelWidthPixel * 2)
				studentIndex++
			}
			posX = xMarginPixel
			posY -= labelHeightPixel
		}
		contentStream.close
		if (studentIndex === firstStudent) {
			return -1
		} else {
			return studentIndex
		}
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
			qrCodeGen.generateQRCodeImage(data, size * 4, size * 4, qrCodeMargin, qrCodeFile.path)
			val PDImageXObject qrCode = PDImageXObject.createFromFile(qrCodeFile.path, document)
			contentStream.drawImage(qrCode, posX, posY, size, size)
			qrCodeFile.delete
		}
	}

	/**
	 * Insère du texte sur une page
	 * @param contentStream Stream entre le document et la page où insérer le texte
	 * @param document Document auquel ajouter le texte
	 * @param posX Position du texte sur l'axe X
	 * @param posX Position du texte sur l'axe Y
	 * @param charByLine Nombre maximal de caractères par string
	 * @param text Texte à écrire
	 */
	private def void insertText(PDPageContentStream contentStream, PDDocument document, int posX, int posY,
		int charByLine, String text) {
		contentStream.beginText
		contentStream.setFont(
			PDType0Font.load(document, ResourcesUtils.getInputStreamResource("resources_annotation/arial.ttf")), 6)
		contentStream.setLeading(8.0f)
		contentStream.newLineAtOffset(posX, posY)
		val String[] splitedTexts = splitString(text, charByLine)
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

class StudentDataComparator implements Comparator<List<String>> {
	override int compare(List<String> o1, List<String> o2) {
		val String firstString_o1 = o1.get(0)
		val String firstString_o2 = o2.get(0)
		return firstString_o1.compareTo(firstString_o2)
	}
}
