package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.core.Question
import fr.istic.tools.scanexam.core.QuestionZone
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.rendering.ImageType
import org.apache.pdfbox.rendering.PDFRenderer
import org.eclipse.xtend.lib.annotations.Accessors
import fr.istic.tools.scanexam.core.Exam

abstract class Service 
{
	/**
	 * Pdf chargé
	 */
	 @Accessors protected PDDocument document;

	/**
	 * Index de la page courante du modèle d'exam
	 */
	private int pdfPageIndex
		/**
	 * Index de la page courante du modèle d'exam
	 */
	protected int pageIndex 
	/**
	 * @return Identifiant de l'examen
	 * @author degas
	 */
	def int getExamId()
	{
		return ExamSingleton.instance.id;
	}
	
	/**@return Nom de l'examen
	 * @author degas
	 */
	def String getExamName()
	{
		return ExamSingleton.instance.name;
	}
	/* PDF RELATED */
	
		/**
	 * Change la page courante par la page du numéro envoyé en paramètre (ne change rien si la page n'existe pas)
	 * @param page Numéro de page où se rendre
	 */
	def goToPdfPage(int page) {
		if(page >= 0 && page < document.pages.size) {
			pdfPageIndex = page
		}
	}
	def getCurrentPdfPage()
	{
		pageToImage(document.pages.get(pdfPageIndex));
	}
	def nextPdfPage() 
	{
		if (pdfPageIndex + 1 < document.pages.size) 
		{
			pdfPageIndex++
		}

	}
	
	
	/**
	 * Change la page courante par la page la précédent si elle existe (ne change rien sinon)
	 */
	def previousPdfPage() 
	{
		 if (pdfPageIndex > 0) 
		 {
		 	pdfPageIndex--;
		 }
	}
	
	def int currentPdfPageNumber(){
		return pdfPageIndex
	}
	
	
	def int getPdfsize() {
		return document.pages.size
	}
	
	def pageToImage(PDPage page)
	{
		val renderer = new PDFRenderer(document);
		val bufferedImage = renderer.renderImageWithDPI(pdfPageIndex, 300, ImageType.RGB);
		bufferedImage
	}	
	
	/**
	 * Index de la page courante du modèle d'exam
	 */
	/* ! PDF RELATED */
	
	/* EXAM RELATED */
	
	
	protected def getCurrentPage()
	{
		return ExamSingleton.getPage(pageIndex);
	}
	protected def nextPage()
	{
		if (pageIndex + 1 < ExamSingleton.instance.pages.length) 
		{
			pageIndex++
		}
	}
	def previousPage() 
	{
		 if (pageIndex > 0) 
		 {
		 	pageIndex--;
		 }
	}
	
	
	def Question getQuestion(int id)
	{
		for(page: ExamSingleton.instance.pages) {
			val question = page.questions.findFirst[question | question.id == id]
			if(question !== null)
				return question
		}
		return null
	}
	
	def getQuestionAtPage(int pageIndex)
	{
		ExamSingleton.getPage(pageIndex).questions
	}
	

	/** Retourne la zone associée à une question
	 * @param index Index de la question
	 * @author degas
	 */
	def QuestionZone getQuestionZone(int index)
	{
		return ExamSingleton.getQuestion(pageIndex,index).zone
	}

	
	/**
	 * @return le numéro de la page courante dans le PDF courant
	 */
	def getCurrentPageNumber() {
		return pageIndex;
	}
	
	/* ! EXAM RELATED */
	
}
