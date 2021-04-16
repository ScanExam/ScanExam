package fr.istic.tools.scanexam.presenter;

import fr.istic.tools.scanexam.presenter.Presenter;
import fr.istic.tools.scanexam.services.api.Service;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

/**
 * Controlleur du pdf
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class PresenterPdf {
  /**
   * Pdf chargé
   */
  private PDDocument document;
  
  /**
   * Index de la page courante du modèle d'exam
   */
  protected int pdfPageIndex;
  
  private Presenter presenter;
  
  /**
   * Association with the model via the Service API
   */
  private Service service;
  
  /**
   * Largeur de la fenêtre
   */
  protected int width;
  
  /**
   * Hauteur de la fenêtre
   */
  protected int height;
  
  /**
   * InputStream du pdf
   */
  protected ByteArrayOutputStream pdfOutput;
  
  /**
   * Constructor
   * @author Benjamin Danlos
   * @param {@link EditorPresenter} (not null)
   * @param {@link GraduationPresenter} (not null)
   * Constructs a PDFPresenter object.
   */
  public PresenterPdf(final Service s, final Presenter p) {
    Objects.<Service>requireNonNull(s);
    Objects.<Presenter>requireNonNull(p);
    this.service = s;
    this.presenter = p;
  }
  
  /**
   * set {@link Presenter} for the association
   * @param {@link Presenter} presenter to make the association with
   * @author Benjamin Danlos
   */
  public Presenter setPresenter(final Presenter p) {
    Presenter _xblockexpression = null;
    {
      Objects.<Presenter>requireNonNull(p);
      _xblockexpression = this.presenter = p;
    }
    return _xblockexpression;
  }
  
  /**
   * returns the current presenter associated
   * @return {@link Presenter}
   * @author Benjamin Danlos
   */
  public Presenter getPresenter() {
    return this.presenter;
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
   * Crée le modèle de l'examen
   * @param name Nom du modèle d'examen
   * @param file Fichier du modèle d'examen
   */
  public void create(final String name, final File file) {
    try {
      Objects.<File>requireNonNull(file);
      byte[] _readAllBytes = Files.readAllBytes(Path.of(file.getAbsolutePath()));
      final InputStream input = new ByteArrayInputStream(_readAllBytes);
      ByteArrayOutputStream _byteArrayOutputStream = new ByteArrayOutputStream();
      this.pdfOutput = _byteArrayOutputStream;
      input.transferTo(this.pdfOutput);
      this.document = PDDocument.load(file);
      this.service.onDocumentLoad(IterableExtensions.size(this.document.getPages()));
      this.service.setExamName(name);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public PDDocument create(final ByteArrayInputStream stream) {
    try {
      PDDocument _xblockexpression = null;
      {
        ByteArrayOutputStream _byteArrayOutputStream = new ByteArrayOutputStream();
        this.pdfOutput = _byteArrayOutputStream;
        stream.transferTo(this.pdfOutput);
        _xblockexpression = this.document = PDDocument.load(this.getPdfInputStream());
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public boolean atCorrectPage(final int page) {
    int _currentPdfPageNumber = this.currentPdfPageNumber();
    return (page == _currentPdfPageNumber);
  }
  
  public int getPdfPageCount() {
    return IterableExtensions.size(this.document.getPages());
  }
  
  public ByteArrayOutputStream getPdfOutputStream() {
    try {
      ByteArrayOutputStream _xblockexpression = null;
      {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        this.document.save(outputStream);
        _xblockexpression = outputStream;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * @return un InputStream vers le PDF
   */
  public ByteArrayInputStream getPdfInputStream() {
    byte[] _byteArray = this.pdfOutput.toByteArray();
    return new ByteArrayInputStream(_byteArray);
  }
}
