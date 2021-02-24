package fr.istic.tools.scanexam.view;

/**
 * Interface d'adaptateur du correcteur d'examen
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public interface CorrectorAdapter {
  default void previousQuestion() {
  }
  
  default void nextQuestion() {
  }
}
