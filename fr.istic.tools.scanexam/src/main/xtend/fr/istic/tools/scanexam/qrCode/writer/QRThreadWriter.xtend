package fr.istic.tools.scanexam.qrCode.writer
import org.apache.pdfbox.pdmodel.PDDocument
import java.util.concurrent.CountDownLatch

class QRThreadWriter extends Thread implements Runnable {

	QRCodeGeneratorImpl generator
	int borneInf
	int borneMax
	PDDocument docSujetMaitre
	int numThread
	int nbPages
	CountDownLatch countDown
	CountDownLatch countDownMain
	String name
	String pathImage

	new(QRCodeGeneratorImpl gen, int inf, int max, PDDocument docSujetMaitre, int numThread, int nbPages, CountDownLatch countDown, CountDownLatch countDownMain, String name, String pathImage) {
		this.generator = gen
		this.borneInf = inf;
		this.borneMax = max
		this.docSujetMaitre = docSujetMaitre
		this.numThread = numThread
		this.nbPages = nbPages
		this.countDown = countDown
		this.countDownMain = countDownMain
		this.name = name
		this.pathImage = pathImage
	}

	override run() {
		countDownMain.await();
		for (i : borneInf ..< borneMax) {
			generator.insertQRCodeInSubject(name, docSujetMaitre, i, numThread, nbPages, pathImage)
		}
		countDown.countDown
	}

}
