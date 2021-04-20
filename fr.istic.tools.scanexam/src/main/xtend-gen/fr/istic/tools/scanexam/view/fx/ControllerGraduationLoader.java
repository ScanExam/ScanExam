package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import fr.istic.tools.scanexam.view.fx.component.FormattedTextField;
import fr.istic.tools.scanexam.view.fx.component.validator.ValidFilePathValidator;
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import fr.istic.tools.scanexam.view.fx.utils.DialogMessageSender;
import java.io.File;
import java.util.Objects;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
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
   * Button de validation du formulaire
   */
  @FXML
  private Button btnOk;
  
  /**
   * Ce composant ne sert qu'à attraper l'hover event, étant donné que les éléments disabled ne le trigger pas
   */
  @FXML
  private Pane hoverPane;
  
  private ControllerFxGraduation controllerGraduation;
  
  private static final Logger logger = LogManager.getLogger();
  
  private ControllerFxEdition controllerEdition;
  
  /**
   * Initialise le composant avec le presenter composé en paramètre
   * @param loader le presenter
   */
  public void initialize(final ServiceGraduation serviceGraduation, final ControllerFxEdition controllerEdition, final ControllerFxGraduation controllerGraduation) {
    this.controllerGraduation = controllerGraduation;
    this.controllerEdition = controllerEdition;
    this.hBoxLoad.disableProperty().bind(this.rbLoadModel.selectedProperty().not());
    this.btnOk.disableProperty().bind(
      this.txtFldFile.wrongFormattedProperty().or(this.txtFldFileGraduation.wrongFormattedProperty()).or(this.txtFldFileGraduation.textProperty().isEmpty()).or(
        this.rbLoadModel.selectedProperty().and(this.txtFldFile.textProperty().isEmpty())));
    ValidFilePathValidator _validFilePathValidator = new ValidFilePathValidator(".xmi");
    this.txtFldFile.addFormatValidator(_validFilePathValidator);
    ValidFilePathValidator _validFilePathValidator_1 = new ValidFilePathValidator(".xmi");
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
      this.loadFile("*.xmi", "file.format.pdf", this.txtFldFileGraduation);
    };
    this.btnBrowseGraduation.setOnAction(_function_2);
    boolean _hasExamLoaded = serviceGraduation.hasExamLoaded();
    boolean _not = (!_hasExamLoaded);
    if (_not) {
      this.rbUseLoaded.setDisable(true);
      this.rbLoadModel.setSelected(true);
    }
  }
  
  /**
   * Anime toutes les composants vides
   */
  private void shakeEmptyComponents() {
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
  public void valid() {
    if ((this.rbUseLoaded.isSelected() || this.controllerEdition.loadTemplate(new File(this.txtFldFile.getText())))) {
      String _text = this.txtFldFileGraduation.getText();
      File _file = new File(_text);
      boolean _load = this.controllerGraduation.load(_file);
      boolean _not = (!_load);
      if (_not) {
        DialogMessageSender.sendDialog(Alert.AlertType.ERROR, "graduationLoader.graduationConfirmationDialog.title", "graduationLoader.graduationConfirmationDialog.fail", null);
      } else {
        this.quit();
      }
    }
  }
}
