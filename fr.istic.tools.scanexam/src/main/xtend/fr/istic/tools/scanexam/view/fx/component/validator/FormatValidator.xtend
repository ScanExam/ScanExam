package fr.istic.tools.scanexam.view.fx.component.validator

import java.util.Optional

/**
 * Interface pour représenter un validateur de format de String
 * @author Théo Giraudet
 */
@FunctionalInterface
interface FormatValidator {
	
	/**
	 * @param un String dont le format doit être validé
	 * @return un String décrivant l'erreur si le format n'a pas pu être validé, None sinon
	 */
	def Optional<String> validate(String toValidate)
	
}