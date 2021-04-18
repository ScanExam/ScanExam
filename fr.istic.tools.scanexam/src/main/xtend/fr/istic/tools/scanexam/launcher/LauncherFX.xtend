package fr.istic.tools.scanexam.launcher

import com.sun.javafx.css.StyleManager
import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.presenter.PresenterEdition
import fr.istic.tools.scanexam.presenter.PresenterGraduation
import fr.istic.tools.scanexam.services.api.ServiceEdition
import fr.istic.tools.scanexam.services.api.ServiceGraduation
import fr.istic.tools.scanexam.utils.ResourcesUtils
import fr.istic.tools.scanexam.view.fx.ControllerRoot
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage

/** 
 * Classe pour lancer directement la vue en utilisant la librairie JavaFX
 * @author Stefan Locke
 */
class LauncherFX extends Application implements Launcher {


	static ServiceEdition serviceEdition
	
	static ServiceGraduation serviceGraduation
	
	/** 
	 * Lancement de l'application FX
	 */
	def static void launchApp(ServiceEdition serviceEdition, ServiceGraduation serviceGraduation) {
		
		LauncherFX.serviceEdition = serviceEdition
		LauncherFX.serviceGraduation = serviceGraduation
		
		launch(null);
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
			
			
			val controllerEditor = (editorLoader.controller as ControllerFxEdition);
			val controllerGraduator = (graduatorLoader.controller as ControllerFxGraduation);
			var controllerRoot = (rootLoader.controller as ControllerRoot);
		
			
			
			controllerRoot.corrector = graduatorRoot
			controllerRoot.editor = editorRoot
			
			val rootScene = new Scene(mainRoot, 1280, 720);
			
			Application.userAgentStylesheet = Application.STYLESHEET_MODENA
			StyleManager.instance.addUserAgentStylesheet("viewResources/MyStyle.css")
			//rootScene.stylesheets.add("viewResources/MyStyle.css")
			
			
			controllerGraduator.presenter = new PresenterGraduation(serviceGraduation)
			controllerGraduator.init
			
			controllerEditor.presenter = new PresenterEdition(serviceEdition)
			controllerEditor.init
			
			controllerRoot.correctorController = controllerGraduator
			controllerRoot.editorController = controllerEditor
			controllerRoot.init
			primaryStage.setTitle("ScanExam");
			primaryStage.setScene(rootScene);
			primaryStage.setMinHeight(720);
			primaryStage.setMinWidth(720);
			primaryStage.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
			primaryStage.show();
	}
	
	override launch() {
		
	}

	
	
}

