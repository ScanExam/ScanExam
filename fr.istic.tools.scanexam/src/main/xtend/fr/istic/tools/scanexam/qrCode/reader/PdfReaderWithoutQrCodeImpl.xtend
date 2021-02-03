package fr.istic.tools.scanexam.qrCode.reader

import java.io.File
import java.util.HashSet
import java.util.Set
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.PDFRenderer

class PdfReaderWithoutQrCodeImpl implements PdfReaderWithoutQrCode {
	Set<Copie> sheets
	int nbSheetsTotal
	int nbPagesInSheet
	String pathToPDF

	new(String pathToPDF, int nbPages, int nbCopies) {
		this.pathToPDF = pathToPDF
		this.nbPagesInSheet = nbPages
		this.nbSheetsTotal = nbCopies

		sheets = new HashSet<Copie>()
	}

	override readPDf() {
		val PDDocument doc = PDDocument.load(new File(pathToPDF))
		val PDFRenderer pdf = new PDFRenderer(doc)
		createThread(doc.numberOfPages, pdf)
	}

	/**
	 * 
	 * @param nbCopies nombre de copies désirées
	 * @param docSujetMaitre document dans lequel insérer les Codes
	 * @param outputFile Chemin d'acces du fichier //TODO 
	 * @param nbPages nombre de pages du sujet Maitre 
	 *  
	 */
	def createThread(int nbPage, PDFRenderer pdfRenderer) {

		val ExecutorService service = Executors.newFixedThreadPool(4)

		service.execute(new PdfReaderWithoutQrCodeThread(this, 0, (nbPage / 4), pdfRenderer))
		service.execute(new PdfReaderWithoutQrCodeThread(this, (nbPage / 4), (nbPage / 2), pdfRenderer))
		service.execute(new PdfReaderWithoutQrCodeThread(this, (nbPage / 2), 3 * (nbPage / 4), pdfRenderer))
		service.execute(new PdfReaderWithoutQrCodeThread(this, (3 * nbPage / 4), nbPage, pdfRenderer))

		service.shutdown()
		service.awaitTermination(5, TimeUnit.MINUTES);
	}

	def readPartition(PDFRenderer renderer, int i, int j) {
		for (page : i ..< j) {
			val Copie cop = new Copie(page / nbPagesInSheet, page, page % nbPagesInSheet)
			// WARNING race condition sur le get de la taille dans addCopie
			synchronized (sheets) {
				addCopie(cop)
			}

			println("Success page " + page)
		}
	}

	def addCopie(Copie copie) {
		var boolean trouve = false
		var int i = 0

		var len = sheets.length

		while (!trouve && i < len) {
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

	def boolean isExamenComplete() {
		var boolean ret = true
		for (i : 0 ..< sheets.length) {
			ret = ret && sheets.get(i).isCopyComplete(nbPagesInSheet)
		}
		return ret && (nbSheetsTotal == sheets.length)
	}

	def Set<Copie> getUncompleteCopies() {
		val Set<Copie> uncompleteCopies = new HashSet<Copie>()

		for (i : 0 ..< sheets.length) {
			if (!sheets.get(i).isCopyComplete(nbPagesInSheet))
				uncompleteCopies.add(sheets.get(i))
		}
		return uncompleteCopies
	}

	def Set<Copie> getCompleteCopies(){
		var Set<Copie> completeCopies = new HashSet<Copie>()
		
		completeCopies = sheets.stream
			.filter(copie|copie.isCopyComplete(nbPagesInSheet))
			.collect(Collectors.toSet)
		
		return completeCopies
		/*for(i : 0 ..< sheets.length){
			if(sheets.get(i).isCopyComplete(nbPagesInSheet))
				completeCopies.add(sheets.get(i))
		}*/
		
		//return completeCopies
	}

	def Copie getCopie(int numCopie) {
		for (i : 0 ..< sheets.length)
			if (sheets.get(i).numCopie == numCopie)
				return sheets.get(i)
	}

	
	def Set<Copie> getSheets() {
		return sheets
	}

	def static void main(String[] arg) {
		// cinq copies de deux pages
		val PdfReaderWithoutQrCodeImpl qrcodeReader = new PdfReaderWithoutQrCodeImpl("pfo_example_Inserted.pdf", 8, 8)

		qrcodeReader.readPDf
		// qrcodeReader.readQRCodeImage( pdfRenderer,0,document.numberOfPages)
		for (i : 0 ..< qrcodeReader.sheets.length)
			println(qrcodeReader.sheets.get(i).toString())

	}
	
	override getStudentSheets() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override getNbPagesPdf() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override getNbPagesTreated() {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

}
