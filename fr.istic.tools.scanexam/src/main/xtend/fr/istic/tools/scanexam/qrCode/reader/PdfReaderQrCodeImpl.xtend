package fr.istic.tools.scanexam.qrCode.reader

import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.LuminanceSource
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.Result
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import fr.istic.tools.scanexam.api.DataFactory
import fr.istic.tools.scanexam.core.StudentSheet
import java.awt.image.BufferedImage
import java.io.IOException
import java.util.HashMap
import java.util.HashSet
import java.util.Map
import java.util.Set
import java.util.regex.Pattern
import java.util.stream.Collectors
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.ImageType
import org.apache.pdfbox.rendering.PDFRenderer
import java.io.File

class PdfReaderQrCodeImpl implements PdfReaderQrCode {

	Set<Copie> sheets
	int nbSheetsTotal
	int nbPagesInSheet
	int nbPagesInPdf
	PDDocument doc
	boolean isFinished

	new(PDDocument doc, int nbPages, int nbCopies) {
		this.doc = doc
		this.nbPagesInSheet = nbPages
		this.nbSheetsTotal = nbCopies
		this.isFinished = false

		sheets = new HashSet<Copie>()
	}

	override readPDf() {
		try {
			this.nbPagesInPdf = doc.numberOfPages
			val PDFRenderer pdf = new PDFRenderer(doc)
			createThread(doc.numberOfPages, pdf)
		} catch (Exception e) {
			e.printStackTrace
			return false
		}
		return true
	}

	def void readQRCodeImage(PDFRenderer pdfRenderer, int startPages, int endPages) throws IOException {
		for (page : startPages ..< endPages) {
			val BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB)
			// val int taille = (bim.getHeight() * 0.3f) as int
			// val BufferedImage dest = bim.getSubimage(0, bim.getHeight() -  taille , taille, taille)
			val Pattern pattern = Pattern.compile("_")
			val String[] items = pattern.split(decodeQRCodeBuffered(bim))

			val Copie cop = new Copie(Integer.parseInt(items.get(items.size - 2)), page,
				Integer.parseInt(items.get(items.size - 1)))

			// WARNING Possible racecondtion dans addCopie
			synchronized (sheets) {
				addCopie(cop)
			}
		}

	}

	/**
	 * @param qrCodeimage la bufferedImage a décoder
	 * @return le texte decode du QRCOde se trouvant dans qrCodeImage
	 * @throws IOException
	 * 
	 *                     Décode le contenu de qrCodeImage et affiche le contenu
	 *                     décodé dans le system.out
	 */
	def String decodeQRCodeBuffered(BufferedImage bufferedImage) throws IOException {
		val LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage)
		val BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source))

		val Map<DecodeHintType, Object> map = new HashMap()
		map.put(DecodeHintType.ALLOWED_EAN_EXTENSIONS, BarcodeFormat.QR_CODE)

		try {
			val MultiFormatReader mfr = new MultiFormatReader()
			mfr.setHints(map)
			val Result result = mfr.decodeWithState(bitmap)
			return result.getText()
		} catch (NotFoundException e) {
			System.out.println("There is no QR code in the image")
			return ""
		}
	}

	/**
	 * 
	 * @param nbCopies nombre de copies désirées
	 * @param docSujetMaitre document dans lequel insérer les Codes
	 *  
	 */
	def createThread(int nbPage, PDFRenderer pdfRenderer) {

		val PdfReaderThreadManager manager = new PdfReaderThreadManager(nbPage, pdfRenderer, this)
		manager.start
	}

	/**
	 * Dis si un examen est complet ou non
	 * @return true si l'examen est complet (contient toutes les pages de toutes les copies), false sinon
	 */
	def boolean isExamenComplete() {
		var boolean ret = true
		for (i : 0 ..< sheets.length) {
			ret = ret && sheets.get(i).isCopyComplete(nbPagesInSheet)
		}
		return ret && (nbSheetsTotal == sheets.length)
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
			sheets.get(i).addInSet(copie.pagesCopie)
		} else
			sheets.add(copie)
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

			for (j : 0 ..< temp.get(i).pagesCopie.length) {
				pagesArray.set(temp.get(i).pagesCopie.get(j).numPageInSubject,
					temp.get(i).pagesCopie.get(j).numPageInPDF)
			}
			res.add(dF.createStudentSheet(index, pagesArray))

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
			for (e : 0 ..< nbPagesInSheet) {
				pagesArray.set(e, -1)
			}

			for (j : 0 ..< temp.get(i).pagesCopie.length) {
				pagesArray.set(temp.get(i).pagesCopie.get(j).numPageInSubject,
					temp.get(i).pagesCopie.get(j).numPageInPDF)
			}
			res.add(dF.createStudentSheet(index, pagesArray))
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
		var int res = 0
		//TODO a revoir plus tard 
		synchronized(sheets) {
			for (i : 0 ..< sheets.length) {
				res += sheets.get(i).setPages.length
			}

		}
		return res
	}
	
	override isFinished(){
		return isFinished
	}
	
	def setFinished(boolean bool){
		this.isFinished = bool
	}

	def static void main(String[] arg) {
		val File pdf = new File("./src/main/resources/QRCode/pfo_example_Inserted.pdf")
		val PDDocument doc = PDDocument.load(pdf)
		val PdfReaderQrCodeImpl qrcodeReader = new PdfReaderQrCodeImpl(doc, 8, 32)

		qrcodeReader.readPDf
		println("le threads principal continue")

		while (qrcodeReader.getNbPagesTreated != qrcodeReader.getNbPagesPdf) {
		}
	}

}
