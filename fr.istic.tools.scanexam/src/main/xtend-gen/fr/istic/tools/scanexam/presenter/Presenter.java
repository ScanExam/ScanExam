package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.PresenterMarkingScheme;
import fr.istic.tools.scanexam.presenter.PresenterPdf;

/**
 * Interface des presenters
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public interface Presenter {
  PresenterPdf getPresenterPdf();
  
  PresenterMarkingScheme getPresenterMarkingScheme();
}
