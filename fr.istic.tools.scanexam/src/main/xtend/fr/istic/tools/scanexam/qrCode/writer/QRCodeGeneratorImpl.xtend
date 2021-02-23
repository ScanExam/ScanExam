package fr.istic.tools.scanexam.qrCode.writer

import java.nio.file.FileSystems

import java.nio.file.Path
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.WriterException
import java.io.IOException
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode
import java.io.File
import org.apache.pdfbox.multipdf.PDFMergerUtility
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import org.apache.pdfbox.io.MemoryUsageSetting
import java.util.concurrent.CountDownLatch

class QRCodeGeneratorImpl implements QRCodeGenerator {

	/**
	 * @param text Le texte a encoder 
	 * @param width  Largeur de l'image 
	 * @param height Hauteur de l'image
	 * @param filePath Chemin du nouveau fichier 
	 * 
	 * CrÃ©e un QRCode (21 * 21 carrÃ©s) de taille width * height encryptant la chaine text.
	 * Un fichier PNG du QRCode est crÃ©e en suivant le filePath
	 *  
	 * @throws WriterException
	 * @throws IOException
	 */
	def generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException{
		val QRCodeWriter qrCodeWriter = new QRCodeWriter()
		val BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
		val Path path = FileSystems.getDefault().getPath(filePath)
		MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path)
	}

	/**
	 * Ajoute une image imagePath sur toutes les pages du pdf inputFile puis
	 * l'enregistre dans outputFile
	 * 
	 * @param inputFile  Chemin du pdf cible
	 * @param imagePath  Chemin de l'image a insÃ©rer
	 * @param outputFile Chemin du fichier a Ã©crire
	 * 
	 * @throws IOException If there is an error writing the data.
	 */
	def createPdfFromImageInAllPages(String inputFile, String imagePath, String outputFile) throws IOException {
		try (val PDDocument doc = PDDocument.load(new File(inputFile))) {
			val float scale = 0.3f

			val PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc)
			for (PDPage page : doc.getPages()) {
				try (val PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true,
						true)) {

					contentStream.drawImage(pdImage, 0, 0, pdImage.getWidth() * scale, pdImage.getHeight() * scale)
				}
			}
			doc.save(outputFile);
		}
	}

	override createAllExamCopies(String inputFile, int nbCopie) {

		val String base = inputFile.substring(0, inputFile.lastIndexOf('.'))
		val String outputFile = base + "_Inserted.pdf"
		// val String save = base + "_save.pdf"
		val PDDocument doc = PDDocument.load(new File(base + ".pdf"))
		val int nbPages = doc.numberOfPages

		val PDFMergerUtility PDFmerger = new PDFMergerUtility()

		PDFmerger.setDestinationFileName(base + "_Duplicated.pdf");

		for (i : 0 ..< nbCopie) {
			PDFmerger.addSource(inputFile)
		}

		val File f2 = new File(base + "_Duplicated.pdf")

		val MemoryUsageSetting memUsSett = MemoryUsageSetting.setupMainMemoryOnly()
		memUsSett.tempDir = f2

		/* FIXME
		 * problÃ¨me de mÃ©moire lors de grands nombres de pages (2000+)
		 * Voir pour ne pas rajouter page par page, mais par paquets de pages
		 */
		PDFmerger.mergeDocuments(memUsSett)

		val PDDocument docSujetMaitre = PDDocument.load(f2)
		createThread(nbCopie, docSujetMaitre, nbPages)

		docSujetMaitre.save(outputFile)

		// Supressiopn des documents temporaires
		for (i : 1 ..< 5) {
			val File png = new File("./QRCode" + i + ".png")
			if (png.delete())
				println("Deleted png " + i)
		}

		val File f2test = new File("pfo_example_Duplicated.pdf")
		if ( f2test.delete)
			println("Deleted dupli")
	}

	/**
	 * 
	 * @param nbCopies nombre de copies dÃ©sirÃ©es //FIXME gerer le cas ou le nbCopies < 4 (nombre de threads)
	 * @param docSujetMaitre document dans lequel insÃ©rer les Codes
	 * @param nbPages nombre de pages du sujet Maitre 
	 *  
	 */
	def createThread(int nbCopie, PDDocument docSujetMaitre, int nbPage) {
		val ExecutorService service = Executors.newFixedThreadPool(4)
		val CountDownLatch LatchThreads = new CountDownLatch(4);
		val CountDownLatch LatchMain = new CountDownLatch(1);

		if (nbCopie <= 4) {
			service.execute(new QRThreadWriter(this, 0, nbCopie, docSujetMaitre, 1, nbPage, LatchThreads, LatchMain))
		} else {
			service.execute(
				new QRThreadWriter(this, 0, (nbCopie / 4), docSujetMaitre, 1, nbPage, LatchThreads, LatchMain))
			service.execute(
				new QRThreadWriter(this, (nbCopie / 4), (nbCopie / 2), docSujetMaitre, 2, nbPage, LatchThreads,
					LatchMain))
			service.execute(
				new QRThreadWriter(this, (nbCopie / 2), 3 * (nbCopie / 4), docSujetMaitre, 3, nbPage, LatchThreads,
					LatchMain))
			service.execute(
				new QRThreadWriter(this, (3 * nbCopie / 4), nbCopie, docSujetMaitre, 4, nbPage, LatchThreads,
					LatchMain))
		}

		LatchMain.countDown();
		LatchThreads.await();
		service.shutdown()

	}

	/**
	 * InsÃ¨re le QRCode sur chacun des pages (en changeant le numÃ©ro de page sur chacunes des pages)
	 * 
	 * @param nameExam nom de l'examen Ã  insÃ©rer dans le QRCode
	 * @param numSubject numÃ©ro de l'examen Ã  insÃ©rer dans le QRCode
	 */
	def insertQRCodeInSubject(PDDocument docSujetMaitre, int numCopie, int numThread, int nbPagesSujet) {

		for (i : 0 ..< nbPagesSujet) {
			insertQRCodeInPage(i, docSujetMaitre, numThread.toString, numCopie, nbPagesSujet)
		}
	}

	/**
	 * InÃ¨sre un QRCode sur une page
	 */
	def insertQRCodeInPage(int numPage, PDDocument doc, String nbThread, int numCopie, int nbPagesSujet) {
		val String stringAEncoder = "PFO2019_" + numCopie + "_" + numPage
		// TODO Gestion de l'id a faire une fois la partie de crÃ©ation des copies rÃ©alisÃ©e
		val String pathImage = "./QRCode" + nbThread.toString() + ".png"
		generateQRCodeImage(stringAEncoder, 350, 350, pathImage)

		val PDImageXObject pdImage = PDImageXObject.createFromFile(pathImage, doc)
		val float scale = 0.3f

		try(val PDPageContentStream contentStream= new PDPageContentStream(doc, doc.getPage(numPage+ ((numCopie*nbPagesSujet))), AppendMode.APPEND, true,
						true)) {

			contentStream.drawImage(pdImage, 0, 0, pdImage.getWidth() * scale, pdImage.getHeight() * scale)
		}
	}

	def static void main(String[] arg) {

		val QRCodeGeneratorImpl gen = new QRCodeGeneratorImpl()
		val String input = "./pfo_example.pdf"
		gen.createAllExamCopies(input, 5)

		val String in = "./pfo_example_Inserted.pdf"
		val File f = new File(in)
		val PDDocument doc = PDDocument.load(f)
		val File desti = new File("./pfo_example_Dirty.pdf")

		doc.removePage(12)
		doc.save(desti)

		println("Done")

	}

}
