package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.config.LanguageManager
import java.util.Objects
import java.util.Optional
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import javafx.stage.Window
import org.apache.logging.log4j.LogManager
import java.io.File

class FileLoaderDisplayer {
	
	static val logger = LogManager.logger
	
	/**
	 * Affiche un sélectionneur de fichier
	 * @param format l'extension du fichier à ouvrir (non null)
	 * @param formatDes la description du format (non null)
	 * @param startPath le répertoire initial du loader (non null)
	 * @param root la fenêtre racine du loader (non null)
	 */
	def Optional<String> loadFile(String format, String formatDes, File startPath, Window root) {
		Objects.requireNonNull(format)
		Objects.requireNonNull(formatDes)
		Objects.requireNonNull(startPath)
		Objects.requireNonNull(root)
		var fileChooser = new FileChooser
		fileChooser.extensionFilters.add(new ExtensionFilter(LanguageManager.translate(formatDes), format))
		fileChooser.initialDirectory = startPath
		var file = fileChooser.showOpenDialog(root)
		if (file !== null)
			Optional.of(file.path)
		else {
			logger.warn("File not chosen")
			Optional.empty
		}
	}	
	
}