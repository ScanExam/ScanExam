package fr.istic.tools.scanexam.launcher;

import fr.istic.tools.scanexam.launcher.Launcher;
import fr.istic.tools.scanexam.presenter.PresenterBindings;
import fr.istic.tools.scanexam.view.swing.EditionAdapterSwing;
import fr.istic.tools.scanexam.view.swing.EditionViewSwing;
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
   * METHODES
   */
  private static EditionAdapterSwing edit = new EditionAdapterSwing();
  
  private static GraduationAdapterSwing grad;
  
  /**
   * Lancement de l'application Swing
   */
  public void launch() {
    final Runnable _function = new Runnable() {
      public void run() {
        try {
          PresenterBindings.linkEditorPresenter(LauncherSwing.edit);
        } catch (final Throwable _t) {
          if (_t instanceof Exception) {
            final Exception e = (Exception)_t;
            e.printStackTrace();
          } else {
            throw Exceptions.sneakyThrow(_t);
          }
        }
      }
    };
    EventQueue.invokeLater(_function);
  }
  
  public static void swapToEditor() {
    PresenterBindings.linkEditorPresenter(LauncherSwing.edit);
  }
  
  public static void swapToGraduator(final EditionViewSwing view) {
    GraduationAdapterSwing _graduationAdapterSwing = new GraduationAdapterSwing();
    LauncherSwing.grad = _graduationAdapterSwing;
    LauncherSwing.grad.setViewEditor(view);
    PresenterBindings.linkGraduationPresenter(LauncherSwing.grad);
  }
}
