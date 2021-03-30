package fr.istic.tools.scanexam.qrCode.reader

import org.apache.pdfbox.rendering.PDFRenderer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.CountDownLatch

class PdfReaderThreadManager extends Thread implements Runnable {

	int nbPage;
	PDFRenderer pdfRenderer
	PdfReaderQrCodeImpl reader

	new(int nbPage, PDFRenderer pdfRenderer, PdfReaderQrCodeImpl reader) {
		this.nbPage = nbPage
		this.pdfRenderer = pdfRenderer
		this.reader = reader
	}

	override run() {
		val ExecutorService service = Executors.newFixedThreadPool(4)
		val CountDownLatch latchThreads = new CountDownLatch(4)

		service.execute(new PdfReaderQrCodeThread(reader, 0, (nbPage / 4), pdfRenderer, latchThreads))
		service.execute(new PdfReaderQrCodeThread(reader, (nbPage / 4), (nbPage / 2), pdfRenderer, latchThreads))
		service.execute(new PdfReaderQrCodeThread(reader, (nbPage / 2), 3 * (nbPage / 4), pdfRenderer, latchThreads))
		service.execute(new PdfReaderQrCodeThread(reader, (3 * nbPage / 4), nbPage, pdfRenderer, latchThreads))

		latchThreads.await()
		println("Fermeture du Thread Manager : " + latchThreads.count)
		reader.setFinished(true)
		service.shutdown()
		
		
	}
	

}
