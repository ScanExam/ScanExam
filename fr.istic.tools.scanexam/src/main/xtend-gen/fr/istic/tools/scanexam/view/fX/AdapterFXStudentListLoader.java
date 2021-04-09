package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.presenter.PresenterStudentListLoader;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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
public class AdapterFXStudentListLoader {
  /**
   * Logger du programme
   */
  private static final Logger logger = LogManager.getLogger();
  
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
   * Constructeur
   */
  public AdapterFXStudentListLoader() {
    PresenterStudentListLoader _presenterStudentListLoader = new PresenterStudentListLoader();
    this.presStudentList = _presenterStudentListLoader;
  }
  
  @FXML
  public void saveAndQuit() {
    this.presStudentList.sendToService(this.txtFldFile.getText(), this.txtFldFirstCell.getText());
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
    List<String> _asList = Arrays.<String>asList("*.ods", "*.ots", "*.sxc", "*.stc", "*.fods", "*.xml", "*.xlsx", "*.xltx", "*.xlsm", "*.xlsb", "*.xls", "*.xlc", "*.xlm", "*.xlw", "*.xlk", "*.et", "*.xlt", "*.ett", "*.dif", "*.wk1", "*.xls", "*.123", "*.wb2", "*.csv");
    FileChooser.ExtensionFilter _extensionFilter = new FileChooser.ExtensionFilter("Calc files", _asList);
    _extensionFilters.add(_extensionFilter);
    String _property = System.getProperty("user.home");
    String _property_1 = System.getProperty("file.separator");
    String _plus = (_property + _property_1);
    String _plus_1 = (_plus + "Documents");
    File _file = new File(_plus_1);
    fileChooser.setInitialDirectory(_file);
    File file = fileChooser.showOpenDialog(this.mainPane.getScene().getWindow());
    if ((file != null)) {
      this.txtFldFile.setText(file.getPath());
    } else {
      AdapterFXStudentListLoader.logger.warn("File not chosen");
    }
  }
  
  /**
   * SETTERS
   */
  public void setPresenterStudentListLoader(final PresenterStudentListLoader presStudentList) {
    this.presStudentList = presStudentList;
  }
}
