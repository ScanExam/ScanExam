package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.presenter.PresenterConfiguration;
import java.util.Locale;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Classe pour gérer la fenêtre de configuration en JavaFX
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class AdapterFXConfiguration {
  /**
   * Controlleur de la configuration
   */
  private PresenterConfiguration presConfig;
  
  /**
   * Pane principale de la vue
   */
  @FXML
  public Pane mainPane;
  
  /**
   * Sélection de la langue
   */
  @FXML
  public ComboBox<Locale> cmbBxLanguage;
  
  /**
   * Champ de l'email
   */
  @FXML
  public TextField txtFldEmail;
  
  /**
   * Champ du mot de passe de l'email
   */
  @FXML
  public PasswordField pwdFldEmailPassword;
  
  /**
   * Champ de l'hébergeur de l'email
   */
  @FXML
  public TextField txtFldEmailHost;
  
  /**
   * Champ du port de l'email
   */
  @FXML
  public TextField txtFldEmailPort;
  
  /**
   * Constructeur. Initialise les différents champs avec la valeur actuelle de la configuration
   */
  public AdapterFXConfiguration() {
    PresenterConfiguration _presenterConfiguration = new PresenterConfiguration();
    this.presConfig = _presenterConfiguration;
  }
  
  @FXML
  public void saveAndQuit() {
    this.presConfig.updateConfig(this.cmbBxLanguage.getValue(), this.txtFldEmail.getText(), this.pwdFldEmailPassword.getText(), this.txtFldEmailHost.getText(), this.txtFldEmailPort.getText());
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
  public void setPresenterConfiguration(final PresenterConfiguration presConfig) {
    this.presConfig = presConfig;
  }
}
