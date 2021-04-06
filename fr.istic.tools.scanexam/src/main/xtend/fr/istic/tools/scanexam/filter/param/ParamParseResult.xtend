package fr.istic.tools.scanexam.filter.param

import java.util.NoSuchElementException
import java.util.Objects

/**
 * Représente la réussite ou non du parse d'une valeur par un {@link FilterParam}
 * @author Théo Giraudet
 * @see FilterParam 
 */
package class ParamParseResult {
	
	/**
	 * @param message le message décrivant la raison de l'échec du parse (non null)
	 * @return un {@link ParamParseResult} représentant l'échec du parse
	 */
	static def ParamParseResult failed(String message) {
		Objects.requireNonNull(message)
		return new ParamParseResult(message)
	}
	
	/**
	 * @return un {@link ParamParseResult} représentant la réussite du parse
	 */
	static def ParamParseResult succeed() {
		return new ParamParseResult(null)
	}

	val String message

	private new(String message) {
		this.message = message
	}

	/**
	 * @return true si le {@link ParamParseResult} courant représente une réussite du parse, false sinon
	 */
	def boolean succeeded() {
		message !== null
	}

	/**
	 * @return le message décrivant la raison de l'échec si le {@link ParamParseResult} courant représente un échec du parse
	 * @throw NoSuchElementException si le {@link ParamParseResult} courant représente une réussite du parse
	 */
	def String getFailureMessage() {
		if(message === null)
			throw new NoSuchElementException("No message present");
		return message;
	}

}
