package fr.istic.tools.scanexam.view.fx;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.config.ConfigurationManager;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.core.config.Config;
import fr.istic.tools.scanexam.exportation.SendMailTls;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.utils.extensions.LocaleExtensions;
import fr.istic.tools.scanexam.view.fx.Encryption;
import fr.istic.tools.scanexam.view.fx.component.FormattedTextField;
import fr.istic.tools.scanexam.view.fx.component.validator.EmailValidator;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Locale;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.crypto.spec.SecretKeySpec;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Classe pour gérer la fenêtre de configuration en JavaFX
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class ControllerConfiguration {
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
  public FormattedTextField txtFldEmail;
  
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
   * Configuration
   */
  private final Config config = ConfigurationManager.instance;
  
  /**
   * Clé de crytage et ses paramètres
   */
  private final char[] keyPassword = new Function0<char[]>() {
    @Override
    public char[] apply() {
      try {
        char[] _charArray = InetAddress.getLocalHost().getHostName().toCharArray();
        return _charArray;
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    }
  }.apply();
  
  private final byte[] salt = new String("12345678").getBytes();
  
  private final int iterationCount = 40000;
  
  private final int keyLength = 128;
  
  private final SecretKeySpec key = new Function0<SecretKeySpec>() {
    @Override
    public SecretKeySpec apply() {
      try {
        SecretKeySpec _createSecretKey = Encryption.createSecretKey(ControllerConfiguration.this.keyPassword, ControllerConfiguration.this.salt, ControllerConfiguration.this.iterationCount, ControllerConfiguration.this.keyLength);
        return _createSecretKey;
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    }
  }.apply();
  
  /**
   * Initialise les différents champs avec la valeur actuelle de la configuration
   */
  public void initialize() {
    try {
      this.cmbBxLanguage.setValue(LocaleExtensions.capitalizeDisplayName(LanguageManager.getCurrentLanguage()));
      this.txtFldEmail.setText(this.config.getEmail());
      String _xifexpression = null;
      String _emailPassword = this.config.getEmailPassword();
      boolean _tripleNotEquals = (_emailPassword != "");
      if (_tripleNotEquals) {
        _xifexpression = Encryption.decrypt(this.config.getEmailPassword(), this.key);
      } else {
        _xifexpression = "";
      }
      this.pwdFldEmailPassword.setText(_xifexpression);
      this.txtFldEmailHost.setText(this.config.getMailHost());
      this.txtFldEmailPort.setText(Integer.valueOf(this.config.getMailPort()).toString());
      this.cmbBxLanguage.setItems(FXCollections.<String>observableArrayList(this.getLanguages()));
      EmailValidator _emailValidator = new EmailValidator();
      this.txtFldEmail.addFormatValidator(_emailValidator);
      final ChangeListener<Boolean> _function = (ObservableValue<? extends Boolean> value, Boolean oldVal, Boolean newVal) -> {
        if (((!(newVal).booleanValue()) && (!this.txtFldEmail.getWrongFormatted()))) {
          this.completeHostInfos();
        }
      };
      this.txtFldEmail.focusedProperty().addListener(_function);
      this.btnSave.disableProperty().bind(this.txtFldEmail.wrongFormattedProperty());
      final EventHandler<ActionEvent> _function_1 = (ActionEvent e) -> {
        this.checkMail();
      };
      this.btnCheckMail.setOnAction(_function_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @FXML
  public void saveAndQuit() {
    final boolean needRestart = this.updateConfig(this.cmbBxLanguage.getValue(), this.txtFldEmail.getText(), this.pwdFldEmailPassword.getText(), 
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
  
  /**
   * Met à jour la configuration
   * @param language Nouvelle langue
   * @param email Nouvel email
   * @param emailPassword Nouveau mot de passe de l'email
   * @param emailHost Nouvel hébergeur de l'email
   * @param emailPort Nouveau port de l'email
   * @return true si le programme doit être redémarré pour que l'ensemble de la configuration soit prise en compte
   */
  public boolean updateConfig(final String language, final String email, final String emailPassword, final String emailHost, final String emailPort) {
    try {
      boolean needToRestart = false;
      final Function1<Locale, Boolean> _function = (Locale locale) -> {
        return Boolean.valueOf(language.equals(LocaleExtensions.capitalizeDisplayName(locale)));
      };
      final Locale newLocale = IterableExtensions.<Locale>findFirst(LanguageManager.getSupportedLocales(), _function);
      if ((newLocale == null)) {
        String _plus = (newLocale + " is not supported.");
        throw new IllegalArgumentException(_plus);
      }
      Locale _currentLanguage = LanguageManager.getCurrentLanguage();
      boolean _notEquals = (!Objects.equal(newLocale, _currentLanguage));
      needToRestart = _notEquals;
      this.config.setLanguage(newLocale.toString());
      this.config.setEmail(email);
      this.config.setEmailPassword(Encryption.encrypt(emailPassword, this.key));
      this.config.setMailHost(emailHost);
      this.config.setMailPort(Integer.parseInt(emailPort));
      ConfigurationManager.save();
      return needToRestart;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public Collection<String> getLanguages() {
    final Function1<Locale, String> _function = (Locale local) -> {
      return LocaleExtensions.capitalizeDisplayName(local);
    };
    return IterableExtensions.<String>toList(IterableExtensions.<Locale, String>map(LanguageManager.getSupportedLocales(), _function));
  }
  
  /**
   * @param name l'adresse mail du login
   * @param password le mot de passe du login
   * @param host l'host SMTP
   * @param port le port SMTP
   * @return true si le programme a réussi à se connecter à l'adresse mail, false sinon
   */
  public SendMailTls.LoginResult checkLogin(final String name, final String password, final String host, final String port) {
    String _xifexpression = null;
    if ((name == null)) {
      _xifexpression = "";
    } else {
      _xifexpression = name;
    }
    String _xifexpression_1 = null;
    if ((password == null)) {
      _xifexpression_1 = "";
    } else {
      _xifexpression_1 = password;
    }
    String _xifexpression_2 = null;
    if ((host == null)) {
      _xifexpression_2 = "";
    } else {
      _xifexpression_2 = host;
    }
    return SendMailTls.checkLogin(_xifexpression, _xifexpression_1, _xifexpression_2, Integer.parseInt(port));
  }
  
  /**
   * @param email une adresse email (non nulle)
   * @return une paire composée de l'Host et du Port SMTP pour cette adresse mail, si ceux-ci se trouvent dans le fichier mailing/configMailFile.properties
   */
  public Pair<String, String> getSmtpInfos(final String email) {
    return SendMailTls.getSmtpInformation(email);
  }
  
  @FXML
  public void quit() {
    Window _window = this.mainPane.getScene().getWindow();
    final Stage stage = ((Stage) _window);
    stage.close();
  }
  
  /**
   * Complète les informations de l'hôte à partir de l'adresse mail saisie par l'utilisateur, à condition que l'hôte soit connu
   */
  private void completeHostInfos() {
    String _text = this.txtFldEmail.getText();
    boolean _notEquals = (!Objects.equal(_text, ""));
    if (_notEquals) {
      final Pair<String, String> infos = this.getSmtpInfos(this.txtFldEmail.getText());
      this.txtFldEmailHost.setText(infos.getKey());
      this.txtFldEmailPort.setText(infos.getValue());
    }
  }
  
  /**
   * @param name l'adresse mail du login
   * @param password le mot de passe du login
   * @param host l'host SMTP
   * @param port le port SMTP
   * @return true si le programme a réussi à se connecter à l'adresse mail, false sinon
   */
  private void checkMail() {
    final Task<SendMailTls.LoginResult> _function = new Task<SendMailTls.LoginResult>() {
      @Override
      protected SendMailTls.LoginResult call() throws Exception {
        SendMailTls.LoginResult _xblockexpression = null;
        {
          ControllerConfiguration.this.mainPane.getScene().setCursor(Cursor.WAIT);
          ControllerConfiguration.this.mainPane.setDisable(true);
          final SendMailTls.LoginResult result = ControllerConfiguration.this.checkLogin(ControllerConfiguration.this.txtFldEmail.getText(), ControllerConfiguration.this.pwdFldEmailPassword.getText(), ControllerConfiguration.this.txtFldEmailHost.getText(), 
            ControllerConfiguration.this.txtFldEmailPort.getText());
          ControllerConfiguration.this.mainPane.setDisable(false);
          ControllerConfiguration.this.mainPane.getScene().setCursor(Cursor.DEFAULT);
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
  private void sendCheckMailResult(final SendMailTls.LoginResult result) {
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
