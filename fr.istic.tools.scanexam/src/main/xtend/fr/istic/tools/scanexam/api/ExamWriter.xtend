package fr.istic.tools.scanexam.api

import fr.istic.tools.scanexam.api.ExamReader
import fr.istic.tools.scanexam.core.*;

interface ExamWriter extends ExamReader {
	
	/**
	 * Permet de lier une Question q à une zone du PDF définie par un Rectangle R
	 * @param q Une Question
	 * @param r Un Rectangle
	 * @author degas
	 */
	def void addQuestion(Question q, QuestionZone r)
	
	/**
	 * Supprime une question
	 * @param index Index de la question à supprimer
	 * 
	 * @author degas
	 */
	def void removeQuestion(int index)
	
}