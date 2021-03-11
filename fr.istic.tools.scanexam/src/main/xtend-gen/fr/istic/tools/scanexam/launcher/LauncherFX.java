package fr.istic.tools.scanexam.launcher;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.launcher.Launcher;
import fr.istic.tools.scanexam.presenter.PresenterBindings;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fX.ControllerFXCorrector;
import fr.istic.tools.scanexam.view.fX.ControllerFXEditor;
import fr.istic.tools.scanexam.view.fX.EditorAdapterFX;
import fr.istic.tools.scanexam.view.fX.GraduationAdapterFX;
import java.io.InputStream;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
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
    editorLoader.setResources(LanguageManager.getCurrentBundle());
    graduatorLoader.setResources(LanguageManager.getCurrentBundle());
    final Parent editorRoot = editorLoader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/EditorUI.fxml"));
    final Parent graduatorRoot = graduatorLoader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/CorrectorUI.fxml"));
    Object _controller = editorLoader.<Object>getController();
    final ControllerFXEditor controllerEditor = ((ControllerFXEditor) _controller);
    Object _controller_1 = graduatorLoader.<Object>getController();
    final ControllerFXCorrector controllerGraduator = ((ControllerFXCorrector) _controller_1);
    controllerEditor.setEditorAdapterFX(LauncherFX.edit);
    LauncherFX.edit.setControllerFXCreator(controllerEditor);
    controllerGraduator.setAdapterCorrection(LauncherFX.grad);
    LauncherFX.grad.setController(controllerGraduator);
    final Scene editorScene = new Scene(editorRoot, 1280, 720);
    final Scene graduatorScene = new Scene(graduatorRoot, 1280, 720);
    controllerGraduator.init();
    LauncherFX.pStage = primaryStage;
    LauncherFX.eScene = editorScene;
    LauncherFX.gScene = graduatorScene;
    primaryStage.setTitle("Corrector GUI - ScanExam");
    primaryStage.setScene(editorScene);
    primaryStage.setMinHeight(720);
    primaryStage.setMinWidth(720);
    ObservableList<Image> _icons = primaryStage.getIcons();
    InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
    Image _image = new Image(_inputStreamResource);
    _icons.add(_image);
    primaryStage.show();
  }
  
  private static EditorAdapterFX edit;
  
  private static GraduationAdapterFX grad;
  
  private static Stage pStage;
  
  private static Scene eScene;
  
  private static Scene gScene;
  
  @Override
  public void launch() {
    EditorAdapterFX _editorAdapterFX = new EditorAdapterFX();
    LauncherFX.edit = _editorAdapterFX;
    GraduationAdapterFX _graduationAdapterFX = new GraduationAdapterFX();
    LauncherFX.grad = _graduationAdapterFX;
    PresenterBindings.linkEditorPresenter(LauncherFX.edit);
    PresenterBindings.linkGraduationPresenter(LauncherFX.grad);
    LauncherFX.launchApp(null);
  }
  
  public static void swapToEditor() {
    LauncherFX.pStage.setScene(LauncherFX.eScene);
  }
  
  public static void swapToGraduator() {
    LauncherFX.pStage.setScene(LauncherFX.gScene);
  }
}
