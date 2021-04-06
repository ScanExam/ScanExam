package fr.istic.tools.scanexam.filter.filter

import java.util.List
import java.util.function.Predicate
import fr.istic.tools.scanexam.filter.param.FilterParam

/**
 * Représente un filtre appliquable sur T<br/>
 * Peut être considéré comme un {@link Predicate Predicater&lt;T&gt;}
 * @author Théo Giraudet
 * @see Predicate
 */
interface BasicFilter<T> extends Predicate<T> {
	
	/**
	 * @return la liste des paramètres que prend le filtre courant
	 * @see FilterParam
	 */
	def List<FilterParam<?>> getParams()
	
}
