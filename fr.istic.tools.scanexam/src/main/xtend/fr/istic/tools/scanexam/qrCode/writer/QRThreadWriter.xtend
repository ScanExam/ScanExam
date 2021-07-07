package fr.istic.tools.scanexam.qrCode.writer

import org.apache.pdfbox.pdmodel.PDDocument
import java.util.concurrent.CountDownLatch
import fr.istic.tools.scanexam.core.QrCodeZone

class QRThreadWriter extends Thread implements Runnable {

	QRCodeGeneratorImpl writer
	int borneInf
	int borneMax
	/** Type de qr code à insérer */
	int qrCodeType
	/** Zone sur le document où insérer le qrcode */
	QrCodeZone qrCodeZone
	PDDocument docSujetMaitre
	int nbPages
	CountDownLatch countDown
	String pathImage

	new(QRCodeGeneratorImpl writer, int qrCodeType, int inf, int max, QrCodeZone qrCodeZone, PDDocument docSujetMaitre,
		int nbPages, CountDownLatch countDown, String pathImage) {
		this.writer = writer
		this.borneInf = inf;
		this.borneMax = max
		this.qrCodeType = qrCodeType
		this.qrCodeZone = qrCodeZone
		this.docSujetMaitre = docSujetMaitre
		this.nbPages = nbPages
		this.countDown = countDown
		this.pathImage = pathImage
	}

	override run() {
		for (i : borneInf ..< borneMax) {
			writer.insertQRCodeInSubject(qrCodeZone, docSujetMaitre, i, nbPages, qrCodeType, pathImage)
		}
		countDown.countDown
	}

}
