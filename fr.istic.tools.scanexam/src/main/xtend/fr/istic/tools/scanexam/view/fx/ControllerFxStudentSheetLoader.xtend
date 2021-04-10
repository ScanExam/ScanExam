package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.presenter.PresenterStudentSheetLoader
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.RadioButton
import javafx.scene.control.TextField
import javafx.scene.layout.HBox

class ControllerFxStudentSheetLoader {
	
	/* RadioButton de l'option "Utiliser le modèle chargé" */
	@FXML
	var RadioButton rbUseLoaded
	
	/* RadioButton de l'option "Charger un nouveau modèle" */
	@FXML
	var RadioButton rbLoadModel
	
	/* HBox avec les nœuds à activer si deuxième RadioButton sélectionné */
	@FXML
	var HBox hBoxLoad
	
	/* TextField de la saisie du path pour le nouveau modèle */
	@FXML
	var TextField txtFldFile
	
	/* Button de chargement du modèle */
	@FXML
	var Button btnBrowse
	
	/* Button de validation du formulaire */
	@FXML
	var Button btnOk
	
	
	var PresenterStudentSheetLoader presStudentListLoader;
	
	/**
	 * Initialise le composant avec le presenter composé en paramètre
	 * @param loader le presenter
	 */
	def initialize(PresenterStudentSheetLoader loader) {
		presStudentListLoader = loader
		hBoxLoad.disableProperty.bind(rbLoadModel.selectedProperty.not)
	}
	
	@FXML
	def quit() {
		
	}
	
	@FXML
	def saveAndQuit() {
		
	}
	
}