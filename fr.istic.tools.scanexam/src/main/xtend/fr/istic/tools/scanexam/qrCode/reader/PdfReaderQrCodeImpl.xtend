package fr.istic.tools.scanexam.qrCode.reader

import com.google.zxing.BinaryBitmap
import com.google.zxing.LuminanceSource
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.Result
import com.google.zxing.ResultPoint
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.utils.DataFactory
import java.awt.image.BufferedImage
import java.io.IOException
import java.io.InputStream
import java.util.ArrayList
import java.util.Collections
import java.util.HashSet
import java.util.List
import java.util.Set
import java.util.regex.Pattern
import java.util.stream.Collectors
import javafx.util.Pair
import org.apache.logging.log4j.LogManager
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.rendering.ImageType
import org.apache.pdfbox.rendering.PDFRenderer
import org.apache.pdfbox.util.Matrix
import fr.istic.tools.scanexam.qrCode.QrCodeType

class PdfReaderQrCodeImpl implements PdfReaderQrCode {

	protected val logger = LogManager.logger
	protected Set<Copie> sheets
	protected int nbPagesInSheet
	protected int nbPagesInPdf
	protected PDDocument doc
	protected String docPath
	protected Pair<Float, Float> qrPos
	protected boolean isFinished
	protected List<Integer> pagesMalLues;

	protected int missingSheets
	protected int treatedSheets

	new(InputStream input, String docPath, int nbPages, Pair<Float, Float> qrPos) {
		this.doc = PDDocument.load(input)
		this.docPath = docPath
		this.nbPagesInSheet = nbPages
		this.qrPos = qrPos
		this.isFinished = false
		missingSheets = 0
		treatedSheets = 0
		sheets = new HashSet<Copie>()
	}

	override readPDf() {
		try {
			this.nbPagesInPdf = doc.numberOfPages
			createThread(doc.numberOfPages, doc, docPath, qrPos)
		} catch (Exception e) {
			logger.error("Cannot read PDF", e)
			return false
		}
		return true
	}

