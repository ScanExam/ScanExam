package fr.istic.tools.scanexam.qrCode.reader

import org.apache.pdfbox.rendering.PDFRenderer

class PdfReaderWithoutQrCodeThread extends Thread implements Runnable {
	
	PdfReaderWithoutQrCodeImpl reader
	PDFRenderer pdf
	int borneInf
	int borneMax

	new(PdfReaderWithoutQrCodeImpl reader, int inf, int max, PDFRenderer pdf) {
		this.reader = reader
		this.pdf = pdf
		this.borneInf = inf
		this.borneMax = max
	}

	override run() {
		this.reader.readPartition(this.pdf, this.borneInf, this.borneMax)
	}
	
}