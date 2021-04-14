package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.Page;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.services.ExamSingleton;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

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
  
  public EList<Question> getQuestionAtPage(final int pageIndex) {
    return ExamSingleton.getPage(pageIndex).getQuestions();
  }
  
  public void onDocumentLoad(final int pdfPageCount) {
  }
  
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
