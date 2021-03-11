package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.core.QuestionZone
import java.io.File
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.rendering.ImageType
import org.apache.pdfbox.rendering.PDFRenderer
import org.eclipse.xtend.lib.annotations.Accessors
import fr.istic.tools.scanexam.core.Question

abstract class Service 
{
	/**
	 * Pdf chargé
	 */
	 @Accessors protected PDDocument document;

	/**
	 * Index de la page courante
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
	
	
	def getCurrentPdfPage()
	{
		pageToImage(document.pages.get(pageIndex));
	}
	
	def pageToImage(PDPage page)
	{
		val renderer = new PDFRenderer(document);
		val bufferedImage = renderer.renderImageWithDPI(pageIndex, 300, ImageType.RGB);
		bufferedImage
	}
	/**
	 * Change la page courante par la page la suivant si elle existe (ne change rien sinon)
	 */
	def nextPage() 
	{
		if (pageIndex + 1 < document.pages.size) 
		{
			pageIndex++
		}

	}
	/**
	 * Change la page courante par la page la précédent si elle existe (ne change rien sinon)
	 */
	def previousPage() 
	{
		 if (pageIndex > 0) 
		 {
		 	pageIndex--;
		 }
	}
	
	def Question getQuestion(int id)
	{
		currentPage.questions.findFirst[question | question.id == id]
	}
	def renameQuestion(int id,String name)
	{
		val question = getQuestion(id)
		question.name = name
	}
	def getQuestionAtPage(int pageIndex)
	{
		ExamSingleton.getPage(pageIndex).questions
	}
	def removeQuestion(int id)
	{
		currentPage.questions.remove(id);
	}
	
	def getTemplatePageAmount()
	{
		ExamSingleton.templatePageAmount
	}
	
	/**
	 * Change la page courante par la page du numéro envoyé en paramètre (ne change rien si la page n'existe pas)
	 * @param page Numéro de page où se rendre
	 */
	def goToPage(int page) {
		if(page >= 0 && page < document.pages.size) {
			pageIndex = page
		}
	}


	/**
	 * @return le nombre de page du PDF courant
	 */
	protected def getCurrentPage()
	{
		return ExamSingleton.getPage(pageIndex);
	}
	/** Retourne la zone associée à une question
	 * @param index Index de la question
	 * @author degas
	 */
	def QuestionZone getQuestionZone(int index)
	{
		return ExamSingleton.getQuestion(pageIndex,index).zone
	}
	
	
	def void save(String path)

	
	def int getPageNumber() {
		return document.pages.size
	}
	
	/**
	 * @return le numéro de la page courante dans le PDF courant
	 */
	def getCurrentPageNumber() {
		return pageIndex;
	}

}
