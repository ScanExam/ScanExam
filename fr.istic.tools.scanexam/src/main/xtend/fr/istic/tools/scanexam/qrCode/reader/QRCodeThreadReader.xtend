package fr.istic.tools.scanexam.qrCode.reader

import org.apache.pdfbox.rendering.PDFRenderer

class QRCodeThreadReader extends Thread implements Runnable {

	QRCodeReaderImpl reader
	PDFRenderer pdf
	int borneInf
	int borneMax

	new(QRCodeReaderImpl reader, int inf, int max, PDFRenderer pdf) {
		this.reader = reader
		this.pdf = pdf
		this.borneInf = inf
		this.borneMax = max
	}

	override run() {
		println("test")
		this.reader.readQRCodeImage(this.pdf, this.borneInf, this.borneMax)
	}
}
