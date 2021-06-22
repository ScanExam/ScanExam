package fr.istic.tools.scanexam.qrCode.reader

import org.apache.pdfbox.rendering.PDFRenderer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.CountDownLatch
import org.apache.pdfbox.pdmodel.PDDocument
import javafx.util.Pair

class PdfReaderThreadManager extends Thread implements Runnable {

	int nbPage;
	PDDocument doc
	String docPath
	Pair<Float, Float> qrPos
	PdfReaderQrCodeImpl reader

	new(int nbPage, PDDocument doc, String docPath, Pair<Float, Float> qrPos, PdfReaderQrCodeImpl reader) {
		this.nbPage = nbPage
		this.doc = doc
		this.docPath = docPath
		this.qrPos = qrPos
		this.reader = reader
	}

	override run() {
		val ExecutorService service = Executors.newFixedThreadPool(4)
		val CountDownLatch latchThreads = new CountDownLatch(4)
		val PDFRenderer pdf = new PDFRenderer(doc)
		service.execute(new PdfReaderQrCodeThread(reader, doc, docPath, 0, (nbPage / 4), pdf, qrPos, latchThreads))
		service.execute(
			new PdfReaderQrCodeThread(reader, doc, docPath, (nbPage / 4), (nbPage / 2), pdf, qrPos, latchThreads))
		service.execute(
			new PdfReaderQrCodeThread(reader, doc, docPath, (nbPage / 2), (3 * nbPage / 4), pdf, qrPos, latchThreads))
		service.execute(
			new PdfReaderQrCodeThread(reader, doc, docPath, (3 * nbPage / 4), nbPage, pdf, qrPos, latchThreads))

		latchThreads.await()
		reader.setFinished(true)
		service.shutdown()

		doc.close

	}

}
