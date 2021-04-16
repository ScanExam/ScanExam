package fr.istic.tools.scanexam;

import fr.istic.tools.scanexam.config.ConfigurationManager;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.launcher.LauncherFX;
import fr.istic.tools.scanexam.services.ServiceEdition;
import fr.istic.tools.scanexam.services.ServiceGraduation;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

@SuppressWarnings("all")
public class Main {
  private static final Logger logger = LogManager.getLogger();
  
  public static void main(final String[] args) {
    Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.ALL);
    ConfigurationManager.init();
    LanguageManager.init(ConfigurationManager.instance.getLanguage());
    final ServiceEdition serviceEdition = new ServiceEdition();
    final ServiceGraduation serviceGraduation = new ServiceGraduation();
    LauncherFX.launchApp(serviceEdition, serviceGraduation);
  }
}
