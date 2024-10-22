package fr.istic.tools.scanexam.view.fx;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.config.ConfigurationManager;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.core.config.Config;
import fr.istic.tools.scanexam.services.api.ServiceEdition;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fx.ControllerConfiguration;
import fr.istic.tools.scanexam.view.fx.ControllerGradeExport;
import fr.istic.tools.scanexam.view.fx.ControllerGraduationCreator;
import fr.istic.tools.scanexam.view.fx.ControllerGraduationLoader;
import fr.istic.tools.scanexam.view.fx.ControllerLinkManuallySheets;
import fr.istic.tools.scanexam.view.fx.ControllerSendMail;
import fr.istic.tools.scanexam.view.fx.ControllerStudentListLoader;
import fr.istic.tools.scanexam.view.fx.ControllerStudentSheetExport;
import fr.istic.tools.scanexam.view.fx.ControllerTemplateCreator;
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import fr.istic.tools.scanexam.view.fx.students.ControllerFxStudents;
import fr.istic.tools.scanexam.view.fx.utils.DialogMessageSender;
import java.awt.Desktop;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class ControllerRoot implements Initializable {
  @FXML
  private Tab correctorTab;
  
  @FXML
  private Tab editorTab;
  
  @FXML
  private Tab studentsTab;
  
  @FXML
  private CheckMenuItem autoZoom;
  
  /**
   * BUTTONS
   */
  @FXML
  private MenuItem saveGraduationButton;
  
  @FXML
  private MenuItem saveTemplateButton;
  
  @FXML
  private MenuItem exportToExamButton;
  
  @FXML
  private MenuItem loadStudentNamesButton;
  
  @FXML
  private MenuItem pdfExportButton;
  
  @FXML
  private MenuItem sendMailButton;
  
  @FXML
  private MenuItem pdfExportGradeButton;
  
  @FXML
  private MenuItem linkManuallySheetsButton;
  
  @FXML
  private TabPane tabPane;
  
  @Accessors
  private ControllerFxGraduation graduationController;
  
  @Accessors
  private ControllerFxEdition editionController;
  
  @Accessors
  private ControllerFxStudents studentsController;
  
  private ServiceEdition serviceEdition;
  
  private ServiceGraduation serviceGraduation;
  
  private static final Logger logger = LogManager.getLogger();
  
  public void init() {
    final ChangeListener<Tab> _function = (ObservableValue<? extends Tab> obs, Tab oldVal, Tab newVal) -> {
      this.graduationController.changedTab();
    };
    this.tabPane.getSelectionModel().selectedItemProperty().addListener(_function);
  }
  
  public void setEditorNode(final Node n) {
    this.editorTab.setContent(n);
  }
  
  public void setGraduationNode(final Node n) {
    this.correctorTab.setContent(n);
  }
  
  public void setStudentsNode(final Node n) {
    this.studentsTab.setContent(n);
  }
  
  @FXML
  public void loadTemplatePressedEditor() {
    this.tabPane.getSelectionModel().select(this.editorTab);
    this.editionController.loadTemplatePressed();
  }
  
  @FXML
  public void saveGraduation() {
    this.graduationController.saveExam();
  }
  
  @FXML
  public void loadTemplatePressedCorrector() {
    try {
      this.tabPane.getSelectionModel().select(this.correctorTab);
      final FXMLLoader loader = new FXMLLoader();
      loader.setResources(LanguageManager.getCurrentBundle());
      final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/GraduationLoaderUI.fxml"));
      final Stage dialog = new Stage();
      dialog.setTitle(LanguageManager.translate("menu.file.loadGraduation"));
      ObservableList<Image> _icons = dialog.getIcons();
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
      Image _image = new Image(_inputStreamResource);
      _icons.add(_image);
      loader.<ControllerGraduationLoader>getController().initialize(this.serviceGraduation, this.editionController, 
        this.graduationController);
      Scene _scene = new Scene(view, 384, 355);
      dialog.setScene(_scene);
      dialog.setResizable(false);
      dialog.show();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void goToCorrectorTab(final int id) {
    this.tabPane.getSelectionModel().select(this.correctorTab);
    this.graduationController.selectStudent(id);
  }
  
  @FXML
  public void createNewTemplatePressed() {
    try {
      this.tabPane.getSelectionModel().select(this.editorTab);
      final FXMLLoader loader = new FXMLLoader();
      loader.setResources(LanguageManager.getCurrentBundle());
      final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/TemplateCreatorUI.fxml"));
      final Stage dialog = new Stage();
      dialog.setTitle(LanguageManager.translate("menu.file.new"));
      ObservableList<Image> _icons = dialog.getIcons();
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
      Image _image = new Image(_inputStreamResource);
      _icons.add(_image);
      loader.<ControllerTemplateCreator>getController().initialize(this.editionController);
      Scene _scene = new Scene(view, 384, 155);
      dialog.setScene(_scene);
      dialog.setResizable(false);
      dialog.show();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @FXML
  public void SaveTemplatePressed() {
    this.editionController.saveTemplatePressed();
  }
  
  @FXML
  public void loadStudentList() {
    try {
      final FXMLLoader loader = new FXMLLoader();
      loader.setResources(LanguageManager.getCurrentBundle());
      final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/StudentListLoaderUI.fxml"));
      final Stage dialog = new Stage();
      dialog.setTitle(LanguageManager.translate("menu.file.loadStudentList"));
      ObservableList<Image> _icons = dialog.getIcons();
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
      Image _image = new Image(_inputStreamResource);
      _icons.add(_image);
      loader.<ControllerStudentListLoader>getController().initialize(this.serviceGraduation);
      Scene _scene = new Scene(view, 384, 206);
      dialog.setScene(_scene);
      dialog.setResizable(false);
      dialog.show();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @FXML
  public void updateConfig() {
    try {
      final FXMLLoader loader = new FXMLLoader();
      loader.setResources(LanguageManager.getCurrentBundle());
      final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/ConfigUI.fxml"));
      final Stage dialog = new Stage();
      dialog.setTitle(LanguageManager.translate("menu.edit.updateconfig"));
      ObservableList<Image> _icons = dialog.getIcons();
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
      Image _image = new Image(_inputStreamResource);
      _icons.add(_image);
      loader.<ControllerConfiguration>getController().initialize();
      Scene _scene = new Scene(view, 384, 280);
      dialog.setScene(_scene);
      dialog.setResizable(false);
      dialog.show();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @FXML
  public void linkManuallySheets() {
    try {
      final FXMLLoader loader = new FXMLLoader();
      loader.setResources(LanguageManager.getCurrentBundle());
      final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/ManuallyLinkSheets.fxml"));
      final Stage dialog = new Stage();
      ObservableList<Image> _icons = dialog.getIcons();
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
      Image _image = new Image(_inputStreamResource);
      _icons.add(_image);
      loader.<ControllerLinkManuallySheets>getController().init(this.serviceGraduation, this.graduationController.getPdfManager(), 
        this.graduationController);
      dialog.setTitle(LanguageManager.translate("menu.edit.linkSheetsTitle"));
      Scene _scene = new Scene(view);
      dialog.setScene(_scene);
      dialog.setResizable(false);
      dialog.show();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @FXML
  public void pdfExport() {
    final Collection<String> nameList = this.serviceGraduation.getStudentIds();
    boolean _isEmpty = nameList.isEmpty();
    if (_isEmpty) {
      DialogMessageSender.sendTranslateDialog(
        Alert.AlertType.WARNING, 
        "sendMail.noStudentDataHeader", 
        "sendMail.noStudentDataHeader", 
        "sendMail.noStudentData");
      return;
    }
    final Collection<StudentSheet> studentSheets = this.serviceGraduation.getStudentSheets();
    int _xifexpression = (int) 0;
    boolean _isEmpty_1 = nameList.isEmpty();
    boolean _not = (!_isEmpty_1);
    if (_not) {
      final Function1<StudentSheet, Boolean> _function = (StudentSheet x) -> {
        boolean _contains = nameList.contains(x.getStudentID());
        return Boolean.valueOf((!_contains));
      };
      int _size = IterableExtensions.size(IterableExtensions.<StudentSheet>filter(studentSheets, _function));
      _xifexpression = ((int) _size);
    } else {
      _xifexpression = (-1);
    }
    final int nbSheetWithoutName = _xifexpression;
    String _translate = LanguageManager.translate("sendMail.noStudentDataHeader");
    String _xifexpression_1 = null;
    if ((nbSheetWithoutName > 1)) {
      _xifexpression_1 = String.format(LanguageManager.translate("sendMail.notAllStudent"), Integer.valueOf(nbSheetWithoutName));
    } else {
      _xifexpression_1 = LanguageManager.translate("sendMail.notAllStudent1");
    }
    DialogMessageSender.sendDialog(
      Alert.AlertType.WARNING, _translate, _xifexpression_1, 
      null);
    DirectoryChooser dirChooser = new DirectoryChooser();
    String _property = System.getProperty("user.home");
    String _property_1 = System.getProperty("file.separator");
    String _plus = (_property + _property_1);
    String _plus_1 = (_plus + 
      "Documents");
    File _file = new File(_plus_1);
    dirChooser.setInitialDirectory(_file);
    Stage _stage = new Stage();
    File directory = dirChooser.showDialog(_stage);
    if ((directory == null)) {
      ControllerRoot.logger.warn("Directory not chosen");
    } else {
      this.graduationController.exportGraduationToPdf(directory);
    }
  }
  
  @FXML
  public void sendMail() {
    try {
      final Config config = ConfigurationManager.instance;
      final boolean rightConfig = (((Objects.equal(config.getEmail(), "") || Objects.equal(config.getEmailPassword(), "")) || Objects.equal(config.getMailHost(), "")) || 
        (config.getMailPort() == 0));
      if (rightConfig) {
        DialogMessageSender.sendTranslateDialog(
          Alert.AlertType.ERROR, 
          "error", 
          "sendMail.noCredentialTitle", 
          "sendMail.noCredentialBody");
      } else {
        final FXMLLoader loader = new FXMLLoader();
        loader.setResources(LanguageManager.getCurrentBundle());
        final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/SendMailUI.fxml"));
        final Stage dialog = new Stage();
        loader.<ControllerSendMail>getController().init(this.serviceGraduation, this.graduationController);
        dialog.setTitle(LanguageManager.translate("menu.edit.sendmail"));
        ObservableList<Image> _icons = dialog.getIcons();
        InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
        Image _image = new Image(_inputStreamResource);
        _icons.add(_image);
        Scene _scene = new Scene(view, 672, 416);
        dialog.setScene(_scene);
        dialog.setResizable(false);
        dialog.show();
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @FXML
  public void loadStudentCopiesPressed() {
    try {
      this.tabPane.getSelectionModel().select(this.correctorTab);
      final FXMLLoader loader = new FXMLLoader();
      loader.setResources(LanguageManager.getCurrentBundle());
      final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/GraduationCreatorUI.fxml"));
      final Stage dialog = new Stage();
      dialog.setTitle(LanguageManager.translate("menu.file.loadStudentSheet"));
      ObservableList<Image> _icons = dialog.getIcons();
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
      Image _image = new Image(_inputStreamResource);
      _icons.add(_image);
      loader.<ControllerGraduationCreator>getController().initialize(this.serviceGraduation, this.editionController, 
        this.graduationController, this);
      Scene _scene = new Scene(view, 384, 405);
      dialog.setScene(_scene);
      dialog.setResizable(false);
      dialog.show();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @FXML
  public void exportToSheets() {
    try {
      final FXMLLoader loader = new FXMLLoader();
      loader.setResources(LanguageManager.getCurrentBundle());
      final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/StudentSheetExportUI.fxml"));
      final Stage dialog = new Stage();
      dialog.setTitle(LanguageManager.translate("menu.file.exportToExam"));
      ObservableList<Image> _icons = dialog.getIcons();
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
      Image _image = new Image(_inputStreamResource);
      _icons.add(_image);
      loader.<ControllerStudentSheetExport>getController().initialize(this.editionController, this.serviceEdition);
      Scene _scene = new Scene(view, 384, 160);
      dialog.setScene(_scene);
      dialog.setResizable(false);
      dialog.show();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @FXML
  public void exportStudentsQrCodes() {
    try {
      final FXMLLoader loader = new FXMLLoader();
      loader.setResources(LanguageManager.getCurrentBundle());
      final Parent view = loader.<Parent>load(
        ResourcesUtils.getInputStreamResource("viewResources/StudentsQrCodeDocGeneratorUI.fxml"));
      final Stage dialog = new Stage();
      dialog.setTitle(LanguageManager.translate("menu.file.exportStudentsQrCodes"));
      ObservableList<Image> _icons = dialog.getIcons();
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
      Image _image = new Image(_inputStreamResource);
      _icons.add(_image);
      Scene _scene = new Scene(view, 384, 374);
      dialog.setScene(_scene);
      dialog.setResizable(false);
      dialog.show();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @FXML
  public void gradeExport() {
    try {
      final FXMLLoader loader = new FXMLLoader();
      loader.setResources(LanguageManager.getCurrentBundle());
      final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/GradeExportUI.fxml"));
      final Stage dialog = new Stage();
      dialog.setTitle(LanguageManager.translate("menu.edit.gradeExport"));
      ObservableList<Image> _icons = dialog.getIcons();
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
      Image _image = new Image(_inputStreamResource);
      _icons.add(_image);
      loader.<ControllerGradeExport>getController().initialize(this.graduationController);
      Scene _scene = new Scene(view, 384, 160);
      dialog.setScene(_scene);
      dialog.setResizable(false);
      dialog.show();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @FXML
  public void saveCorrection() {
    this.graduationController.saveExam();
  }
  
  @FXML
  public boolean toggleAutoZoom() {
    return this.graduationController.setToAutoZoom(Boolean.valueOf(this.autoZoom.isSelected()));
  }
  
  public void init(final ServiceEdition serviceEdition, final ServiceGraduation serviceGraduation) {
    this.serviceEdition = serviceEdition;
    this.serviceGraduation = serviceGraduation;
    this.saveGraduationButton.disableProperty().bind(this.graduationController.getLoadedModel().not());
    this.saveTemplateButton.disableProperty().bind(this.editionController.getLoadedModel().not());
    this.exportToExamButton.disableProperty().bind(this.editionController.getLoadedModel().not());
    this.loadStudentNamesButton.disableProperty().bind(this.graduationController.getLoadedModel().not());
    this.pdfExportButton.disableProperty().bind(this.graduationController.getLoadedModel().not());
    this.sendMailButton.disableProperty().bind(this.graduationController.getLoadedModel().not());
    this.pdfExportGradeButton.disableProperty().bind(this.graduationController.getLoadedModel().not());
    this.linkManuallySheetsButton.disableProperty().bind(this.graduationController.getLoadedModel().not());
  }
  
  @Override
  public void initialize(final URL location, final ResourceBundle resources) {
  }
  
  @FXML
  public void openGuide() {
    Desktop _xifexpression = null;
    boolean _isDesktopSupported = Desktop.isDesktopSupported();
    if (_isDesktopSupported) {
      _xifexpression = Desktop.getDesktop();
    } else {
      _xifexpression = null;
    }
    final Desktop desktop = _xifexpression;
    if (((desktop != null) && desktop.isSupported(Desktop.Action.BROWSE))) {
      try {
        desktop.browse(URI.create(LanguageManager.translate("guide.link")));
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          final Exception e = (Exception)_t;
          e.printStackTrace();
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    }
  }
  
  @FXML
  public Optional<ButtonType> openAbout() {
    Optional<ButtonType> _xblockexpression = null;
    {
      final Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle(LanguageManager.translate("menu.help.about"));
      alert.setHeaderText(LanguageManager.translate("about.title"));
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("istic_logo.png");
      final Image image = new Image(_inputStreamResource);
      final ImageView imageView = new ImageView(image);
      alert.setGraphic(imageView);
      alert.setContentText("BEUREL Luca, CARUANA Romain, COCHET Julien, DANLOS Benjamin, DEGAS Antoine, DERRIEN Steven, GHOUTI TERKI Rida, MA Qian, GIRAUDET Théo, GUIBERT Thomas, LALANDE MARCHAND Arthur, LELOUP Alexis, LOCKE Stefan, LUMBROSO Marius, PAYS Matthieu​");
      alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
      _xblockexpression = alert.showAndWait();
    }
    return _xblockexpression;
  }
  
  @Pure
  public ControllerFxGraduation getGraduationController() {
    return this.graduationController;
  }
  
  public void setGraduationController(final ControllerFxGraduation graduationController) {
    this.graduationController = graduationController;
  }
  
  @Pure
  public ControllerFxEdition getEditionController() {
    return this.editionController;
  }
  
  public void setEditionController(final ControllerFxEdition editionController) {
    this.editionController = editionController;
  }
  
  @Pure
  public ControllerFxStudents getStudentsController() {
    return this.studentsController;
  }
  
  public void setStudentsController(final ControllerFxStudents studentsController) {
    this.studentsController = studentsController;
  }
}
