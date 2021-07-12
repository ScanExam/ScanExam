package fr.istic.tools.scanexam.qrCode.reader

import com.google.zxing.BinaryBitmap
import com.google.zxing.LuminanceSource
import com.google.zxing.NotFoundException
import com.google.zxing.Result
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.multi.qrcode.QRCodeMultiReader
import fr.istic.tools.scanexam.qrCode.QrCodeType
import java.awt.image.BufferedImage
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList
import java.util.HashSet
import java.util.List
import java.util.Set
import java.util.regex.Pattern
import javafx.util.Pair
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.ImageType
import org.apache.pdfbox.rendering.PDFRenderer
import java.util.Random

/**
 * Classe lisant la deuxième version des qr codes (avec préfixe)
 * @author Julien Cochet
 */
class PdfReaderQrCodeV2Impl extends PdfReaderQrCodeImpl {
	// ----------------------------------------------------------------------------------------------------
	/*
	 * VARIABLES
	 */
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * Copies déjà vues, ne sert que pour les qr codes n'ayant que le numéro de page.
	 * List: liste des copies, set : pages de cette copie
	 */
	var List<Set<Integer>> pagesSeen

	/** Paterne séparant les différentes informations dans les qr codes */
	val Pattern pattern = Pattern.compile("_")

	// ----------------------------------------------------------------------------------------------------
	/*
	 * CONSTRUCTEUR
	 */
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Constructeur, n'est pas différent de celui de PdfReaderQrCodeImpl
	 * @param input InputStream vers le document à analyser
	 * @param docPath Chemin vers le document à analyser
	 * @param nbPages Nombre de pages à analyser
	 * @param qrPos Position où devrait se trouver le qr code principal (SHEET_PAGE ou PAGE)
	 */
	new(InputStream input, String docPath, int nbPages, Pair<Float, Float> qrPos) {
		super(input, docPath, nbPages, qrPos)
	}

	// ----------------------------------------------------------------------------------------------------
	/*
	 * MÉTHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Crée un thread lisant des qr codes
	 * @param nbPage nombre de copies désirées
	 * @param doc document dans lequel insérer les Codes
	 * @param docPath chemin du document dans lequel insérer les Codes
	 * @param qrPos Position à laquelle devrait se trouver les qr codes
	 */
	override createThread(int nbPage, PDDocument doc, String docPath, Pair<Float, Float> qrPos) {
		val int qrCodeType = findQrCodeType(new PDFRenderer(doc), nbPage)
		if (qrCodeType !== -1) {
			val PdfReaderThreadManager manager = new PdfReaderThreadManager(nbPage, doc, docPath, qrPos, qrCodeType,
				this)
			manager.start
		} else {
			logger.error("Cannot find QRCode in student papers")
		}
	}

