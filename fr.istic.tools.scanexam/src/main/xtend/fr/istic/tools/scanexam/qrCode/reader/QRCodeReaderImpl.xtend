package fr.istic.tools.scanexam.qrCode.reader

import org.apache.pdfbox.rendering.PDFRenderer
import java.awt.image.BufferedImage
import java.io.IOException
import org.apache.pdfbox.rendering.ImageType
import com.google.zxing.Result
import com.google.zxing.MultiFormatReader
import com.google.zxing.DecodeHintType
import java.util.HashMap
import com.google.zxing.LuminanceSource
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.NotFoundException
import java.util.Map
import com.google.zxing.BinaryBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.HybridBinarizer
import org.apache.pdfbox.pdmodel.PDDocument
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern
import java.io.File
import java.util.Set
import java.util.HashSet

class QRCodeReaderImpl implements QRCodeReader {
	
	Set<Copie> sheets
	int nbSheetsTotal
	int nbPagesInSheet
	
	new(int nbPages, int nbCopies){
		this.nbPagesInSheet = nbPages
		this.nbSheetsTotal = nbCopies
		
		sheets = new HashSet<Copie>()
	}

	override readQRCodeImage(PDFRenderer pdfRenderer, int startPages, int endPages) throws IOException {
		for (page : startPages ..< endPages) {
			val BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB)
			// val int taille = (bim.getHeight() * 0.3f) as int
			// val BufferedImage dest = bim.getSubimage(0, bim.getHeight() -  taille , taille, taille)

			
			val Pattern pattern = Pattern.compile("_")
			val String[] items = pattern.split(decodeQRCodeBuffered(bim))
			
			val Copie cop = new Copie(Integer.parseInt(items.get(1)), Integer.parseInt(items.get(2)), page)
			addCopie(cop)
			println("Success page " + page)
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
	 * @param outputFile Chemin d'acces du fichier //TODO 
	 * @param nbPages nombre de pages du sujet Maitre 
	 *  
	 */
	def createThread(int nbCopie, PDDocument docSujetMaitre, String outputFile, int nbPage) {

		val ExecutorService service = Executors.newFixedThreadPool(4)

		/*service.execute(new QRThread(this, 0, (nbCopie / 4), docSujetMaitre, outputFile, 1, nbPage))
		service.execute(new QRThread(this, (nbCopie / 4), (nbCopie / 2), docSujetMaitre, outputFile, 2, nbPage))
		service.execute(new QRThread(this, (nbCopie / 2), 3 * (nbCopie / 4), docSujetMaitre, outputFile, 3, nbPage))
		service.execute(new QRThread(this, (3 * nbCopie / 4), nbCopie, docSujetMaitre, outputFile, 4, nbPage))
*/
		service.shutdown()
		service.awaitTermination(1, TimeUnit.MINUTES);
	}


	def boolean isExamenComplete(){
		var boolean ret = true
		for(i : 0 ..< sheets.length){
			ret = ret && sheets.get(i).isCopyComplete(nbPagesInSheet)
		}
		return ret && (nbSheetsTotal == sheets.length)
	}
	
	def Set<Copie> getUncompleteCopies(){
		val Set<Copie> uncompleteCopies = new HashSet<Copie>()
		
		for(i : 0 ..< sheets.length){
			if(!sheets.get(i).isCopyComplete(nbPagesInSheet))
				uncompleteCopies.add(sheets.get(i))
		}
		return uncompleteCopies
	}
	
	def Set<Copie> getCompleteCopies(){
		val Set<Copie> completeCopies = new HashSet<Copie>()
		
		for(i : 0 ..< sheets.length){
			if(sheets.get(i).isCopyComplete(nbPagesInSheet))
				completeCopies.add(sheets.get(i))
		}
		return completeCopies
	}
	
	def Copie getCopie(int numCopie){
		for(i : 0 ..< sheets.length)
			if(sheets.get(i).numCopie == numCopie)
				return sheets.get(i)
	}
	
	def addCopie(Copie copie){
		var boolean trouve = false
		var int i = 0
		
		while(!trouve && i < sheets.length){
			if(sheets.get(i).numCopie == copie.numCopie)
				trouve = true
			i++
		}//while
		
		i--
		if(trouve){
			sheets.get(i).addInSet(copie.pagesCopie)
		}
		else
			sheets.add(copie)
	}
		
	def Set<Copie> getSheets(){
		return sheets
	}

	def static void main(String[] arg) {
		//cinq copies de deux pages
		val QRCodeReaderImpl qrcodeReader = new QRCodeReaderImpl(2,20)		
		
		val PDDocument document = PDDocument.load(new File("TDIA_Inserted.pdf"));
		val PDFRenderer pdfRenderer = new PDFRenderer(document);
		
		
		qrcodeReader.readQRCodeImage( pdfRenderer,0,document.numberOfPages)
		
		
		for(i:0 ..<qrcodeReader.sheets.length)
			println(qrcodeReader.sheets.get(i).toString())
			
		println(qrcodeReader.isExamenComplete())
		
	}
}
