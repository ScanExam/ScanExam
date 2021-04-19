package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.services.api.Service
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.Objects
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.rendering.ImageType
import org.apache.pdfbox.rendering.PDFRenderer
import org.eclipse.xtend.lib.annotations.Accessors

/** 
 * Controlleur du pdf
 * @author Julien Cochet
 */
class PdfManager {
	/**
	 * Pdf chargé
	 */
	PDDocument document;
	/**
	 * presenter used for the edition of an exam
	 * @author Benjamin Danlos
	 */
	/**
	 * Index de la page courante du modèle d'exam
	 */
	 @Accessors int pdfPageIndex


	/**
	 * Association with the model via the Service API
	 */
	Service service;
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	/* Largeur de la fenêtre */
	protected int width

	/* Hauteur de la fenêtre */
	protected int height

	/* InputStream du pdf */
	protected ByteArrayOutputStream pdfOutput

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * CONSTRUCTEUR
	 */
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Constructor 
	 * @author Benjamin Danlos
	 * @param {@link EditorPresenter} (not null)
	 * @param {@link GraduationPresenter} (not null)
	 * Constructs a PDFPresenter object.
	 */
	new(Service s) {
		Objects.requireNonNull(s)

		this.service = s

	}

	
	/**
	 * Change la page courante par la page du numéro envoyé en paramètre (ne change rien si la page n'existe pas)
	 * @param page Numéro de page où se rendre
	 */
	def goToPdfPage(int page) {
		if (page >= 0 && page < document.pages.size) {
			pdfPageIndex = page
		}
	}

	def getCurrentPdfPage() {
		pageToImage(document.pages.get(pdfPageIndex));
	}

	def nextPdfPage() {
		println("-" + pdfPageIndex + "-")
		if (pdfPageIndex + 1 < document.pages.size) {
			pdfPageIndex++
		}

	}

	/**
	 * Change la page courante par la page la précédent si elle existe (ne change rien sinon)
	 */
	def previousPdfPage() {
		if (pdfPageIndex > 0) {
			pdfPageIndex--;
		}
	}

	def int currentPdfPageNumber() {
		return pdfPageIndex
	}

	def pageToImage(PDPage page) {
		val renderer = new PDFRenderer(document);
		val bufferedImage = renderer.renderImageWithDPI(pdfPageIndex, 300, ImageType.RGB);
		bufferedImage
	}

	/**
	 * Crée le modèle de l'examen
	 * @param name Nom du modèle d'examen
	 * @param file Fichier du modèle d'examen
	 */
	def create(String name, File file) {
		Objects.requireNonNull(file)
		val InputStream input = new ByteArrayInputStream(Files.readAllBytes(Path.of(file.absolutePath)))
		pdfOutput = new ByteArrayOutputStream()
		input.transferTo(pdfOutput)
		document = PDDocument.load(file)

		//service.onDocumentLoad(document.pages.size);
		//service.setExamName(name)
	}

	def create(ByteArrayInputStream stream) {
		pdfOutput = new ByteArrayOutputStream()
		stream.transferTo(pdfOutput)
		document = PDDocument.load(getPdfInputStream)
	}

	def boolean atCorrectPage(int page) {
		return page == currentPdfPageNumber
	}

	def getPdfPageCount() {
		document.pages.size;
	}

	def getPdfOutputStream() {
		val outputStream = new ByteArrayOutputStream();
		document.save(outputStream);
		outputStream
	}

	/**
	 * @return un InputStream vers le PDF
	 */
	def getPdfInputStream() {
		return new ByteArrayInputStream(pdfOutput.toByteArray)
	}

}
