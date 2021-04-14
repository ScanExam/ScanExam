package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.core.Question

abstract class Service 
{

	
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
	
	def void onDocumentLoad(int pdfPageCount)
	{
		
	}


	def boolean hasExamLoaded() {
		ExamSingleton.instance !== null
	}
	
	/**
	 * Met à jour le nom de l'examen
	 * @param name Nouevau nom de l'examen
	 */
	def void setExamName(String name) {
		ExamSingleton.instance.name = name
	}
	
}
