package fr.istic.tools.scanexam.view.fX;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;

@SuppressWarnings("all")
public class ControllerRoot {
  @FXML
  private Tab correctorTab;
  
  @FXML
  private Tab editorTab;
  
  public void setEditor(final Node n) {
    this.editorTab.setContent(n);
  }
  
  public void setCorrector(final Node n) {
    this.correctorTab.setContent(n);
  }
}
