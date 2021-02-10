package fr.istic.tools.scanexam.sessions;

import fr.istic.tools.scanexam.core.Exam;
import fr.istic.tools.scanexam.core.Page;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.QuestionZone;
import java.io.File;
import java.util.Collections;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public abstract class Session {
  /**
   * Pdf chargé
   */
  @Accessors
  private PDDocument document;
  
  /**
   * Examen courant
   */
  private Exam exam;
  
  /**
   * Index de la page courante
   */
  private int pageIndex;
  
  /**
   * @return Identifiant de l'examen
   * @author degas
   */
  public int getId() {
    return this.exam.getId();
  }
  
  /**
   * @return Nom de l'examen
   * @author degas
   */
  public String getName() {
    return this.exam.getName();
  }
  
  /**
   * Permet de récupérer une Question
   * @param index Index de la question
   * @return Question Retourne une instance de Question
   * @author degas
   */
  public Question getQuestion(final int index) {
    return this.exam.getPages().get(this.pageIndex).getQuestions().get(index);
  }
  
  /**
   * Rend la liste des Questions définies dans un Examen
   * @return List<Question>
   * @author degas
   */
  public List<Question> getQuestions() {
    return Collections.<Question>unmodifiableList(this.exam.getPages().get(this.pageIndex).getQuestions());
  }
  
  public Page getPage() {
    return this.exam.getPages().get(this.pageIndex);
  }
  
  /**
   * Retourne la zone associée à une question
   * @param index Index de la question
   * @author degas
   */
  public QuestionZone getQuestionZone(final int index) {
    return this.getQuestion(index).getZone();
  }
  
  public abstract void save();
  
  public abstract void open(final File xmiFile);
  
  @Pure
  public PDDocument getDocument() {
    return this.document;
  }
  
  public void setDocument(final PDDocument document) {
    this.document = document;
  }
}
