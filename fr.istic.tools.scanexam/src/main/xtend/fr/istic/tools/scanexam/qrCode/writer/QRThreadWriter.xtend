package fr.istic.tools.scanexam.qrCode.writer
import org.apache.pdfbox.pdmodel.PDDocument

class QRThreadWriter extends Thread implements Runnable {

	QRCodeGeneratorImpl generator
	int borneInf
	int borneMax
	PDDocument docSujetMaitre
	int numThread
	int nbPages

	new(QRCodeGeneratorImpl gen, int inf, int max, PDDocument docSujetMaitre, int numThread, int nbPages) {
		this.generator = gen
		this.borneInf = inf;
		this.borneMax = max
		this.docSujetMaitre = docSujetMaitre
		this.numThread = numThread
		this.nbPages = nbPages
	}

	override run() {
		for (i : borneInf ..< borneMax) {
			generator.insertQRCodeInSubject(docSujetMaitre, i, numThread, nbPages)
		}
	}

}
