package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.config.ConfigurationManager;
import fr.istic.tools.scanexam.core.config.Config;
import java.util.Locale;

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
   */
  public void updateConfig(final Locale language, final String email, final String emailPassword, final String emailHost, final String emailPort) {
    this.config.setLanguage(language);
    this.config.setEmail(email);
    this.config.setEmailPassword(emailPassword);
    this.config.setMailHost(emailHost);
    this.config.setMailPort(emailPort);
  }
  
  /**
   * GETTERS
   */
  public Config getConfig() {
    return this.config;
  }
}
