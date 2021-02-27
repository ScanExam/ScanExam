package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.core.QuestionZone
import java.awt.image.BufferedImage
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors

abstract class Service 
{
	/**
	 * Pdf chargé
	 */
	 @Accessors protected List<BufferedImage> pages
	
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
		pages.get(pageIndex)
	}
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
	
	def void open(String xmiFile)
	
	/**
	 * Change la page courante par la page la suivant si elle existe (ne change rien sinon)
	 */
	def void nextPage()
	
	/**
	 * Change la page courante par la page la précédent si elle existe (ne change rien sinon)
	 */
	def void previousPage()

	/**
	 * @return le nombre de page du PDF courant
	 */
	def int getPageNumber() {
		return pages.size
	}
	
	/**
	 * @return le numéro de la page courante dans le PDF courant
	 */
	def getCurrentPageNumber() {
		return pageIndex;
	}

}
