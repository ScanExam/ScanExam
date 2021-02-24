package fr.istic.tools.scanexam.view;

import fr.istic.tools.scanexam.core.Question;
import java.util.List;

@SuppressWarnings("all")
public interface ControllerI {
  void showQuestion(final Question question);
  
  void initQuestionNames(final List<String> names);
}
