package fr.istic.tools.scanexam.services;

import fr.istic.tools.scanexam.core.Page;
import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.QuestionZone;
import fr.istic.tools.scanexam.services.ExamSingleton;
import java.awt.image.BufferedImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
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
   * Index de la page courante du modèle d'exam
   */
  private int pdfPageIndex;
  
  /**
   * Index de la page courante du modèle d'exam
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
  
  /**
   * Change la page courante par la page du numéro envoyé en paramètre (ne change rien si la page n'existe pas)
   * @param page Numéro de page où se rendre
   */
  public int goToPdfPage(final int page) {
    int _xifexpression = (int) 0;
    if (((page >= 0) && (page < IterableExtensions.size(this.document.getPages())))) {
      _xifexpression = this.pdfPageIndex = page;
    }
    return _xifexpression;
  }
  
  public BufferedImage getCurrentPdfPage() {
    return this.pageToImage(this.document.getPages().get(this.pdfPageIndex));
  }
  
  public int nextPdfPage() {
    int _xblockexpression = (int) 0;
    {
      InputOutput.<String>println((("-" + Integer.valueOf(this.pdfPageIndex)) + "-"));
      int _xifexpression = (int) 0;
      int _size = IterableExtensions.size(this.document.getPages());
      boolean _lessThan = ((this.pdfPageIndex + 1) < _size);
      if (_lessThan) {
        _xifexpression = this.pdfPageIndex++;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * Change la page courante par la page la précédent si elle existe (ne change rien sinon)
   */
  public int previousPdfPage() {
    int _xifexpression = (int) 0;
    if ((this.pdfPageIndex > 0)) {
      _xifexpression = this.pdfPageIndex--;
    }
    return _xifexpression;
  }
  
  public int currentPdfPageNumber() {
    return this.pdfPageIndex;
  }
  
  public int getPdfsize() {
    return IterableExtensions.size(this.document.getPages());
  }
  
  public BufferedImage pageToImage(final PDPage page) {
    try {
      BufferedImage _xblockexpression = null;
      {
        final PDFRenderer renderer = new PDFRenderer(this.document);
        final BufferedImage bufferedImage = renderer.renderImageWithDPI(this.pdfPageIndex, 300, ImageType.RGB);
        _xblockexpression = bufferedImage;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * EXAM RELATED
   */
  protected Page getCurrentPage() {
    return ExamSingleton.getPage(this.pageIndex);
  }
  
  protected int nextPage() {
    int _xifexpression = (int) 0;
    int _length = ((Object[])Conversions.unwrapArray(ExamSingleton.instance.getPages(), Object.class)).length;
    boolean _lessThan = ((this.pageIndex + 1) < _length);
    if (_lessThan) {
      _xifexpression = this.pageIndex++;
    }
    return _xifexpression;
  }
  
  public int previousPage() {
    int _xifexpression = (int) 0;
    if ((this.pageIndex > 0)) {
      _xifexpression = this.pageIndex--;
    }
    return _xifexpression;
  }
  
  public Question getQuestion(final int id) {
    EList<Page> _pages = ExamSingleton.instance.getPages();
    for (final Page page : _pages) {
      {
        final Function1<Question, Boolean> _function = (Question question) -> {
          int _id = question.getId();
          return Boolean.valueOf((_id == id));
        };
        final Question question = IterableExtensions.<Question>findFirst(page.getQuestions(), _function);
        if ((question != null)) {
          return question;
        }
      }
    }
    return null;
  }
  
  public EList<Question> getQuestionAtPage(final int pageIndex) {
    return ExamSingleton.getPage(pageIndex).getQuestions();
  }
  
  /**
   * Retourne la zone associée à une question
   * @param index Index de la question
   * @author degas
   */
  public QuestionZone getQuestionZone(final int index) {
    return ExamSingleton.getQuestion(this.pageIndex, index).getZone();
  }
  
  /**
   * @return le numéro de la page courante dans le PDF courant
   */
  public int getCurrentPageNumber() {
    return this.pdfPageIndex;
  }
  
  @Pure
  public PDDocument getDocument() {
    return this.document;
  }
  
  public void setDocument(final PDDocument document) {
    this.document = document;
  }
}
