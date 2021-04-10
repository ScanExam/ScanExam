package fr.istic.tools.scanexam.view.fx.component.validator

import java.io.File
import java.util.Optional

/**
 * Validateur pour vérifier que le chemin d'accès pointe bien vers un fichier existant
 */
class ValidFilePathValidator implements FormatValidator {

	override validate(String toValidate) {
		val file = new File(toValidate)
		if (!file.exists)
			return Optional.of("studentlist.info.fileNotExist")
		else if (!file.isFile)
			return Optional.of("studentlist.info.fileNotValid")
		else
			return Optional.empty
	}

}
