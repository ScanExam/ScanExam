package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.Presenter;
import fr.istic.tools.scanexam.presenter.PresenterMarkingScheme;
import fr.istic.tools.scanexam.presenter.PresenterPdf;
import fr.istic.tools.scanexam.presenter.PresenterQRCode;
import fr.istic.tools.scanexam.presenter.PresenterQuestionZone;
import fr.istic.tools.scanexam.services.ExamEditionService;
import fr.istic.tools.scanexam.view.Adapter;
import java.util.Objects;

/**
 * Class defining the presenter for the exam creation view(s)
 * and linking with more concrete sub presenters
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class EditorPresenter implements Presenter {
  /**
   * Bidirectional associations with the concrete presenters
   * and main controller of the view
   */
  private PresenterQRCode presQRCode;
  
  private PresenterQuestionZone presQuestionZone;
  
  private PresenterMarkingScheme presMarkingScheme;
  
  private EditorPresenter editorPresenter;
  
  private ExamEditionService service;
  
  private PresenterPdf presPdf;
  
  private Adapter<EditorPresenter> adapter;
  
  public EditorPresenter(final Adapter<EditorPresenter> adapter, final ExamEditionService service) {
    Objects.<ExamEditionService>requireNonNull(service);
    this.service = service;
    Objects.<Adapter<EditorPresenter>>requireNonNull(adapter);
    this.adapter = adapter;
    PresenterPdf _presenterPdf = new PresenterPdf(service, this);
    this.presPdf = _presenterPdf;
    PresenterQuestionZone _presenterQuestionZone = new PresenterQuestionZone(service, this);
    this.presQuestionZone = _presenterQuestionZone;
    PresenterMarkingScheme _presenterMarkingScheme = new PresenterMarkingScheme(service, this);
    this.presMarkingScheme = _presenterMarkingScheme;
  }
  
  /**
   * @return current {@link PresenterQRCode}
   */
  public PresenterQRCode getPresenterQRCode() {
    return this.presQRCode;
  }
  
  /**
   * @return current {@link PresenterQuestionZone}
   */
  public PresenterQuestionZone getPresenterQuestionZone() {
    return this.presQuestionZone;
  }
  
  /**
   * @return current {@link PresenterMarkingScheme}
   */
  @Override
  public PresenterMarkingScheme getPresenterMarkingScheme() {
    return this.presMarkingScheme;
  }
  
  @Override
  public PresenterPdf getPresenterPdf() {
    return this.presPdf;
  }
  
  public void save(final String path) {
    this.service.save(path);
  }
  
  public boolean load(final String path) {
    return this.service.open(path);
  }
  
  public void close() {
    System.exit(0);
  }
  
  public int getQuestionId() {
    return this.service.getQuestionId();
  }
  
  public int addGradeItem(final String name, final double points) {
    return 0;
  }
  
  public Object updateGradeItem(final int gradeItemId, final String name, final double points) {
    return null;
  }
  
  public Object removeGradeItem(final int gradeItemId) {
    return null;
  }
}
