package fr.istic.tools.scanexam.qrCode.reader

import org.apache.pdfbox.rendering.PDFRenderer
import java.util.concurrent.CountDownLatch
import org.apache.pdfbox.pdmodel.PDDocument

class PdfReaderQrCodeThread extends Thread implements Runnable {

	PdfReaderQrCodeImpl reader
	PDDocument pdDoc
	String docPath
	PDFRenderer pdf
	int borneInf
	int borneMax
	CountDownLatch countDown

	new(PdfReaderQrCodeImpl reader, PDDocument pdDoc, String docPath, int inf, int max, PDFRenderer pdf,
		CountDownLatch countDown) {
		this.reader = reader
		this.pdDoc = pdDoc
		this.docPath = docPath
		this.pdf = pdf
		this.borneInf = inf
		this.borneMax = max
		this.countDown = countDown
	}

	override run() {
		this.reader.readQRCodeImage(this.pdDoc, this.docPath, this.pdf, this.borneInf, this.borneMax)
		countDown.countDown
	}
}
