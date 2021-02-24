package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.PresenterVueCorrection;
import fr.istic.tools.scanexam.presenter.PresenterVueCreation;
import fr.istic.tools.scanexam.services.ExamEditionService;
import fr.istic.tools.scanexam.services.ExamGraduationService;
import fr.istic.tools.scanexam.view.Adapter;

@SuppressWarnings("all")
public class PresenterLinker {
  public void linkEditorPresenter(final Adapter adapter) {
    final ExamEditionService service = new ExamEditionService();
    final PresenterVueCreation presenter = new PresenterVueCreation(adapter, service);
    adapter.setPresenter(presenter);
  }
  
  public void linkGraduationPresenter(final Adapter adapter) {
    final ExamGraduationService service = new ExamGraduationService();
    final PresenterVueCorrection presenter = new PresenterVueCorrection(adapter, service);
    adapter.setPresenter(presenter);
  }
}
