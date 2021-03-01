package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.Page;
import fr.istic.tools.scanexam.core.QuestionZone;
import fr.istic.tools.scanexam.services.ExamSingleton;
import java.awt.image.BufferedImage;
import java.util.List;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public abstract class Service {
  /**
   * Pdf chargé
   */
  @Accessors
  protected List<BufferedImage> pages;
  
  /**
   * Index de la page courante
   */
  protected int pageIndex;
  
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
  
  public BufferedImage getCurrentPdfPage() {
    return this.pages.get(this.pageIndex);
  }
  
  protected Page getCurrentPage() {
    return ExamSingleton.getPage(this.pageIndex);
  }
  
  /**
   * Retourne la zone associée à une question
   * @param index Index de la question
   * @author degas
   */
  public QuestionZone getQuestionZone(final int index) {
    return ExamSingleton.getQuestion(this.pageIndex, index).getZone();
  }
  
  public abstract void save(final String path);
  
  public abstract void open(final String xmiFile);
  
  /**
   * Change la page courante par la page la suivant si elle existe (ne change rien sinon)
   */
  public abstract void nextPage();
  
  /**
   * Change la page courante par la page la précédent si elle existe (ne change rien sinon)
   */
  public abstract void previousPage();
  
  /**
   * @return le nombre de page du PDF courant
   */
  public int getPageNumber() {
    return this.pages.size();
  }
  
  /**
   * @return le numéro de la page courante dans le PDF courant
   */
  public int getCurrentPageNumber() {
    return this.pageIndex;
  }
  
  @Pure
  public List<BufferedImage> getPages() {
    return this.pages;
  }
  
  public void setPages(final List<BufferedImage> pages) {
    this.pages = pages;
  }
}