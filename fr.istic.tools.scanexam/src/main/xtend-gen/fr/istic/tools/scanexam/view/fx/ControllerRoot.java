package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.presenter.PresenterConfiguration;
import fr.istic.tools.scanexam.presenter.PresenterStudentSheetExport;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fx.AdapterFxConfiguration;
import fr.istic.tools.scanexam.view.fx.AdapterFxStudentListLoader;
import fr.istic.tools.scanexam.view.fx.ControllerCorrectionLoader;
import fr.istic.tools.scanexam.view.fx.ControllerGraduationLoader;
import fr.istic.tools.scanexam.view.fx.ControllerStudentSheetExport;
import fr.istic.tools.scanexam.view.fx.editor.ControllerFxEdition;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class ControllerRoot implements Initializable {
  @FXML
  private Tab correctorTab;
  
  @FXML
  private Tab editorTab;
  
  @FXML
  private CheckMenuItem autoZoom;
  
  private ControllerFxGraduation corrector;
  
  private ControllerFxEdition editor;
  
  public ControllerFxEdition setEditorController(final ControllerFxEdition editor) {
    return this.editor = editor;
  }
  
  public ControllerFxGraduation setCorrectorController(final ControllerFxGraduation corrector) {
    return this.corrector = corrector;
  }
  
  public void setEditor(final Node n) {
    this.editorTab.setContent(n);
  }
  
  public void setCorrector(final Node n) {
    this.correctorTab.setContent(n);
  }
  
  @FXML
  public void loadTemplatePressedEditor() {
    this.editor.loadTemplatePressed();
  }
  
  @FXML
  public void loadTemplatePressedCorrector() {
    try {
      final FXMLLoader loader = new FXMLLoader();
      loader.setResources(LanguageManager.getCurrentBundle());
      final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/CorrectionLoaderUI.FXML"));
      final Stage dialog = new Stage();
      dialog.setTitle(LanguageManager.translate("menu.file.loadGraduation"));
      ObservableList<Image> _icons = dialog.getIcons();
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
      Image _image = new Image(_inputStreamResource);
      _icons.add(_image);
      loader.<ControllerCorrectionLoader>getController().initialize(this.corrector.getAdapter().getPresenter().getPresenterCorrectionLoader());
      Scene _scene = new Scene(view, 384, 355);
      dialog.setScene(_scene);
      dialog.setResizable(false);
      dialog.show();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @FXML
  public void createNewTemplatePressed() {
    this.editor.newTemplatePressed();
  }
  
  @FXML
  public void SaveTemplatePressed() {
    this.editor.saveTemplatePressed();
  }
  
  @FXML
  public void loadStudentList() {
    try {
      final FXMLLoader loader = new FXMLLoader();
      loader.setResources(LanguageManager.getCurrentBundle());
      final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/StudentListLoaderUI.FXML"));
      final Stage dialog = new Stage();
      dialog.setTitle(LanguageManager.translate("menu.file.loadStudentList"));
      ObservableList<Image> _icons = dialog.getIcons();
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
      Image _image = new Image(_inputStreamResource);
      _icons.add(_image);
      loader.<AdapterFxStudentListLoader>getController().initialize(this.corrector.getAdapter().getPresenter().getPresenterStudentList());
      Scene _scene = new Scene(view, 384, 160);
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
      final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/ConfigUI.FXML"));
      final Stage dialog = new Stage();
      dialog.setTitle(LanguageManager.translate("menu.edit.updateconfig"));
      ObservableList<Image> _icons = dialog.getIcons();
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
      Image _image = new Image(_inputStreamResource);
      _icons.add(_image);
      AdapterFxConfiguration _controller = loader.<AdapterFxConfiguration>getController();
      PresenterConfiguration _presenterConfiguration = new PresenterConfiguration();
      _controller.initialize(_presenterConfiguration);
      Scene _scene = new Scene(view, 384, 280);
      dialog.setScene(_scene);
      dialog.setResizable(false);
      dialog.show();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @FXML
  public void sendMail() {
    try {
      final FXMLLoader loader = new FXMLLoader();
      loader.setResources(LanguageManager.getCurrentBundle());
      final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/SendMailUI.FXML"));
      final Stage dialog = new Stage();
      dialog.setTitle(LanguageManager.translate("menu.edit.sendmail"));
      ObservableList<Image> _icons = dialog.getIcons();
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
      Image _image = new Image(_inputStreamResource);
      _icons.add(_image);
      Scene _scene = new Scene(view, 672, 416);
      dialog.setScene(_scene);
      dialog.setResizable(false);
      dialog.show();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @FXML
  public void loadStudentCopiesPressed() {
    try {
      final FXMLLoader loader = new FXMLLoader();
      loader.setResources(LanguageManager.getCurrentBundle());
      final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/StudentSheetLoaderUI.FXML"));
      final Stage dialog = new Stage();
      dialog.setTitle(LanguageManager.translate("menu.file.loadStudentSheet"));
      ObservableList<Image> _icons = dialog.getIcons();
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
      Image _image = new Image(_inputStreamResource);
      _icons.add(_image);
      loader.<ControllerGraduationLoader>getController().initialize(this.corrector.getAdapter().getPresenter().getPresenterStudentSheet());
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
      final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/StudentSheetExportUI.FXML"));
      final Stage dialog = new Stage();
      dialog.setTitle(LanguageManager.translate("menu.file.exportToExam"));
      ObservableList<Image> _icons = dialog.getIcons();
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
      Image _image = new Image(_inputStreamResource);
      _icons.add(_image);
      ControllerStudentSheetExport _controller = loader.<ControllerStudentSheetExport>getController();
      PresenterStudentSheetExport _presenterStudentSheetExport = new PresenterStudentSheetExport();
      _controller.initialize(_presenterStudentSheetExport);
      Scene _scene = new Scene(view, 384, 107);
      dialog.setScene(_scene);
      dialog.setResizable(false);
      dialog.show();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @FXML
  public boolean toggleAutoZoom() {
    return this.corrector.setToAutoZoom(Boolean.valueOf(this.autoZoom.isSelected()));
  }
  
  public void initialize(final URL location, final ResourceBundle resources) {
  }
}
