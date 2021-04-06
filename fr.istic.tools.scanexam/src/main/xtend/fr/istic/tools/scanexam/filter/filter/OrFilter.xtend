package fr.istic.tools.scanexam.filter.filter

import java.util.LinkedList
import java.util.List
import java.util.Objects
import java.util.function.Predicate

/**
 * Représente une disjonction de tous les prédicats associés à ce filtre
 * @author Théo Giraudet
 * @see BasicFilter
 * @see Predicate
 */
class OrFilter<T> implements OperatorFilter<T> {

	val List<Predicate<T>> filters;

	/**
	 * Construit une nouvelle disjonction de prédicats
	 */
	new() {
		filters = new LinkedList<Predicate<T>>();
	}

	/**
	 * Ajoute un nouveau prédicat à la disjonction
	 * @param filter un prédicat à ajouter
	 */
	override addFilter(Predicate<T> filter) {
		Objects.requireNonNull(filter)
		filters.add(filter)
	}
	
	/**
	 * @param t une valeur sur laquelle appliquer le prédicat
	 * @return true si l'évaluation de <i>t</i> par au moins l'un des prédicats du filtre renvoie vrai, false sinon
	 */
	override test(T t) {
		val bigOr = filters.reduce[acc, elem | acc.or(elem)]
		bigOr.test(t)
	}

}
