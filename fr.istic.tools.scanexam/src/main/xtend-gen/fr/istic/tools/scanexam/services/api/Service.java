package fr.istic.tools.scanexam.services.api;

import fr.istic.tools.scanexam.core.Question;
import java.util.List;

/**
 * Interface de typage
 * @author Antoine Degas, Marius Lumbroso, Théo Giraudet
 */
@SuppressWarnings("all")
public interface Service {
  /**
   * @return le nombre de pages de l'Examen
   */
  int getTemplatePageAmount();
  
  /**
   * @return vrai si un modèle d'examen est chargé, false sinon
   */
  boolean hasExamLoaded();
  
  /**
   * Met à jour le nom de l'examen
   * @param name Nouevau nom de l'examen
   */
  void setExamName(final String name);
  
  /**
   * @return Identifiant de l'examen
   * @author degas
   */
  int getExamId();
  
  /**
   * @return Nom de l'examen
   * @author degas
   */
  String getExamName();
  
  /**
   * @param pageIndex l'ID d'une page
   * @return la liste des Questions sur la page dont l'ID est <i>pageIndex</i>
   */
  List<Question> getQuestionAtPage(final int pageIndex);
}
