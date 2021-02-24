package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.Exam;
import fr.istic.tools.scanexam.core.Page;
import fr.istic.tools.scanexam.core.Question;
import java.util.Collections;
import java.util.List;

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
    return ExamSingleton.instance.getPages().get(pageId).getQuestions().get(questionid);
  }
  
  /**
   * Rend la liste des Questions définies dans un Examen
   * @return List<Question>
   * @author degas
   */
  public static List<Question> getQuestions(final int pageId) {
    return Collections.<Question>unmodifiableList(ExamSingleton.instance.getPages().get(pageId).getQuestions());
  }
  
  public static Page getPage(final int pageId) {
    return ExamSingleton.instance.getPages().get(pageId);
  }
}
