package fr.istic.tools.scanexam.qrCode.reader

import fr.istic.tools.scanexam.api.DataFactory
import fr.istic.tools.scanexam.core.StudentSheet
import java.util.ArrayList
import java.util.HashSet
import java.util.List
import java.util.Set
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.stream.Collectors
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.PDFRenderer
import java.util.concurrent.CountDownLatch
import java.io.InputStream
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class PdfReaderWithoutQrCodeImpl implements PdfReaderWithoutQrCode {
	Set<Copie> sheets
	int nbSheetsTotal
	int nbPagesInSheet
	InputStream inStream
	int nbPagesInPdf;

	new(InputStream inStream, int nbPages, int nbCopies) {
		this.inStream = inStream
		this.nbPagesInSheet = nbPages
		this.nbSheetsTotal = nbCopies

		sheets = new HashSet<Copie>()
	}

	/**
	 * Lit le PDF spécifié
	 */
	override readPDf() {
		try {
			val PDDocument doc = PDDocument.load(inStream)
			this.nbPagesInPdf = doc.numberOfPages
			val PDFRenderer pdf = new PDFRenderer(doc)
			createThread(doc.numberOfPages, pdf)
			doc.close
		} catch (Exception e) {
			e.printStackTrace
			return false
		}
		return true
	}

	/**
	 * Méthode qui créer 4 threads pour lire le pdf
	 * @param pdfRenderer pdf en source de lecture
	 * @param nbPage nombre de pages du sujet Maitre 
	 *  
	 */
	def createThread(int nbPage, PDFRenderer pdfRenderer) {
		val ExecutorService service = Executors.newFixedThreadPool(4)
		val CountDownLatch latchThreads = new CountDownLatch(4);
		val CountDownLatch latchMain = new CountDownLatch(1);

		service.execute(new PdfReaderWithoutQrCodeThread(this, 0, (nbPage / 4), pdfRenderer, latchThreads, latchMain))
		service.execute(
			new PdfReaderWithoutQrCodeThread(this, (nbPage / 4), (nbPage / 2), pdfRenderer, latchThreads, latchMain))
		service.execute(
			new PdfReaderWithoutQrCodeThread(this, (nbPage / 2), 3 * (nbPage / 4), pdfRenderer, latchThreads,
				latchMain))
		service.execute(
			new PdfReaderWithoutQrCodeThread(this, (3 * nbPage / 4), nbPage, pdfRenderer, latchThreads, latchMain))

		latchMain.countDown()
		latchThreads.await()
		service.shutdown()

	}
	
	//TODO
	def verificationClosureThreads(ExecutorService service, CountDownLatch lThreads){
		if(lThreads.count == 0)
			service.shutdown()
	}


	val Lock lock = new ReentrantLock()
	/**
	 * Méthode qui lit une certaine partie d'un pdf
	 * @param renderer le pdf lu
	 * @param i la borne inférieure
	 * @param j la borne supérieure
	 */
	def readPartition(PDFRenderer renderer, int i, int j) {
		for (page : i ..< j) {
			val Copie cop = new Copie(page / nbPagesInSheet, page, page % nbPagesInSheet)
			
			lock.lock()
			addCopie(cop)
			lock.unlock()

			//println("Success page " + page)
		}
	}

	/**
	 * Ajoute une copie lu au tas de copies déjà lues. Si la copie existe déjà, on merge les pages
	 * @param sheet la copie à ajouter 
	 */
	def addCopie(Copie sheet) {
		var boolean trouve = false
		var int i = 0

		var len = sheets.length

		while (!trouve && i < len) {
			if (sheets.get(i).numCopie == sheet.numCopie)
				trouve = true
			i++
		} // while
		i--
		if (trouve) {
			sheets.get(i).addInSet(sheet.pagesCopie)
		} else
			sheets.add(sheet)
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
	 * Renvoie une collection de toutes les copies lues, complètes ou non
	 * @return une collection de toutes les copies lues
	 */
	def Set<Copie> getSheets() {
		return sheets
	}

	
		
	override getCompleteStudentSheets() {
		val Set<StudentSheet> res = new HashSet<StudentSheet>()
		var Set<Copie> temp = new HashSet<Copie>()
		val DataFactory dF = new DataFactory()

		temp = completeCopies

		for (i : 0 ..< temp.length) {
			val int index = temp.get(i).numCopie
			val List<Integer> pages = new ArrayList<Integer>()

			for (j : 0 ..< temp.get(i).pagesCopie.length) {
				pages.add(temp.get(i).pagesCopie.get(j).numPageInSubject, temp.get(i).pagesCopie.get(j).numPageInPDF)
			}

			res.add(dF.createStudentSheet(index, pages))
		}
		return res
		
	}
	
	override getUncompleteStudentSheets() {
		val Set<StudentSheet> res = new HashSet<StudentSheet>()
		var Set<Copie> temp = new HashSet<Copie>()
		val DataFactory dF = new DataFactory()

		temp = uncompleteCopies

		for (i : 0 ..< temp.length) {
			val int index = temp.get(i).numCopie
			val List<Integer> pages = new ArrayList<Integer>()

			for (j : 0 ..< temp.get(i).pagesCopie.length) {
				pages.add(temp.get(i).pagesCopie.get(j).numPageInSubject, temp.get(i).pagesCopie.get(j).numPageInPDF)
			}

			res.add(dF.createStudentSheet(index, pages))
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

		for (i : 0 ..< sheets.length) {
			res += sheets.get(i).setPages.length
		}
		return res
	}

	def static void main(String[] arg) {
		/*val File pdf = new File("pfo_example_Inserted.pdf")
		val PdfReaderWithoutQrCodeImpl qrcodeReader = new PdfReaderWithoutQrCodeImpl(pdf, 8, 10)

		qrcodeReader.readPDf
		for (i : 0 ..< qrcodeReader.sheets.length)
			println(qrcodeReader.sheets.get(i).toString())

		
		println("Nombre de pages  du doc : " + qrcodeReader.nbPagesPdf)
		println("Nombre de pages traitées : " + qrcodeReader.nbPagesTreated)

		println("Examen complet? : " + qrcodeReader.isExamenComplete)*/
	}

	
}
