package fr.istic.tools.scanexam.qrCode.writer

import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.StringWriter
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import org.apache.commons.io.IOUtils
import org.apache.pdfbox.io.MemoryUsageSetting
import org.apache.pdfbox.multipdf.PDFMergerUtility
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject

class QRCodeGeneratorImpl implements QRCodeGenerator {


	boolean isFinished

	/**
	 * Créer toutes les copies d'examen en y insérant les QrCodes correspondant dans chaque pages
	 * 
	 * @param inputFile Chemin du sujet maitre
	 * @param outputPath chemin de sortie
	 * @param idExam l'id de l'examen
	 * @param nbCopies Nombre de copies de l'examen souhaité
	 */
	override createAllExamCopies(InputStream inputFile, OutputStream outputStream, String idExam, int nbCopie) {

		try {
			val StringWriter stringWriterInput = new StringWriter()

			IOUtils.copy(inputFile, stringWriterInput, "UTF-8")

			//val String input = stringWriterInput.toString()
			val PDDocument doc = PDDocument.load(inputFile)
			
			val int nbPages = doc.numberOfPages
			val PDFMergerUtility PDFmerger = new PDFMergerUtility()

			val MemoryUsageSetting memUsSett = MemoryUsageSetting.setupMainMemoryOnly()

			val File outpath = new File(outputStream.toString)
			var String output = outpath.absolutePath
			output += "/" + idExam + ".pdf"

			PDFmerger.setDestinationFileName(output);

			for (i : 0 ..< nbCopie) {
				PDFmerger.addSource(inputFile)
			}

			val File f2 = new File(output)

			memUsSett.tempDir = f2

			PDFmerger.mergeDocuments(memUsSett)

			val PDDocument docSujetMaitre = PDDocument.load(f2)
			createThread(idExam, nbCopie, docSujetMaitre, nbPages, output)

		} // fin try
		catch (Exception e) {
			e.printStackTrace()
		}
	}



	/**
	 * CrÃ©e un QRCode (21 * 21 carrÃ©s) de taille width * height encryptant la chaine text.
	 * Un fichier PNG du QRCode est crÃ©e en suivant le filePath
	 * @param text Le texte a encoder 
	 * @param width  Largeur de l'image 
	 * @param height Hauteur de l'image
	 * @param filePath Chemin du nouveau fichier 
	 * 
	 * @throws WriterException
	 * @throws IOException
	 */
	def generateQRCodeImage(String text, int width, int height, String filePath) {
		try {
			val QRCodeWriter qrCodeWriter = new QRCodeWriter()
			val BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
			val Path path = FileSystems.getDefault().getPath(filePath)
			MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path)
		} catch (WriterException | IOException e) {
			e.printStackTrace
		}
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
	def createPdfFromImageInAllPages(String inputFile, String imagePath, String outputFile) {
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
		} catch (IOException e) {
			e.printStackTrace()
		}
	}

	/**
	 * @param name l'intitulé du document
	 * @param nbCopies nombre de copies dÃ©sirÃ©es
	 * @param docSujetMaitre document dans lequel insÃ©rer les Codes
	 * @param nbPages nombre de pages du sujet Maitre 
	 *  
	 */
	def createThread(String examID, int nbCopie, PDDocument docSujetMaitre, int nbPage, String output) {
		

		if (nbCopie <= 4) {
			val ExecutorService service = Executors.newFixedThreadPool(1)
			var File qrcode = File.createTempFile("qrcode", ".png")

			val CountDownLatch LatchThreads = new CountDownLatch(1)
			service.execute(
				new QRThreadWriter(this, 0, nbCopie, docSujetMaitre, nbPage, LatchThreads, examID, qrcode.absolutePath))
			LatchThreads.await()
			service.shutdown()

			qrcode.deleteOnExit
		} else {

			val PdfThreadManagerWriter manager = new PdfThreadManagerWriter(nbPage, docSujetMaitre, this, nbCopie, examID, output)
			manager.start
		

		}

	}

	/**
	 * InsÃ¨re le QRCode sur chaque pages d'un sujet (en changeant le numÃ©ro de page sur chacunes des pages)
	 * 
	 * @param name l'intitulé du document
	 * @param docSujetMaitre le sujet maitre
	 * @param numCopie le nombre de copies souhaitées
	 * @param numThread le nombre de threads à executer
	 * @param nbPagesSuject le nombre de page du sujet maître
	 */
	def insertQRCodeInSubject(String name, PDDocument docSujetMaitre, int numCopie, int nbPagesSujet,
		String pathImage) {

		for (i : 0 ..< nbPagesSujet) {
			insertQRCodeInPage(name, i, docSujetMaitre, numCopie, nbPagesSujet, pathImage)
		}
	}

	/**
	 * InÃ¨sre un QRCode sur une page
	 * @param name l'intitulé du document
	 * @param docSujetMaitre le sujet maitre
	 * @param numCopie le nombre de copies souhaitées
	 * @param numThread le nombre de threads à executer
	 * @param nbPagesSuject le nombre de page du sujet maître
	 */
	def insertQRCodeInPage(String name, int numPage, PDDocument doc, int numCopie, int nbPagesSujet, String pathImage) {
		val String stringAEncoder = name + "_" + numCopie + "_" + numPage

		generateQRCodeImage(stringAEncoder, 350, 350, pathImage)

		val PDImageXObject pdImage = PDImageXObject.createFromFile(pathImage, doc)
		val float scale = 0.3f

		try(val PDPageContentStream contentStream= new PDPageContentStream(doc, doc.getPage(numPage+ ((numCopie*nbPagesSujet))), AppendMode.APPEND, true,
						true)) {

			contentStream.drawImage(pdImage, 0, 0, pdImage.getWidth() * scale, pdImage.getHeight() * scale)
		}
	}

	override isFinished(){
		return isFinished
	}
	
	def setFinished(boolean bool){
		this.isFinished = bool
	}

	def static void main(String[] arg) {

		val QRCodeGeneratorImpl gen = new QRCodeGeneratorImpl()
		
		
		val InputStream input2 = new ByteArrayInputStream(Files.readAllBytes(Path.of("C:/Users/Skinz/Documents/impots 2020.pdf")))
				
		//FileUtils.readFileToByteArray(File input)

		val OutputStream output = new ByteArrayOutputStream()
		gen.createAllExamCopies(input2, output, "42PFO2021", 8)

	/*
	 * val String in = "./src/main/resources/QRCode/pfo_example_Inserted.pdf"
	 * val File f = new File(in)
	 * val PDDocument doc = PDDocument.load(f)
	 * val File desti = new File("./src/main/resources/QRCode/pfo_example_Inserted.pdf")

	 * //doc.removePage(12)
	 * doc.save(desti)
	 * 
	 */
	}

}
