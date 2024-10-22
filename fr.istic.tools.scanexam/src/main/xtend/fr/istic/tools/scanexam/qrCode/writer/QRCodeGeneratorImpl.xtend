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
import org.apache.logging.log4j.LogManager
import org.apache.pdfbox.io.MemoryUsageSetting
import org.apache.pdfbox.multipdf.PDFMergerUtility
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.apache.pdfbox.pdmodel.font.PDType0Font
import fr.istic.tools.scanexam.utils.ResourcesUtils
import fr.istic.tools.scanexam.core.QrCodeZone
import java.util.EnumMap
import com.google.zxing.EncodeHintType
import java.util.Map
import fr.istic.tools.scanexam.qrCode.QrCodeType

class QRCodeGeneratorImpl implements QRCodeGenerator {

	boolean isFinished
	int nbTreated = 0
	int numberPagesAllSheets
	val logger = LogManager.logger

	/**
	 * Créer toutes les copies d'examen en y insérant les QrCodes correspondant dans chaque pages
	 * 
	 * @param inputFile Chemin du sujet maitre
	 * @param outputPath chemin de sortie
	 * @param qrCodeZone zone sur le document où insérer le qrcode
	 * @param qrCodeType Type de qr code à insérer
	 * @param nbCopies Nombre de copies de l'examen souhaité
	 */
	override createAllExamCopies(InputStream inputFile, File outFile, QrCodeZone qrCodeZone, int qrCodeType,
		int nbCopie) {

		try {

			val byte[] byteArray = newByteArrayOfSize(inputFile.available)

			inputFile.read(byteArray)

			val File temp = File.createTempFile("pdfTemp", ".pdf")
			temp.deleteOnExit
			val OutputStream oS = new FileOutputStream(temp)

			oS.write(byteArray)

			oS.close

			val PDDocument doc = PDDocument.load(temp)

			numberPagesAllSheets = nbCopie * doc.numberOfPages

			val int nbPages = doc.numberOfPages

			val MemoryUsageSetting memUsSett = MemoryUsageSetting.setupMainMemoryOnly()

			var PDFMergerUtility ut = new PDFMergerUtility()

			for (i : 0 ..< nbCopie) {
				ut.addSource(temp)
			}

			ut.destinationFileName = outFile.absolutePath

			if (!ut.destinationFileName.contains(".pdf")) {
				ut.destinationFileName = ut.destinationFileName + ".pdf"
			}

			memUsSett.tempDir = temp

			ut.mergeDocuments(memUsSett)

			val PDDocument docSujetMaitre = PDDocument.load(outFile)

			createThread(nbCopie, qrCodeZone, qrCodeType, docSujetMaitre, doc, nbPages, new FileOutputStream(outFile))

		} // fin try
		catch (Exception e) {
			logger.error("Cannot insert QR codes", e)
		}
	}

	/**
	 * Créé un qr code d'une taille correspondante à une zone de qr code.
	 * Un fichier PNG du QRCode est créé en suivant le filePath
	 * @param qrCodeZone Zone du qr code
	 * @param doc Sujet où se trouve la zone
	 * @param numPage Numéro de la page où se trouve la zone de qr code
	 * @param data Données à inscrire dans le qr code
	 * @param pathImage Chemin vers l'image du qr code générée
	 */
	private def void generateQrCodeImageWithQrCodeZone(QrCodeZone qrCodeZone, PDDocument doc, int numPage, String data,
		String pathImage) {
		val float docWidth = doc.getPage(numPage).mediaBox.width
		val float docHeight = doc.getPage(numPage).mediaBox.height
		val float qrCodeWidth = qrCodeZone.width * docWidth
		val float qrCodeHeight = qrCodeZone.height * docHeight
		val float qrCodeSize = qrCodeWidth < qrCodeHeight ? qrCodeWidth : qrCodeHeight
		generateQRCodeImage(data, (qrCodeSize * 4) as int, (qrCodeSize * 4) as int, 0, pathImage)
	}

