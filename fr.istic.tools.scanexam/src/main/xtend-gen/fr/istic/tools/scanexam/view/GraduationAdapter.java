package fr.istic.tools.scanexam.view;

import fr.istic.tools.scanexam.presenter.GraduationPresenter;
import fr.istic.tools.scanexam.view.Adapter;
import java.util.List;

/**
 * Interface d'adaptateur du correcteur d'examen
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public interface GraduationAdapter extends Adapter<GraduationPresenter> {
  List<String> questionNames();
  
  /**
   * @return next question
   */
  void nextQuestion();
  
  /**
   * @param question is the actual question
   * @return previous question
   */
  void previousQuestion();
  
  void thisQuestion(final int index);
}
