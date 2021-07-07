package fr.istic.tools.scanexam.launcher

import com.sun.javafx.css.StyleManager
import fr.istic.tools.scanexam.config.LanguageManager
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
import fr.istic.tools.scanexam.view.fx.students.ControllerFxStudents

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
			val editionLoader = new FXMLLoader();
			val graduationLoader = new FXMLLoader();
			val rootLoader = new FXMLLoader();
			val studentsLoader = new FXMLLoader()
			
			editionLoader.setResources(LanguageManager.currentBundle);
			graduationLoader.setResources(LanguageManager.currentBundle);
			rootLoader.setResources(LanguageManager.currentBundle);
			studentsLoader.resources = LanguageManager.currentBundle
			
			val mainRoot = rootLoader.load(ResourcesUtils.getInputStreamResource("viewResources/RootUI.fxml"));
			val editionRoot = editionLoader.load(ResourcesUtils.getInputStreamResource("viewResources/EditorUI.fxml"));
			val graduationRoot = graduationLoader.load(ResourcesUtils.getInputStreamResource("viewResources/CorrectorUI.fxml"));
			val studentsRoot = studentsLoader.load(ResourcesUtils.getInputStreamResource("viewResources/StudentsUI.fxml"))
			
			val controllerEdition = (editionLoader.controller as ControllerFxEdition);
			val controllerGraduation = (graduationLoader.controller as ControllerFxGraduation);
			var controllerRoot = (rootLoader.controller as ControllerRoot);
			val controllerStudents = (studentsLoader.controller as ControllerFxStudents)
		
			
			
			controllerRoot.setEditorNode(editionRoot)
			controllerRoot.setGraduationNode(graduationRoot)
			controllerRoot.studentsNode = studentsRoot
			
			val rootScene = new Scene(mainRoot, 1280, 720);
			
			Application.userAgentStylesheet = Application.STYLESHEET_MODENA
			StyleManager.instance.addUserAgentStylesheet("viewResources/MyStyle.css")
			//rootScene.stylesheets.add("viewResources/MyStyle.css")
			
			
			controllerStudents.init(serviceGraduation)
			controllerGraduation.init(serviceGraduation, controllerStudents)
			controllerEdition.init(serviceEdition)
			
			controllerRoot.graduationController = controllerGraduation
			controllerRoot.editionController = controllerEdition
			controllerRoot.init()
			controllerRoot.init(serviceEdition,serviceGraduation)
			primaryStage.setTitle("ScanExam");
			primaryStage.setScene(rootScene);
			primaryStage.setMinHeight(720);
			primaryStage.setMinWidth(720);
			primaryStage.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));
			primaryStage.show();
			
			controllerGraduation.setKeybinds
	}
	
	override launch() {
		
	}

	
	
}

