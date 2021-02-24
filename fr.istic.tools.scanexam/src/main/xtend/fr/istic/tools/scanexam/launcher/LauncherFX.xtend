package fr.istic.tools.scanexam.launcher

import fr.istic.tools.scanexam.utils.ResourcesUtils
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import fr.istic.tools.scanexam.view.fX.EditorAdapterFX
import fr.istic.tools.scanexam.presenter.PresenterLinker

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
			println("started App");
			val loader = new FXMLLoader();
			val root = loader.load(ResourcesUtils.getInputStreamResource("/viewResources/Proto.fxml"));
			primaryStage.setTitle("Corrector GUI - ScanExam");
			primaryStage.setScene(new Scene(root, 1280, 720));
			primaryStage.setMinHeight(720);
			primaryStage.setMinWidth(720);
			primaryStage.show();
	}
	
	override launch() {
		val edit = new EditorAdapterFX();
		PresenterLinker.linkEditorPresenter(edit);
		launchApp(null);
	}
	
}

