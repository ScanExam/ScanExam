package fr.istic.tools.scanexam.view.fX;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.mailing.SendMailTls;
import fr.istic.tools.scanexam.presenter.PresenterConfiguration;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.fX.utils.ShakeEffect;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Classe pour gérer la fenêtre de configuration en JavaFX
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class AdapterFXConfiguration implements Initializable {
  /**
   * Controlleur de la configuration
   */
  private PresenterConfiguration presConfig;
  
  /**
   * Pane principale de la vue
   */
  @FXML
  public VBox mainPane;
  
  /**
   * Sélection de la langue
   */
  @FXML
  public ComboBox<String> cmbBxLanguage;
  
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
   * Bouton de sauvegarde de la configuration
   */
  @FXML
  public Button btnSave;
  
  /**
   * Bouton pour tester le login à l'adresse mail
   */
  @FXML
  public Button btnCheckMail;
  
  /**
   * Constructeur. Initialise les différents champs avec la valeur actuelle de la configuration
   */
  @Override
  public void initialize(final URL location, final ResourceBundle resources) {
    PresenterConfiguration _presenterConfiguration = new PresenterConfiguration();
    this.presConfig = _presenterConfiguration;
    this.cmbBxLanguage.setValue(this.presConfig.getLanguage());
    this.txtFldEmail.setText(this.presConfig.getEmail());
    this.pwdFldEmailPassword.setText(this.presConfig.getEmailPassword());
    this.txtFldEmailHost.setText(this.presConfig.getMailHost());
    this.txtFldEmailPort.setText(this.presConfig.getMailPort());
    this.cmbBxLanguage.setItems(FXCollections.<String>observableArrayList(this.presConfig.getLanguages()));
    final UnaryOperator<TextFormatter.Change> _function = (TextFormatter.Change change) -> {
      TextFormatter.Change _xifexpression = null;
      boolean _matches = Pattern.compile("\\d*").matcher(change.getText()).matches();
      if (_matches) {
        _xifexpression = change;
      } else {
        _xifexpression = null;
      }
      return _xifexpression;
    };
    TextFormatter<String> _textFormatter = new TextFormatter<String>(_function);
    this.txtFldEmailPort.setTextFormatter(_textFormatter);
    final ChangeListener<Boolean> _function_1 = (ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) -> {
      if ((!(newVal).booleanValue())) {
        boolean _equals = this.txtFldEmailPort.getText().equals("");
        if (_equals) {
          this.txtFldEmailPort.setText("0");
        }
      }
    };
    this.txtFldEmailPort.focusedProperty().addListener(_function_1);
    final ChangeListener<Boolean> _function_2 = (ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) -> {
      if ((!(newVal).booleanValue())) {
        this.verifyMailAddress();
      }
    };
    this.txtFldEmail.focusedProperty().addListener(_function_2);
    final EventHandler<ActionEvent> _function_3 = (ActionEvent e) -> {
      this.checkMail();
    };
    this.btnCheckMail.setOnAction(_function_3);
  }
  
  @FXML
  public void saveAndQuit() {
    final boolean needRestart = this.presConfig.updateConfig(this.cmbBxLanguage.getValue(), this.txtFldEmail.getText(), this.pwdFldEmailPassword.getText(), 
      this.txtFldEmailHost.getText(), this.txtFldEmailPort.getText());
    if (needRestart) {
      final Alert alert = new Alert(Alert.AlertType.INFORMATION);
      Window _window = alert.getDialogPane().getScene().getWindow();
      final Stage stage = ((Stage) _window);
      ObservableList<Image> _icons = stage.getIcons();
      InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
      Image _image = new Image(_inputStreamResource);
      _icons.add(_image);
      alert.setAlertType(Alert.AlertType.CONFIRMATION);
      alert.setTitle(LanguageManager.translate("config.restartDialog.title"));
      alert.setHeaderText(LanguageManager.translate("config.restartDialog.text"));
      alert.showAndWait();
    }
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
  
  /**
   * Vérifie si l'adresse mail saisie est syntaxiquement valide. Si oui, remplie automatiquement le port et l'host si ceux-ci sont connus.
   * Si non, bloque la validation du formulaire (jusqu'à syntaxe valide ou textfield vide).
   */
  public void verifyMailAddress() {
    final PseudoClass errorClass = PseudoClass.getPseudoClass("wrong-format");
    if ((((this.txtFldEmail.getText() != null) && (!Objects.equal(this.txtFldEmail.getText(), ""))) && (!this.presConfig.checkEmailFormat(this.txtFldEmail.getText())))) {
      this.txtFldEmail.pseudoClassStateChanged(errorClass, true);
      ShakeEffect.shake(this.txtFldEmail);
      this.btnSave.setDisable(true);
    } else {
      this.txtFldEmail.pseudoClassStateChanged(errorClass, false);
      this.btnSave.setDisable(false);
      if (((this.txtFldEmail.getText() != null) && (!Objects.equal(this.txtFldEmail.getText(), "")))) {
        final Pair<String, String> infos = this.presConfig.getSmtpInfos(this.txtFldEmail.getText());
        if (((this.txtFldEmailHost.getText() == null) || Objects.equal(this.txtFldEmailHost.getText(), ""))) {
          this.txtFldEmailHost.setText(infos.getKey());
        }
        if (((this.txtFldEmailPort.getText() == null) || Objects.equal(this.txtFldEmailPort.getText(), "0"))) {
          this.txtFldEmailPort.setText(infos.getValue());
        }
      }
    }
  }
  
  /**
   * @param name l'adresse mail du login
   * @param password le mot de passe du login
   * @param host l'host SMTP
   * @param port le port SMTP
   * @return true si le programme a réussi à se connecter à l'adresse mail, false sinon
   */
  public void checkMail() {
    final Task<SendMailTls.LoginResult> _function = new Task<SendMailTls.LoginResult>() {
      @Override
      protected SendMailTls.LoginResult call() throws Exception {
        SendMailTls.LoginResult _xblockexpression = null;
        {
          AdapterFXConfiguration.this.mainPane.getScene().setCursor(Cursor.WAIT);
          final SendMailTls.LoginResult result = AdapterFXConfiguration.this.presConfig.checkLogin(AdapterFXConfiguration.this.txtFldEmail.getText(), AdapterFXConfiguration.this.pwdFldEmailPassword.getText(), AdapterFXConfiguration.this.txtFldEmailHost.getText(), 
            AdapterFXConfiguration.this.txtFldEmailPort.getText());
          AdapterFXConfiguration.this.mainPane.getScene().setCursor(Cursor.DEFAULT);
          _xblockexpression = result;
        }
        return _xblockexpression;
      }
    };
    final Task<SendMailTls.LoginResult> task = _function;
    final Service<SendMailTls.LoginResult> _function_1 = new Service<SendMailTls.LoginResult>() {
      @Override
      protected Task<SendMailTls.LoginResult> createTask() {
        return task;
      }
    };
    final Service<SendMailTls.LoginResult> service = _function_1;
    final EventHandler<WorkerStateEvent> _function_2 = (WorkerStateEvent e) -> {
      this.sendCheckMailResult(service.getValue());
    };
    service.setOnSucceeded(_function_2);
    service.start();
  }
  
  /**
   * Affiche une boîte de dialogue prévenant l'utilisateur de la réussite ou non de la connexion à son adresse mail
   * @param result un LoginResult
   */
  public void sendCheckMailResult(final SendMailTls.LoginResult result) {
    final Alert alert = new Alert(Alert.AlertType.NONE);
    Window _window = alert.getDialogPane().getScene().getWindow();
    final Stage stage = ((Stage) _window);
    ObservableList<Image> _icons = stage.getIcons();
    InputStream _inputStreamResource = ResourcesUtils.getInputStreamResource("logo.png");
    Image _image = new Image(_inputStreamResource);
    _icons.add(_image);
    boolean _equals = Objects.equal(result, SendMailTls.LoginResult.SUCCESS);
    if (_equals) {
      alert.setAlertType(Alert.AlertType.CONFIRMATION);
      alert.setTitle(LanguageManager.translate("config.emailConfirmationDialog.title"));
      alert.setHeaderText(LanguageManager.translate("config.emailConfirmationDialog.success"));
    } else {
      alert.setAlertType(Alert.AlertType.ERROR);
      alert.setTitle(LanguageManager.translate("config.emailConfirmationDialog.title"));
      boolean _equals_1 = Objects.equal(result, SendMailTls.LoginResult.HOST_NOT_FOUND);
      if (_equals_1) {
        alert.setHeaderText(LanguageManager.translate("config.emailConfirmationDialog.hostNotFound"));
      } else {
        alert.setHeaderText(LanguageManager.translate("config.emailConfirmationDialog.badLogin"));
      }
    }
    alert.showAndWait();
  }
}
