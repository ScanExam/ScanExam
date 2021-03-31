package fr.istic.tools.scanexam.presenter;

/**
 * Interface des presenters
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public interface Presenter {
  PresenterPdf getPresenterPdf();
  
  PresenterMarkingScheme getPresenterMarkingScheme();
}
