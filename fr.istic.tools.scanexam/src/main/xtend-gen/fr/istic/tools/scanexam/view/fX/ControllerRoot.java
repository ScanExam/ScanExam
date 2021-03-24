package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.view.fX.corrector.ControllerFXCorrector;
import fr.istic.tools.scanexam.view.fX.editor.ControllerFXEditor;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class ControllerRoot {
  @FXML
  private Tab correctorTab;
  
  @FXML
  private Tab editorTab;
  
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
  public String sendMail() {
    return InputOutput.<String>println("sending mail");
  }
}
