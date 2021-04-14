package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.presenter.PresenterTemplateCreator;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fx.component.FormattedTextField;
import fr.istic.tools.scanexam.view.fx.component.validator.ValidFilePathValidator;
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition;
import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contrôleur pour l'UI de création d'un modèle d'examen
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class ControllerTemplateCreator {
  /**
   * Composant racine
   */
  @FXML
  private VBox mainPane;
  
  /**
   * TextField pour spécifier le nom du modèle
   */
  @FXML
  private FormattedTextField txtFldTemplateName;
  
  /**
   * TextField de la saisie du path pour le nouveau modèle
   */
  @FXML
  private FormattedTextField txtFldTemplateFile;
  
  /**
   * Button de chargement du modèle
   */
  @FXML
  private Button btnBrowser;
  
  /**
   * Button de validation du formulaire
   */
  @FXML
  private Button btnOk;
  
  /**
   * Ce composant ne sert qu'à attraper l'hover event, étant donné que les éléments disabled ne le trigger pas
   */
  @FXML
  private Pane hoverPane;
  
  /**
   * Presenter de la création du modèle
   */
  private PresenterTemplateCreator presTemplateCreator;
  
  /**
   * Controller JaveFX de l'edition d'examen
   */
  private ControllerFxEdition ctrlEditor;
  
  /**
   * Logger
   */
  private static final Logger logger = LogManager.getLogger();
  
  /**
   * Initialise le composant avec le presenter composé en paramètre
   * @param presenter Presenter de la création du modèle
   */
  public void initialize(final PresenterTemplateCreator presenter, final ControllerFxEdition ctrlFxEditor) {
    this.presTemplateCreator = presenter;
    this.ctrlEditor = ctrlFxEditor;
    this.btnOk.disableProperty().bind(
      this.txtFldTemplateFile.wrongFormattedProperty().or(this.txtFldTemplateName.textProperty().isEmpty()));
    ValidFilePathValidator _validFilePathValidator = new ValidFilePathValidator(".pdf");
    this.txtFldTemplateFile.addFormatValidator(_validFilePathValidator);
    final EventHandler<MouseEvent> _function = (MouseEvent e) -> {
      boolean _isDisabled = this.btnOk.isDisabled();
      if (_isDisabled) {
        this.shakeEmptyComponents();
      }
    };
    this.hoverPane.setOnMouseEntered(_function);
    final EventHandler<ActionEvent> _function_1 = (ActionEvent e) -> {
      this.loadFile("*.pdf", "file.format.pdf", this.txtFldTemplateFile);
    };
    this.btnBrowser.setOnAction(_function_1);
  }
  
  /**
   * Anime toutes les composants vides
   */
  private void shakeEmptyComponents() {
    this.txtFldTemplateName.shakeIfEmpty();
    this.txtFldTemplateFile.shakeIfEmpty();
  }
  
  /**
   * Affiche un sélectionneur de fichier et met le path dans le composant spécifié
   * @param format l'extension du fichier à ouvrir (non null)
   * @param formatDes la description du format (non null)
   * @param destination le composant dans lequel afficher le path choisi (non null)
   */
  private void loadFile(final String format, final String formatDes, final FormattedTextField destination) {
    Objects.<String>requireNonNull(format);
    Objects.<String>requireNonNull(formatDes);
    Objects.<FormattedTextField>requireNonNull(destination);
    FileChooser fileChooser = new FileChooser();
    ObservableList<FileChooser.ExtensionFilter> _extensionFilters = fileChooser.getExtensionFilters();
    String _translate = LanguageManager.translate(formatDes);
    FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter(_translate, format);
    _extensionFilters.add(_extensionFilter);
    String _text = destination.getText();
    final File specifiedFile = new File(_text);
    if ((specifiedFile.exists() && specifiedFile.isDirectory())) {
      fileChooser.setInitialDirectory(specifiedFile);
    } else {
      if ((specifiedFile.exists() && specifiedFile.isFile())) {
        fileChooser.setInitialDirectory(specifiedFile.getParentFile());
      } else {
        String _property = System.getProperty("user.home");
        String _property_1 = System.getProperty("file.separator");
        String _plus = (_property + _property_1);
        String _plus_1 = (_plus + "Documents");
        File _file = new File(_plus_1);
        fileChooser.setInitialDirectory(_file);
      }
    }
    File file = fileChooser.showOpenDialog(this.mainPane.getScene().getWindow());
    if ((file != null)) {
      destination.setText(file.getPath());
      this.presTemplateCreator.setTemplateFile(file);
    } else {
      ControllerTemplateCreator.logger.warn("File not chosen");
    }
  }
  
  @FXML
  public void quit() {
    Window _window = this.mainPane.getScene().getWindow();
    final Stage stage = ((Stage) _window);
    stage.close();
  }
  
  @FXML
  public void saveAndQuit() {
    boolean _checkName = this.presTemplateCreator.checkName(this.txtFldTemplateName.getText());
    if (_checkName) {
      boolean _checkFilepath = this.presTemplateCreator.checkFilepath(this.txtFldTemplateFile.getText());
      if (_checkFilepath) {
        this.presTemplateCreator.setTemplateName(this.txtFldTemplateName.getText());
        this.presTemplateCreator.createTemplate();
        this.ctrlEditor.render();
        this.quit();
      } else {
        this.sendDialog(Alert.AlertType.ERROR, "templateLoader.dialog.title", "templateLoader.dialog.nameFail", null);
      }
    } else {
      this.sendDialog(Alert.AlertType.ERROR, "templateLoader.dialog.title", "templateLoader.dialog.fileFail", null);
    }
  }
  
  /**
   * Affiche un Dialog avec les informations suivantes :
   * @param type le type de l'alerte (non null)
   * @param title le titre le l'alerte (non null)
   * @param headerText le header de l'alerte (non null)
   * @param content le contenu de l'alerte
   */
  private void sendDialog(final Alert.AlertType type, final String title, final String headerText, @Nullable final String content) {
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
}
