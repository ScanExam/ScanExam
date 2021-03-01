package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.Page;
import fr.istic.tools.scanexam.core.QuestionZone;
import fr.istic.tools.scanexam.services.ExamSingleton;
import java.awt.image.BufferedImage;
import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public abstract class Service {
  /**
   * Pdf chargé
   */
  @Accessors
  protected PDDocument document;
  
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
    return this.pageToImage(this.document.getPages().get(this.pageIndex));
  }
  
  public BufferedImage pageToImage(final PDPage page) {
    try {
      BufferedImage _xblockexpression = null;
      {
        final PDFRenderer renderer = new PDFRenderer(this.document);
        final BufferedImage bufferedImage = renderer.renderImageWithDPI(this.pageIndex, 300, ImageType.RGB);
        _xblockexpression = bufferedImage;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Change la page courante par la page la suivant si elle existe (ne change rien sinon)
   */
  public int nextPage() {
    int _xifexpression = (int) 0;
    int _size = IterableExtensions.size(this.document.getPages());
    boolean _lessThan = ((this.pageIndex + 1) < _size);
    if (_lessThan) {
      _xifexpression = this.pageIndex++;
    }
    return _xifexpression;
  }
  
  /**
   * Change la page courante par la page la précédent si elle existe (ne change rien sinon)
   */
  public int previousPage() {
    int _xifexpression = (int) 0;
    if ((this.pageIndex > 0)) {
      _xifexpression = this.pageIndex--;
    }
    return _xifexpression;
  }
  
  /**
   * Change la page courante par la page du numéro envoyé en paramètre (ne change rien si la page n'existe pas)
   * @param page Numéro de page où se rendre
   */
  public int goToPage(final int page) {
    int _xifexpression = (int) 0;
    if (((page >= 0) && (page < IterableExtensions.size(this.document.getPages())))) {
      _xifexpression = this.pageIndex = page;
    }
    return _xifexpression;
  }
  
  /**
   * @return le nombre de page du PDF courant
   */
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
  
  public abstract void create(final File file);
  
  public int getPageNumber() {
    return IterableExtensions.size(this.document.getPages());
  }
  
  /**
   * @return le numéro de la page courante dans le PDF courant
   */
  public int getCurrentPageNumber() {
    return this.pageIndex;
  }
  
  @Pure
  public PDDocument getDocument() {
    return this.document;
  }
  
  public void setDocument(final PDDocument document) {
    this.document = document;
  }
}
