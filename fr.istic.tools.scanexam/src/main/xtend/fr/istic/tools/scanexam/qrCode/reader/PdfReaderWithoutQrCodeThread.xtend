package fr.istic.tools.scanexam.qrCode.reader

import org.apache.pdfbox.rendering.PDFRenderer
import java.util.concurrent.CountDownLatch

class PdfReaderWithoutQrCodeThread extends Thread implements Runnable {

	PdfReaderWithoutQrCodeImpl reader
	PDFRenderer pdf
	int borneInf
	int borneMax
	CountDownLatch countDown
	CountDownLatch countDownMain

	new(PdfReaderWithoutQrCodeImpl reader, int inf, int max, PDFRenderer pdf, CountDownLatch countDown,
		CountDownLatch countDownMain) {
		this.reader = reader
		this.pdf = pdf
		this.borneInf = inf
		this.borneMax = max
		this.countDown = countDown
		this.countDownMain = countDownMain
	}

	override run() {
		countDownMain.await();
		this.reader.readPartition(this.pdf, this.borneInf, this.borneMax)
		countDown.countDown();
	}

}
