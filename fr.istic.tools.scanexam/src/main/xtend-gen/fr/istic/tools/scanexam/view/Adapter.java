package fr.istic.tools.scanexam.view;

import fr.istic.tools.scanexam.presenter.Presenter;

/**
 * Interface d'adaptateur général
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public interface Adapter<T extends Presenter> {
  void setPresenter(final T presenter);
}
