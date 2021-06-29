package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.core.QrCodeZone;
import fr.istic.tools.scanexam.qrCode.QrCodeType;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl;
import fr.istic.tools.scanexam.services.api.ServiceEdition;
import fr.istic.tools.scanexam.view.fx.component.FormattedTextField;
import fr.istic.tools.scanexam.view.fx.component.validator.FormatValidator;
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition;
import java.io.File;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
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
  
  private ControllerFxEdition controllerEdition;
  
  private ServiceEdition service;
  
  private static final Logger logger = LogManager.getLogger();
  
  /**
   * Initialise le composant avec le presenter composé en paramètre
   * @param loader le presenter
   */
  public void initialize(final ControllerFxEdition controllerEdition, final ServiceEdition service) {
    this.service = service;
    this.controllerEdition = controllerEdition;
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
      boolean _export = this.export(fileOpt.get(), QrCodeType.SHEET_PAGE, Integer.parseInt(this.txtFlbNbSheet.getText()));
      if (_export) {
        this.quit();
      }
    }
  }
  
  public boolean export(final File file, final int qrCodeType, final int number) {
    boolean _xblockexpression = false;
    {
      Optional<QrCodeZone> _qrCodeZone = this.service.getQrCodeZone();
      boolean _tripleEquals = (_qrCodeZone == null);
      if (_tripleEquals) {
        this.service.createQrCode(0.025f, 0.875f, 0.1f, 0.1f);
      }
      final QRCodeGenerator generator = new QRCodeGeneratorImpl();
      generator.createAllExamCopies(this.controllerEdition.getPdfManager().getPdfInputStream(), file, this.service.getQrCodeZone().get(), qrCodeType, number);
      _xblockexpression = true;
    }
    return _xblockexpression;
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
    FileChooser fileChooser = new FileChooser();
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
    File file = fileChooser.showSaveDialog(this.mainPane.getScene().getWindow());
    if ((file != null)) {
      return Optional.<File>of(file);
    } else {
      ControllerStudentSheetExport.logger.warn("Folder not chosen");
      return Optional.<File>empty();
    }
  }
}
