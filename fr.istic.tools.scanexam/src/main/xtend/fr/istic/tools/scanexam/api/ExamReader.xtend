package fr.istic.tools.scanexam.api

import fr.istic.tools.scanexam.core.*;
import java.util.List

interface ExamReader {
	
	/**
	 * @return Identifiant de l'examen
	 * @author degas
	 */
	def int getId()
	
	/**@return Nom de l'examen
	 * @author degas
	 */
	def String getName()
	
	/** Permet de récupérer une Question
	 * @param index Index de la question
	 * @return Question Retourne une instance de Question
	 * @author degas
	 */
	def Question getQuestion(int index)
	
	/**  Rend la liste des Questions définies dans un Examen
	 * @return List<Question>
	 * @author degas
	 */
	def List<Question> getQuestions()
	
	/** Retourne la zone associée à une question
	 * @param index Index de la question
	 * @author degas
	 */
	def QuestionZone getQuestionZone(int index)
}