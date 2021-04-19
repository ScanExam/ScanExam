package fr.istic.tools.scanexam.view.fx

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.utils.ResourcesUtils
import java.net.URL
import java.util.ResourceBundle
import javafx.beans.binding.Bindings
import javafx.beans.property.DoubleProperty
import javafx.beans.property.ReadOnlyDoubleProperty
import javafx.beans.property.ReadOnlyStringProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Cursor
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.scene.image.Image
import javafx.scene.layout.VBox
import javafx.stage.Modality
import javafx.stage.Stage

/**
 * Le contrôleur de la vue affichant la progression d'une tâche de fond.
 * @author Théo Giraudet
 */
class ControllerWaiting implements Initializable {
	
	/* Le composant racine de la vue */
	@FXML
	var VBox root
	
	/* La barre de progression de la tâche */
	@FXML
	var ProgressBar progressBar
	
	/* Le label affichant la valeur en pourcentage de la progression de la tâche */
	@FXML
	var Label percentLabel
	
	/* Le label affichant où en est la tâche sous forme de message de progression */
	@FXML
	var Label progressLabel
	
	/*  la propriété de valeur courante de la progression */
	var DoubleProperty currentProgress
	

	/**
	 * Initialise l'état de la fenêtre
	 * @InheritDoc
	 */
	override initialize(URL location, ResourceBundle resources) {
		currentProgress = new SimpleDoubleProperty(this, "progressCurrent", 0d)
		
		// Binding
		progressBar.progress = 0d
		progressBar.progressProperty.bind(currentProgress)
		val percentProperty = Bindings.createLongBinding([| Math.round(currentProgress.get * 100)], currentProgress).asString
		percentLabel.textProperty.bind(percentProperty.concat("%"))
		currentProgress.addListener(obs, oldVal, newVal | Math.abs(1 - newVal as Double) < 0.0001 ? (root.getScene.getWindow as Stage).close)
	}
	
	/**
	 * Affiche une fenêtre de progression de la tâche
	 * @param progressTextProperty la propriété du string à afficher pour message de progression sur l'UI 
	 * @param progressProperty une propriété contenant une valeur sur [0, 1] représentant la progression de la tâche
	 */
	def static void openWaitingDialog(ReadOnlyStringProperty progressTextProperty, ReadOnlyDoubleProperty progressProperty, Stage parentStage) {
		val FXMLLoader loader = new FXMLLoader
		loader.setResources(LanguageManager.currentBundle)
		val Parent view = loader.load(ResourcesUtils.getInputStreamResource("viewResources/WaitingUI.FXML"))
		val Stage dialog = new Stage
		val controller = loader.<ControllerWaiting>controller
		val scene = new Scene(view, 289, 103)
		controller.currentProgress.bind(progressProperty)
		controller.progressLabel.textProperty.bind(progressTextProperty)
		dialog.setTitle(LanguageManager.translate("waitingUi.taskInProgress"))
		dialog.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
		dialog.setScene(scene)
		dialog.setResizable(false);
		dialog.initModality(Modality.WINDOW_MODAL)
		dialog.initOwner(parentStage)
		dialog.show
		scene.cursor = Cursor.WAIT
	}	
}