package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.PresenterEdition;
import fr.istic.tools.scanexam.presenter.PresenterGraduation;
import fr.istic.tools.scanexam.services.ServiceEdition;
import fr.istic.tools.scanexam.services.ServiceGraduation;
import fr.istic.tools.scanexam.view.Adapter;

@SuppressWarnings("all")
public class PresenterBindings {
  public static void linkEditorPresenter(final Adapter<PresenterEdition> adapter) {
    final ServiceEdition service = new ServiceEdition();
    final PresenterEdition presenter = new PresenterEdition(adapter, service);
    adapter.setPresenter(presenter);
  }
  
  public static void linkGraduationPresenter(final Adapter<PresenterGraduation> adapter) {
    final ServiceGraduation service = new ServiceGraduation();
    final PresenterGraduation presenter = new PresenterGraduation(adapter, service);
    adapter.setPresenter(presenter);
  }
}
