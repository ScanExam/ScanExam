package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.Page;
import fr.istic.tools.scanexam.core.QuestionZone;
import fr.istic.tools.scanexam.services.ExamSingleton;
import java.io.InputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public abstract class Service {
  /**
   * Pdf chargé
   */
  @Accessors
  private PDDocument document;
  
  /**
   * Index de la page courante
   */
  private int pageIndex;
  
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
  
  public InputStream getCurrentPdfPage() {
    try {
      return this.document.getPage(this.pageIndex).getContents();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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
  
  @Pure
  public PDDocument getDocument() {
    return this.document;
  }
  
  public void setDocument(final PDDocument document) {
    this.document = document;
  }
}
