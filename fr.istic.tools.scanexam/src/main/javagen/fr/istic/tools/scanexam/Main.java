package fr.istic.tools.scanexam;

import fr.istic.tools.scanexam.config.ConfigurationManager;
import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.view.MainJavaFX;
import fr.istic.tools.scanexam.view.MainSwing;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("all")
public class Main {
  /**
   * Représente la library graphique choisi par l'utilisateur pour le programme
   */
  public enum GraphicLib {
    JAVAFX,
    
    SWING;
  }
  
  /**
   * Library graphique à utiliser par défaut.
   */
  private static final Main.GraphicLib DEFAULT_LIB = Main.GraphicLib.SWING;
  
  public static void main(final String[] args) {
    LanguageManager.init();
    ConfigurationManager.init();
    LanguageManager.change(ConfigurationManager.instance.getLanguage());
    Main.launchView(Main.getUiLib(args));
  }
  
  public static void launchView(final Main.GraphicLib graphicLib) {
    if (graphicLib != null) {
      switch (graphicLib) {
        case JAVAFX:
          MainJavaFX.launchApp(null);
          break;
        case SWING:
          MainSwing.launchApp();
          break;
        default:
          break;
      }
    }
  }
  
  /**
   * Parse les arguments d'entrée du programme et retourne l'ID correspondant à la library graphique choisie.<br/>
   * Déclenche l'arrêt du programme si il y a plus d'argument spécifié ou que l'argument spécifié n'est pas un argument valide.
   * @param args un tableau d'arguments (non null)
   * @return {@link GraphicLib}.JAVAFX si JavaFX est choisi, {@link GraphicLib}.Swing si Swing est choisi, {@link #DEFAULT_LIB DEFAULT_LIB} si <b>args</b> est vide.
   */
  public static Main.GraphicLib getUiLib(final String... args) {
    Objects.<String[]>requireNonNull(args);
    int _length = args.length;
    boolean _equals = (_length == 1);
    if (_equals) {
      boolean _equals_1 = (args[0]).equals("-javafx");
      if (_equals_1) {
        return Main.GraphicLib.JAVAFX;
      } else {
        boolean _equals_2 = (args[0]).equals("-swing");
        if (_equals_2) {
          return Main.GraphicLib.SWING;
        } else {
          Logger _global = Logger.getGlobal();
          String _get = args[0];
          String _plus = ("Argument " + _get);
          String _plus_1 = (_plus + " is not a valid argument!");
          _global.log(Level.SEVERE, _plus_1);
          System.exit(0);
        }
      }
    } else {
      int _length_1 = args.length;
      boolean _greaterThan = (_length_1 > 1);
      if (_greaterThan) {
        Logger.getGlobal().log(Level.SEVERE, "Too many arguments! Only \'-javafx\' or \'-swing\' are accepted.");
        System.exit(0);
      }
    }
    return Main.DEFAULT_LIB;
  }
}
