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
import java.util.regex.Pattern
import javafx.util.Pair
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.ImageType
import org.apache.pdfbox.rendering.PDFRenderer

/**
 * Classe lisant la deuxième version des qr codes (avec préfixe)
 * @author Julien Cochet
 */
class PdfReaderQrCodeV2Impl extends PdfReaderQrCodeImpl {

	// ----------------------------------------------------------------------------------------------------
	/*
	 * CONSTRUCTEUR
	 */
	// ----------------------------------------------------------------------------------------------------
	new(InputStream input, String docPath, int nbPages, Pair<Float, Float> qrPos) {
		super(input, docPath, nbPages, qrPos)
	}

	// ----------------------------------------------------------------------------------------------------
	/*
	 * MÉTHODES
	 */
	// ----------------------------------------------------------------------------------------------------
	/**
	 * Analyse les qr codes, corrige les problèmes de numérisation (ex: à l'envers) et lie les pages aux élèves
	 * @param pdDoc Document à analyser
	 * @param docPath Chemin du document à analyser
	 * @param pdfRenderer Rendu du document à analyser
	 * @param startPages Première page à analyser
	 * @param endPages Dernière page à analyser
	 * @param qrPos Position où devrait ce trouver le qr code
	 */
	override void readQRCodeImage(PDDocument pdDoc, String docPath, PDFRenderer pdfRenderer, int startPages,
		int endPages, Pair<Float, Float> qrPos) throws IOException {
		pagesMalLues = new ArrayList<Integer>
		for (page : startPages ..< endPages) {
			// des fois (randoms) outofmemory
			var BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 250, ImageType.GRAY)
			val LuminanceSource source = new BufferedImageLuminanceSource(bim)
			val BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source))
			try {
				var int numCopie = -1
				var int numPageInSubject = -1
				var String studentName = null

				val QRCodeMultiReader multiReader = new QRCodeMultiReader
				val Result[] results = multiReader.decodeMultiple(bitmap)
				for (result : results) {
					if (result.text.startsWith(QrCodeType.SHEET_PAGE.toString)) {
						val String[] items = sheetPageQrCode(result, bim, pdDoc, page)
						numCopie = Integer.parseInt(items.get(items.size - 2))
						numPageInSubject = Integer.parseInt(items.get(items.size - 1))
					} else {
						if (result.text.startsWith(QrCodeType.STUDENT.toString)) {
							studentName = studentQrCode(result)
						}
					}
				}

				if (numCopie >= 0 && numPageInSubject >= 0) {
					val Copie cop = new Copie(numCopie, page, numPageInSubject, studentName)
					synchronized (sheets) {
						addCopie(cop)
					}
				} else {
					pagesMalLues.add(page)
					logger.error("Cannot read QRCode in page " + page)
				}
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

	private def String[] sheetPageQrCode(Result result, BufferedImage bim, PDDocument pdDoc, int page) {
		val Pattern pattern = Pattern.compile("_")
		val String[] items = pattern.split(result.text)
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
		return items
	}

	private def String studentQrCode(Result result) {
		val Pattern pattern = Pattern.compile("_")
		val String[] items = pattern.split(result.text)
		return items.get(items.size - 1)
	}
}