	/**
	 * Analyse les qr codes, corrige les problèmes de numérisation (ex: à l'envers) et lie les pages aux élèves
	 * @param pdDoc Document à analyser
	 * @param docPath Chemin du document à analyser
	 * @param pdfRenderer Rendu du document à analyser
	 * @param startPages Première page à analyser
	 * @param endPages Dernière page à analyser
	 * @param qrPos Position où devrait ce trouver le qr code
	 */
	def void readQRCodeImage(PDDocument pdDoc, String docPath, PDFRenderer pdfRenderer, int startPages, int endPages,
		Pair<Float, Float> qrPos) throws IOException {
		pagesMalLues = new ArrayList<Integer>
		for (page : startPages ..< endPages) {
			// des fois (randoms) outofmemory
			var BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 250, ImageType.GRAY)
			val LuminanceSource source = new BufferedImageLuminanceSource(bim)
			val BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source))
			val MultiFormatReader mfr = new MultiFormatReader

			try {
				val Result result = mfr.decodeWithState(bitmap)
				val Pattern pattern = Pattern.compile("_")
				val String[] items = pattern.split(result.text)

				val float orientation = qrCodeOrientation(result)
				val Pair<Float, Float> position = qrCodePosition(result, orientation, bim.width, bim.height)
				if (orientation <= -0.5f || orientation >= 0.5f) {
					rotatePdf(pdDoc, docPath, page, orientation)
				}
				if (qrPos.key >= 0.0f && qrPos.value >= 0.0f) {
					val diffX = qrPos.key - position.key
					val diffY = position.value - qrPos.value
					if (diffX <= -0.01f || diffX >= 0.01f || diffY <= -0.01f || diffY >= 0.01f) {
						repositionPdf(pdDoc, docPath, page, diffX, diffY)
					}
				}
				val Copie cop = new Copie(Integer.parseInt(items.get(items.size - 2)), page,
					Integer.parseInt(items.get(items.size - 1)))

				synchronized (sheets) {
					addCopie(cop)
				}
			} catch (ArrayIndexOutOfBoundsException | NotFoundException e) {
				pagesMalLues.add(page)
				logger.error("Cannot read QRCode in page " + page, e)
			} finally {
				treatedSheets++
			}
			bim.flush
			System.gc

		}
	}

	/**
	 * Retourne l'orientation d'un QR code sous la forme d'un angle compris entre
	 * ]-180;180]°. Plus le QR code est orienté vers la droite plus il gagne de
	 * dégrés.
	 * 
	 * @param result Résultat du dédodage du QR code
	 * @return Orientation du QR code
	 */
	protected def float qrCodeOrientation(Result result) {
		val ResultPoint[] resultPoints = result.resultPoints
		val ResultPoint a = resultPoints.get(1)
		val ResultPoint b = resultPoints.get(2)

		val float distanceX = b.x - a.x
		val float distanceY = b.y - a.y
		var float angle = (Math.atan(distanceY / distanceX) * (180 / Math.PI)) as float
		if (angle > 0 && a.getX() > b.getX() && a.getY() >= b.getY()) {
			angle -= 180
		} else if (angle <= 0 && b.getX() < a.getX() && b.getY() >= a.getY()) {
			angle += 180
		}
		return angle
	}

	/**
	 * Retourne la position d'un QR code en pixel. On considère que le point
	 * d'origine du QR code est en haut à gauche et que les coordonnées (0, 0) sont
	 * sont bas à gauche de la page. Ne marche que si le code à un angle de 0 ou
	 * 180°.
	 * 
	 * @param result    Résultat du dédodage du QR code
	 * @param docWidth  Laugueur du document où se trouve le QR code
	 * @param docHeight Hauteur du document où se trouve le QR code
	 * @return Paire contenant les positions x et y du QR code
	 */
	protected def Pair<Float, Float> qrCodePosition(Result result, float orientation, float docWidth, float docHeight) {
		val ResultPoint[] resultPoints = result.resultPoints
		val ResultPoint a = resultPoints.get(1)
		val ResultPoint b = resultPoints.get(2)
		val ResultPoint c = resultPoints.get(0)

		val Pair<Float, Float> disorientedA = rotatePoint(new Pair<Float, Float>(a.x, a.y),
			new Pair<Float, Float>(docWidth / 2, docHeight / 2), orientation)
		val Pair<Float, Float> disorientedB = rotatePoint(new Pair<Float, Float>(b.x, b.y),
			new Pair<Float, Float>(docWidth / 2, docHeight / 2), orientation)
		val Pair<Float, Float> disorientedC = rotatePoint(new Pair<Float, Float>(c.x, c.y),
			new Pair<Float, Float>(docWidth / 2, docHeight / 2), orientation)

		var float x = (disorientedB.key + disorientedA.key) / 2
		var float y = (disorientedC.value + disorientedA.value) / 2

		// Point d'origine en haut à gauche
		val double widthX2 = Math.pow(disorientedB.key - disorientedA.key, 2)
		val double widthY2 = Math.pow(disorientedB.value - disorientedA.value, 2)
		var double width = Math.sqrt(widthX2 + widthY2)
		val double heightX2 = Math.pow(disorientedC.key - disorientedA.key, 2)
		val double heightY2 = Math.pow(disorientedC.value - disorientedA.value, 2)
		var double height = Math.sqrt(heightX2 + heightY2)
		width /= (4.0f / 3.0f)
		height /= (4.0f / 3.0f)
		x -= width as float
		y -= height as float

		return new Pair<Float, Float>(x / docWidth, y / docHeight)
	}

	/**
	 * Retourne l'image d'un point par une rotation (repère X de gauche à droite, Y du
	 * haut vers le bas).
	 * @param point Point à transformer
	 * @param centre Centre de la rotation
	 * @param angle Angle en degrés
	 * @return Image de M par la rotation d'angle angle autour du centre
	 */
	protected def Pair<Float, Float> rotatePoint(Pair<Float, Float> point, Pair<Float, Float> center, float angle) {
		val angleRad = angle * Math.PI / 180
		val xPoint = point.key - center.key
		val yPoint = point.value - center.value
		val x = xPoint * Math.cos(angleRad) + yPoint * Math.sin(angleRad) + center.key
		val y = - xPoint * Math.sin(angleRad) + yPoint * Math.cos(angleRad) + center.value
		return new Pair<Float, Float>(x as float, y as float)
	}

	/**
	 * Réoriente le contenu d'une page de pdf selon un angle donné
	 * @param pdDoc Document sur lequel travailler
	 * @param docPath Chemin du document sur lequel travailler
	 * @param page Page où effectuer la rotation
	 * @param rotation Nouvelle inclinaison du contenu
	 */
	protected def void rotatePdf(PDDocument pdDoc, String docPath, int page, float rotation) {
		val PDPage pdPage = pdDoc.documentCatalog.pages.get(page)
		val PDPageContentStream cs = new PDPageContentStream(pdDoc, pdPage, PDPageContentStream.AppendMode.PREPEND,
			false, false)
		val PDRectangle cropBox = pdPage.cropBox
		val float tx = (cropBox.lowerLeftX + cropBox.upperRightX) / 2
		val float ty = (cropBox.lowerLeftY + cropBox.upperRightY) / 2
		cs.transform(Matrix.getTranslateInstance(tx, ty))
		cs.transform(Matrix.getRotateInstance(Math.toRadians(rotation), 0, 0))
		cs.transform(Matrix.getTranslateInstance(-tx, -ty))
		cs.close
		pdDoc.save(docPath)
	}

	/**
	 * Repositionne le contenu d'une page de pdf selon un les longueurs x et y données
	 * @param pdDoc Document sur lequel travailler
	 * @param docPath Chemin du document sur lequel travailler
	 * @param page Page où effectuer la rotation
	 * @param offsetX Longueur vers laquelle décaler le contenu sur l'axe x
	 * @param offsetY Longueur vers laquelle décaler le contenu sur l'axe y
	 */
	protected def void repositionPdf(PDDocument pdDoc, String docPath, int page, float offsetX, float offsetY) {
		val PDPage pdPage = pdDoc.documentCatalog.pages.get(page)
		val PDPageContentStream cs = new PDPageContentStream(pdDoc, pdPage, PDPageContentStream.AppendMode.PREPEND,
			false, false)
		cs.transform(Matrix.getTranslateInstance(offsetX * pdPage.mediaBox.width, offsetY * pdPage.mediaBox.height))
		cs.close
		pdDoc.save(docPath)
	}

	/**
	 * Crée un thread lisant des qr codes
	 * @param nbPage nombre de copies désirées
	 * @param doc document dans lequel insérer les Codes
	 * @param docPath chemin du document dans lequel insérer les Codes
	 * @param qrPos Position à laquelle devrait se trouver les qr codes
	 */
	def createThread(int nbPage, PDDocument doc, String docPath, Pair<Float, Float> qrPos) {
		val PdfReaderThreadManager manager = new PdfReaderThreadManager(nbPage, doc, docPath, qrPos,
			QrCodeType.SHEET_PAGE, this)
		manager.start
	}

	/**
	 * Dis si un examen est complet ou non
	 * @return true si l'examen est complet (contient toutes les pages de toutes les copies), false sinon
	 */
	def boolean isExamenComplete() {
		var boolean ret = true
		var List<Integer> idSheets = new ArrayList<Integer>()

		for (i : 0 ..< sheets.length) {
			ret = ret && sheets.get(i).isCopyComplete(nbPagesInSheet)
			idSheets.add(sheets.get(i).numCopie)
		}

		Collections.sort(idSheets, [ a, b |
			a - b
		])

		missingSheets = Math.abs(idSheets.get(idSheets.size - 1) - (idSheets.size - 1))
		ret = ret && (missingSheets == 0)

		return ret
	}

	/**
	 * Renvoie une collection de toutes les copies incomplètes
	 * @return une collection de copies incomplètes
	 */
	def Set<Copie> getUncompleteCopies() {
		val Set<Copie> uncompleteCopies = new HashSet<Copie>()

		for (i : 0 ..< sheets.length) {
			if (!sheets.get(i).isCopyComplete(nbPagesInSheet))
				uncompleteCopies.add(sheets.get(i))
		}
		return uncompleteCopies
	}

	/**
	 * Renvoie une collection de toutes les copies complètes
	 * @return une collection de copies complètes
	 */
	def Set<Copie> getCompleteCopies() {
		var Set<Copie> completeCopies = new HashSet<Copie>()

		completeCopies = sheets.stream.filter(copie|copie.isCopyComplete(nbPagesInSheet)).collect(Collectors.toSet)

		return completeCopies

	}

	/**
	 * Renvoie une copie spécifique à son indentifiant
	 * @param numCopie le numéro de la copie voulue
	 * @return la copie correspondante
	 */
	def Copie getCopie(int numCopie) {
		for (i : 0 ..< sheets.length)
			if (sheets.get(i).numCopie == numCopie)
				return sheets.get(i)
	}

	/**
	 * Ajoute une copie lu au tas de copies déjà lues. Si la copie existe déjà, on merge les pages
	 * @param sheet la copie à ajouter 
	 */
	def addCopie(Copie copie) {
		var boolean trouve = false
		var int i = 0

		while (!trouve && i < sheets.length) {
			if (sheets.get(i).numCopie == copie.numCopie)
				trouve = true
			i++
		} // while
		i--
		if (trouve) {
			val Copie oldCopie = sheets.get(i)
			sheets.remove(oldCopie)
			val int numCopie = oldCopie.numCopie
			val Set<Page> pages = oldCopie.pagesCopie
			pages.addAll(copie.pagesCopie)
			val String studentId = oldCopie.studentId !== null ? oldCopie.studentId : copie.studentId
			val String studentLastName = oldCopie.studentLastName !== null ? oldCopie.studentLastName : copie.
					studentLastName
			val String studentFirstName = oldCopie.studentFirstName !== null ? oldCopie.studentFirstName : copie.
					studentFirstName
			val Copie newCopie = new Copie(numCopie, pages, studentId, studentLastName, studentFirstName)
			sheets.add(newCopie)
		} else {
			sheets.add(copie)
		}
	}

	/**
	 * Renvoie une collection de toutes les copies lues, complètes ou non
	 * @return une collection de toutes les copies lues
	 */
	def Set<Copie> getSheets() {
		return sheets
	}

	/**
	 * Renvoie la collection des copies complètes uniquement au format de l'API
	 * @return la collection des copies complètes au format de l'API
	 */
	override getCompleteStudentSheets() {
		val Set<StudentSheet> res = new HashSet<StudentSheet>()
		var Set<Copie> temp = new HashSet<Copie>()
		val DataFactory dF = new DataFactory()

		temp = completeCopies

		for (i : 0 ..< temp.length) {
			val int index = temp.get(i).numCopie
			val int[] pagesArray = newIntArrayOfSize(nbPagesInSheet)
			val studentId = temp.get(i).studentId
			val studentLastName = temp.get(i).studentLastName
			val studentFirstName = temp.get(i).studentFirstName

			for (j : 0 ..< temp.get(i).pagesCopie.length) {
				pagesArray.set(temp.get(i).pagesCopie.get(j).numPageInSubject,
					temp.get(i).pagesCopie.get(j).numPageInPDF)
			}
			if (studentId !== null && studentLastName !== null && studentFirstName !== null) {
				res.add(dF.createStudentSheet(index, pagesArray, studentId, studentLastName, studentFirstName))
			} else {
				res.add(dF.createStudentSheet(index, pagesArray))
			}

		}
		return res
	}

	/**
	 * Renvoie la collection des copies incomplètes uniquement au format de l'API
	 * Les copies manquantes sont signalées par la présence d'un -1 en numéro de page
	 * @return la collection des copies incomplètes au format de l'API
	 */
	override getUncompleteStudentSheets() {
		val Set<StudentSheet> res = new HashSet<StudentSheet>()
		var Set<Copie> temp = new HashSet<Copie>()
		val DataFactory dF = new DataFactory()

		temp = uncompleteCopies

		for (i : 0 ..< temp.length) {
			val int index = temp.get(i).numCopie
			val int[] pagesArray = newIntArrayOfSize(nbPagesInSheet)
			val studentId = temp.get(i).studentId
			val studentLastName = temp.get(i).studentLastName
			val studentFirstName = temp.get(i).studentFirstName
			for (e : 0 ..< nbPagesInSheet) {
				pagesArray.set(e, -1)
			}

			for (j : 0 ..< temp.get(i).pagesCopie.length) {
				pagesArray.set(temp.get(i).pagesCopie.get(j).numPageInSubject,
					temp.get(i).pagesCopie.get(j).numPageInPDF)
			}
			if (studentId !== null && studentLastName !== null && studentFirstName !== null) {
				res.add(dF.createStudentSheet(index, pagesArray, studentId, studentLastName, studentFirstName))
			} else {
				res.add(dF.createStudentSheet(index, pagesArray))
			}
		}
		return res
	}

	/**
	 * Renvoie le nombre de total de pages du PDF de toutes les copies
	 * @return le nombre de pages du PDF source
	 */
	override getNbPagesPdf() {
		return this.nbPagesInPdf;
	}

	/**
	 * Renvoie le nombre de pages traitées par la lecture du PDF
	 * @return le nombre de pages que le reader a lu du PDF source
	 */
	override getNbPagesTreated() {
		return treatedSheets
	}

	override isFinished() {
		return isFinished
	}

	def setFinished(boolean bool) {
		this.isFinished = bool
	}

	override getFailedPages() {
		return pagesMalLues
	}

/*def static void main(String[] arg) {
 * 	val File pdf = new File("./src/main/resources/QRCode/pfo_example_Inserted.pdf")
 * 	val PDDocument doc = PDDocument.load(pdf)
 * 	val PdfReaderQrCodeImpl qrcodeReader = new PdfReaderQrCodeImpl(doc, 8)
 * 	
 * 	qrcodeReader.readPDf


 * 	while (qrcodeReader.getNbPagesTreated != qrcodeReader.getNbPagesPdf) {
 * 	}
 * 	
 * 	qrcodeReader.isExamenComplete
 }*/
}
