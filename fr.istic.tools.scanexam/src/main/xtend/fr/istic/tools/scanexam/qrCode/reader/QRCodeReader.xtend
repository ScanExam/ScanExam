package fr.istic.tools.scanexam.qrCode.reader

import org.apache.pdfbox.rendering.PDFRenderer
import java.io.IOException

interface QRCodeReader {

	/**
	 * Lit les QRCodes du document pdfRenderer entre les pages startPages et totalPages
	 * 
	 * @param pdfRenderer Pdf a lire
	 * @param startPages  page de lecture de début
	 * @param totalPages  page de lecture de fin
	 * @throws IOException
	 * 
	 */
	def void readQRCodeImage(PDFRenderer pdfRenderer, int startPages, int endPages)throws IOException
}
