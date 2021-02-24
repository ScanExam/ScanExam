package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.core.Exam
import fr.istic.tools.scanexam.core.Question
import java.util.List
import java.util.Collections
import fr.istic.tools.scanexam.core.Page

/**
 * A revoir ?
 */
final class ExamSingleton 
{
	protected static Exam instance = null;
	
	 /** Permet de récupérer une Question
	 * @param index Index de la question
	 * @return Question Retourne une instance de Question
	 * @author degas
	 */
	static def Question getQuestion(int pageId,int questionid)
	{
		return instance.pages.get(pageId).questions.get(questionid);
	}
	
	/**  Rend la liste des Questions définies dans un Examen
	 * @return List<Question>
	 * @author degas
	 */
	static def List<Question> getQuestions(int pageId)
	{
		return Collections.unmodifiableList(instance.pages.get(pageId).questions);
	}
	
	static def Page getPage(int pageId)
	{
		return instance.pages.get(pageId);
	}
	
}
