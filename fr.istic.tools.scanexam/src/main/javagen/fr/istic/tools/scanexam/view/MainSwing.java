package fr.istic.tools.scanexam.view;

import fr.istic.tools.scanexam.box.BoxList;
import fr.istic.tools.scanexam.controller.PdfPresenterSwing;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import fr.istic.tools.scanexam.view.ExamCreationSwingView;
import java.awt.EventQueue;
import java.io.InputStream;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Classe pour lancer directement la vue en utilisant la librairie Swing
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class MainSwing {
  /**
   * Lancement de l'application Swing ---->  launchApp(presenter(session));
   */
  public static void launchApp() {
    final Runnable _function = () -> {
      try {
        InputStream pdfInput = ResourcesUtils.getInputStreamResource("/viewResources/pfo_example.pdf");
        BoxList _boxList = new BoxList();
        PdfPresenterSwing pdfPresenteur = new PdfPresenterSwing(1280, 720, pdfInput, _boxList);
        ExamCreationSwingView window = new ExamCreationSwingView(pdfPresenteur);
        window.getWindow().setVisible(true);
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
