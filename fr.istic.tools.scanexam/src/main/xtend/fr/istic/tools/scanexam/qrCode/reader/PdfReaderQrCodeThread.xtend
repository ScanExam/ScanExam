package fr.istic.tools.scanexam.qrCode.reader

import org.apache.pdfbox.rendering.PDFRenderer
import java.util.concurrent.CountDownLatch
import org.apache.pdfbox.pdmodel.PDDocument
import javafx.util.Pair

class PdfReaderQrCodeThread extends Thread implements Runnable {

	PdfReaderQrCodeImpl reader
	PDDocument pdDoc
	String docPath
	PDFRenderer pdf
	int borneInf
	int borneMax
	Pair<Float, Float> qrPos
	CountDownLatch countDown

	new(PdfReaderQrCodeImpl reader, PDDocument pdDoc, String docPath, int inf, int max, PDFRenderer pdf,
		Pair<Float, Float> qrPos, CountDownLatch countDown) {
		this.reader = reader
		this.pdDoc = pdDoc
		this.docPath = docPath
		this.pdf = pdf
		this.borneInf = inf
		this.borneMax = max
		this.qrPos = qrPos
		this.countDown = countDown
	}

	override run() {
		this.reader.readQRCodeImage(this.pdDoc, this.docPath, this.pdf, this.borneInf, this.borneMax, this.qrPos)
		countDown.countDown
	}
}
