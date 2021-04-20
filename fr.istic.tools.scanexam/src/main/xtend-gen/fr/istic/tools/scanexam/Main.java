package fr.istic.tools.scanexam;

import fr.istic.tools.scanexam.config.ConfigurationManager;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.launcher.LauncherFX;
import fr.istic.tools.scanexam.services.ServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;

@SuppressWarnings("all")
public class Main {
  public static void main(final String[] args) {
    Configurator.setAllLevels(LogManager.getRootLogger().getName(), Level.ALL);
    ConfigurationManager.init();
    LanguageManager.init(ConfigurationManager.instance.getLanguage());
    final ServiceImpl service = new ServiceImpl();
    LauncherFX.launchApp(service, service);
  }
}
