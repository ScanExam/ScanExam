package fr.istic.tools.scanexam.launcher;

import com.sun.javafx.css.StyleManager;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.launcher.Launcher;
import fr.istic.tools.scanexam.presenter.PresenterBindings;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fx.AdapterFxEdition;
import fr.istic.tools.scanexam.view.fx.AdapterFxGraduation;
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
  /**
   * Lancement de l'application FX
   */
  public static void launchApp(final String[] args) {
    Application.launch(args);
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
    controllerEditor.setAdapterFxEdition(LauncherFX.edit);
    LauncherFX.edit.setControllerFXCreator(controllerEditor);
    controllerGraduator.setAdapterCorrection(LauncherFX.grad);
    LauncherFX.grad.setController(controllerGraduator);
    controllerRoot.setCorrector(graduatorRoot);
    controllerRoot.setEditor(editorRoot);
    final Scene rootScene = new Scene(mainRoot, 1280, 720);
    Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
    StyleManager.getInstance().addUserAgentStylesheet("viewResources/MyStyle.css");
    controllerGraduator.init();
    controllerEditor.init();
    controllerRoot.setCorrectorController(controllerGraduator);
    controllerRoot.setEditorController(controllerEditor);
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
  
  private static AdapterFxEdition edit;
  
  private static AdapterFxGraduation grad;
  
  @Override
  public void launch() {
    AdapterFxEdition _adapterFxEdition = new AdapterFxEdition();
    LauncherFX.edit = _adapterFxEdition;
    AdapterFxGraduation _adapterFxGraduation = new AdapterFxGraduation();
    LauncherFX.grad = _adapterFxGraduation;
    PresenterBindings.linkEditorPresenter(LauncherFX.edit);
    PresenterBindings.linkGraduationPresenter(LauncherFX.grad);
    LauncherFX.launchApp(null);
  }
  
  public static Object swapToEditor() {
    return null;
  }
  
  public static Object swapToGraduator() {
    return null;
  }
}
