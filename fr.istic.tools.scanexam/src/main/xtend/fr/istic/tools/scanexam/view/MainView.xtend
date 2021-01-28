package fr.istic.tools.scanexam.view

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.utils.ResourcesUtils
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import java.awt.EventQueue

/** 
 * Classe pour lancer directement la vue de la création d'examen
 * @author Julien Cochet, Stefan Locke
 */
class MainView {

	// ----------------------------------------------------------------------------------------------------
	/** 
	 * MAIN
	 */
	// ----------------------------------------------------------------------------------------------------
	
	static boolean SWING = false
	
	/** 
	 * Lancement de l'application
	 */
	def static void main(String[] args) {
		LanguageManager.init()
		if (SWING) {
			lunchSwing()
		} else {
			lunchFX(args)
		}
	}
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * SWING
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/** 
	 * Lancement de l'application Swing
	 */
	def static void lunchSwing() {
		EventQueue.invokeLater([
			try {
				var SwingView window = new SwingView()
				window.getWindow().setVisible(true)
			} catch (Exception e) {
				e.printStackTrace()
			}
		])
	}
	
	// ----------------------------------------------------------------------------------------------------
	/** 
	 * FX
	 */
	// ----------------------------------------------------------------------------------------------------
	
	/** 
	 * Lancement de l'application FX
	 */
	def static void lunchFX(String[] args) {
		FXMain.fxMain(args)
	}
	
	/**
	 * Classe de lancement de l'application FX
	 */
	static class FXMain extends Application {
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

		def static void fxMain(String[] args) {
			launch(args);
		}
	}
	
}
