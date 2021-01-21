package fr.istic.tools.scanexam.qrCode.writer

import java.nio.file.FileSystems
import java.nio.file.Path
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.WriterException
import java.io.IOException
import java.util.Scanner
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode
import java.io.File
import org.apache.pdfbox.multipdf.PDFMergerUtility
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.lang.Character.Subset

class QRCodeGeneratorImpl implements QRCodeGenerator{

	/**
	 * @param text Le texte a encoder 
	 * @param width  Largeur de l'image 
	 * @param height Hauteur de l'image
	 * @param filePath Chemin du nouveau fichier 
	 * 
	 * Crée un QRCode (21 * 21 carrés) de taille width * height encryptant la chaine text.
	 * Un fichier PNG du QRCode est crée en suivant le filePath
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
	 * @param imagePath  Chemin de l'image a insérer
	 * @param outputFile Chemin du fichier a écrire
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


	override createAllExamCopies(String inputFile, String outputFile, int nbCopie) {
		val PDFMergerUtility PDFmerger = new PDFMergerUtility()
		PDFmerger.setDestinationFileName("./pfo_exemple_Inserted.pdf");

		val PDDocument doc = PDDocument.load(new File("./pfo_example.pdf"))

		val int nbPages = doc.numberOfPages
		doc.save("./pfo_exemple1.pdf");

		for (i : 0 ..< nbCopie) {
			PDFmerger.addSource("./pfo_exemple1.pdf")
		}

		// adding the source files
		// Merging the two documents
		PDFmerger.mergeDocuments()

		val PDDocument docSujetMaitre = PDDocument.load(new File("./pfo_exemple_Inserted.pdf"))
		createThread(nbCopie, docSujetMaitre, outputFile, nbPages)

		docSujetMaitre.save(outputFile)

	}

	/**
	 * 
	 * @param nbCopies nombre de copies désirées
	 * @param docSujetMaitre document dans lequel insérer les Codes
	 * @param outputFile Chemin d'acces du fichier //TODO 
	 * @param nbPages nombre de pages du sujet Maitre 
	 *  
	 */
	def createThread(int nbCopie, PDDocument docSujetMaitre, String outputFile, int nbPage) {

		val ExecutorService service = Executors.newFixedThreadPool(4)

		service.execute(new QRThreadWriter(this, 0, (nbCopie / 4), docSujetMaitre, outputFile, 1, nbPage))
		service.execute(new QRThreadWriter(this, (nbCopie / 4), (nbCopie / 2), docSujetMaitre, outputFile, 2, nbPage))
		service.execute(new QRThreadWriter(this, (nbCopie / 2), 3 * (nbCopie / 4), docSujetMaitre, outputFile, 3, nbPage))
		service.execute(new QRThreadWriter(this, (3 * nbCopie / 4), nbCopie, docSujetMaitre, outputFile, 4, nbPage))

		service.shutdown()
		service.awaitTermination(1, TimeUnit.MINUTES);
	}

	/**
	 * Insère le QRCode sur chacun des pages (en changeant le numéro de page sur chacunes des pages)
	 * 
	 * @param nameExam nom de l'examen à insérer dans le QRCode
	 * @param numSubject numéro de l'examen à insérer dans le QRCode
	 */
	def insertQRCodeInSubject(PDDocument docSujetMaitre, int numCopie, String outputFile, int numThread,
		int nbPagesSujet) {

		for (i : 0 ..< nbPagesSujet) {
			insertQRCodeInPage(i, docSujetMaitre, numThread.toString, numCopie, nbPagesSujet)
		}
	}

	/**
	 * Inèsre un QRCode sur une page
	 */
	def insertQRCodeInPage(int numPage, PDDocument doc, String nbThread, int numCopie, int nbPagesSujet) {
		val String stringAEncoder = "PFO2019_" + numCopie + "_" + numPage

		val String pathImage = "./QRCode" + nbThread.toString() + ".png"
		generateQRCodeImage(stringAEncoder, 350, 350, pathImage)

		val PDImageXObject pdImage = PDImageXObject.createFromFile(pathImage, doc)
		val float scale = 0.3f

		try(val PDPageContentStream contentStream= new PDPageContentStream(doc, doc.getPage(numPage+ ((numCopie*nbPagesSujet))), AppendMode.APPEND, true,
						true)) {

			contentStream.drawImage(pdImage, 0, 0, pdImage.getWidth() * scale, pdImage.getHeight() * scale)
		}
	}

	/*
	 * creation des threads
	 * 
	 * tant que => Il reste des pages sans QRCode
	 * 		Genere le bon QRCode
	 * 		insere le QRCode
	 * 
	 * Fin des threads
	 * 
	 * 
	 * 
	 * On sait crer un QRCode et l'insérer dans une page
	 * Créer une copie d'examen en insérant le QRCode sur chacune des pages (String nameExam, int numCopie) .... for(nbpage -> i) créer le QRCode de nom nameExam + i.IntegerToString
	 * Créer toutes les copies d'examen //ici on sépare en freds
	 * 
	 * 
	 */
	def static void main(String[] arg) {

		val QRCodeGeneratorImpl gen = new QRCodeGeneratorImpl()
		val String input = "./pfo_example.pdf"
		val String output = "./pfo_example_Inserted.pdf"
		gen.createAllExamCopies(input, output, 40)

	}

}
