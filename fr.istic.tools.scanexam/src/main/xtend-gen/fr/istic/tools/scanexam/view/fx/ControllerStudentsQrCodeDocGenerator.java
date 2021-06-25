package fr.istic.tools.scanexam.view.fx;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.qrCode.writer.StudentsQrCodeDocGenerator;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fx.component.FormattedTextField;
import fr.istic.tools.scanexam.view.fx.component.validator.FormatValidator;
import fr.istic.tools.scanexam.view.fx.component.validator.ValidFilePathValidator;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

/**
 * Classe gérant l'interface de génération de document qr code d'élèves
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class ControllerStudentsQrCodeDocGenerator {
  private enum LoadState {
    SUCCESS,
    
    NOT_VALID;
  }
  
  /**
   * Logger du programme
   */
  private static final Logger logger = LogManager.getLogger();
  
  /**
   * Formats supportés pour la liste des étudiants
   */
  private static final List<String> supportedFormat = Arrays.<String>asList("*.xlsx", "*.xls");
  
  /**
   * Pane principale de la vue
   */
  @FXML
  public Pane mainPane;
  
  /**
   * Champ du fichier à charger
   */
  @FXML
  public FormattedTextField txtFldFile;
  
  /**
   * Bouton de validation
   */
  @FXML
  public Button btnOk;
  
  /**
   * METHODES
   */
  @FXML
  public void saveAndQuit() {
    final Optional<File> saveFile = this.selectSavePath();
    boolean _isEmpty = saveFile.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      String _text = this.txtFldFile.getText();
      File _file = new File(_text);
      final ControllerStudentsQrCodeDocGenerator.LoadState state = this.exportStudentsQrCodes(_file, saveFile.get());
      boolean _isDisable = this.btnOk.isDisable();
      boolean _not_1 = (!_isDisable);
      if (_not_1) {
        this.dispDialog(state);
      }
      this.quit();
    }
  }
  
  @FXML
  public void quit() {
    Window _window = this.mainPane.getScene().getWindow();
    final Stage stage = ((Stage) _window);
    stage.close();
  }
  
  /**
   * Initialise le contrôleur
   */
  public void initialize() {
    this.btnOk.disableProperty().bind(this.txtFldFile.wrongFormattedProperty());
    final FormatValidator _function = (String text) -> {
      Optional<String> _xifexpression = null;
      final Function1<String, String> _function_1 = (String f) -> {
        return f.substring(1);
      };
      final Function1<String, Boolean> _function_2 = (String f) -> {
        return Boolean.valueOf(text.endsWith(f));
      };
      String _findFirst = IterableExtensions.<String>findFirst(ListExtensions.<String, String>map(ControllerStudentsQrCodeDocGenerator.supportedFormat, _function_1), _function_2);
      boolean _tripleNotEquals = (_findFirst != null);
      if (_tripleNotEquals) {
        _xifexpression = Optional.<String>empty();
      } else {
        _xifexpression = Optional.<String>of(
          "file.info.fileNotValid");
      }
      return _xifexpression;
    };
    this.txtFldFile.addFormatValidator(_function);
    ValidFilePathValidator _validFilePathValidator = new ValidFilePathValidator();
    this.txtFldFile.addFormatValidator(_validFilePathValidator);
  }
  
  /**
   * Ouvre une fenêtre pour sélectionner le fichier contenant la liste d'élèves
   */
  public void loadStudentsList() {
    FileChooser fileChooser = new FileChooser();
    ObservableList<FileChooser.ExtensionFilter> _extensionFilters = fileChooser.getExtensionFilters();
    FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter("XLS files", ControllerStudentsQrCodeDocGenerator.supportedFormat);
    _extensionFilters.add(_extensionFilter);
    String _text = this.txtFldFile.getText();
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
      this.txtFldFile.setText(file.getPath());
    } else {
      ControllerStudentsQrCodeDocGenerator.logger.warn("File not chosen");
    }
  }
  
  /**
   * Ouvre une fenêtre pour sélectionner le fichier où sauvegarder le pdf généré
   * @param Fichier où sauvegarder le pdf généré
   */
  private Optional<File> selectSavePath() {
    final FileChooser fileChooser = new FileChooser();
    ObservableList<FileChooser.ExtensionFilter> _extensionFilters = fileChooser.getExtensionFilters();
    String _translate = LanguageManager.translate("file.format.pdf");
    FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter(_translate, "*.pdf");
    _extensionFilters.add(_extensionFilter);
    String _property = System.getProperty("user.home");
    String _property_1 = System.getProperty("file.separator");
    String _plus = (_property + _property_1);
    String _plus_1 = (_plus + "Documents");
    File _file = new File(_plus_1);
    fileChooser.setInitialDirectory(_file);
    final File file = fileChooser.showSaveDialog(this.mainPane.getScene().getWindow());
    if ((file != null)) {
      return Optional.<File>of(file);
    } else {
      ControllerStudentsQrCodeDocGenerator.logger.warn("Folder not chosen");
      return Optional.<File>empty();
    }
  }
  
  /**
   * Affiche un boîte de dialog décrivant la réussite ou non du chargement des données
   * @param state un LoadState décrivant le réussite ou non du chargement des données
   */
  private void dispDialog(final ControllerStudentsQrCodeDocGenerator.LoadState state) {
    final Alert alert = new Alert(Alert.AlertType.NONE);
    alert.setTitle(LanguageManager.translate("studentsQrCodeDoc.alert.title"));
    Window _window = alert.getDialogPane().getScene().getWindow();
    final Stage stage = ((Stage) _window);
    ObservableList<Image> _icons = stage.getIcons();
    InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
    Image _image = new Image(_inputStreamResource);
    _icons.add(_image);
    boolean _equals = Objects.equal(state, ControllerStudentsQrCodeDocGenerator.LoadState.SUCCESS);
    if (_equals) {
      alert.setAlertType(Alert.AlertType.CONFIRMATION);
      alert.setHeaderText(String.format(LanguageManager.translate("studentsQrCodeDoc.alert.success")));
    } else {
      alert.setAlertType(Alert.AlertType.ERROR);
      alert.setHeaderText(LanguageManager.translate("studentsQrCodeDoc.alert.error"));
    }
    alert.showAndWait();
  }
  
  /**
   * Envoie la liste des étudiants et le fichier où enregistrer le document des qr codes au générateur
   * @param studentsList Chemin du fichier contenant la liste des étudiants
   * @param exportFile Chemin du fichier où enregistrer le document des qr codes
   * @return un LoadState représentant l'état terminal de l'export des qr codes
   */
  private ControllerStudentsQrCodeDocGenerator.LoadState exportStudentsQrCodes(final File studentsList, final File exportFile) {
    final StudentsQrCodeDocGenerator generator = new StudentsQrCodeDocGenerator();
    generator.generateDocument(studentsList, exportFile);
    return ControllerStudentsQrCodeDocGenerator.LoadState.SUCCESS;
  }
}
