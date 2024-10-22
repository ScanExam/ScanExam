package fr.istic.tools.scanexam.qrCode.reader

import org.apache.pdfbox.rendering.PDFRenderer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.CountDownLatch
import org.apache.pdfbox.pdmodel.PDDocument
import javafx.util.Pair
import fr.istic.tools.scanexam.qrCode.QrCodeType

class PdfReaderThreadManager extends Thread implements Runnable {

	int nbPage;
	PDDocument doc
	String docPath
	Pair<Float, Float> qrPos
	val int qrCodeType
	val int nbThread
	PdfReaderQrCodeImpl reader

	new(int nbPage, PDDocument doc, String docPath, Pair<Float, Float> qrPos, int qrCodeType,
		PdfReaderQrCodeImpl reader) {
		this.nbPage = nbPage
		this.doc = doc
		this.docPath = docPath
		this.qrPos = qrPos
		this.qrCodeType = qrCodeType
		if (this.qrCodeType == QrCodeType.PAGE) {
			this.nbThread = 1
		} else {
			this.nbThread = 4
		}
		this.reader = reader
	}

	override run() {
		/* TODO et FIXME
		 * Intégrer la preuve de concept de l'adresse : https://github.com/ScanExam/QRCodeFixMemoryError/blob/master/src/main/java/QRCodeLivrable/QRCodeBasique/ReaderThreadManager.java
		 * Cette P.O.C sert à mieux gérer la mémoire des thread
		 * et surtout à passer outre que PDFBox ne soit pas threadsafe
		 * Il faut cependant revoir la formule qui permet la subdivision afin de trouver quelque chose de moins empirique
		 */
		
		val ExecutorService service = Executors.newFixedThreadPool(nbThread)
		val CountDownLatch latchThreads = new CountDownLatch(nbThread)
		val PDFRenderer pdf = new PDFRenderer(doc)
		for (i : 0 ..< nbThread) {
			service.execute(
				new PdfReaderQrCodeThread(reader, doc, docPath, (i * nbPage / nbThread), ((i + 1) * nbPage / nbThread),
					pdf, qrPos, qrCodeType, latchThreads))
		}
		latchThreads.await
		reader.setFinished(true)
		service.shutdown()
		doc.close
	}

}
