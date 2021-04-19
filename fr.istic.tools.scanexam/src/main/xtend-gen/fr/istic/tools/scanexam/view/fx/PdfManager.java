package fr.istic.tools.scanexam.view.fx;

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
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Controlleur du pdf
 * @author Benjamin Danlos, Julien Cochet
 */
@SuppressWarnings("all")
public class PdfManager {
  /**
   * Pdf chargé
   */
  private PDDocument document;
  
  /**
   * Index de la page courante du modèle d'exam
   */
  @Accessors
  private int pdfPageIndex;
  
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
  
  /**
   * Retourne la page courante
   * @return Page courante
   */
  public BufferedImage getCurrentPdfPage() {
    return this.pageToImage(this.document.getPages().get(this.pdfPageIndex));
  }
  
  /**
   * Passe à la page suivante s'il y en a une. Ne fait rien sinon
   */
  public int nextPdfPage() {
    int _xifexpression = (int) 0;
    int _size = IterableExtensions.size(this.document.getPages());
    boolean _lessThan = ((this.pdfPageIndex + 1) < _size);
    if (_lessThan) {
      _xifexpression = this.pdfPageIndex++;
    }
    return _xifexpression;
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
  
  /**
   * Renvoie le numéro du page courant
   * @return Numéro de page courant
   */
  public int currentPdfPageNumber() {
    return this.pdfPageIndex;
  }
  
  /**
   * Transforme une PDPage en BufferedImage
   * @param page PDPage à convertir
   * @return Image convertie
   */
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
   * Charger le PDF à partir d'un fichier
   * @param file PDF
   */
  public PDDocument create(final File file) {
    try {
      PDDocument _xblockexpression = null;
      {
        Objects.<File>requireNonNull(file);
        byte[] _readAllBytes = Files.readAllBytes(Path.of(file.getAbsolutePath()));
        final InputStream input = new ByteArrayInputStream(_readAllBytes);
        _xblockexpression = this.create(input);
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Charge le PDF à partir d'un input stream
   * @param stream InputStream du PDF
   */
  public PDDocument create(final InputStream input) {
    try {
      PDDocument _xblockexpression = null;
      {
        ByteArrayOutputStream _byteArrayOutputStream = new ByteArrayOutputStream();
        this.pdfOutput = _byteArrayOutputStream;
        input.transferTo(this.pdfOutput);
        _xblockexpression = this.document = PDDocument.load(this.getPdfInputStream());
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Retourne vrai si la page courante est celle passée en paramètre
   * @param page Numéro de page à vérifier
   * @return True si la page courante est celle passée en paramètre, false sinon
   */
  public boolean atCorrectPage(final int page) {
    int _currentPdfPageNumber = this.currentPdfPageNumber();
    return (page == _currentPdfPageNumber);
  }
  
  /**
   * Retourne le nombre total de page du pdf
   * @return Nombre total de page du pdf
   */
  public int getPdfPageCount() {
    return IterableExtensions.size(this.document.getPages());
  }
  
  /**
   * Recupère l'output stream du pdf courant
   * @return ByteArrayOutputStream du pdf courant
   */
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
   * Recupère l'input stream du pdf courant
   * @return ByteArrayInputStream du pdf courant
   */
  public ByteArrayInputStream getPdfInputStream() {
    byte[] _byteArray = this.pdfOutput.toByteArray();
    return new ByteArrayInputStream(_byteArray);
  }
  
  /**
   * Renvoie le document
   * @return Document
   */
  public PDDocument getPdfDocument() {
    return this.document;
  }
  
  @Pure
  public int getPdfPageIndex() {
    return this.pdfPageIndex;
  }
  
  public void setPdfPageIndex(final int pdfPageIndex) {
    this.pdfPageIndex = pdfPageIndex;
  }
}
