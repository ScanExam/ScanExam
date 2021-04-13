package fr.istic.tools.scanexam.qrCode.writer

import java.util.concurrent.ExecutorService
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import org.apache.pdfbox.pdmodel.PDDocument
import java.io.File
import java.io.OutputStream

class PdfThreadManagerWriter extends Thread implements Runnable {

	int nbPage
	PDDocument docSujetMaitre
	QRCodeGeneratorImpl writer
	int nbCopie
	String examID
	OutputStream output

	new(int nbPage, PDDocument docSujetMaitre, QRCodeGeneratorImpl writer, int nbCopie, String examID, OutputStream output) {
		this.nbPage = nbPage
		this.docSujetMaitre = docSujetMaitre
		this.writer = writer
		this.nbCopie = nbCopie
		this.examID = examID
		this.output = output
	}

	override run() {
		val ExecutorService service = Executors.newFixedThreadPool(4)
		val CountDownLatch latchThreads = new CountDownLatch(4)

		var File qrcode0 = File.createTempFile("qrcode0", ".png")
		var File qrcode1 = File.createTempFile("qrcode1", ".png")
		var File qrcode2 = File.createTempFile("qrcode2", ".png")
		var File qrcode3 = File.createTempFile("qrcode3", ".png")

		service.execute(
			new QRThreadWriter(writer, 0, (nbCopie / 4), docSujetMaitre, nbPage, latchThreads, examID, qrcode0.absolutePath))
		service.execute(
			new QRThreadWriter(writer, (nbCopie / 4), (nbCopie / 2), docSujetMaitre, nbPage, latchThreads, examID,
				qrcode1.absolutePath))
		service.execute(
			new QRThreadWriter(writer, (nbCopie / 2), (3 * nbCopie / 4), docSujetMaitre, nbPage, latchThreads, examID,
				qrcode2.absolutePath))
		service.execute(
			new QRThreadWriter(writer, (3 * nbCopie / 4), nbCopie, docSujetMaitre, nbPage, latchThreads, examID,
				qrcode3.absolutePath))

		latchThreads.await()
		println("Fermeture du Thread Manager : " + latchThreads.count)
		writer.setFinished(true)
		service.shutdown()
		docSujetMaitre.save(output)
 
		qrcode0.deleteOnExit
		qrcode1.deleteOnExit
		qrcode2.deleteOnExit
		qrcode3.deleteOnExit

	}
}
