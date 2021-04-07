package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fX.corrector.ControllerFXCorrector;
import fr.istic.tools.scanexam.view.fX.editor.ControllerFXEditor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class ControllerRoot {
  @FXML
  private Tab correctorTab;
  
  @FXML
  private Tab editorTab;
  
  @FXML
  private CheckMenuItem autoZoom;
  
  private ControllerFXCorrector corrector;
  
  private ControllerFXEditor editor;
  
  public ControllerFXEditor setEditorController(final ControllerFXEditor editor) {
    return this.editor = editor;
  }
  
  public ControllerFXCorrector setCorrectorController(final ControllerFXCorrector corrector) {
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
    this.corrector.loadPressed();
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
  public Object LoadStudentCopiesPressed() {
    return null;
  }
  
  @FXML
  public void loadStudentList() {
    try {
      final FXMLLoader loader = new FXMLLoader();
      loader.setResources(LanguageManager.getCurrentBundle());
      final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/StudentListLoaderUI.fxml"));
      final Stage dialog = new Stage();
      dialog.setTitle(LanguageManager.translate("menu.file.loadStudentList"));
      Scene _scene = new Scene(view, 384, 160);
      dialog.setScene(_scene);
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
      Scene _scene = new Scene(view, 384, 280);
      dialog.setScene(_scene);
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
      final Parent view = loader.<Parent>load(ResourcesUtils.getInputStreamResource("viewResources/SendMailUI.fxml"));
      final Stage dialog = new Stage();
      dialog.setTitle(LanguageManager.translate("menu.edit.sendmail"));
      Scene _scene = new Scene(view, 672, 360);
      dialog.setScene(_scene);
      dialog.show();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @FXML
  public boolean toggleAutoZoom() {
    return this.corrector.setToAutoZoom(Boolean.valueOf(this.autoZoom.isSelected()));
  }
}
