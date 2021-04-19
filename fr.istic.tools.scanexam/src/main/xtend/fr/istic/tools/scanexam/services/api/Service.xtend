package fr.istic.tools.scanexam.services.api

import fr.istic.tools.scanexam.core.Question
import java.util.List

/**
 * Interface de typage
 * @author Antoine Degas, Marius Lumbroso, Théo Giraudet
 */
interface Service {
	
	
	/**
	 * @return le nombre de pages de l'Examen
	 */
	def int getTemplatePageAmount()

	/**
	 * @return vrai si un modèle d'examen est chargé, false sinon
	 */
	def boolean hasExamLoaded()

	/**
	 * Met à jour le nom de l'examen
	 * @param name Nouevau nom de l'examen
	 */
	def void setExamName(String name)
	
	/**
	 * @return Identifiant de l'examen
	 * @author degas
	 */
	def int getExamId()

	/**@return Nom de l'examen
	 * @author degas
	 */
	def String getExamName()
	
	/**
	 * @param pageIndex l'ID d'une page
	 * @return la liste des Questions sur la page dont l'ID est <i>pageIndex</i> 
	 */
	def List<Question> getQuestionAtPage(int pageIndex)
	
}
