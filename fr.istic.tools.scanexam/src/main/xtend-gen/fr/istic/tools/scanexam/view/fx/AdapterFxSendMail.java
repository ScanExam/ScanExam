package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.presenter.PresenterSendMail;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Classe pour envoyer les corrigés par mail en JavaFX
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class AdapterFxSendMail {
  /**
   * Controlleur de la configuration
   */
  private PresenterSendMail presSendMail;
  
  /**
   * Pane principale de la vue
   */
  @FXML
  public Pane mainPane;
  
  /**
   * Champ de texte du titre
   */
  @FXML
  public TextField txtFldTitle;
  
  /**
   * Champ de texte du mail
   */
  @FXML
  public HTMLEditor htmlEditor;
  
  /**
   * Constructeur
   */
  public AdapterFxSendMail() {
    PresenterSendMail _presenterSendMail = new PresenterSendMail();
    this.presSendMail = _presenterSendMail;
  }
  
  @FXML
  public void saveAndQuit() {
    this.presSendMail.sendMails(this.txtFldTitle.getText(), this.htmlEditor.getHtmlText());
    this.quit();
  }
  
  @FXML
  public void quit() {
    Window _window = this.mainPane.getScene().getWindow();
    final Stage stage = ((Stage) _window);
    stage.close();
  }
  
  /**
   * SETTERS
   */
  public void setPresenterSendMail(final PresenterSendMail presSendMail) {
    this.presSendMail = presSendMail;
  }
}
