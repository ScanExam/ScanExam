package fr.istic.tools.scanexam.qrCode.reader

import org.apache.pdfbox.rendering.PDFRenderer

class PDFReaderQRCodeThread extends Thread implements Runnable {

	PDFReaderQRCodeImpl reader
	PDFRenderer pdf
	int borneInf
	int borneMax

	new(PDFReaderQRCodeImpl reader, int inf, int max, PDFRenderer pdf) {
		this.reader = reader
		this.pdf = pdf
		this.borneInf = inf
		this.borneMax = max
	}

	override run() {
		this.reader.readQRCodeImage(this.pdf, this.borneInf, this.borneMax)
	}
}
