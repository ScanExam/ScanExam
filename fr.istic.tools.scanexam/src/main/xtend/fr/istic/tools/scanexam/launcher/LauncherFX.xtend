package fr.istic.tools.scanexam.launcher

import com.sun.javafx.css.StyleManager
import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.presenter.PresenterBindings
import fr.istic.tools.scanexam.utils.ResourcesUtils
import fr.istic.tools.scanexam.view.fx.ControllerRoot
import fr.istic.tools.scanexam.view.fx.EditorAdapterFX
import fr.istic.tools.scanexam.view.fx.GraduationAdapterFX
import fr.istic.tools.scanexam.view.fx.corrector.ControllerFXCorrector
import fr.istic.tools.scanexam.view.fx.editor.ControllerFXEditor
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import javafx.fxml.FXMLLoader

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
			val rootLoader = new FXMLLoader();
			
			editorLoader.setResources(LanguageManager.currentBundle);
			graduatorLoader.setResources(LanguageManager.currentBundle);
			rootLoader.setResources(LanguageManager.currentBundle);
			
			val mainRoot = rootLoader.load(ResourcesUtils.getInputStreamResource("viewResources/RootUI.FXML"));
			val editorRoot = editorLoader.load(ResourcesUtils.getInputStreamResource("viewResources/EditorUI.FXML"));
			val graduatorRoot = graduatorLoader.load(ResourcesUtils.getInputStreamResource("viewResources/CorrectorUI.FXML"));
			
			
			val controllerEditor = (editorLoader.controller as ControllerFXEditor);
			val controllerGraduator = (graduatorLoader.controller as ControllerFXCorrector);
			var controllerRoot = (rootLoader.controller as ControllerRoot);
		
			controllerEditor.editorAdapterFX = edit;
			edit.controllerFXCreator =  controllerEditor;
			
			controllerGraduator.adapterCorrection = grad;
			grad.controller =  controllerGraduator;
			
			controllerRoot.corrector = graduatorRoot
			controllerRoot.editor = editorRoot
			
			val rootScene = new Scene(mainRoot, 1280, 720);
			
			Application.userAgentStylesheet = Application.STYLESHEET_MODENA
			StyleManager.instance.addUserAgentStylesheet("viewResources/MyStyle.css")
			//rootScene.stylesheets.add("viewResources/MyStyle.css")
			
			
			
			controllerGraduator.init
			controllerEditor.init
			controllerRoot.correctorController = controllerGraduator
			controllerRoot.editorController = controllerEditor
		
			primaryStage.setTitle("ScanExam");
			primaryStage.setScene(rootScene);
			primaryStage.setMinHeight(720);
			primaryStage.setMinWidth(720);
			primaryStage.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
			primaryStage.show();
	}
	
	static EditorAdapterFX edit; 
	static GraduationAdapterFX grad;
	
	override launch() {
		edit = new EditorAdapterFX();
		grad = new GraduationAdapterFX();
		PresenterBindings.linkEditorPresenter(edit);
		PresenterBindings.linkGraduationPresenter(grad);
		launchApp(null);
	}
	
	static def swapToEditor(){
		
	}
	
	static def swapToGraduator(){
	
	}
}

