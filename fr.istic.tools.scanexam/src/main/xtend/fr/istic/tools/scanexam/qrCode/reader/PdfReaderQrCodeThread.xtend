package fr.istic.tools.scanexam.qrCode.reader

import org.apache.pdfbox.rendering.PDFRenderer
import java.util.concurrent.CountDownLatch

class PdfReaderQrCodeThread extends Thread implements Runnable {

	PdfReaderQrCodeImpl reader
	PDFRenderer pdf
	int borneInf
	int borneMax
	CountDownLatch countDown

	new(PdfReaderQrCodeImpl reader, int inf, int max, PDFRenderer pdf, CountDownLatch countDown) {
		this.reader = reader
		this.pdf = pdf
		this.borneInf = inf
		this.borneMax = max
		this.countDown = countDown
	}

	override run() {
		this.reader.readQRCodeImage(this.pdf, this.borneInf, this.borneMax)
		countDown.countDown
		println("Fermeture du Thread de Lecture")
	}
}
