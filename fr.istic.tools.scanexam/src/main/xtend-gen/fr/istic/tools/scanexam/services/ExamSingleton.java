package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.Exam;
import fr.istic.tools.scanexam.core.Page;
import fr.istic.tools.scanexam.core.Question;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Function;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pair;

/**
 * Classe permettant de manipuler directement l'Exam
 */
@SuppressWarnings("all")
final class ExamSingleton {
  /**
   * Instance Singleton de l'Examen
   */
  public static Exam instance = null;
  
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
  public static Collection<Question> getQuestions(final int pageId) {
    return Collections.<Question>unmodifiableCollection(ExamSingleton.instance.getPages().get(pageId).getQuestions());
  }
  
  /**
   * @param absoluteQuestionId la position absolue d'une question dans l'Examen
   * @return la Question associée à cette position si elle existe, Optional.empty sinon
   */
  public static Optional<Question> getQuestionFromIndex(final int absoluteQuestionId) {
    final Function1<Page, EList<Question>> _function = (Page p) -> {
      return p.getQuestions();
    };
    final Function1<Pair<Integer, Question>, Boolean> _function_1 = (Pair<Integer, Question> p) -> {
      Integer _key = p.getKey();
      return Boolean.valueOf(((_key).intValue() == absoluteQuestionId));
    };
    final Function<Pair<Integer, Question>, Question> _function_2 = (Pair<Integer, Question> q) -> {
      return q.getValue();
    };
    return Optional.<Pair<Integer, Question>>ofNullable(
      IterableExtensions.<Pair<Integer, Question>>findFirst(IterableExtensions.<Question>indexed(IterableExtensions.<Page, Question>flatMap(ExamSingleton.instance.getPages(), _function)), _function_1)).<Question>map(_function_2);
  }
  
  /**
   * @return le nombre de pages de l'Examen
   */
  public static int getTemplatePageAmount() {
    return ExamSingleton.instance.getPages().size();
  }
  
  /**
   * @param pageId l'ID de la page à récupérer
   * @return la Page dont l'ID est <i>pageId</i>
   */
  public static Page getPage(final int pageId) {
    return ExamSingleton.instance.getPages().get(pageId);
  }
}
