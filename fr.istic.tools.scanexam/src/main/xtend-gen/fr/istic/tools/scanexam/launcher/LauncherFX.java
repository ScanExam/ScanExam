package fr.istic.tools.scanexam.launcher;

import com.sun.javafx.css.StyleManager;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.launcher.Launcher;
import fr.istic.tools.scanexam.services.api.ServiceEdition;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fx.ControllerRoot;
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import fr.istic.tools.scanexam.view.fx.students.ControllerFxStudents;
import java.io.InputStream;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Classe pour lancer directement la vue en utilisant la librairie JavaFX
 * @author Stefan Locke
 */
@SuppressWarnings("all")
public class LauncherFX extends Application implements Launcher {
  private static ServiceEdition serviceEdition;
  
  private static ServiceGraduation serviceGraduation;
  
  /**
   * Lancement de l'application FX
   */
  public static void launchApp(final ServiceEdition serviceEdition, final ServiceGraduation serviceGraduation) {
    LauncherFX.serviceEdition = serviceEdition;
    LauncherFX.serviceGraduation = serviceGraduation;
    Application.launch(null);
  }
  
  /**
   * Classe de lancement de l'application FX
   */
  @Override
  public void start(final Stage primaryStage) throws Exception {
    final FXMLLoader editionLoader = new FXMLLoader();
    final FXMLLoader graduationLoader = new FXMLLoader();
    final FXMLLoader rootLoader = new FXMLLoader();
    final FXMLLoader studentsLoader = new FXMLLoader();
    editionLoader.setResources(LanguageManager.getCurrentBundle());
    graduationLoader.setResources(LanguageManager.getCurrentBundle());
    rootLoader.setResources(LanguageManager.getCurrentBundle());
    studentsLoader.setResources(LanguageManager.getCurrentBundle());
    final Parent mainRoot = rootLoader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/RootUI.fxml"));
    final Node editionRoot = editionLoader.<Node>load(ResourcesUtils.getInputStreamResource("viewResources/EditorUI.fxml"));
    final Node graduationRoot = graduationLoader.<Node>load(ResourcesUtils.getInputStreamResource("viewResources/CorrectorUI.fxml"));
    final Node studentsRoot = studentsLoader.<Node>load(ResourcesUtils.getInputStreamResource("viewResources/StudentsUI.fxml"));
    Object _controller = editionLoader.<Object>getController();
    final ControllerFxEdition controllerEdition = ((ControllerFxEdition) _controller);
    Object _controller_1 = graduationLoader.<Object>getController();
    final ControllerFxGraduation controllerGraduation = ((ControllerFxGraduation) _controller_1);
    Object _controller_2 = rootLoader.<Object>getController();
    ControllerRoot controllerRoot = ((ControllerRoot) _controller_2);
    Object _controller_3 = studentsLoader.<Object>getController();
    final ControllerFxStudents controllerStudents = ((ControllerFxStudents) _controller_3);
    controllerRoot.setEditorNode(editionRoot);
    controllerRoot.setGraduationNode(graduationRoot);
    controllerRoot.setStudentsNode(studentsRoot);
    final Scene rootScene = new Scene(mainRoot, 1280, 720);
    Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
    StyleManager.getInstance().addUserAgentStylesheet("viewResources/MyStyle.css");
    controllerStudents.init(LauncherFX.serviceGraduation, controllerRoot);
    controllerGraduation.init(LauncherFX.serviceGraduation, controllerStudents);
    controllerEdition.init(LauncherFX.serviceEdition);
    controllerRoot.setGraduationController(controllerGraduation);
    controllerRoot.setEditionController(controllerEdition);
    controllerRoot.init();
    controllerRoot.init(LauncherFX.serviceEdition, LauncherFX.serviceGraduation);
    primaryStage.setTitle("ScanExam");
    primaryStage.setScene(rootScene);
    primaryStage.setMinHeight(720);
    primaryStage.setMinWidth(720);
    ObservableList<Image> _icons = primaryStage.getIcons();
    InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
    Image _image = new Image(_inputStreamResource);
    _icons.add(_image);
    primaryStage.show();
    controllerGraduation.setKeybinds();
  }
  
  @Override
  public void launch() {
  }
}
