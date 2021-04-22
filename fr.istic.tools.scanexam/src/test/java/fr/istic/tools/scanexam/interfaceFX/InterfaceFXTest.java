package fr.istic.tools.scanexam.interfaceFX;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Locale;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import com.sun.javafx.css.StyleManager;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.services.ServiceImpl;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fx.ControllerRoot;
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class InterfaceFXTest {

	/**
	 * EMPLACEMENT DES BOUTONS
	 * package fr.istic.tools.scanexam.view.fx.editor; -> ControllerFxEdition
	 * package fr.istic.tools.scanexam.view.fx.graduation -> ControllerFxGraduation
	 * 
	 * Fichier fxml : src/main/resources/viewResources
	 * ConfigUI
	 */
	
	
	/**
	 * Lancement de l'interface FX
	 * @throws IOException 
	 */
	@Start
	public void start(Stage stage) throws IOException {
		LanguageManager.init(Locale.FRENCH);
		final FXMLLoader editionLoader = new FXMLLoader();
		final FXMLLoader graduationLoader = new FXMLLoader();
		final FXMLLoader rootLoader = new FXMLLoader();
		
		editionLoader.setResources(LanguageManager.getCurrentBundle());
		graduationLoader.setResources(LanguageManager.getCurrentBundle());
		rootLoader.setResources(LanguageManager.getCurrentBundle());
		
		final Parent mainRoot = rootLoader.load(ResourcesUtils.getInputStreamResource("viewResources/RootUI.fxml"));
		final Node editionRoot = editionLoader.load(ResourcesUtils.getInputStreamResource("viewResources/EditorUI.fxml"));
		final Node graduationRoot = graduationLoader.load(ResourcesUtils.getInputStreamResource("viewResources/CorrectorUI.fxml"));
		
		
		final ControllerFxEdition controllerEdition = (ControllerFxEdition) editionLoader.getController();
		final ControllerFxGraduation controllerGraduation = (ControllerFxGraduation) graduationLoader.getController();
		final ControllerRoot controllerRoot = (ControllerRoot) rootLoader.getController();
	
		final ServiceImpl service = new ServiceImpl();
		
		controllerRoot.setEditorNode(editionRoot);
		controllerRoot.setGraduationNode(graduationRoot);
		
		final Scene rootScene = new Scene(mainRoot, 1280, 720);
		
		Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
		StyleManager.getInstance().addUserAgentStylesheet("viewResources/MyStyle.css");
		
		controllerGraduation.init(service);
		
		
		controllerEdition.init(service);
		
		controllerRoot.setGraduationController(controllerGraduation);
		controllerRoot.setEditionController(controllerEdition);
		controllerRoot.init(service, service);
		stage.setTitle("ScanExam");
		stage.setScene(rootScene);
		stage.setMinHeight(720);
		stage.setMinWidth(720);
		stage.getIcons().add(new Image(ResourcesUtils.getInputStreamResource("logo.png")));

		stage.show();
		
	}
	
	@DisplayName("Premier Test : Clique Simple")
	@Test
    public void cliqueSimple(FxRobot robot) {
		robot.clickOn("#nextPageButton");
        assertTrue(true);
    }


}
