package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.PresenterGradeScale;
import fr.istic.tools.scanexam.presenter.PresenterPdf;

/**
 * Interface des presenters
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public interface Presenter {
  PresenterPdf getPresenterPdf();
  
  PresenterGradeScale getPresenterMarkingScheme();
}
