package fr.istic.tools.scanexam.view.fx.utils;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import java.io.InputStream;
import java.util.Objects;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.annotation.Nullable;

@SuppressWarnings("all")
public class DialogMessageSender {
  /**
   * Affiche un Dialog avec les informations suivantes, traduite :
   * @param type le type de l'alerte (non null)
   * @param title le titre le l'alerte (non null)
   * @param headerText le header de l'alerte (non null)
   * @param content le contenu de l'alerte
   */
  public static void sendTranslateDialog(final Alert.AlertType type, final String title, final String headerText, @Nullable final String content) {
    Objects.<Alert.AlertType>requireNonNull(type);
    Objects.<String>requireNonNull(title);
    Objects.<String>requireNonNull(headerText);
    final Alert alert = new Alert(type);
    Window _window = alert.getDialogPane().getScene().getWindow();
    final Stage stage = ((Stage) _window);
    ObservableList<Image> _icons = stage.getIcons();
    InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
    Image _image = new Image(_inputStreamResource);
    _icons.add(_image);
    alert.setTitle(LanguageManager.translate(title));
    alert.setHeaderText(LanguageManager.translate(headerText));
    if ((content != null)) {
      alert.setContentText(LanguageManager.translate(content));
    }
    alert.showAndWait();
  }
  
  /**
   * Affiche un Dialog avec les informations suivantes :
   * @param type le type de l'alerte (non null)
   * @param title le titre (code) le l'alerte (non null)
   * @param headerText le header (code) de l'alerte (non null)
   * @param content le contenu (code) de l'alerte
   */
  public static void sendDialog(final Alert.AlertType type, final String title, final String headerText, @Nullable final String content) {
    Objects.<Alert.AlertType>requireNonNull(type);
    Objects.<String>requireNonNull(title);
    Objects.<String>requireNonNull(headerText);
    final Alert alert = new Alert(type);
    Window _window = alert.getDialogPane().getScene().getWindow();
    final Stage stage = ((Stage) _window);
    ObservableList<Image> _icons = stage.getIcons();
    InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
    Image _image = new Image(_inputStreamResource);
    _icons.add(_image);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    if ((content != null)) {
      alert.setContentText(content);
    }
    alert.showAndWait();
  }
}
