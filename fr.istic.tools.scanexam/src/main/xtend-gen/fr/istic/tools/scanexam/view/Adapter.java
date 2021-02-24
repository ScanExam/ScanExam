package fr.istic.tools.scanexam.view;

import fr.istic.tools.scanexam.view.CorrectorAdapter;
import fr.istic.tools.scanexam.view.EditorAdapter;

/**
 * Interface d'adaptateur général
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public interface Adapter {
  /**
   * Renvoie l'adaptateur du correcteur d'examen
   * @return correctorAdapter
   */
  CorrectorAdapter getCorrectorAdapter();
  
  /**
   * Renvoie l'adaptateur de l'éditeur d'examen
   * @return editorAdapter
   */
  EditorAdapter getEditorAdapter();
}
