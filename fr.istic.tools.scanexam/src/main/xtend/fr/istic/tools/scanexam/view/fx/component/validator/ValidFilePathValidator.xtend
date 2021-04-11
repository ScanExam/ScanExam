package fr.istic.tools.scanexam.view.fx.component.validator

import java.io.File
import java.util.Optional

/**
 * Validateur pour vérifier que le chemin d'accès pointe bien vers un fichier existant
 */
class ValidFilePathValidator implements FormatValidator {

	val String suffix

	/**
	 * @param endWith le string par lequel le Path doit finir
	 */
	new(String suffix) {
		this.suffix = suffix
	}
	
	/**
	 * Construit un nouveau Validateur de chemin d'accès
	 */
	new() {
		this("")
	}

	override validate(String toValidate) {
		val file = new File(toValidate)
		if (!file.exists)
			return Optional.of("studentlist.info.fileNotExist")
		else if (!file.isFile || !toValidate.endsWith(suffix))
			return Optional.of("studentlist.info.fileNotValid")
		else
			return Optional.empty
	}

}
