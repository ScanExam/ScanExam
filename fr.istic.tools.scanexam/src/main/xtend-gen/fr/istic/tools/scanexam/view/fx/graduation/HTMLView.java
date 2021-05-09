package fr.istic.tools.scanexam.view.fx.graduation;

import fr.istic.tools.scanexam.view.fx.graduation.Grader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

@SuppressWarnings("all")
public class HTMLView implements Initializable {
  @FXML
  private HTMLEditor html;
  
  public static Grader.GradeItem item;
  
  /**
   * Permet de ne pouvoir qu'un seul éditeur à la fois
   */
  public static boolean isHTMLEditorOpen = false;
  
  public static Stage stage;
  
  @Override
  public void initialize(final URL location, final ResourceBundle resources) {
  }
  
  @FXML
  public void onMouseClicked() {
    String htmlText = this.html.getHtmlText();
    boolean _contains = htmlText.contains("contenteditable=\"true\"");
    if (_contains) {
      htmlText = htmlText.replace("contenteditable=\"true\"", "contenteditable=\"false\"");
    }
    boolean _contains_1 = htmlText.contains("body ");
    if (_contains_1) {
      htmlText = htmlText.replace("body ", "body style=\"height: 130; max-width: 130; padding-right: 10;\" ");
    }
    HTMLView.item.changeText(htmlText);
    HTMLView.stage.close();
  }
}
