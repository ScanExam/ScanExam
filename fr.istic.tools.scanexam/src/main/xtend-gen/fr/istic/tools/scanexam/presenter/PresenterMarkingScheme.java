package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.Presenter;
import fr.istic.tools.scanexam.services.ExamEditionService;
import java.util.Objects;

/**
 * Class to manage an exam marking scheme (french : barème)
 * @author Benjamin Danlos
 */
@SuppressWarnings("all")
public class PresenterMarkingScheme {
  /**
   * Presenter for the creation view
   */
  private Presenter presenter;
  
  private ExamEditionService service;
  
  /**
   * Constructor
   * Constructs a Presenter manipulating the grading schele with the modele
   * @param {@link ExamEditionService} s : the Service API linked with the model
   * @param {@link Presenter} p : a Presenter used by the view
   * @author Benjamin Danlos
   */
  public PresenterMarkingScheme(final ExamEditionService s, final Presenter p) {
    Objects.<ExamEditionService>requireNonNull(s);
    Objects.<Presenter>requireNonNull(p);
    this.presenter = p;
    this.service = s;
  }
  
  /**
   * setter for the Presenter attribute
   * @param {@link Presenter} p : instance of the presenter (not null)
   */
  public Presenter setPresenterVueCreation(final Presenter pres) {
    Presenter _xblockexpression = null;
    {
      Objects.<Presenter>requireNonNull(pres);
      _xblockexpression = this.presenter = pres;
    }
    return _xblockexpression;
  }
  
  /**
   * @return current {@link Presenter}
   */
  public Presenter getPresenter() {
    return this.presenter;
  }
  
  /**
   * Ajoute une nouvelle entrée à la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle ajouter l'entrée
   * @param desc la description de l'entrée
   * @param point le nombre de point de l'entrée
   * @return l'ID de l'entrée
   */
  public int addEntry(final int questionId, final String desc, final float point) {
    return this.service.addEntry(questionId, desc, point);
  }
  
  /**
   * Modifie une entrée de la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle modifier l'entrée
   * @param gradeEntryId l'ID de l'entrée à modifier
   * @param desc la nouvelle description de l'entrée
   * @param point le nouveau nombre de point de l'entrée
   */
  public void modifyEntry(final int questionId, final int gradeEntryId, final String desc, final float point) {
    this.service.modifyEntry(questionId, gradeEntryId, desc, point);
  }
  
  /**
   * Supprime une entrée de la liste des points attribuable à la question
   * @param questionId l'ID de la question dans laquelle supprimer l'entrée
   * @param gradeEntryId l'ID de l'entrée à supprimer
   */
  public boolean removeEntry(final int questionId, final int gradeEntryId) {
    return this.service.removeEntry(questionId, gradeEntryId);
  }
}
