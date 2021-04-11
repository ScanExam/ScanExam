package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.presenter.PresenterStudentSheetExport;
import fr.istic.tools.scanexam.view.fx.component.FormattedTextField;
import fr.istic.tools.scanexam.view.fx.component.validator.FormatValidator;
import java.io.File;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("all")
public class ControllerStudentSheetExport {
  /**
   * Composant racine
   */
  @FXML
  private VBox mainPane;
  
  /**
   * TextField pour saisir le nombre de copies voulues
   */
  @FXML
  private FormattedTextField txtFlbNbSheet;
  
  /**
   * Button pour exporter
   */
  @FXML
  private Button btnExport;
  
  private PresenterStudentSheetExport presenter;
  
  private static final Logger logger = LogManager.getLogger();
  
  /**
   * Initialise le composant avec le presenter composé en paramètre
   * @param loader le presenter
   */
  public void initialize(final PresenterStudentSheetExport presenter) {
    this.presenter = presenter;
    final FormatValidator _function = (String str) -> {
      Optional<String> _xifexpression = null;
      int _parseInt = Integer.parseInt(str);
      boolean _greaterThan = (_parseInt > 0);
      if (_greaterThan) {
        _xifexpression = Optional.<String>empty();
      } else {
        _xifexpression = Optional.<String>of("exportStudentSheet.errorZeroValue");
      }
      return _xifexpression;
    };
    this.txtFlbNbSheet.addFormatValidator(_function);
    this.btnExport.disableProperty().bind(this.txtFlbNbSheet.wrongFormattedProperty());
  }
  
  @FXML
  public void exportAndQuit() {
    final Optional<File> fileOpt = this.loadFolder();
    boolean _isPresent = fileOpt.isPresent();
    if (_isPresent) {
      boolean _export = this.presenter.export(fileOpt.get(), Integer.parseInt(this.txtFlbNbSheet.getText()));
      if (_export) {
        this.quit();
      }
    }
  }
  
  @FXML
  public void quit() {
    Window _window = this.mainPane.getScene().getWindow();
    final Stage stage = ((Stage) _window);
    stage.close();
  }
  
  /**
   * Affiche un sélectionneur de dossier
   * @return le chemin du dossier sélectionné par l'utilisateur, Optional.empty si aucun dossier sélectionné
   */
  private Optional<File> loadFolder() {
    DirectoryChooser directoryChooser = new DirectoryChooser();
    String _property = System.getProperty("user.home");
    String _property_1 = System.getProperty("file.separator");
    String _plus = (_property + _property_1);
    String _plus_1 = (_plus + "Documents");
    File _file = new File(_plus_1);
    directoryChooser.setInitialDirectory(_file);
    File file = directoryChooser.showDialog(this.mainPane.getScene().getWindow());
    if ((file != null)) {
      return Optional.<File>of(file);
    } else {
      ControllerStudentSheetExport.logger.warn("Folder not chosen");
      return Optional.<File>empty();
    }
  }
}
