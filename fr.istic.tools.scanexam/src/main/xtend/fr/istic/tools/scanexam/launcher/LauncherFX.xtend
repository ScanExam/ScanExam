package fr.istic.tools.scanexam.launcher

import fr.istic.tools.scanexam.utils.ResourcesUtils
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import fr.istic.tools.scanexam.view.fX.EditorAdapterFX
import fr.istic.tools.scanexam.presenter.PresenterBindings
import javafx.scene.image.Image
import java.util.ResourceBundle
import java.util.Locale
import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.view.fX.ControllerFXEditor

/** 
 * Classe pour lancer directement la vue en utilisant la librairie JavaFX
 * @author Stefan Locke
 */
class LauncherFX extends Application implements Launcher {

	/** 
	 * Lancement de l'application FX
	 */
	def static void launchApp(String[] args) {
		launch(args);
	}
	
	
	/**
	 * Classe de lancement de l'application FX
	 */
	override start(Stage primaryStage) throws Exception {
			val loader = new FXMLLoader();
			loader.setResources(LanguageManager.currentBundle);
			val root = loader.load(ResourcesUtils.getInputStreamResource("/viewResources/EditorUI.fxml"));
			var controller = (loader.controller as ControllerFXEditor);
			controller.editorAdapterFX = edit;
			edit.controllerFXCreator =  controller;
			primaryStage.setTitle("Corrector GUI - ScanExam");
			primaryStage.setScene(new Scene(root, 1280, 720));
			primaryStage.setMinHeight(720);
			primaryStage.setMinWidth(720);
			primaryStage.icons.add(new Image(ResourcesUtils.getInputStreamResource("/logo.png")));
			primaryStage.show();
	}
	static EditorAdapterFX edit; 
	override launch() {
		edit = new EditorAdapterFX();
		PresenterBindings.linkEditorPresenter(edit);
		launchApp(null);
	}
	
}

