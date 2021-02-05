package fr.istic.tools.scanexam.sessions

import fr.istic.tools.scanexam.core.Exam
import fr.istic.tools.scanexam.core.Page
import fr.istic.tools.scanexam.core.Question
import fr.istic.tools.scanexam.core.QuestionZone
import java.util.Collections
import java.util.List
import org.apache.pdfbox.pdmodel.PDDocument
import org.eclipse.xtend.lib.annotations.Accessors

abstract class Session 
{
	@Accessors PDDocument document
	protected Exam exam
	
	private int pageIndex
	/**
	 * @return Identifiant de l'examen
	 * @author degas
	 */
	def int getId()
	{
		return exam.id
	}
	
	/**@return Nom de l'examen
	 * @author degas
	 */
	def String getName()
	{
		return exam.name;
	}
	
	/** Permet de récupérer une Question
	 * @param index Index de la question
	 * @return Question Retourne une instance de Question
	 * @author degas
	 */
	def Question getQuestion(int index)
	{
		return exam.pages.get(pageIndex).questions.get(index);
	}
	
	/**  Rend la liste des Questions définies dans un Examen
	 * @return List<Question>
	 * @author degas
	 */
	def List<Question> getQuestions()
	{
		return Collections.unmodifiableList(exam.pages.get(pageIndex).questions);
	}
	
	def Page getPage()
	{
		return exam.pages.get(pageIndex);
	}
	
	/** Retourne la zone associée à une question
	 * @param index Index de la question
	 * @author degas
	 */
	def QuestionZone getQuestionZone(int index)
	{
		return getQuestion(index).zone
	}
	
	
}