	/**
	 * Créé un QRCode (21 * 21 carrés) de taille width * height chiffrant la chaine text.
	 * Un fichier PNG du QRCode est créé en suivant le filePath
	 * @param text Le texte a encoder 
	 * @param width  Largeur de l'image 
	 * @param height Hauteur de l'image
	 * @param margin Marge autour de l'image
	 * @param filePath Chemin du nouveau fichier 
	 * 
	 * @throws WriterException
	 * @throws IOException
	 */
	def generateQRCodeImage(String text, int width, int height, int margin, String filePath) {
		try {
			val QRCodeWriter qrCodeWriter = new QRCodeWriter()
			val Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType)
			hintMap.put(EncodeHintType.MARGIN, margin)
			val BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hintMap)
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
	 * @param nbCopies nombre de copies désirées
	 * @param qrCodeZone zone sur le document où insérer le qrcode
	 * @param qrCodeType Type de qr code à insérer
	 * @param docSujetMaitre document dans lequel insérer les Codes
	 * @param nbPages nombre de pages du sujet Maitre 
	 *  
	 */
	def createThread(int nbCopie, QrCodeZone qrCodeZone, int qrCodeType, PDDocument docSujetMaitre, PDDocument doc,
		int nbPage, OutputStream output) {

		if (nbCopie < 4) {
			val ExecutorService service = Executors.newFixedThreadPool(1)
			var File qrcode = File.createTempFile("qrcode", ".png")

			val CountDownLatch LatchThreads = new CountDownLatch(1)
			service.execute(
				new QRThreadWriter(this, qrCodeType, 0, nbCopie, qrCodeZone, docSujetMaitre, nbPage, LatchThreads,
					qrcode.absolutePath))
			LatchThreads.await()
			service.shutdown()

			docSujetMaitre.save(output)

			qrcode.deleteOnExit
		} else {

			val PdfThreadManagerWriter manager = new PdfThreadManagerWriter(nbPage, qrCodeZone, qrCodeType,
				docSujetMaitre, doc, this, nbCopie, output)
			manager.start

		}

	}

	/**
	 * Insère le QRCode sur chaque pages d'un sujet (en changeant le numéro de page sur chacunes des pages)
	 * 
	 * @param qrCodeZone zone sur le document où insérer le qrcode
	 * @param docSujetMaitre le sujet maitre
	 * @param numCopie le nombre de copies souhaitées
	 * @param nbPagesSuject le nombre de page du sujet maître
	 * @param qrCodeType Type de qr code à insérer
	 * @param pathImage chemin vers l'image du qr code générée
	 */
	def insertQRCodeInSubject(QrCodeZone qrCodeZone, PDDocument docSujetMaitre, int numCopie, int nbPagesSujet,
		int qrCodeType, String pathImage) {
		for (i : 0 ..< nbPagesSujet) {
			insertQRCodeInPage(qrCodeZone, docSujetMaitre, i, numCopie, nbPagesSujet, qrCodeType, pathImage)
		}
	}

	/**
	 * Insère sur une page un QRCode
	 * @param qrCodeZone zone sur le document où insérer le qrcode
	 * @param doc le sujet maitre
	 * @param numPage numéro de la page où insérer le qr code
	 * @param numCopie uméro de la copie d'examen
	 * @param nbPagesSuject le nombre de page du sujet maître
	 * @param qrCodeType Type de qr code à insérer
	 * @param pathImage chemin vers l'image du qr code générée
	 */
	def insertQRCodeInPage(QrCodeZone qrCodeZone, PDDocument doc, int numPage, int numCopie, int nbPagesSujet,
		int qrCodeType, String pathImage) {
		if (qrCodeType === QrCodeType.SHEET_PAGE) {
			insertSheetAndPageQRCodeInPage(qrCodeZone, doc, numPage, numCopie, nbPagesSujet, pathImage)
		} else {
			if (qrCodeType === QrCodeType.PAGE) {
				insertPageQRCodeInPage(qrCodeZone, doc, numPage, pathImage)
			}
		}
	}

	/**
	 * Insère sur une page un QRCode contenant l'identifiant de la copie et le numéro de la page
	 * @param qrCodeZone zone sur le document où insérer le qrcode
	 * @param doc le sujet maitre
	 * @param numPage numéro de la page où insérer le qr code
	 * @param numCopie uméro de la copie d'examen
	 * @param nbPagesSuject le nombre de page du sujet maître
	 * @param pathImage chemin vers l'image du qr code générée
	 */
	private def insertSheetAndPageQRCodeInPage(QrCodeZone qrCodeZone, PDDocument doc, int numPage, int numCopie,
		int nbPagesSujet, String pathImage) {
		val String data = QrCodeType.SHEET_PAGE + "_" + numCopie + "_" + numPage
		generateQrCodeImageWithQrCodeZone(qrCodeZone, doc, numPage, data, pathImage)
		val PDPage page = doc.getPage(numPage + (numCopie * nbPagesSujet))
		insertQrCodeImageInPage(doc, page, numPage, qrCodeZone, pathImage, data)
	}