	/**
	 * Analyse les qr codes des plusieurs pages et traite les informations qu'ils contiennent
	 * @param pdDoc Document à analyser
	 * @param docPath Chemin du document à analyser
	 * @param pdfRenderer Rendu du document à analyser
	 * @param startPages Première page à analyser
	 * @param endPages Dernière page à analyser
	 * @param qrPos Position où devrait ce trouver le qr code
	 * @param type de qr codes à lire sur l'image
	 */
	def void readQRCodeImage(PDDocument pdDoc, String docPath, PDFRenderer pdfRenderer, int startPages, int endPages,
		Pair<Float, Float> qrPos, int qrCodeType) throws IOException {
		pagesMalLues = new ArrayList
		if (qrCodeType === QrCodeType.PAGE) {
			pagesSeen = new ArrayList
		}
		for (page : startPages ..< endPages) {
			var BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 250, ImageType.GRAY)
			val LuminanceSource source = new BufferedImageLuminanceSource(bim)
			val BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source))
			val QRCodeMultiReader multiReader = new QRCodeMultiReader
			try {
				val Result[] results = multiReader.decodeMultiple(bitmap)
				qrCodeAnalysis(results, bim, pdDoc, page, qrCodeType)
			} catch (ArrayIndexOutOfBoundsException | NotFoundException e) {
				pagesMalLues.add(page)
				logger.error("Cannot read QRCode in page " + page, e)
			} finally {
				treatedSheets++
			}
			bim.flush
			System.gc
		}
	}

	/**
	 * Détermine quel type de qr code doit être analysé (SHEET_PAGE ou PAGE)
	 * @param pdfRenderer Rendu du pdf
	 * @param nbPage Nombre de pages dans le document
	 * @return Type de qr code, -1 si rien de trouver
	 */
	private def int findQrCodeType(PDFRenderer pdfRenderer, int nbPage) {
		var int qrCodeType = -1
		val Random rand = new Random
		val int analysisMax = 10
		val int nbAnalysis = nbPage < analysisMax ? nbPage : analysisMax
		var int i = 0
		while (i < nbAnalysis && qrCodeType == -1) {
			val int page = rand.nextInt(nbPage)
			var BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 250, ImageType.GRAY)
			val LuminanceSource source = new BufferedImageLuminanceSource(bim)
			val BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source))
			val QRCodeMultiReader multiReader = new QRCodeMultiReader

			try {
				val Result[] results = multiReader.decodeMultiple(bitmap)
				for (result : results) {
					if (result.text.startsWith(QrCodeType.SHEET_PAGE.toString)) {
						qrCodeType = QrCodeType.SHEET_PAGE
					} else {
						if (result.text.startsWith(QrCodeType.PAGE.toString)) {
							qrCodeType = QrCodeType.PAGE
						}
					}
				}

			} catch (ArrayIndexOutOfBoundsException | NotFoundException e) {
				logger.error("Cannot read QRCode in page " + page, e)
			}
			bim.flush
			i++
		}
		return qrCodeType
	}

	/**
	 * Analyse les qr codes d'une page et traite les informations qu'ils contiennent
	 * @param results Résultats des qr codes
	 * @param bim Image de la page
	 * @param pdDoc Document où se trouve la page
	 * @param page Numéro de la page à traiter
	 * @param Type du qr code principal (SHEET_PAGE ou PAGE)
	 */
	private def void qrCodeAnalysis(Result[] results, BufferedImage bim, PDDocument pdDoc, int page, int qrCodeType) {
		var int numCopie = -1
		var int numPageInSubject = -1
		var String studentId = null
		var String studentLastName = null
		var String studentFirstName = null

		for (result : results) {
			if (result.text.startsWith(QrCodeType.SHEET_PAGE.toString) ||
				result.text.startsWith(QrCodeType.PAGE.toString)) {
				repositioningPage(result, bim, pdDoc, page)
				val String[] items = pattern.split(result.text)
				if (qrCodeType === QrCodeType.SHEET_PAGE) {
					numCopie = Integer.parseInt(items.get(items.size - 2))
				} else {
					if (qrCodeType === QrCodeType.PAGE) {
						numCopie = generateNumCopie(Integer.parseInt(items.get(items.size - 1)))
					}
				}
				numPageInSubject = Integer.parseInt(items.get(items.size - 1))
			} else {
				if (result.text.startsWith(QrCodeType.STUDENT.toString)) {
					val String[] items = pattern.split(result.text)
					studentId = items.get(items.size - 3)
					studentLastName = items.get(items.size - 2)
					studentFirstName = items.get(items.size - 1)
				}
			}
		}

		if (numCopie >= 0 && page >= 0 && numPageInSubject >= 0) {
			val Copie copie = new Copie(numCopie, page, numPageInSubject, studentId, studentLastName, studentFirstName)
			synchronized (sheets) {
				addCopie(copie)
			}
		} else {
			pagesMalLues.add(page)
			logger.error("Cannot read QRCode in page " + page)
		}
	}

	/**
	 * Réoriente et repositionne une page donnée, à partir des coordonnées d'un qr code
	 * @param result Résultat du qr code sur lequel appuyer le repositionnement
	 * @param bim Image de la page
	 * @param pdDoc Document où se trouve la page
	 * @param page Numéro de la page à traiter
	 */
	private def void repositioningPage(Result result, BufferedImage bim, PDDocument pdDoc, int page) {
		val float orientation = qrCodeOrientation(result)
		val Pair<Float, Float> position = qrCodePosition(result, orientation, bim.width, bim.height)
		if (orientation <= -0.5f || orientation >= 0.5f) {
			rotatePdf(pdDoc, docPath, page, orientation)
		}
		if (qrPos.key >= 0.0f && qrPos.value >= 0.0f) {
			val diffX = qrPos.key - position.key
			val diffY = position.value - qrPos.value
			if (diffX <= -0.01f || diffX >= 0.01f || diffY <= -0.01f || diffY >= 0.01f) {
				repositionPdf(pdDoc, docPath, page, diffX, diffY)
			}
		}
	}

	/**
	 * Retourne le premier étudiant n'ayant pas la page donnée
	 * @param page Numéro de page
	 * @return Numéro du premier étudiant n'ayant pas la page
	 */
	private def int generateNumCopie(int page) {
		var int numCopie = 0
		var boolean pageNotFind = true
		while (pageNotFind) {
			if (pagesSeen.size > numCopie) {
				if (pagesSeen.get(numCopie).contains(page)) {
					numCopie++
				} else {
					pagesSeen.get(numCopie).add(page)
					pageNotFind = false
				}
			} else {
				val Set<Integer> pageSet = new HashSet
				pageSet.add(page)
				pagesSeen.add(pageSet)
				pageNotFind = false
			}
		}
		return numCopie
	}
}
