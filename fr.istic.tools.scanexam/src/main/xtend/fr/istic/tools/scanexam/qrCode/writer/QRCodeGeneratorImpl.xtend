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
import java.io.InputStream
import java.io.StringWriter
import org.apache.commons.io.IOUtils
import java.io.ByteArrayInputStream
import java.io.OutputStream
import java.io.ByteArrayOutputStream

class QRCodeGeneratorImpl implements QRCodeGenerator {


	/**
	 * Créer toutes les copies d'examen en y insérant les QrCodes correspondant dans chaque pages
	 * 
	 * @param inputFile Chemin du sujet maitre
	 * @param outputPath chemin de sortie
	 * @param idExam l'id de l'examen
	 * @param nbCopies Nombre de copies de l'examen souhaité
	 */
	override createAllExamCopies(InputStream inputFile, OutputStream outputPath, String idExam, int nbCopie) {
		
		try{
			val StringWriter stringWriterInput = new StringWriter()
			
			IOUtils.copy(inputFile, stringWriterInput, "UTF-8")
			
			val String input = stringWriterInput.toString()
			val PDDocument doc = PDDocument.load(new File(input))
			val int nbPages = doc.numberOfPages
			val PDFMergerUtility PDFmerger = new PDFMergerUtility()
			
			val MemoryUsageSetting memUsSett = MemoryUsageSetting.setupMainMemoryOnly()
			
			val File outpath = new File(outputPath.toString)
            var String output = outpath.absolutePath
            output += "/" + idExam + ".pdf"

			
			PDFmerger.setDestinationFileName(output);
	
			for (i : 0 ..< nbCopie) {
				PDFmerger.addSource(input)
			}
			
			val File f2 = new File(output)
			
			memUsSett.tempDir = f2
			
			PDFmerger.mergeDocuments(memUsSett)
			
			val PDDocument docSujetMaitre = PDDocument.load(f2)
			createThread(idExam, nbCopie, docSujetMaitre, nbPages)
			docSujetMaitre.save(output)
			
		}//fin try
		catch(Exception e){
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
	def generateQRCodeImage(String text, int width, int height, String filePath){
		try{
			val QRCodeWriter qrCodeWriter = new QRCodeWriter()
			val BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height)
			val Path path = FileSystems.getDefault().getPath(filePath)
			MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path)
		}
		catch(WriterException | IOException e){
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
	def createPdfFromImageInAllPages(String inputFile, String imagePath, String outputFile){
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
		catch (IOException e){
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
	def createThread(String name, int nbCopie, PDDocument docSujetMaitre, int nbPage) {
		val ExecutorService service = Executors.newFixedThreadPool(4)
		
		val CountDownLatch LatchMain = new CountDownLatch(1)

		if (nbCopie <= 4) {
			var File qrcode = File.createTempFile("qrcode", ".png")
			
			val CountDownLatch LatchThreads = new CountDownLatch(1)
			service.execute(new QRThreadWriter(this, 0, nbCopie, docSujetMaitre, nbPage, LatchThreads, LatchMain, name, qrcode.absolutePath))
			LatchMain.countDown()
			LatchThreads.await()
			service.shutdown()
			
			qrcode.deleteOnExit
		} else {
			val CountDownLatch LatchThreads = new CountDownLatch(4)
			var File qrcode1 = File.createTempFile("qrcode1", ".png")
			var File qrcode2 = File.createTempFile("qrcode2", ".png")
			var File qrcode3 = File.createTempFile("qrcode3", ".png")
			var File qrcode4 = File.createTempFile("qrcode4", ".png")
			
			service.execute(
				new QRThreadWriter(this, 0, (nbCopie / 4), docSujetMaitre, nbPage, LatchThreads, LatchMain, name, qrcode1.absolutePath))
			service.execute(
				new QRThreadWriter(this, (nbCopie / 4), (nbCopie / 2), docSujetMaitre, nbPage, LatchThreads,
					LatchMain, name, qrcode2.absolutePath))
			service.execute(
				new QRThreadWriter(this, (nbCopie / 2), (3 * nbCopie / 4), docSujetMaitre, nbPage, LatchThreads,
					LatchMain, name, qrcode3.absolutePath))
			service.execute(
				new QRThreadWriter(this, (3 * nbCopie / 4), nbCopie, docSujetMaitre, nbPage, LatchThreads,
					LatchMain, name, qrcode4.absolutePath))
			LatchMain.countDown()
			LatchThreads.await()
			service.shutdown()
			qrcode1.deleteOnExit
			qrcode2.deleteOnExit
			qrcode3.deleteOnExit
			qrcode4.deleteOnExit
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
	def insertQRCodeInSubject(String name, PDDocument docSujetMaitre, int numCopie, int nbPagesSujet, String pathImage) {

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


	def static void main(String[] arg) {

		val QRCodeGeneratorImpl gen = new QRCodeGeneratorImpl()
		val InputStream input = new ByteArrayInputStream("D:/dataScanExam/in/pfo_example.pdf".getBytes())
		val OutputStream output = new ByteArrayOutputStream()
        output.write("D:/dataScanExam/out".getBytes())
		gen.createAllExamCopies(input, output, "42PFO2021", 8)

		/*
		val String in = "./src/main/resources/QRCode/pfo_example_Inserted.pdf"
		val File f = new File(in)
		val PDDocument doc = PDDocument.load(f)
		val File desti = new File("./src/main/resources/QRCode/pfo_example_Inserted.pdf")

		//doc.removePage(12)
		doc.save(desti)
		* 
		*/

	}

}
