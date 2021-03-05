package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.Exam;
import fr.istic.tools.scanexam.core.Page;
import fr.istic.tools.scanexam.core.Question;
import java.util.Collection;
import java.util.Collections;

/**
 * A revoir ?
 */
@SuppressWarnings("all")
public final class ExamSingleton {
  protected static Exam instance = null;
  
  /**
   * Permet de récupérer une Question
   * @param index Index de la question
   * @return Question Retourne une instance de Question
   * @author degas
   */
  public static Question getQuestion(final int pageId, final int questionid) {
    return ExamSingleton.instance.getPages().get(pageId).getQuestions().get(Integer.valueOf(questionid));
  }
  
  /**
   * Rend la liste des Questions définies dans un Examen
   * @return List<Question>
   * @author degas
   */
  public static Collection<Question> getQuestions(final int pageId) {
    return Collections.<Question>unmodifiableCollection(ExamSingleton.instance.getPages().get(pageId).getQuestions().values());
  }
  
  public static Page getPage(final int pageId) {
    return ExamSingleton.instance.getPages().get(pageId);
  }
}
