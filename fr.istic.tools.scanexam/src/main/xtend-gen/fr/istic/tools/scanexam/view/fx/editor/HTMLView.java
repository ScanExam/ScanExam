package fr.istic.tools.scanexam.view.fx.editor;

import javafx.fxml.FXML;
import javafx.scene.web.HTMLEditor;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class HTMLView {
  @FXML
  private HTMLEditor html;
  
  /**
   * Permet de ne pouvoir qu'un seul éditeur à la fois
   */
  public static boolean isHTMLEditorOpen = false;
  
  @FXML
  private String onMouseClicked() {
    return InputOutput.<String>println(this.html.getHtmlText());
  }
}
