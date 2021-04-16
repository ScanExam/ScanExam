package fr.istic.tools.scanexam.view.fx;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.presenter.PresenterStudentListLoader;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fx.component.FormattedTextField;
import fr.istic.tools.scanexam.view.fx.component.validator.FormatValidator;
import fr.istic.tools.scanexam.view.fx.component.validator.ValidFilePathValidator;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextFormatter;
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
 * Classe pour charger la liste des étudiant en JavaFX
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class ControllerStudentListLoader {
  /**
   * Logger du programme
   */
  private static final Logger logger = LogManager.getLogger();
  
  private static final Pattern cellPattern = Pattern.compile("[A-Z]+[1-9][0-9]*");
  
  private static final List<String> supportedFormat = Arrays.<String>asList("*.ods", "*.ots", "*.sxc", "*.stc", "*.fods", "*.xml", "*.xlsx", "*.xltx", "*.xlsm", 
    "*.xlsb", "*.xls", "*.xlc", "*.xlm", "*.xlw", "*.xlk", "*.et", "*.xlt", "*.ett", "*.dif", "*.wk1", 
    "*.xls", "*.123", "*.wb2", "*.csv");
  
  /**
   * Controlleur de la configuration
   */
  private PresenterStudentListLoader presStudentList;
  
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
   * Champ de la première casse
   */
  @FXML
  public FormattedTextField txtFldFirstCell;
  
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
    String _text = this.txtFldFile.getText();
    File _file = new File(_text);
    final PresenterStudentListLoader.LoadState state = this.presStudentList.loadFile(_file, this.txtFldFirstCell.getText());
    boolean _isDisable = this.btnOk.isDisable();
    boolean _not = (!_isDisable);
    if (_not) {
      this.dispDialog(state);
    }
    this.quit();
  }
  
  @FXML
  public void quit() {
    Window _window = this.mainPane.getScene().getWindow();
    final Stage stage = ((Stage) _window);
    stage.close();
  }
  
  /**
   * Ouvre une fenêtre pour sélectionner le fichier souhaité
   */
  public void loadFile() {
    FileChooser fileChooser = new FileChooser();
    ObservableList<FileChooser.ExtensionFilter> _extensionFilters = fileChooser.getExtensionFilters();
    FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter("Calc files", ControllerStudentListLoader.supportedFormat);
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
        String _plus_1 = (_plus + 
          "Documents");
        File _file = new File(_plus_1);
        fileChooser.setInitialDirectory(_file);
      }
    }
    File file = fileChooser.showOpenDialog(this.mainPane.getScene().getWindow());
    if ((file != null)) {
      this.txtFldFile.setText(file.getPath());
    } else {
      ControllerStudentListLoader.logger.warn("File not chosen");
    }
  }
  
  /**
   * Initialise le contrôleur
   */
  public void initialize(final PresenterStudentListLoader presStudentList) {
    this.presStudentList = presStudentList;
    this.txtFldFile.setText(presStudentList.getStudentListPath());
    this.txtFldFirstCell.setText(presStudentList.getStudentListShift());
    final UnaryOperator<TextFormatter.Change> _function = (TextFormatter.Change change) -> {
      TextFormatter.Change _xblockexpression = null;
      {
        change.setText(change.getText().toUpperCase());
        _xblockexpression = change;
      }
      return _xblockexpression;
    };
    TextFormatter<String> _textFormatter = new TextFormatter<String>(_function);
    this.txtFldFirstCell.setTextFormatter(_textFormatter);
    this.btnOk.disableProperty().bind(this.txtFldFile.wrongFormattedProperty().or(this.txtFldFirstCell.wrongFormattedProperty()));
    final FormatValidator _function_1 = (String text) -> {
      Optional<String> _xifexpression = null;
      boolean _matches = ControllerStudentListLoader.cellPattern.matcher(this.txtFldFirstCell.getText()).matches();
      boolean _not = (!_matches);
      if (_not) {
        _xifexpression = Optional.<String>of("studentlist.info.badCellFormat");
      } else {
        _xifexpression = Optional.<String>empty();
      }
      return _xifexpression;
    };
    this.txtFldFirstCell.addFormatValidator(_function_1);
    final FormatValidator _function_2 = (String text) -> {
      Optional<String> _xifexpression = null;
      final Function1<String, String> _function_3 = (String f) -> {
        return f.substring(1);
      };
      final Function1<String, Boolean> _function_4 = (String f) -> {
        return Boolean.valueOf(text.endsWith(f));
      };
      String _findFirst = IterableExtensions.<String>findFirst(ListExtensions.<String, String>map(ControllerStudentListLoader.supportedFormat, _function_3), _function_4);
      boolean _tripleNotEquals = (_findFirst != null);
      if (_tripleNotEquals) {
        _xifexpression = Optional.<String>empty();
      } else {
        _xifexpression = Optional.<String>of("studentlist.info.fileNotValid");
      }
      return _xifexpression;
    };
    this.txtFldFile.addFormatValidator(_function_2);
    ValidFilePathValidator _validFilePathValidator = new ValidFilePathValidator();
    this.txtFldFile.addFormatValidator(_validFilePathValidator);
  }
  
  /**
   * Affiche un boîte de dialog décrivant la réussite ou non du chargement des données
   * @param state un LoadState décrivant le réussite ou non du chargement des données
   */
  public void dispDialog(final PresenterStudentListLoader.LoadState state) {
    final Alert alert = new Alert(Alert.AlertType.NONE);
    Window _window = alert.getDialogPane().getScene().getWindow();
    final Stage stage = ((Stage) _window);
    ObservableList<Image> _icons = stage.getIcons();
    InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
    Image _image = new Image(_inputStreamResource);
    _icons.add(_image);
    boolean _equals = Objects.equal(state, PresenterStudentListLoader.LoadState.SUCCESS);
    if (_equals) {
      alert.setAlertType(Alert.AlertType.CONFIRMATION);
      alert.setTitle(LanguageManager.translate("studentlist.loadConfirmation.title"));
      alert.setHeaderText(String.format(LanguageManager.translate("studentlist.loadConfirmation.success"), Integer.valueOf(this.presStudentList.getNumberPair())));
      alert.setContentText(this.presStudentList.getStudentList());
    } else {
      alert.setAlertType(Alert.AlertType.ERROR);
      alert.setTitle(LanguageManager.translate("studentlist.loadConfirmation.title"));
      boolean _equals_1 = Objects.equal(state, PresenterStudentListLoader.LoadState.X_NOT_VALID);
      if (_equals_1) {
        alert.setHeaderText(LanguageManager.translate("studentlist.loadConfirmation.xNotValid"));
      } else {
        alert.setHeaderText(LanguageManager.translate("studentlist.loadConfirmation.yNotValid"));
      }
    }
    alert.showAndWait();
  }
}
