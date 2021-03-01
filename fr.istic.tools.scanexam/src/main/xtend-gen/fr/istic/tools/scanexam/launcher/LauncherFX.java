package fr.istic.tools.scanexam.launcher;

import fr.istic.tools.scanexam.launcher.Launcher;
import fr.istic.tools.scanexam.presenter.PresenterBindings;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fX.ControllerFXCreator;
import fr.istic.tools.scanexam.view.fX.EditorAdapterFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.eclipse.xtext.xbase.lib.InputOutput;

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
    InputOutput.<String>println("started App");
    final FXMLLoader loader = new FXMLLoader();
    final Parent root = loader.<Parent>load(ResourcesUtils.getInputStreamResource("/viewResources/Creator.fxml"));
    Object _controller = loader.<Object>getController();
    ControllerFXCreator controller = ((ControllerFXCreator) _controller);
    controller.setEditorAdapterFX(LauncherFX.edit);
    LauncherFX.edit.setControllerFXCreator(controller);
    primaryStage.setTitle("Corrector GUI - ScanExam");
    Scene _scene = new Scene(root, 1280, 720);
    primaryStage.setScene(_scene);
    primaryStage.setMinHeight(720);
    primaryStage.setMinWidth(720);
    primaryStage.show();
  }
  
  private static EditorAdapterFX edit;
  
  @Override
  public void launch() {
    EditorAdapterFX _editorAdapterFX = new EditorAdapterFX();
    LauncherFX.edit = _editorAdapterFX;
    PresenterBindings.linkEditorPresenter(LauncherFX.edit);
    LauncherFX.launchApp(null);
  }
}