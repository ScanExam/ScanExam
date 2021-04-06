package fr.istic.tools.scanexam.filter.filter

import java.util.List
import java.util.function.Predicate

/**
 * Représente un opérateur pour lier une liste de {@link BasicFilter BasicFilter&lt;T&gt;}
 * @author Théo Giraudet
 */
interface OperatorFilter<T> extends Predicate<T> {

	/** Ajoute un filtre à la liste des filtres sur lequel appliquer l'opérateur courant
	 * @param filter un filtre à ajouter à la liste
	 */
	def void addFilter(Predicate<T> filter)
	
	/** Ajoute une liste de filtre à la liste des filtres sur lequel appliquer l'opérateur courant
	 * @param filters une liste de filtre à ajouter à la liste
	 */
	def void addFilters(List<Predicate<T>> filters) {
		filters.forEach[filter | addFilter(filter)]
	}
	
}
