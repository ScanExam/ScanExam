package fr.istic.tools.scanexam.view

import fr.istic.tools.scanexam.utils.ResourcesUtils
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

/** 
 * Classe pour lancer directement la vue en utilisant la librairie JavaFX
 * @author Stefan Locke
 */
class MainJavaFX extends Application {

	/** 
	 * Lancement de l'application FX
	 */
	def static void launchApp(String[] args) {
		launch(args);
	}
	
	def static void launchApp() {
		launch(null);
	}
	
	/**
	 * Classe de lancement de l'application FX
	 */
	override start(Stage primaryStage) throws Exception {
			println("started App");
			val loader = new FXMLLoader();
			val root = loader.load(ResourcesUtils.getInputStreamResource("/viewResources/Proto.fxml"));
			primaryStage.setTitle("Corrector GUI - ScanExam");
			primaryStage.setScene(new Scene(root, 1280, 720));
			primaryStage.setMinHeight(720);
			primaryStage.setMinWidth(720);
			primaryStage.show();
	}
}
