package fr.istic.tools.scanexam.view.fx;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.presenter.PresenterStudentListLoader;
import fr.istic.tools.scanexam.view.fx.utils.BadFormatDisplayer;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Classe pour charger la liste des étudiant en JavaFX
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class AdapterFxStudentListLoader implements Initializable {
  /**
   * Logger du programme
   */
  private static final Logger logger = LogManager.getLogger();
  
  private static final Pattern cellPattern = Pattern.compile("[A-Z]+[1-9][0-9]*");
  
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
  public TextField txtFldFile;
  
  /**
   * Champ de la première casse
   */
  @FXML
  public TextField txtFldFirstCell;
  
  /**
   * Bouton de validation
   */
  @FXML
  public Button btnOk;
  
  /**
   * Caractérise les erreurs dans le formulaire. Si au moins l'une des valeurs est à true, alors le bouton de validation est bloqué
   */
  private final boolean[] errors = new boolean[2];
  
  /**
   * METHODES
   */
  @Override
  public void initialize(final URL location, final ResourceBundle resources) {
    this.txtFldFile.setText("");
    this.txtFldFirstCell.setText("A1");
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
    final ChangeListener<Boolean> _function_1 = (ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) -> {
      if ((!(newVal).booleanValue())) {
        this.verifyFirstCell();
      }
    };
    this.txtFldFirstCell.focusedProperty().addListener(_function_1);
    final ChangeListener<Boolean> _function_2 = (ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) -> {
      if ((!(newVal).booleanValue())) {
        this.verifyFilePath();
      }
    };
    this.txtFldFile.focusedProperty().addListener(_function_2);
    final ChangeListener<Boolean> _function_3 = (ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) -> {
      if ((!(newVal).booleanValue())) {
        boolean _equals = this.txtFldFirstCell.getText().equals("");
        if (_equals) {
          this.txtFldFirstCell.setText("A1");
        }
      }
    };
    this.txtFldFirstCell.focusedProperty().addListener(_function_3);
  }
  
  @FXML
  public void saveAndQuit() {
    String _text = this.txtFldFile.getText();
    File _file = new File(_text);
    this.presStudentList.loadFile(_file, this.txtFldFirstCell.getText());
    this.verifyFilePath();
    this.verifyFirstCell();
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
    List<String> _asList = Arrays.<String>asList("*.ods", "*.ots", "*.sxc", "*.stc", "*.fods", "*.xml", "*.xlsx", "*.xltx", "*.xlsm", 
      "*.xlsb", "*.xls", "*.xlc", "*.xlm", "*.xlw", "*.xlk", "*.et", "*.xlt", "*.ett", "*.dif", "*.wk1", 
      "*.xls", "*.123", "*.wb2", "*.csv");
    FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter("Calc files", _asList);
    _extensionFilters.add(_extensionFilter);
    String _property = System.getProperty("user.home");
    String _property_1 = System.getProperty("file.separator");
    String _plus = (_property + _property_1);
    String _plus_1 = (_plus + 
      "Documents");
    File _file = new File(_plus_1);
    fileChooser.setInitialDirectory(_file);
    File file = fileChooser.showOpenDialog(this.mainPane.getScene().getWindow());
    if ((file != null)) {
      this.txtFldFile.setText(file.getPath());
    } else {
      AdapterFxStudentListLoader.logger.warn("File not chosen");
    }
    this.verifyFilePath();
  }
  
  /**
   * Vérifie la syntaxe de la cellule spécifiée par l'utilisateur, et refuse l'entrée si celle-ci n'est pas valide
   */
  private void verifyFirstCell() {
    if (((!AdapterFxStudentListLoader.cellPattern.matcher(this.txtFldFirstCell.getText()).matches()) && (!Objects.equal(this.txtFldFirstCell.getText(), "")))) {
      BadFormatDisplayer.dispBadFormatView(this.txtFldFirstCell, true);
      this.errors[1] = true;
      String _translate = LanguageManager.translate("studentlist.info.badCellFormat");
      Tooltip _tooltip = new Tooltip(_translate);
      this.txtFldFirstCell.setTooltip(_tooltip);
    } else {
      BadFormatDisplayer.dispBadFormatView(this.txtFldFirstCell, false);
      this.errors[0] = false;
      this.txtFldFirstCell.setTooltip(null);
    }
    this.updateLockingState();
  }
  
  /**
   * Vérifie si le chemin spécifié par l'utilisateur pointe bien vers un fichier du bon format, refuse l'entrée si cela n'est pas le cas
   */
  private void verifyFilePath() {
    String _text = this.txtFldFile.getText();
    final File file = new File(_text);
    if (((!((file.exists() && file.isFile()) && file.getName().endsWith(".xlsx"))) && (!Objects.equal(this.txtFldFile.getText(), "")))) {
      BadFormatDisplayer.dispBadFormatView(this.txtFldFile, true);
      this.errors[1] = true;
      boolean _exists = file.exists();
      boolean _not = (!_exists);
      if (_not) {
        String _translate = LanguageManager.translate("studentlist.info.fileNotExist");
        Tooltip _tooltip = new Tooltip(_translate);
        this.txtFldFile.setTooltip(_tooltip);
      } else {
        String _translate_1 = LanguageManager.translate("studentlist.info.fileNotValid");
        Tooltip _tooltip_1 = new Tooltip(_translate_1);
        this.txtFldFile.setTooltip(_tooltip_1);
      }
    } else {
      BadFormatDisplayer.dispBadFormatView(this.txtFldFile, false);
      this.errors[1] = false;
      this.txtFldFile.setTooltip(null);
    }
    this.updateLockingState();
  }
  
  /**
   * Actualise l'état de l'interface en bloquant ou non le bouton de validation si il y a des erreurs
   */
  private void updateLockingState() {
    this.btnOk.setDisable((this.errors[0] || this.errors[1]));
  }
  
  /**
   * SETTERS
   */
  public void setPresenterStudentListLoader(final PresenterStudentListLoader presStudentList) {
    this.presStudentList = presStudentList;
  }
}
