package fr.istic.tools.scanexam.filter.filter

import fr.istic.tools.scanexam.filter.param.FilterParam
import java.util.Collection
import java.util.List
import java.util.Set
import java.util.function.Predicate

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
	
	/**
	 * @return l'ensemble des enfants du filtre courant.<br/>
	 * Un enfant est un BasicFilter dont l'état dépend du filtre courant ou devant exister si ce filtre existe.<br/>
	 * Un filtre peut ne pas avoir d'enfant, dans ce cas, le retour est vide
	 */
	def Collection<BasicFilter<?>> getChildren() {
		Set.of()
	}
	
	/**
	 * @param l'ensemble des enfants du filtre courant 
	 * Un enfant est un BasicFilter dont l'état dépend du filtre courant ou devant exister si ce filtre existe.<br/>
	 */
	def void setChildren(Collection<BasicFilter<?>> children) {}
}
