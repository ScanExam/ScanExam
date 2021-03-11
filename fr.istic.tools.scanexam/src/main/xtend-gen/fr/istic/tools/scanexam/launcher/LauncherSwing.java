package fr.istic.tools.scanexam.launcher;

import fr.istic.tools.scanexam.launcher.Launcher;
import fr.istic.tools.scanexam.presenter.PresenterBindings;
import fr.istic.tools.scanexam.view.swing.GraduationAdapterSwing;
import java.awt.EventQueue;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Classe pour lancer directement la vue en utilisant la librairie Swing
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class LauncherSwing implements Launcher {
  /**
   * Lancement de l'application Swing
   */
  @Override
  public void launch() {
    final Runnable _function = () -> {
      try {
        GraduationAdapterSwing _graduationAdapterSwing = new GraduationAdapterSwing();
        PresenterBindings.linkGraduationPresenter(_graduationAdapterSwing);
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
