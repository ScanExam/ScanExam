package fr.istic.tools.scanexam.view.fx.utils

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.utils.ResourcesUtils
import java.util.Objects
import javafx.scene.control.Alert
import javafx.scene.control.Alert.AlertType
import javafx.scene.image.Image
import javafx.stage.Stage
import javax.annotation.Nullable

class DialogMessageSender {

	
	/**
	 * Affiche un Dialog avec les informations suivantes :
	 * @param type le type de l'alerte (non null)
	 * @param title le titre le l'alerte (non null)
	 * @param headerText le header de l'alerte (non null)
	 * @param content le contenu de l'alerte
	 */
	def static void sendDialog(AlertType type, String title, String headerText, @Nullable String content) {
		Objects.requireNonNull(type)
		Objects.requireNonNull(title)
		Objects.requireNonNull(headerText)
		
		val alert = new Alert(type)
		val stage = alert.getDialogPane().getScene().getWindow() as Stage
		stage.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")))
		alert.setTitle = LanguageManager.translate(title)
		alert.setHeaderText = LanguageManager.translate(headerText)
		if(content !== null)
			alert.contentText = LanguageManager.translate(content)
		alert.showAndWait
	}
}