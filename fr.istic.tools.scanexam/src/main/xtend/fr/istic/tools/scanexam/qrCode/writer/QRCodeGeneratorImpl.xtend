package fr.istic.tools.scanexam.qrCode.writer

import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import org.apache.pdfbox.io.MemoryUsageSetting
import org.apache.pdfbox.multipdf.PDFMergerUtility
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.apache.logging.log4j.LogManager

class QRCodeGeneratorImpl implements QRCodeGenerator {

	boolean isFinished
	val logger = LogManager.logger
	
	/**
	 * Créer toutes les copies d'examen en y insérant les QrCodes correspondant dans chaque pages
	 * 
	 * @param inputFile Chemin du sujet maitre
	 * @param outputPath chemin de sortie
	 * @param idExam l'id de l'examen
	 * @param nbCopies Nombre de copies de l'examen souhaité
	 */
	override createAllExamCopies(InputStream inputFile, File outFile, String idExam, int nbCopie) {

        try {
            
            val byte[] byteArray = newByteArrayOfSize(inputFile.available)
            
            inputFile.read(byteArray)
            
            println(inputFile)
            
            val File temp = File.createTempFile("pdfTemp",".pdf")
			temp.deleteOnExit            
            val OutputStream oS = new FileOutputStream(temp)
            
            oS.write(byteArray)
            
            oS.close
            
            val PDDocument doc = PDDocument.load(temp)
            
            val int nbPages = doc.numberOfPages

            val MemoryUsageSetting memUsSett = MemoryUsageSetting.setupMainMemoryOnly()

            //val File f2 = File.createTempFile(idExam, ".pdf")

            var PDFMergerUtility ut = new PDFMergerUtility()
            
            for (i : 0 ..< nbCopie) {
                ut.addSource(temp)
            }
            
            ut.destinationFileName = outFile.absolutePath
            
            memUsSett.tempDir = temp
            
            //ut.mergeDocuments(MemoryUsageSetting.setupTempFileOnly())
            ut.mergeDocuments(memUsSett)
            
            
            
            val PDDocument docSujetMaitre = PDDocument.load(outFile)

            createThread(idExam, nbCopie, docSujetMaitre, doc, nbPages, new FileOutputStream(outFile))


        } // fin try
        catch (Exception e) {
            logger.error("Cannot insert QR codes", e)
        }
    }

	/**
	 * Créé un QRCode (21 * 21 carrés) de taille width * height chiffrant la chaine text.
	 * Un fichier PNG du QRCode est créé en suivant le filePath
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
			logger.error("Cannot generate QR code", e)
		}
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
			logger.error("Cannot print QR code in page", e)
		}
	}

	/**
	 * @param name l'intitulé du document
	 * @param nbCopies nombre de copies désirées
	 * @param docSujetMaitre document dans lequel insérer les Codes
	 * @param nbPages nombre de pages du sujet Maitre 
	 *  
	 */
	def createThread(String examID, int nbCopie, PDDocument docSujetMaitre, PDDocument doc, int nbPage, OutputStream output) {

		if (nbCopie < 4) {
			val ExecutorService service = Executors.newFixedThreadPool(1)
			var File qrcode = File.createTempFile("qrcode", ".png")

			val CountDownLatch LatchThreads = new CountDownLatch(1)
			service.execute(
				new QRThreadWriter(this, 0, nbCopie, docSujetMaitre, nbPage, LatchThreads, examID, qrcode.absolutePath))
			LatchThreads.await()
			service.shutdown()
			
			docSujetMaitre.save(output)

			qrcode.deleteOnExit
		} else {

			val PdfThreadManagerWriter manager = new PdfThreadManagerWriter(nbPage, docSujetMaitre, doc, this, nbCopie,
				examID, output)
			manager.start

		}

	}

	/**
	 * Insère le QRCode sur chaque pages d'un sujet (en changeant le numéro de page sur chacunes des pages)
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
	 * Inèsre un QRCode sur une page
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

	override isFinished() {
		return isFinished
	}

	def setFinished(boolean bool) {
		this.isFinished = bool
	}

	def static void main(String[] arg) {

		val QRCodeGeneratorImpl gen = new QRCodeGeneratorImpl()
		//val InputStream input = new ByteArrayInputStream("D:/dataScanExam/in/pfo_example.pdf".getBytes())

		val InputStream input2 = new ByteArrayInputStream(
			Files.readAllBytes(Path.of("D:/dataScanExam/in/pfo_example.pdf")))

		// FileUtils.readFileToByteArray(File input)
		val File output = new File("D:/dataScanExam/out/melanie.pdf")
		gen.createAllExamCopies(input2, output, "42PFO2021", 8)

	}

}
