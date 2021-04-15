package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.core.Question

/**
 * Façade abstraite des opérations communes des services
 * @author Antoine Degas, Marius Lumbroso, Théo Giraudet
 */
abstract class Service {

	/**
	 * @return Identifiant de l'examen
	 * @author degas
	 */
	def int getExamId() {
		return ExamSingleton.instance.id;
	}

	/**@return Nom de l'examen
	 * @author degas
	 */
	def String getExamName() {
		return ExamSingleton.instance.name;
	}

	/**
	 * @param l'ID de la Question
	 * @return la Question du modèle correspondant à l'ID spécifié
	 */
	def Question getQuestion(int id) {
		for (page : ExamSingleton.instance.pages) {
			val question = page.questions.findFirst[question|question.id == id]
			if (question !== null)
				return question
		}
		return null
	}

	/**
	 * @param pageIndex l'ID d'une page
	 * @return la liste des Questions sur la page dont l'ID est <i>pageIndex</i> 
	 */
	def getQuestionAtPage(int pageIndex) {
		ExamSingleton.getPage(pageIndex).questions
	}

	/**
	 * Crée un nouveau modèle côté données
	 * @param pageNumber le nombre de pages du modèle
	 */
	def void onDocumentLoad(int pdfPageCount) {}

	/**
	 * @return vrai si un modèle d'examen est chargé, false sinon
	 */
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
