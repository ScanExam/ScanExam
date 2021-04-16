package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.Page;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.services.ExamSingleton;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Façade abstraite des opérations communes des services
 * @author Antoine Degas, Marius Lumbroso, Théo Giraudet
 */
@SuppressWarnings("all")
public abstract class Service {
  /**
   * @return Identifiant de l'examen
   * @author degas
   */
  public int getExamId() {
    return ExamSingleton.instance.getId();
  }
  
  /**
   * @return Nom de l'examen
   * @author degas
   */
  public String getExamName() {
    return ExamSingleton.instance.getName();
  }
  
  /**
   * @param l'ID de la Question
   * @return la Question du modèle correspondant à l'ID spécifié
   */
  public Question getQuestion(final int id) {
    EList<Page> _pages = ExamSingleton.instance.getPages();
    for (final Page page : _pages) {
      {
        final Function1<Question, Boolean> _function = (Question question) -> {
          int _id = question.getId();
          return Boolean.valueOf((_id == id));
        };
        final Question question = IterableExtensions.<Question>findFirst(page.getQuestions(), _function);
        if ((question != null)) {
          return question;
        }
      }
    }
    return null;
  }
  
  /**
   * @param pageIndex l'ID d'une page
   * @return la liste des Questions sur la page dont l'ID est <i>pageIndex</i>
   */
  public EList<Question> getQuestionAtPage(final int pageIndex) {
    return ExamSingleton.getPage(pageIndex).getQuestions();
  }
  
  /**
   * Crée un nouveau modèle côté données
   * @param pageNumber le nombre de pages du modèle
   */
  public void onDocumentLoad(final int pdfPageCount) {
  }
  
  /**
   * @return vrai si un modèle d'examen est chargé, false sinon
   */
  public boolean hasExamLoaded() {
    return (ExamSingleton.instance != null);
  }
  
  /**
   * Met à jour le nom de l'examen
   * @param name Nouevau nom de l'examen
   */
  public void setExamName(final String name) {
    ExamSingleton.instance.setName(name);
  }
}
