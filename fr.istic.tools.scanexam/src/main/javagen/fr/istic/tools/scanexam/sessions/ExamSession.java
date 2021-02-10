package fr.istic.tools.scanexam.sessions;

import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.sessions.Session;
import java.io.File;

/**
 * Representer l'état courant de l'interface graphique
 * doit elle être stoquée dans une classe faisant parti de l'API ?
 * 
 * 
 * Factory -> on créer une session en passant la précedente en paramètre.
 */
@SuppressWarnings("all")
public class ExamSession extends Session {
  /**
   * Permet de lier une Question q à une zone du PDF définie par un Rectangle R
   * @param q Une Question
   * @param r Un Rectangle
   * @author degas
   */
  public void addQuestion(final Question q) {
    this.getPage().getQuestions().add(q);
  }
  
  /**
   * Supprime une question
   * @param index Index de la question à supprimer
   * 
   * @author degas
   */
  public void removeQuestion(final int index) {
    this.getPage().getQuestions().remove(index);
  }
  
  @Override
  public void save() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  @Override
  public void open(final File xmiFile) {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
}
