package fr.istic.tools.scanexam.qrCode.writer

import java.util.concurrent.ExecutorService
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import org.apache.pdfbox.pdmodel.PDDocument
import java.io.File
import java.io.OutputStream
import fr.istic.tools.scanexam.core.QrCodeZone

class PdfThreadManagerWriter extends Thread implements Runnable {

	int nbPage
	/** Zone sur le document où insérer le qrcode */
	QrCodeZone qrCodeZone
	/** Type de qr code à insérer */
	int qrCodeType
	PDDocument docSujetMaitre
	PDDocument doc
	QRCodeGeneratorImpl writer
	int nbCopie
	OutputStream output

	new(int nbPage, QrCodeZone qrCodeZone, int qrCodeType, PDDocument docSujetMaitre, PDDocument doc,
		QRCodeGeneratorImpl writer, int nbCopie, OutputStream output) {
		this.nbPage = nbPage
		this.qrCodeZone = qrCodeZone
		this.qrCodeType = qrCodeType
		this.docSujetMaitre = docSujetMaitre
		this.writer = writer
		this.nbCopie = nbCopie
		this.output = output
		this.doc = doc
	}

	override run() {
		val ExecutorService service = Executors.newFixedThreadPool(4)
		val CountDownLatch latchThreads = new CountDownLatch(4)

		var File qrcode0 = File.createTempFile("qrcode0", ".png")
		var File qrcode1 = File.createTempFile("qrcode1", ".png")
		var File qrcode2 = File.createTempFile("qrcode2", ".png")
		var File qrcode3 = File.createTempFile("qrcode3", ".png")

		service.execute(
			new QRThreadWriter(writer, qrCodeType, 0, (nbCopie / 4), qrCodeZone, docSujetMaitre, nbPage, latchThreads,
				qrcode0.absolutePath))
		service.execute(
			new QRThreadWriter(writer, qrCodeType, (nbCopie / 4), (nbCopie / 2), qrCodeZone, docSujetMaitre, nbPage,
				latchThreads, qrcode1.absolutePath))
		service.execute(
			new QRThreadWriter(writer, qrCodeType, (nbCopie / 2), (3 * nbCopie / 4), qrCodeZone, docSujetMaitre, nbPage,
				latchThreads, qrcode2.absolutePath))
		service.execute(
			new QRThreadWriter(writer, qrCodeType, (3 * nbCopie / 4), nbCopie, qrCodeZone, docSujetMaitre, nbPage,
				latchThreads, qrcode3.absolutePath))

		latchThreads.await()
		writer.setFinished(true)
		service.shutdown()
		docSujetMaitre.save(output)

		doc.close
		docSujetMaitre.close

		qrcode0.deleteOnExit
		qrcode1.deleteOnExit
		qrcode2.deleteOnExit
		qrcode3.deleteOnExit

	}
}
