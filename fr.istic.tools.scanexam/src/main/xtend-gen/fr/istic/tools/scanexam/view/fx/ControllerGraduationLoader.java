package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.presenter.PresenterGraduationLoader;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fx.component.FormattedTextField;
import fr.istic.tools.scanexam.view.fx.component.validator.ValidFilePathValidator;
import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contrôleur pour l'UI de chargement d'une correction
 * @author Théo Giraudet
 */
@SuppressWarnings("all")
public class ControllerGraduationLoader {
  /**
   * Composant racine
   */
  @FXML
  private VBox mainPane;
  
  /**
   * RadioButton de l'option "Utiliser le modèle chargé"
   */
  @FXML
  private RadioButton rbUseLoaded;
  
  /**
   * RadioButton de l'option "Charger un nouveau modèle"
   */
  @FXML
  private RadioButton rbLoadModel;
  
  /**
   * HBox avec les nœuds à activer si deuxième RadioButton sélectionné
   */
  @FXML
  private HBox hBoxLoad;
  
  /**
   * TextField de la saisie du path pour le nouveau modèle
   */
  @FXML
  private FormattedTextField txtFldFile;
  
  /**
   * Button de chargement du modèle
   */
  @FXML
  private Button btnBrowse;
  
  /**
   * TextField de la saisie du path pour les copies
   */
  @FXML
  private FormattedTextField txtFldFileGraduation;
  
  /**
   * Button de chargement des copies
   */
  @FXML
  private Button btnBrowseGraduation;
  
  /**
   * TextField pour spécifier le nom de la correction
   */
  @FXML
  private FormattedTextField txtFldGraduationName;
  
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
  
  private PresenterGraduationLoader presStudentListLoader;
  
  private static final Logger logger = LogManager.getLogger();
  
  /**
   * Initialise le composant avec le presenter composé en paramètre
   * @param loader le presenter
   */
  public void initialize(final PresenterGraduationLoader loader) {
    this.presStudentListLoader = loader;
    this.hBoxLoad.disableProperty().bind(this.rbLoadModel.selectedProperty().not());
    this.btnOk.disableProperty().bind(
      this.txtFldFile.wrongFormattedProperty().or(this.txtFldFileGraduation.wrongFormattedProperty()).or(this.txtFldGraduationName.textProperty().isEmpty()).or(this.txtFldFileGraduation.textProperty().isEmpty()).or(
        this.rbLoadModel.selectedProperty().and(this.txtFldFile.textProperty().isEmpty())));
    ValidFilePathValidator _validFilePathValidator = new ValidFilePathValidator(".xmi");
    this.txtFldFile.addFormatValidator(_validFilePathValidator);
    ValidFilePathValidator _validFilePathValidator_1 = new ValidFilePathValidator(".pdf");
    this.txtFldFileGraduation.addFormatValidator(_validFilePathValidator_1);
    final EventHandler<MouseEvent> _function = (MouseEvent e) -> {
      boolean _isDisabled = this.btnOk.isDisabled();
      if (_isDisabled) {
        this.shakeEmptyComponents();
      }
    };
    this.hoverPane.setOnMouseEntered(_function);
    final EventHandler<ActionEvent> _function_1 = (ActionEvent e) -> {
      this.loadFile("*.xmi", "file.format.xmi", this.txtFldFile);
    };
    this.btnBrowse.setOnAction(_function_1);
    final EventHandler<ActionEvent> _function_2 = (ActionEvent e) -> {
      this.loadFile("*.pdf", "file.format.pdf", this.txtFldFileGraduation);
    };
    this.btnBrowseGraduation.setOnAction(_function_2);
    boolean _hasTemplateLoaded = this.presStudentListLoader.hasTemplateLoaded();
    boolean _not = (!_hasTemplateLoaded);
    if (_not) {
      this.rbUseLoaded.setDisable(true);
      this.rbLoadModel.setSelected(true);
    }
  }
  
  /**
   * Anime toutes les composants vides
   */
  private void shakeEmptyComponents() {
    this.txtFldGraduationName.shakeIfEmpty();
    this.txtFldFileGraduation.shakeIfEmpty();
    this.txtFldFile.shakeIfEmpty();
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
    } else {
      ControllerGraduationLoader.logger.warn("File not chosen");
    }
  }
  
  @FXML
  public void quit() {
    Window _window = this.mainPane.getScene().getWindow();
    final Stage stage = ((Stage) _window);
    stage.close();
  }
  
  @FXML
  public Object saveAndQuit() {
    Object _xifexpression = null;
    boolean _loadTemplate = this.presStudentListLoader.loadTemplate(this.txtFldFile.getText());
    if (_loadTemplate) {
      Object _xifexpression_1 = null;
      boolean _loadStudentSheets = this.presStudentListLoader.loadStudentSheets(this.txtFldFileGraduation.getText());
      if (_loadStudentSheets) {
        this.quit();
      } else {
        _xifexpression_1 = null;
      }
      _xifexpression = _xifexpression_1;
    } else {
      this.sendDialog(Alert.AlertType.ERROR, "studentSheetLoader.templateConfirmationDialog.title", "studentSheetLoader.templateConfirmationDialog.fail", null);
    }
    return _xifexpression;
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
