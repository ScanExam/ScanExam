package fr.istic.tools.scanexam.launcher

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.presenter.PresenterBindings
import fr.istic.tools.scanexam.utils.ResourcesUtils
import fr.istic.tools.scanexam.view.fX.ControllerFXEditor
import fr.istic.tools.scanexam.view.fX.EditorAdapterFX
import fr.istic.tools.scanexam.view.fX.GraduationAdapterFX
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import fr.istic.tools.scanexam.view.fX.ControllerFXCorrector

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
			val editorLoader = new FXMLLoader();
			val graduatorLoader = new FXMLLoader();
			
			editorLoader.setResources(LanguageManager.currentBundle);
			graduatorLoader.setResources(LanguageManager.currentBundle);
			
			val editorRoot = editorLoader.load(ResourcesUtils.getInputStreamResource("viewResources/EditorUI.fxml"));
			val graduatorRoot = graduatorLoader.load(ResourcesUtils.getInputStreamResource("viewResources/CorrectorUI.fxml"));
			
			val controllerEditor = (editorLoader.controller as ControllerFXEditor);
			val controllerGraduator = (graduatorLoader.controller as ControllerFXCorrector);
			
		
			controllerEditor.editorAdapterFX = edit;
			edit.controllerFXCreator =  controllerEditor;
			
			controllerGraduator.adapterCorrection = grad;
			grad.controller =  controllerGraduator;
			
			
			val editorScene = new Scene(editorRoot, 1280, 720) ;
			val graduatorScene = new Scene(graduatorRoot, 1280, 720);
			
			controllerGraduator.init
			
			pStage = primaryStage;
			eScene = editorScene
			gScene = graduatorScene;
			
			primaryStage.setTitle("Corrector GUI - ScanExam");
			primaryStage.setScene(editorScene);
			primaryStage.setMinHeight(720);
			primaryStage.setMinWidth(720);
			primaryStage.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
			primaryStage.show();
	}
	
	static EditorAdapterFX edit; 
	static GraduationAdapterFX grad;
	static Stage pStage;
	static Scene eScene;
	static Scene gScene;
	
	override launch() {
		edit = new EditorAdapterFX();
		grad = new GraduationAdapterFX();
		PresenterBindings.linkEditorPresenter(edit);
		PresenterBindings.linkGraduationPresenter(grad);
		launchApp(null);
	}
	
	static def swapToEditor(){
		pStage.scene = eScene;
	}
	
	static def swapToGraduator(){
		pStage.scene = gScene;
	}
}

