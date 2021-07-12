package fr.istic.tools.scanexam.qrCode.reader;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import fr.istic.tools.scanexam.qrCode.QrCodeType;
import fr.istic.tools.scanexam.qrCode.reader.Copie;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderQrCodeImpl;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderThreadManager;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import javafx.util.Pair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

/**
 * Classe lisant la deuxième version des qr codes (avec préfixe)
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class PdfReaderQrCodeV2Impl extends PdfReaderQrCodeImpl {
  /**
   * Copies déjà vues, ne sert que pour les qr codes n'ayant que le numéro de page.
   * List: liste des copies, set : pages de cette copie
   */
  private List<Set<Integer>> pagesSeen;
  
  /**
   * Paterne séparant les différentes informations dans les qr codes
   */
  private final Pattern pattern = Pattern.compile("_");
  
  /**
   * Constructeur, n'est pas différent de celui de PdfReaderQrCodeImpl
   * @param input InputStream vers le document à analyser
   * @param docPath Chemin vers le document à analyser
   * @param nbPages Nombre de pages à analyser
   * @param qrPos Position où devrait se trouver le qr code principal (SHEET_PAGE ou PAGE)
   */
  public PdfReaderQrCodeV2Impl(final InputStream input, final String docPath, final int nbPages, final Pair<Float, Float> qrPos) {
    super(input, docPath, nbPages, qrPos);
  }
  
  /**
   * Crée un thread lisant des qr codes
   * @param nbPage nombre de copies désirées
   * @param doc document dans lequel insérer les Codes
   * @param docPath chemin du document dans lequel insérer les Codes
   * @param qrPos Position à laquelle devrait se trouver les qr codes
   */
  @Override
  public void createThread(final int nbPage, final PDDocument doc, final String docPath, final Pair<Float, Float> qrPos) {
    PDFRenderer _pDFRenderer = new PDFRenderer(doc);
    final int qrCodeType = this.findQrCodeType(_pDFRenderer, nbPage);
    if ((qrCodeType != (-1))) {
      final PdfReaderThreadManager manager = new PdfReaderThreadManager(nbPage, doc, docPath, qrPos, qrCodeType, this);
      manager.start();
    } else {
      this.logger.error("Cannot find QRCode in student papers");
    }
  }
  
  /**
   * Analyse les qr codes des plusieurs pages et traite les informations qu'ils contiennent
   * @param pdDoc Document à analyser
   * @param docPath Chemin du document à analyser
   * @param pdfRenderer Rendu du document à analyser
   * @param startPages Première page à analyser
   * @param endPages Dernière page à analyser
   * @param qrPos Position où devrait ce trouver le qr code
   * @param type de qr codes à lire sur l'image
   */
  public void readQRCodeImage(final PDDocument pdDoc, final String docPath, final PDFRenderer pdfRenderer, final int startPages, final int endPages, final Pair<Float, Float> qrPos, final int qrCodeType) throws IOException {
    ArrayList<Integer> _arrayList = new ArrayList<Integer>();
    this.pagesMalLues = _arrayList;
    if ((qrCodeType == QrCodeType.PAGE)) {
      ArrayList<Set<Integer>> _arrayList_1 = new ArrayList<Set<Integer>>();
      this.pagesSeen = _arrayList_1;
    }
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(startPages, endPages, true);
    for (final Integer page : _doubleDotLessThan) {
      {
        BufferedImage bim = pdfRenderer.renderImageWithDPI((page).intValue(), 250, ImageType.GRAY);
        final LuminanceSource source = new BufferedImageLuminanceSource(bim);
        HybridBinarizer _hybridBinarizer = new HybridBinarizer(source);
        final BinaryBitmap bitmap = new BinaryBitmap(_hybridBinarizer);
        final QRCodeMultiReader multiReader = new QRCodeMultiReader();
        try {
          final Result[] results = multiReader.decodeMultiple(bitmap);
          this.qrCodeAnalysis(results, bim, pdDoc, (page).intValue(), qrCodeType);
        } catch (final Throwable _t) {
          if (_t instanceof ArrayIndexOutOfBoundsException || _t instanceof NotFoundException) {
            final Exception e = (Exception)_t;
            this.pagesMalLues.add(page);
            this.logger.error(("Cannot read QRCode in page " + page), e);
          } else {
            throw Exceptions.sneakyThrow(_t);
          }
        } finally {
          this.treatedSheets++;
        }
        bim.flush();
        System.gc();
      }
    }
  }
  
  /**
   * Détermine quel type de qr code doit être analysé (SHEET_PAGE ou PAGE)
   * @param pdfRenderer Rendu du pdf
   * @param nbPage Nombre de pages dans le document
   * @return Type de qr code, -1 si rien de trouver
   */
  private int findQrCodeType(final PDFRenderer pdfRenderer, final int nbPage) {
    try {
      int qrCodeType = (-1);
      final Random rand = new Random();
      final int analysisMax = 10;
      int _xifexpression = (int) 0;
      if ((nbPage < analysisMax)) {
        _xifexpression = nbPage;
      } else {
        _xifexpression = analysisMax;
      }
      final int nbAnalysis = _xifexpression;
      int i = 0;
      while (((i < nbAnalysis) && (qrCodeType == (-1)))) {
        {
          final int page = rand.nextInt(nbPage);
          BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 250, ImageType.GRAY);
          final LuminanceSource source = new BufferedImageLuminanceSource(bim);
          HybridBinarizer _hybridBinarizer = new HybridBinarizer(source);
          final BinaryBitmap bitmap = new BinaryBitmap(_hybridBinarizer);
          final QRCodeMultiReader multiReader = new QRCodeMultiReader();
          try {
            final Result[] results = multiReader.decodeMultiple(bitmap);
            for (final Result result : results) {
              boolean _startsWith = result.getText().startsWith(Integer.valueOf(QrCodeType.SHEET_PAGE).toString());
              if (_startsWith) {
                qrCodeType = QrCodeType.SHEET_PAGE;
              } else {
                boolean _startsWith_1 = result.getText().startsWith(Integer.valueOf(QrCodeType.PAGE).toString());
                if (_startsWith_1) {
                  qrCodeType = QrCodeType.PAGE;
                }
              }
            }
          } catch (final Throwable _t) {
            if (_t instanceof ArrayIndexOutOfBoundsException || _t instanceof NotFoundException) {
              final Exception e = (Exception)_t;
              this.logger.error(("Cannot read QRCode in page " + Integer.valueOf(page)), e);
            } else {
              throw Exceptions.sneakyThrow(_t);
            }
          }
          bim.flush();
          i++;
        }
      }
      return qrCodeType;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Analyse les qr codes d'une page et traite les informations qu'ils contiennent
   * @param results Résultats des qr codes
   * @param bim Image de la page
   * @param pdDoc Document où se trouve la page
   * @param page Numéro de la page à traiter
   * @param Type du qr code principal (SHEET_PAGE ou PAGE)
   */
  private void qrCodeAnalysis(final Result[] results, final BufferedImage bim, final PDDocument pdDoc, final int page, final int qrCodeType) {
    int numCopie = (-1);
    int numPageInSubject = (-1);
    String studentId = null;
    String studentLastName = null;
    String studentFirstName = null;
    for (final Result result : results) {
      if ((result.getText().startsWith(Integer.valueOf(QrCodeType.SHEET_PAGE).toString()) || 
        result.getText().startsWith(Integer.valueOf(QrCodeType.PAGE).toString()))) {
        this.repositioningPage(result, bim, pdDoc, page);
        final String[] items = this.pattern.split(result.getText());
        if ((qrCodeType == QrCodeType.SHEET_PAGE)) {
          int _size = ((List<String>)Conversions.doWrapArray(items)).size();
          int _minus = (_size - 2);
          numCopie = Integer.parseInt(items[_minus]);
        } else {
          if ((qrCodeType == QrCodeType.PAGE)) {
            int _size_1 = ((List<String>)Conversions.doWrapArray(items)).size();
            int _minus_1 = (_size_1 - 1);
            numCopie = this.generateNumCopie(Integer.parseInt(items[_minus_1]));
          }
        }
        int _size_2 = ((List<String>)Conversions.doWrapArray(items)).size();
        int _minus_2 = (_size_2 - 1);
        numPageInSubject = Integer.parseInt(items[_minus_2]);
      } else {
        boolean _startsWith = result.getText().startsWith(Integer.valueOf(QrCodeType.STUDENT).toString());
        if (_startsWith) {
          final String[] items_1 = this.pattern.split(result.getText());
          int _size_3 = ((List<String>)Conversions.doWrapArray(items_1)).size();
          int _minus_3 = (_size_3 - 3);
          studentId = items_1[_minus_3];
          int _size_4 = ((List<String>)Conversions.doWrapArray(items_1)).size();
          int _minus_4 = (_size_4 - 2);
          studentLastName = items_1[_minus_4];
          int _size_5 = ((List<String>)Conversions.doWrapArray(items_1)).size();
          int _minus_5 = (_size_5 - 1);
          studentFirstName = items_1[_minus_5];
        }
      }
    }
    if ((((numCopie >= 0) && (page >= 0)) && (numPageInSubject >= 0))) {
      final Copie copie = new Copie(numCopie, page, numPageInSubject, studentId, studentLastName, studentFirstName);
      synchronized (this.sheets) {
        this.addCopie(copie);
      }
    } else {
      this.pagesMalLues.add(Integer.valueOf(page));
      this.logger.error(("Cannot read QRCode in page " + Integer.valueOf(page)));
    }
  }
  
  /**
   * Réoriente et repositionne une page donnée, à partir des coordonnées d'un qr code
   * @param result Résultat du qr code sur lequel appuyer le repositionnement
   * @param bim Image de la page
   * @param pdDoc Document où se trouve la page
   * @param page Numéro de la page à traiter
   */
  private void repositioningPage(final Result result, final BufferedImage bim, final PDDocument pdDoc, final int page) {
    final float orientation = this.qrCodeOrientation(result);
    final Pair<Float, Float> position = this.qrCodePosition(result, orientation, bim.getWidth(), bim.getHeight());
    if (((orientation <= (-0.5f)) || (orientation >= 0.5f))) {
      this.rotatePdf(pdDoc, this.docPath, page, orientation);
    }
    if ((((this.qrPos.getKey()).floatValue() >= 0.0f) && ((this.qrPos.getValue()).floatValue() >= 0.0f))) {
      Float _key = this.qrPos.getKey();
      Float _key_1 = position.getKey();
      final float diffX = ((_key).floatValue() - (_key_1).floatValue());
      Float _value = position.getValue();
      Float _value_1 = this.qrPos.getValue();
      final float diffY = ((_value).floatValue() - (_value_1).floatValue());
      if (((((diffX <= (-0.01f)) || (diffX >= 0.01f)) || (diffY <= (-0.01f))) || (diffY >= 0.01f))) {
        this.repositionPdf(pdDoc, this.docPath, page, diffX, diffY);
      }
    }
  }
  
  /**
   * Retourne le premier étudiant n'ayant pas la page donnée
   * @param page Numéro de page
   * @return Numéro du premier étudiant n'ayant pas la page
   */
  private int generateNumCopie(final int page) {
    int numCopie = 0;
    boolean pageNotFind = true;
    while (pageNotFind) {
      int _size = this.pagesSeen.size();
      boolean _greaterThan = (_size > numCopie);
      if (_greaterThan) {
        boolean _contains = this.pagesSeen.get(numCopie).contains(Integer.valueOf(page));
        if (_contains) {
          numCopie++;
        } else {
          this.pagesSeen.get(numCopie).add(Integer.valueOf(page));
          pageNotFind = false;
        }
      } else {
        final Set<Integer> pageSet = new HashSet<Integer>();
        pageSet.add(Integer.valueOf(page));
        this.pagesSeen.add(pageSet);
        pageNotFind = false;
      }
    }
    return numCopie;
  }
}
