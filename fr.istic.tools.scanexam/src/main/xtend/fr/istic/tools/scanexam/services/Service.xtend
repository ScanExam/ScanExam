package fr.istic.tools.scanexam.services


import fr.istic.tools.scanexam.core.QuestionZone
import org.apache.pdfbox.pdmodel.PDDocument
import org.eclipse.xtend.lib.annotations.Accessors


abstract class Service 
{
	/**
	 * Pdf chargé
	 */
	@Accessors PDDocument document
	
	/**
	 * Index de la page courante
	 */
	int pageIndex
	
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
		return document.getPage(pageIndex).contents
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
	
	def void open(String xmiFile);


}