	/**
	 * Insère sur une page un QRCode contenant le numéro de la page uniquement
	 * @param qrCodeZone zone sur le document où insérer le qrcode
	 * @param doc le sujet maitre
	 * @param numPage numéro de la page où insérer le qr code
	 * @param pathImage chemin vers l'image du qr code générée
	 */
	private def void insertPageQRCodeInPage(QrCodeZone qrCodeZone, PDDocument doc, int numPage, String pathImage) {
		val String data = QrCodeType.PAGE + "_" + numPage
		generateQrCodeImageWithQrCodeZone(qrCodeZone, doc, numPage, data, pathImage)
		val PDPage page = doc.getPage(numPage)
		insertQrCodeImageInPage(doc, page, numPage, qrCodeZone, pathImage, data)
	}

	/**
	 * Insère l'image d'un qr code sur une page ainsi que ses données sous forme de texte aux 4 coins de la page
	 * @param doc le sujet maitre
	 * @param page la page où insérer l'image
	 * @param numPage numéro de la page où insérer le qr code
	 * @param qrCodeZone zone sur le document où insérer le qrcode
	 * @param pathImage chemin vers l'image du qr code générée
	 * @param data donnée inscrite dans le qr code
	 */
	private def void insertQrCodeImageInPage(PDDocument doc, PDPage page, int numPage, QrCodeZone qrCodeZone,
		String pathImage, String data) {
		val PDImageXObject pdImage = PDImageXObject.createFromFile(pathImage, doc)
		try (val PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true, true)) {
			val float docWidth = doc.getPage(numPage).mediaBox.width
			val float docHeight = doc.getPage(numPage).mediaBox.height
			val float qrCodeX = qrCodeZone.x * docWidth
			val float qrCodeY = docHeight - (qrCodeZone.y * docHeight) - (qrCodeZone.height * docHeight)
			val float qrCodeWidth = qrCodeZone.width * docWidth
			val float qrCodeHeight = qrCodeZone.height * docHeight
			val float qrCodeSize = qrCodeWidth < qrCodeHeight ? qrCodeWidth : qrCodeHeight
			contentStream.drawImage(pdImage, qrCodeX, qrCodeY, qrCodeSize, qrCodeSize)
			insertTextDataInPage(doc, numPage, contentStream, data)
			incrementTreated
		}
	}

	/**
	 * Insert des données sous forme textuelle aux 4 coins d'une page
	 * @param doc PDDocument où inscrire les données
	 * @param numPage Numéro de la page où inscrire les données
	 * @param contentStream PDPageContentStream servant à écrire les données
	 * @param data Données à inscrire
	 */
	private def void insertTextDataInPage(PDDocument doc, int numPage, PDPageContentStream contentStream, String data) {
		val float width = doc.getPage(numPage).mediaBox.width
		val float height = doc.getPage(numPage).mediaBox.height
		contentStream.setFont(
			PDType0Font.load(doc, ResourcesUtils.getInputStreamResource("resources_annotation/arial.ttf")), 6)
		// En bas à gauche
		contentStream.beginText
		contentStream.newLineAtOffset(60, 4)
		contentStream.showText(data)
		contentStream.endText
		// En haut à gauche
		contentStream.beginText
		contentStream.newLineAtOffset(60, height - 12)
		contentStream.showText(data)
		contentStream.endText
		// En bas à droite
		contentStream.beginText
		contentStream.newLineAtOffset(width - 92, 4)
		contentStream.showText(data)
		contentStream.endText
		// En haut à droite
		contentStream.beginText
		contentStream.newLineAtOffset(width - 92, height - 12)
		contentStream.showText(data)
		contentStream.endText
	}

	override isFinished() {
		return isFinished
	}

	def setFinished(boolean bool) {
		this.isFinished = bool
	}

	override getNbTreated() {
		return nbTreated
	}

	def incrementTreated() {
		nbTreated = nbTreated + 1
	}

	override getNumberPagesAllSheets() {
		return numberPagesAllSheets
	}

	def getPercentage() {
		(getNbTreated * 100) / getNumberPagesAllSheets
	}

	def static void main(String[] arg) {

		val QRCodeGeneratorImpl gen = new QRCodeGeneratorImpl()
		// val InputStream input = new ByteArrayInputStream("D:/dataScanExam/in/pfo_example.pdf".getBytes())
		val InputStream input2 = new ByteArrayInputStream(
			Files.readAllBytes(Path.of("D:/dataScanExam/in/pfo_example.pdf")))

		// FileUtils.readFileToByteArray(File input)
		val File output = new File("D:/dataScanExam/out/melanie.pdf")
		gen.createAllExamCopies(input2, output, null, QrCodeType.SHEET_PAGE, 100)

	}

}
