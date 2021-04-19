package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Le contrôleur de la vue affichant la progression d'une tâche de fond.
 * @author Théo Giraudet
 */
@SuppressWarnings("all")
public class ControllerWaiting implements Initializable {
  /**
   * Le composant racine de la vue
   */
  @FXML
  private VBox root;
  
  /**
   * La barre de progression de la tâche
   */
  @FXML
  private ProgressBar progressBar;
  
  /**
   * Le label affichant la valeur en pourcentage de la progression de la tâche
   */
  @FXML
  private Label percentLabel;
  
  /**
   * Le label affichant où en est la tâche sous forme de message de progression
   */
  @FXML
  private Label progressLabel;
  
  /**
   * la propriété de valeur courante de la progression
   */
  private DoubleProperty currentProgress;
  
  /**
   * Initialise l'état de la fenêtre
   * @InheritDoc
   */
  @Override
  public void initialize(final URL location, final ResourceBundle resources) {
    SimpleDoubleProperty _simpleDoubleProperty = new SimpleDoubleProperty(this, "progressCurrent", 0d);
    this.currentProgress = _simpleDoubleProperty;
    this.progressBar.setProgress(0d);
    this.progressBar.progressProperty().bind(this.currentProgress);
    final Callable<Long> _function = () -> {
      double _get = this.currentProgress.get();
      double _multiply = (_get * 100);
      return Long.valueOf(Math.round(_multiply));
    };
    final StringBinding percentProperty = Bindings.createLongBinding(_function, this.currentProgress).asString();
    this.percentLabel.textProperty().bind(percentProperty.concat("%"));
    final ChangeListener<Number> _function_1 = (ObservableValue<? extends Number> obs, Number oldVal, Number newVal) -> {
      double _abs = Math.abs((1 - (((Double) newVal)).doubleValue()));
      boolean _lessThan = (_abs < 0.0001);
      if (_lessThan) {
        Window _window = this.root.getScene().getWindow();
        ((Stage) _window).close();
      }
    };
    this.currentProgress.addListener(_function_1);
  }
  
  /**
   * Affiche une fenêtre de progression de la tâche
   * @param progressTextProperty la propriété du string à afficher pour message de progression sur l'UI
   * @param progressProperty une propriété contenant une valeur sur [0, 1] représentant la progression de la tâche
   */
  public static void openWaitingDialog(final ReadOnlyStringProperty progressTextProperty, final ReadOnlyDoubleProperty progressProperty, final Stage parentStage) {
    try {
      final FXMLLoader loader = new FXMLLoader();
      loader.setResources(LanguageManager.getCurrentBundle());
      final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/WaitingUI.FXML"));
      final Stage dialog = new Stage();
      final ControllerWaiting controller = loader.<ControllerWaiting>getController();
      final Scene scene = new Scene(view, 289, 103);
      controller.currentProgress.bind(progressProperty);
      controller.progressLabel.textProperty().bind(progressTextProperty);
      dialog.setTitle(LanguageManager.translate("waitingUi.taskInProgress"));
      ObservableList<Image> _icons = dialog.getIcons();
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
      Image _image = new Image(_inputStreamResource);
      _icons.add(_image);
      dialog.setScene(scene);
      dialog.setResizable(false);
      dialog.initModality(Modality.WINDOW_MODAL);
      dialog.initOwner(parentStage);
      dialog.show();
      scene.setCursor(Cursor.WAIT);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
