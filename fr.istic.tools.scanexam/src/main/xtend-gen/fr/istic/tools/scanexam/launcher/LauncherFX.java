package fr.istic.tools.scanexam.launcher;

import com.sun.javafx.css.StyleManager;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.launcher.Launcher;
import fr.istic.tools.scanexam.presenter.PresenterEdition;
import fr.istic.tools.scanexam.presenter.PresenterGraduation;
import fr.istic.tools.scanexam.services.ServiceEdition;
import fr.istic.tools.scanexam.services.ServiceGraduation;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fx.ControllerRoot;
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
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
    final FXMLLoader editorLoader = new FXMLLoader();
    final FXMLLoader graduatorLoader = new FXMLLoader();
    final FXMLLoader rootLoader = new FXMLLoader();
    editorLoader.setResources(LanguageManager.getCurrentBundle());
    graduatorLoader.setResources(LanguageManager.getCurrentBundle());
    rootLoader.setResources(LanguageManager.getCurrentBundle());
    final Parent mainRoot = rootLoader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/RootUI.FXML"));
    final Node editorRoot = editorLoader.<Node>load(ResourcesUtils.getInputStreamResource("viewResources/EditorUI.FXML"));
    final Node graduatorRoot = graduatorLoader.<Node>load(ResourcesUtils.getInputStreamResource("viewResources/CorrectorUI.FXML"));
    Object _controller = editorLoader.<Object>getController();
    final ControllerFxEdition controllerEditor = ((ControllerFxEdition) _controller);
    Object _controller_1 = graduatorLoader.<Object>getController();
    final ControllerFxGraduation controllerGraduator = ((ControllerFxGraduation) _controller_1);
    Object _controller_2 = rootLoader.<Object>getController();
    ControllerRoot controllerRoot = ((ControllerRoot) _controller_2);
    controllerRoot.setCorrector(graduatorRoot);
    controllerRoot.setEditor(editorRoot);
    final Scene rootScene = new Scene(mainRoot, 1280, 720);
    Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
    StyleManager.getInstance().addUserAgentStylesheet("viewResources/MyStyle.css");
    PresenterGraduation _presenterGraduation = new PresenterGraduation(LauncherFX.serviceGraduation);
    controllerGraduator.setPresenter(_presenterGraduation);
    controllerGraduator.init();
    PresenterEdition _presenterEdition = new PresenterEdition(LauncherFX.serviceEdition);
    controllerEditor.setPresenter(_presenterEdition);
    controllerEditor.init();
    controllerRoot.setCorrectorController(controllerGraduator);
    controllerRoot.setEditorController(controllerEditor);
    controllerRoot.init();
    primaryStage.setTitle("ScanExam");
    primaryStage.setScene(rootScene);
    primaryStage.setMinHeight(720);
    primaryStage.setMinWidth(720);
    ObservableList<Image> _icons = primaryStage.getIcons();
    InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
    Image _image = new Image(_inputStreamResource);
    _icons.add(_image);
    primaryStage.show();
  }
  
  @Override
  public void launch() {
  }
}
