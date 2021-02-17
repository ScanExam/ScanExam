package fr.istic.tools.scanexam.launcher;

import fr.istic.tools.scanexam.utils.ResourcesUtils;
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
public class LauncherFX extends Application {
  /**
   * Lancement de l'application FX
   */
  public static void launchApp(final String[] args) {
    Application.launch(args);
  }
  
  public static void launchApp() {
    Application.launch(null);
  }
  
  /**
   * Classe de lancement de l'application FX
   */
  @Override
  public void start(final Stage primaryStage) throws Exception {
    InputOutput.<String>println("started App");
    final FXMLLoader loader = new FXMLLoader();
    final Parent root = loader.<Parent>load(ResourcesUtils.getInputStreamResource("/viewResources/Proto.fxml"));
    primaryStage.setTitle("Corrector GUI - ScanExam");
    Scene _scene = new Scene(root, 1280, 720);
    primaryStage.setScene(_scene);
    primaryStage.setMinHeight(720);
    primaryStage.setMinWidth(720);
    primaryStage.show();
  }
}
