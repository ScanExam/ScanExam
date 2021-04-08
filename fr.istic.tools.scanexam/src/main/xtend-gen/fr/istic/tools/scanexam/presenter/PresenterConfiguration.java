package fr.istic.tools.scanexam.presenter;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.config.ConfigurationManager;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.core.config.Config;
import fr.istic.tools.scanexam.mailing.SendMailTls;
import java.util.Collection;
import java.util.Locale;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Classe pour gérer la fenêtre de configuration
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class PresenterConfiguration {
  /**
   * Configuration de l'application
   */
  private final Config config;
  
  /**
   * Constructeur
   */
  public PresenterConfiguration() {
    this.config = ConfigurationManager.instance;
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
    boolean needToRestart = false;
    final Function1<Locale, Boolean> _function = (Locale locale) -> {
      return Boolean.valueOf(language.equals(this.toDisplayName(locale)));
    };
    final Locale newLocale = IterableExtensions.<Locale>findFirst(LanguageManager.getSupportedLocales(), _function);
    if ((newLocale == null)) {
      String _plus = (newLocale + " is not supported.");
      throw new IllegalArgumentException(_plus);
    }
    Locale _currentLanguage = LanguageManager.getCurrentLanguage();
    boolean _notEquals = (!Objects.equal(newLocale, _currentLanguage));
    needToRestart = _notEquals;
    this.config.setLanguage(newLocale);
    this.config.setEmail(email);
    this.config.setEmailPassword(emailPassword);
    this.config.setMailHost(emailHost);
    this.config.setMailPort(Integer.parseInt(emailPort));
    ConfigurationManager.save();
    return needToRestart;
  }
  
  public String getLanguage() {
    return this.toDisplayName(LanguageManager.getCurrentLanguage());
  }
  
  public String getEmail() {
    return this.config.getEmail();
  }
  
  public String getEmailPassword() {
    return this.config.getEmailPassword();
  }
  
  public String getMailHost() {
    return this.config.getMailHost();
  }
  
  public String getMailPort() {
    return Integer.valueOf(this.config.getMailPort()).toString();
  }
  
  public Collection<String> getLanguages() {
    final Function1<Locale, String> _function = (Locale local) -> {
      return this.toDisplayName(local);
    };
    return IterableExtensions.<String>toList(IterableExtensions.<Locale, String>map(LanguageManager.getSupportedLocales(), _function));
  }
  
  /**
   * @param un locale
   * @return le displayname du locale dans sa propre langue
   */
  private String toDisplayName(final Locale locale) {
    final String name = locale.getDisplayName(locale);
    String _upperCase = name.substring(0, 1).toUpperCase();
    String _lowerCase = name.substring(1).toLowerCase();
    return (_upperCase + _lowerCase);
  }
  
  /**
   * @param email un string
   * @return true si <i>email</i> est une adresse email valide, false sinon
   */
  public boolean checkEmailFormat(final String email) {
    return SendMailTls.checkEmailFormat(email);
  }
  
  /**
   * @param name l'adresse mail du login
   * @param password le mot de passe du login
   * @param host l'host SMTP
   * @param port le port SMTP
   * @return true si le programme a réussi à se connecter à l'adresse mail, false sinon
   */
  public SendMailTls.LoginResult checkLogin(final String name, final String password, final String host, final String port) {
    return SendMailTls.checkLogin(name, password, host, Integer.parseInt(port));
  }
  
  /**
   * GETTERS
   */
  public Config getConfig() {
    return this.config;
  }
  
  /**
   * @param email une adresse email (non nulle)
   * @return une paire composée de l'Host et du Port SMTP pour cette adresse mail, si ceux-ci se trouvent dans le fichier mailing/configMailFile.properties
   */
  public Pair<String, String> getSmtpInfos(final String email) {
    return SendMailTls.getSmtpInformation(email);
  }
}
