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

	new(QRCodeGeneratorImpl gen, int inf, int max, PDDocument docSujetMaitre, int numThread, int nbPages, CountDownLatch countDown, CountDownLatch countDownMain) {
		this.generator = gen
		this.borneInf = inf;
		this.borneMax = max
		this.docSujetMaitre = docSujetMaitre
		this.numThread = numThread
		this.nbPages = nbPages
		this.countDown = countDown
		this.countDownMain = countDownMain
	}

	override run() {
		countDownMain.await();
		for (i : borneInf ..< borneMax) {
			generator.insertQRCodeInSubject(docSujetMaitre, i, numThread, nbPages)
		}
		countDown.countDown
	}

}
