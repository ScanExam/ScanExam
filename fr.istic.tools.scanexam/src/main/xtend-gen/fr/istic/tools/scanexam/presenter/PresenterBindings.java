package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.EditorPresenter;
import fr.istic.tools.scanexam.presenter.GraduationPresenter;
import fr.istic.tools.scanexam.services.ExamEditionService;
import fr.istic.tools.scanexam.services.ExamGraduationService;
import fr.istic.tools.scanexam.view.Adapter;

@SuppressWarnings("all")
public class PresenterBindings {
  public static void linkEditorPresenter(final Adapter<EditorPresenter> adapter) {
    final ExamEditionService service = new ExamEditionService();
    final EditorPresenter presenter = new EditorPresenter(adapter, service);
    adapter.setPresenter(presenter);
  }
  
  public static void linkGraduationPresenter(final Adapter<GraduationPresenter> adapter) {
    final ExamGraduationService service = new ExamGraduationService();
    final GraduationPresenter presenter = new GraduationPresenter(adapter, service);
    adapter.setPresenter(presenter);
  }
}
