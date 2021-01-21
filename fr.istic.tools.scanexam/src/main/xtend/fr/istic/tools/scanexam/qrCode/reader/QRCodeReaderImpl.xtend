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

class QRCodeReaderImpl implements QRCodeReader {

	override readQRCodeImage(PDFRenderer pdfRenderer, int startPages, int endPages) throws IOException {
		for (page : startPages ..< endPages) {
			val BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB)
			// val int taille = (bim.getHeight() * 0.3f) as int
			// val BufferedImage dest = bim.getSubimage(0, bim.getHeight() -  taille , taille, taille)
			System.out.println(QRCodeReaderImpl.decodeQRCodeBuffered(bim))
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
	static def String decodeQRCodeBuffered(BufferedImage bufferedImage) throws IOException {
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



	def static void main(String[] arg) {
	}
}
