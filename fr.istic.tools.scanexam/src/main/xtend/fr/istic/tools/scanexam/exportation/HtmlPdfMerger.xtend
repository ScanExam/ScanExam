package fr.istic.tools.scanexam.exportation

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.pdfbox.multipdf.PDFMergerUtility

/** 
 * Ajoute du contenu html à un document pdf
 * @author Julien Cochet
 */
class HtmlPdfMerger {

	// ----------------------------------------------------------------------------------------------------
	/*
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------
	/* Logger */
	final static Logger LOGGER = LogManager.getLogger()
	/* Chemin du pdf temporaire construit avec le html */
	final static Path HTML_PDF_PATH = Files.createTempFile("html", "pdf").toAbsolutePath

	// ----------------------------------------------------------------------------------------------------
	/*
	 * METHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * Assemble un fichier html et un document pdf en un nouveau document pdf
	 * @param htmlFile  Fichier html à ajouter au pdf
	 * @param pdfFile   Document pdf à ajouter au nouveau pdf
	 * @param destPath  Chemin et nom du nouveau document pdf à générer (peut écraser l'ancien pdf)
	 * @param htmlFirst Indique si le contenu du fichier html doit entre avant ou après le document pdf
	 */
	def static void mergeHtmlFileWithPdf(File htmlFile, File pdfFile, Path destPath, boolean htmlFirst) {
		createPdfFromHtmlFile(htmlFile, HTML_PDF_PATH)
		var File htmlPdf = HTML_PDF_PATH.toFile()
		mergeHtmlPdfWithPdf(htmlPdf, pdfFile, destPath, htmlFirst)
		htmlPdf.delete()
	}

	/** 
	 * Assemble du contenu html et un document pdf en un nouveau document pdf
	 * @param htmlContent   Contenu html à ajouter au pdf
	 * @param resourcesPath Chemin du dossier contenant les ressources (images, CSS, etc.) du html. Laisser null si aucune ressource
	 * @param pdfFile       Document pdf à ajouter au nouveau pdf
	 * @param destPath      Chemin et nom du nouveau document pdf à générer (peut écraser l'ancien pdf)
	 * @param htmlFirst     Indique si le contenu du fichier html doit entre avant ou après le document pdf
	 */
	def static void mergeHtmlContentWithPdf(String htmlContent, Path resourcesPath, File pdfFile, Path destPath,
		boolean htmlFirst) {
		createPdfFromHtmlContent(htmlContent, resourcesPath, HTML_PDF_PATH)
		var File htmlPdf = HTML_PDF_PATH.toFile()
		mergeHtmlPdfWithPdf(htmlPdf, pdfFile, destPath, htmlFirst)
		htmlPdf.delete()
	}

	/** 
	 * Créé un nouveau pdf à partir du contenu d'un fichier html
	 * @param htmlFile Fichier html à convertir en pdf
	 * @param destPath Chemin du document pdf à générer
	 */
	def static void createPdfFromHtmlFile(File htmlFile, Path destPath) {
		var PdfRendererBuilder builder = new PdfRendererBuilder()
		builder.withFile(htmlFile)
		createPdfWithBuilder(builder, destPath)
	}

	/** 
	 * Créé un nouveau pdf à partir de contenu html
	 * @param htmlContent   Html à convertir en pdf
	 * @param resourcesPath Chemin du dossier contenant les ressources (images, CSS, etc.) du html. Laisser null si aucune ressource
	 * @param destPath      Chemin du document pdf à générer
	 */
	def static void createPdfFromHtmlContent(String htmlContent, Path resourcesPath, Path destPath) {
		var PdfRendererBuilder builder = new PdfRendererBuilder
		if (!resourcesPath.nullOrEmpty) {
			builder.withHtmlContent(htmlContent, resourcesPath.toUri.toString)
		} else {
			builder.withHtmlContent(htmlContent, null)
		}
		createPdfWithBuilder(builder, destPath)
	}

	/** 
	 * Créé un nouveau pdf à partir du contenu d'un builder contant un fichier ou contenu html
	 * @param builder  PdfRendererBuilder Builder contant déjà un fichier ou contenu html
	 * @param destPath Chemin du document pdf à générer
	 */
	def private static void createPdfWithBuilder(PdfRendererBuilder builder, Path destPath) {
		builder.useFastMode()
		try {
			builder.toStream(new FileOutputStream(destPath.toFile()))
		} catch (FileNotFoundException e) {
			e.printStackTrace()
			LOGGER.error("Cannot find destination.")
		}

		try {
			builder.run()
		} catch (IOException e) {
			e.printStackTrace()
			LOGGER.error("Cannot build pdf from html.")
		}
	}

	/** 
	 * Assemble un pdf construit à partir d'un html et un document pdf en un nouveau document pdf
	 * @param htmlPdfFile Pdf construit à partir d'un html
	 * @param pdfFile     Document pdf à ajouter au nouveau pdf
	 * @param destPath    Chemin et nom du nouveau document pdf à générer (peut écraser l'ancien pdf)
	 * @param htmlFirst   Indique si le contenu du fichier html doit entre avant ou après le document pdf
	 */
	def private static void mergeHtmlPdfWithPdf(File htmlPdfFile, File pdfFile, Path destPath, boolean htmlFirst) {
		if (htmlFirst) {
			mergeTwoPdf(htmlPdfFile, pdfFile, destPath)
		} else {
			mergeTwoPdf(pdfFile, htmlPdfFile, destPath)
		}
	}

	/** 
	 * Assemble 2 documents pdf en un nouveau document pdf. Les 2 pdf seront assemblé dans cet ordre : pdfFile1 puis pdfFile2.
	 * @param pdfFile1 Premier document pdf
	 * @param pdfFile2 Second document pdf
	 * @param destPath Chemin du document pdf à générer (peut écraser un des deux pdf)
	 */
	def private static void mergeTwoPdf(File pdfFile1, File pdfFile2, Path destPath) {
		var PDFMergerUtility merger = new PDFMergerUtility()
		try {
			merger.addSource(pdfFile1)
		} catch (FileNotFoundException e) {
			e.printStackTrace()
			LOGGER.error("Cannot find pdfFile1.")
		}

		try {
			merger.addSource(pdfFile2)
		} catch (FileNotFoundException e) {
			e.printStackTrace()
			LOGGER.error("Cannot find pdfFile2.")
		}

		merger.setDestinationFileName(destPath.toString())
		try {
			merger.mergeDocuments(null)
		} catch (IOException e) {
			e.printStackTrace()
			LOGGER.error("Cannot merge pdfFile1 and pdfFile2.")
		}
	}
}
