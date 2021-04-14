package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.core.Exam
import fr.istic.tools.scanexam.core.Question
import java.util.Collection
import java.util.Collections
import java.util.Optional
import fr.istic.tools.scanexam.core.Page

/**
 * A revoir ?
 */
final class ExamSingleton 
{
	public static Exam instance = null;
	
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
	static def Collection<Question> getQuestions(int pageId)
	{
		return Collections.unmodifiableCollection(instance.pages.get(pageId).questions);
	}
	
	/**
	 * @param absoluteQuestionId la position absolue d'une question dans l'Examen
	 * @return la Question associée à cette position si elle existe, Optional.empty sinon
	 */
	static def Optional<Question> getQuestionFromIndex(int absoluteQuestionId) {
		return Optional.ofNullable(
			instance.pages.flatMap[p | p.questions].indexed.findFirst[p | p.key == absoluteQuestionId]
		).map(q | q.value)
	}
	
	static def int getTemplatePageAmount(){
		instance.pages.size
	}

	static def Page getPage(int pageId)
	{
		return instance.pages.get(pageId);
	}
}
