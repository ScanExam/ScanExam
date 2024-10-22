package fr.istic.tools.scanexam.view.fx

import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.Objects
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.ImageType
import org.apache.pdfbox.rendering.PDFRenderer
import org.eclipse.xtend.lib.annotations.Accessors

/** 
 * Controlleur du pdf
 * @author Benjamin Danlos, Julien Cochet
 */
class PdfManager {
	
	// ----------------------------------------------------------------------------------------------------
	/* 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/* Pdf chargé */
	PDDocument document;

	/* Index de la page courante du modèle d'exam */
	@Accessors int pdfPageIndex

	/* outputStream du pdf */
	ByteArrayOutputStream pdfOutput

	// ----------------------------------------------------------------------------------------------------
	/* 
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/**
	 * Change la page courante par la page du numéro envoyé en paramètre (ne change rien si la page n'existe pas)
	 * @param page Numéro de page où se rendre
	 */
	def goToPdfPage(int page) {
		if (page >= 0 && page < document.pages.size) {
			pdfPageIndex = page
		}
	}

	/**
	 * Retourne la page courante
	 * @return Page courante
	 */
	def BufferedImage getCurrentPdfPage() {
		pageToImage();
	}

	/**
	 * Passe à la page suivante, ou va à la première page s'il n'y en a pas
	 */
	def nextPdfPage() {
		if (pdfPageIndex + 1 < document.pages.size) {
			pdfPageIndex++
		} else {
			pdfPageIndex = 0
		}
	}

	/**
	 * Passe à la page précédente, ou va à la dernière page s'il n'y en a pas
	 */
	def previousPdfPage() {
		if (pdfPageIndex > 0) {
			pdfPageIndex--
		} else {
			pdfPageIndex = document.pages.size - 1
		}
	}

	/**
	 * Renvoie le numéro du page courant
	 * @return Numéro de page courant
	 */
	def int currentPdfPageNumber() {
		return pdfPageIndex
	}

	/**
	 * Transforme une PDPage en BufferedImage
	 * @param page PDPage à convertir
	 * @return Image convertie
	 */
	private def BufferedImage pageToImage() {
		val renderer = new PDFRenderer(document);
		val bufferedImage = renderer.renderImageWithDPI(pdfPageIndex, 300, ImageType.RGB);
		bufferedImage
	}

	/**
	 * Charger le PDF à partir d'un fichier
	 * @param file PDF
	 */
	def create(File file) {
		Objects.requireNonNull(file)
		val InputStream input = new ByteArrayInputStream(Files.readAllBytes(Path.of(file.absolutePath)))
		create(input)
	}
	
	/**
	 * Charge le PDF à partir d'un input stream
	 * @param stream InputStream du PDF
	 */
	def create(InputStream input) {
		pdfOutput = new ByteArrayOutputStream()
		input.transferTo(pdfOutput)
		document = PDDocument.load(getPdfInputStream)
	}

	/**
	 * Retourne vrai si la page courante est celle passée en paramètre
	 * @param page Numéro de page à vérifier
	 * @return True si la page courante est celle passée en paramètre, false sinon
	 */
	def boolean atCorrectPage(int page) {
		return page == currentPdfPageNumber
	}

	/**
	 * Retourne le nombre total de page du pdf
	 * @return Nombre total de page du pdf
	 */
	def int getPdfPageCount() {
		document.pages.size;
	}

	/**
	 * Recupère l'output stream du pdf courant
	 * @return ByteArrayOutputStream du pdf courant
	 */
	def ByteArrayOutputStream getPdfOutputStream() {
		val outputStream = new ByteArrayOutputStream();
		document.save(outputStream);
		outputStream
	}

	/**
	 * Recupère l'input stream du pdf courant
	 * @return ByteArrayInputStream du pdf courant
	 */
	def ByteArrayInputStream getPdfInputStream() {
		return new ByteArrayInputStream(pdfOutput.toByteArray)
	}
}
