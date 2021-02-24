package fr.istic.tools.scanexam.launcher;

import fr.istic.tools.scanexam.launcher.Launcher;
import fr.istic.tools.scanexam.view.EditorAdapterSwing;
import java.awt.EventQueue;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Classe pour lancer directement la vue en utilisant la librairie Swing
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class LauncherSwing implements Launcher {
  /**
   * METHODES
   */
  @Override
  public void setup() {
  }
  
  /**
   * Lancement de l'application Swing
   */
  @Override
  public void launch() {
    final Runnable _function = () -> {
      try {
        new EditorAdapterSwing();
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          final Exception e = (Exception)_t;
          e.printStackTrace();
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    };
    EventQueue.invokeLater(_function);
  }
}
