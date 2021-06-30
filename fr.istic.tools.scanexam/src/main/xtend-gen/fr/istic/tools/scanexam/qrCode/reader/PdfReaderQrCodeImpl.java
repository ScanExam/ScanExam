package fr.istic.tools.scanexam.qrCode.reader;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.qrCode.reader.Copie;
import fr.istic.tools.scanexam.qrCode.reader.Page;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderQrCode;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderThreadManager;
import fr.istic.tools.scanexam.utils.DataFactory;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.util.Matrix;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class PdfReaderQrCodeImpl implements PdfReaderQrCode {
  protected final Logger logger = LogManager.getLogger();
  
  protected Set<Copie> sheets;
  
  protected int nbPagesInSheet;
  
  protected int nbPagesInPdf;
  
  protected PDDocument doc;
  
  protected String docPath;
  
  protected Pair<Float, Float> qrPos;
  
  protected boolean isFinished;
  
  protected List<Integer> pagesMalLues;
  
  protected int missingSheets;
  
  protected int treatedSheets;
  
  public PdfReaderQrCodeImpl(final InputStream input, final String docPath, final int nbPages, final Pair<Float, Float> qrPos) {
    try {
      this.doc = PDDocument.load(input);
      this.docPath = docPath;
      this.nbPagesInSheet = nbPages;
      this.qrPos = qrPos;
      this.isFinished = false;
      this.missingSheets = 0;
      this.treatedSheets = 0;
      HashSet<Copie> _hashSet = new HashSet<Copie>();
      this.sheets = _hashSet;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Override
  public boolean readPDf() {
    try {
      this.nbPagesInPdf = this.doc.getNumberOfPages();
      this.createThread(this.doc.getNumberOfPages(), this.doc, this.docPath, this.qrPos);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        this.logger.error("Cannot read PDF", e);
        return false;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return true;
  }
  
  /**
   * Analyse les qr codes, corrige les problèmes de numérisation (ex: à l'envers) et lie les pages aux élèves
   * @param pdDoc Document à analyser
   * @param docPath Chemin du document à analyser
   * @param pdfRenderer Rendu du document à analyser
   * @param startPages Première page à analyser
   * @param endPages Dernière page à analyser
   * @param qrPos Position où devrait ce trouver le qr code
   */
  public void readQRCodeImage(final PDDocument pdDoc, final String docPath, final PDFRenderer pdfRenderer, final int startPages, final int endPages, final Pair<Float, Float> qrPos) throws IOException {
    ArrayList<Integer> _arrayList = new ArrayList<Integer>();
    this.pagesMalLues = _arrayList;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(startPages, endPages, true);
    for (final Integer page : _doubleDotLessThan) {
      {
        BufferedImage bim = pdfRenderer.renderImageWithDPI((page).intValue(), 250, ImageType.GRAY);
        final LuminanceSource source = new BufferedImageLuminanceSource(bim);
        HybridBinarizer _hybridBinarizer = new HybridBinarizer(source);
        final BinaryBitmap bitmap = new BinaryBitmap(_hybridBinarizer);
        final MultiFormatReader mfr = new MultiFormatReader();
        try {
          final Result result = mfr.decodeWithState(bitmap);
          final Pattern pattern = Pattern.compile("_");
          final String[] items = pattern.split(result.getText());
          final float orientation = this.qrCodeOrientation(result);
          final Pair<Float, Float> position = this.qrCodePosition(result, orientation, bim.getWidth(), bim.getHeight());
          if (((orientation <= (-0.5f)) || (orientation >= 0.5f))) {
            this.rotatePdf(pdDoc, docPath, (page).intValue(), orientation);
          }
          if ((((qrPos.getKey()).floatValue() >= 0.0f) && ((qrPos.getValue()).floatValue() >= 0.0f))) {
            Float _key = qrPos.getKey();
            Float _key_1 = position.getKey();
            final float diffX = ((_key).floatValue() - (_key_1).floatValue());
            Float _value = position.getValue();
            Float _value_1 = qrPos.getValue();
            final float diffY = ((_value).floatValue() - (_value_1).floatValue());
            if (((((diffX <= (-0.01f)) || (diffX >= 0.01f)) || (diffY <= (-0.01f))) || (diffY >= 0.01f))) {
              this.repositionPdf(pdDoc, docPath, (page).intValue(), diffX, diffY);
            }
          }
          int _size = ((List<String>)Conversions.doWrapArray(items)).size();
          int _minus = (_size - 2);
          int _parseInt = Integer.parseInt(items[_minus]);
          int _size_1 = ((List<String>)Conversions.doWrapArray(items)).size();
          int _minus_1 = (_size_1 - 1);
          int _parseInt_1 = Integer.parseInt(items[_minus_1]);
          final Copie cop = new Copie(_parseInt, (page).intValue(), _parseInt_1);
          synchronized (this.sheets) {
            this.addCopie(cop);
          }
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
   * Retourne l'orientation d'un QR code sous la forme d'un angle compris entre
   * ]-180;180]°. Plus le QR code est orienté vers la droite plus il gagne de
   * dégrés.
   * 
   * @param result Résultat du dédodage du QR code
   * @return Orientation du QR code
   */
  protected float qrCodeOrientation(final Result result) {
    final ResultPoint[] resultPoints = result.getResultPoints();
    final ResultPoint a = resultPoints[1];
    final ResultPoint b = resultPoints[2];
    float _x = b.getX();
    float _x_1 = a.getX();
    final float distanceX = (_x - _x_1);
    float _y = b.getY();
    float _y_1 = a.getY();
    final float distanceY = (_y - _y_1);
    double _atan = Math.atan((distanceY / distanceX));
    double _multiply = (_atan * (180 / Math.PI));
    float angle = ((float) _multiply);
    if ((((angle > 0) && (a.getX() > b.getX())) && (a.getY() >= b.getY()))) {
      float _angle = angle;
      angle = (_angle - 180);
    } else {
      if ((((angle <= 0) && (b.getX() < a.getX())) && (b.getY() >= a.getY()))) {
        float _angle_1 = angle;
        angle = (_angle_1 + 180);
      }
    }
    return angle;
  }
  
  /**
   * Retourne la position d'un QR code en pixel. On considère que le point
   * d'origine du QR code est en haut à gauche et que les coordonnées (0, 0) sont
   * sont bas à gauche de la page. Ne marche que si le code à un angle de 0 ou
   * 180°.
   * 
   * @param result    Résultat du dédodage du QR code
   * @param docWidth  Laugueur du document où se trouve le QR code
   * @param docHeight Hauteur du document où se trouve le QR code
   * @return Paire contenant les positions x et y du QR code
   */
  protected Pair<Float, Float> qrCodePosition(final Result result, final float orientation, final float docWidth, final float docHeight) {
    final ResultPoint[] resultPoints = result.getResultPoints();
    final ResultPoint a = resultPoints[1];
    final ResultPoint b = resultPoints[2];
    final ResultPoint c = resultPoints[0];
    float _x = a.getX();
    float _y = a.getY();
    Pair<Float, Float> _pair = new Pair<Float, Float>(Float.valueOf(_x), Float.valueOf(_y));
    Pair<Float, Float> _pair_1 = new Pair<Float, Float>(Float.valueOf((docWidth / 2)), Float.valueOf((docHeight / 2)));
    final Pair<Float, Float> disorientedA = this.rotatePoint(_pair, _pair_1, orientation);
    float _x_1 = b.getX();
    float _y_1 = b.getY();
    Pair<Float, Float> _pair_2 = new Pair<Float, Float>(Float.valueOf(_x_1), Float.valueOf(_y_1));
    Pair<Float, Float> _pair_3 = new Pair<Float, Float>(Float.valueOf((docWidth / 2)), Float.valueOf((docHeight / 2)));
    final Pair<Float, Float> disorientedB = this.rotatePoint(_pair_2, _pair_3, orientation);
    float _x_2 = c.getX();
    float _y_2 = c.getY();
    Pair<Float, Float> _pair_4 = new Pair<Float, Float>(Float.valueOf(_x_2), Float.valueOf(_y_2));
    Pair<Float, Float> _pair_5 = new Pair<Float, Float>(Float.valueOf((docWidth / 2)), Float.valueOf((docHeight / 2)));
    final Pair<Float, Float> disorientedC = this.rotatePoint(_pair_4, _pair_5, orientation);
    Float _key = disorientedB.getKey();
    Float _key_1 = disorientedA.getKey();
    float _plus = ((_key).floatValue() + (_key_1).floatValue());
    float x = (_plus / 2);
    Float _value = disorientedC.getValue();
    Float _value_1 = disorientedA.getValue();
    float _plus_1 = ((_value).floatValue() + (_value_1).floatValue());
    float y = (_plus_1 / 2);
    Float _key_2 = disorientedB.getKey();
    Float _key_3 = disorientedA.getKey();
    float _minus = ((_key_2).floatValue() - (_key_3).floatValue());
    final double widthX2 = Math.pow(_minus, 2);
    Float _value_2 = disorientedB.getValue();
    Float _value_3 = disorientedA.getValue();
    float _minus_1 = ((_value_2).floatValue() - (_value_3).floatValue());
    final double widthY2 = Math.pow(_minus_1, 2);
    double width = Math.sqrt((widthX2 + widthY2));
    Float _key_4 = disorientedC.getKey();
    Float _key_5 = disorientedA.getKey();
    float _minus_2 = ((_key_4).floatValue() - (_key_5).floatValue());
    final double heightX2 = Math.pow(_minus_2, 2);
    Float _value_4 = disorientedC.getValue();
    Float _value_5 = disorientedA.getValue();
    float _minus_3 = ((_value_4).floatValue() - (_value_5).floatValue());
    final double heightY2 = Math.pow(_minus_3, 2);
    double height = Math.sqrt((heightX2 + heightY2));
    double _width = width;
    width = (_width / (4.0f / 3.0f));
    double _height = height;
    height = (_height / (4.0f / 3.0f));
    float _x_3 = x;
    x = (_x_3 - ((float) width));
    float _y_3 = y;
    y = (_y_3 - ((float) height));
    return new Pair<Float, Float>(Float.valueOf((x / docWidth)), Float.valueOf((y / docHeight)));
  }
  
  /**
   * Retourne l'image d'un point par une rotation (repère X de gauche à droite, Y du
   * haut vers le bas).
   * @param point Point à transformer
   * @param centre Centre de la rotation
   * @param angle Angle en degrés
   * @return Image de M par la rotation d'angle angle autour du centre
   */
  protected Pair<Float, Float> rotatePoint(final Pair<Float, Float> point, final Pair<Float, Float> center, final float angle) {
    final double angleRad = ((angle * Math.PI) / 180);
    Float _key = point.getKey();
    Float _key_1 = center.getKey();
    final float xPoint = ((_key).floatValue() - (_key_1).floatValue());
    Float _value = point.getValue();
    Float _value_1 = center.getValue();
    final float yPoint = ((_value).floatValue() - (_value_1).floatValue());
    double _cos = Math.cos(angleRad);
    double _multiply = (xPoint * _cos);
    double _sin = Math.sin(angleRad);
    double _multiply_1 = (yPoint * _sin);
    double _plus = (_multiply + _multiply_1);
    Float _key_2 = center.getKey();
    final double x = (_plus + (_key_2).floatValue());
    double _sin_1 = Math.sin(angleRad);
    double _multiply_2 = ((-xPoint) * _sin_1);
    double _cos_1 = Math.cos(angleRad);
    double _multiply_3 = (yPoint * _cos_1);
    double _plus_1 = (_multiply_2 + _multiply_3);
    Float _value_2 = center.getValue();
    final double y = (_plus_1 + (_value_2).floatValue());
    return new Pair<Float, Float>(Float.valueOf(((float) x)), Float.valueOf(((float) y)));
  }
  
  /**
   * Réoriente le contenu d'une page de pdf selon un angle donné
   * @param pdDoc Document sur lequel travailler
   * @param docPath Chemin du document sur lequel travailler
   * @param page Page où effectuer la rotation
   * @param rotation Nouvelle inclinaison du contenu
   */
  protected void rotatePdf(final PDDocument pdDoc, final String docPath, final int page, final float rotation) {
    try {
      final PDPage pdPage = pdDoc.getDocumentCatalog().getPages().get(page);
      final PDPageContentStream cs = new PDPageContentStream(pdDoc, pdPage, PDPageContentStream.AppendMode.PREPEND, 
        false, false);
      final PDRectangle cropBox = pdPage.getCropBox();
      float _lowerLeftX = cropBox.getLowerLeftX();
      float _upperRightX = cropBox.getUpperRightX();
      float _plus = (_lowerLeftX + _upperRightX);
      final float tx = (_plus / 2);
      float _lowerLeftY = cropBox.getLowerLeftY();
      float _upperRightY = cropBox.getUpperRightY();
      float _plus_1 = (_lowerLeftY + _upperRightY);
      final float ty = (_plus_1 / 2);
      cs.transform(Matrix.getTranslateInstance(tx, ty));
      cs.transform(Matrix.getRotateInstance(Math.toRadians(rotation), 0, 0));
      cs.transform(Matrix.getTranslateInstance((-tx), (-ty)));
      cs.close();
      pdDoc.save(docPath);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Repositionne le contenu d'une page de pdf selon un les longueurs x et y données
   * @param pdDoc Document sur lequel travailler
   * @param docPath Chemin du document sur lequel travailler
   * @param page Page où effectuer la rotation
   * @param offsetX Longueur vers laquelle décaler le contenu sur l'axe x
   * @param offsetY Longueur vers laquelle décaler le contenu sur l'axe y
   */
  protected void repositionPdf(final PDDocument pdDoc, final String docPath, final int page, final float offsetX, final float offsetY) {
    try {
      final PDPage pdPage = pdDoc.getDocumentCatalog().getPages().get(page);
      final PDPageContentStream cs = new PDPageContentStream(pdDoc, pdPage, PDPageContentStream.AppendMode.PREPEND, 
        false, false);
      float _width = pdPage.getMediaBox().getWidth();
      float _multiply = (offsetX * _width);
      float _height = pdPage.getMediaBox().getHeight();
      float _multiply_1 = (offsetY * _height);
      cs.transform(Matrix.getTranslateInstance(_multiply, _multiply_1));
      cs.close();
      pdDoc.save(docPath);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Crée un thread lisant des qr codes
   * @param nbPage nombre de copies désirées
   * @param doc document dans lequel insérer les Codes
   * @param docPath chemin du document dans lequel insérer les Codes
   * @param qrPos Position à laquelle devrait se trouver les qr codes
   */
  public void createThread(final int nbPage, final PDDocument doc, final String docPath, final Pair<Float, Float> qrPos) {
    final PdfReaderThreadManager manager = new PdfReaderThreadManager(nbPage, doc, docPath, qrPos, this);
    manager.start();
  }
  
  /**
   * Dis si un examen est complet ou non
   * @return true si l'examen est complet (contient toutes les pages de toutes les copies), false sinon
   */
  public boolean isExamenComplete() {
    boolean ret = true;
    List<Integer> idSheets = new ArrayList<Integer>();
    int _length = ((Object[])Conversions.unwrapArray(this.sheets, Object.class)).length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        ret = (ret && (((Copie[])Conversions.unwrapArray(this.sheets, Copie.class))[(i).intValue()]).isCopyComplete(this.nbPagesInSheet));
        idSheets.add(Integer.valueOf((((Copie[])Conversions.unwrapArray(this.sheets, Copie.class))[(i).intValue()]).getNumCopie()));
      }
    }
    final Comparator<Integer> _function = (Integer a, Integer b) -> {
      return ((a).intValue() - (b).intValue());
    };
    Collections.<Integer>sort(idSheets, _function);
    int _size = idSheets.size();
    int _minus = (_size - 1);
    Integer _get = idSheets.get(_minus);
    int _size_1 = idSheets.size();
    int _minus_1 = (_size_1 - 1);
    int _minus_2 = ((_get).intValue() - _minus_1);
    this.missingSheets = Math.abs(_minus_2);
    ret = (ret && (this.missingSheets == 0));
    return ret;
  }
  
  /**
   * Renvoie une collection de toutes les copies incomplètes
   * @return une collection de copies incomplètes
   */
  public Set<Copie> getUncompleteCopies() {
    final Set<Copie> uncompleteCopies = new HashSet<Copie>();
    int _length = ((Object[])Conversions.unwrapArray(this.sheets, Object.class)).length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      boolean _isCopyComplete = (((Copie[])Conversions.unwrapArray(this.sheets, Copie.class))[(i).intValue()]).isCopyComplete(this.nbPagesInSheet);
      boolean _not = (!_isCopyComplete);
      if (_not) {
        uncompleteCopies.add(((Copie[])Conversions.unwrapArray(this.sheets, Copie.class))[(i).intValue()]);
      }
    }
    return uncompleteCopies;
  }
  
  /**
   * Renvoie une collection de toutes les copies complètes
   * @return une collection de copies complètes
   */
  public Set<Copie> getCompleteCopies() {
    Set<Copie> completeCopies = new HashSet<Copie>();
    final Predicate<Copie> _function = (Copie copie) -> {
      return copie.isCopyComplete(this.nbPagesInSheet);
    };
    completeCopies = this.sheets.stream().filter(_function).collect(Collectors.<Copie>toSet());
    return completeCopies;
  }
  
  /**
   * Renvoie une copie spécifique à son indentifiant
   * @param numCopie le numéro de la copie voulue
   * @return la copie correspondante
   */
  public Copie getCopie(final int numCopie) {
    int _length = ((Object[])Conversions.unwrapArray(this.sheets, Object.class)).length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      int _numCopie = (((Copie[])Conversions.unwrapArray(this.sheets, Copie.class))[(i).intValue()]).getNumCopie();
      boolean _equals = (_numCopie == numCopie);
      if (_equals) {
        return ((Copie[])Conversions.unwrapArray(this.sheets, Copie.class))[(i).intValue()];
      }
    }
    return null;
  }
  
  /**
   * Ajoute une copie lu au tas de copies déjà lues. Si la copie existe déjà, on merge les pages
   * @param sheet la copie à ajouter
   */
  public boolean addCopie(final Copie copie) {
    boolean _xblockexpression = false;
    {
      boolean trouve = false;
      int i = 0;
      while (((!trouve) && (i < ((Object[])Conversions.unwrapArray(this.sheets, Object.class)).length))) {
        {
          int _numCopie = (((Copie[])Conversions.unwrapArray(this.sheets, Copie.class))[i]).getNumCopie();
          int _numCopie_1 = copie.getNumCopie();
          boolean _equals = (_numCopie == _numCopie_1);
          if (_equals) {
            trouve = true;
          }
          i++;
        }
      }
      i--;
      boolean _xifexpression = false;
      if (trouve) {
        boolean _xblockexpression_1 = false;
        {
          final Copie oldCopie = ((Copie[])Conversions.unwrapArray(this.sheets, Copie.class))[i];
          this.sheets.remove(oldCopie);
          final int numCopie = oldCopie.getNumCopie();
          final Set<Page> pages = oldCopie.getPagesCopie();
          pages.addAll(copie.getPagesCopie());
          String _xifexpression_1 = null;
          String _studentName = oldCopie.getStudentName();
          boolean _tripleNotEquals = (_studentName != null);
          if (_tripleNotEquals) {
            _xifexpression_1 = oldCopie.getStudentName();
          } else {
            _xifexpression_1 = copie.getStudentName();
          }
          final String studentName = _xifexpression_1;
          final Copie newCopie = new Copie(numCopie, pages, studentName);
          _xblockexpression_1 = this.sheets.add(newCopie);
        }
        _xifexpression = _xblockexpression_1;
      } else {
        _xifexpression = this.sheets.add(copie);
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * Renvoie une collection de toutes les copies lues, complètes ou non
   * @return une collection de toutes les copies lues
   */
  public Set<Copie> getSheets() {
    return this.sheets;
  }
  
  /**
   * Renvoie la collection des copies complètes uniquement au format de l'API
   * @return la collection des copies complètes au format de l'API
   */
  @Override
  public Collection<StudentSheet> getCompleteStudentSheets() {
    final Set<StudentSheet> res = new HashSet<StudentSheet>();
    Set<Copie> temp = new HashSet<Copie>();
    final DataFactory dF = new DataFactory();
    temp = this.getCompleteCopies();
    final Set<Copie> _converted_temp = (Set<Copie>)temp;
    int _length = ((Object[])Conversions.unwrapArray(_converted_temp, Object.class)).length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final Set<Copie> _converted_temp_1 = (Set<Copie>)temp;
        final int index = (((Copie[])Conversions.unwrapArray(_converted_temp_1, Copie.class))[(i).intValue()]).getNumCopie();
        final int[] pagesArray = new int[this.nbPagesInSheet];
        final Set<Copie> _converted_temp_2 = (Set<Copie>)temp;
        final String studentName = (((Copie[])Conversions.unwrapArray(_converted_temp_2, Copie.class))[(i).intValue()]).getStudentName();
        final Set<Copie> _converted_temp_3 = (Set<Copie>)temp;
        int _length_1 = ((Object[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_3, Copie.class))[(i).intValue()]).getPagesCopie(), Object.class)).length;
        ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _length_1, true);
        for (final Integer j : _doubleDotLessThan_1) {
          final Set<Copie> _converted_temp_4 = (Set<Copie>)temp;
          final Set<Copie> _converted_temp_5 = (Set<Copie>)temp;
          pagesArray[(((Page[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_4, Copie.class))[(i).intValue()]).getPagesCopie(), Page.class))[(j).intValue()]).getNumPageInSubject()] = 
            (((Page[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_5, Copie.class))[(i).intValue()]).getPagesCopie(), Page.class))[(j).intValue()]).getNumPageInPDF();
        }
        if ((studentName != null)) {
          res.add(dF.createStudentSheet(index, ((List<Integer>)Conversions.doWrapArray(pagesArray)), studentName));
        } else {
          res.add(dF.createStudentSheet(index, ((List<Integer>)Conversions.doWrapArray(pagesArray))));
        }
      }
    }
    return res;
  }
  
  /**
   * Renvoie la collection des copies incomplètes uniquement au format de l'API
   * Les copies manquantes sont signalées par la présence d'un -1 en numéro de page
   * @return la collection des copies incomplètes au format de l'API
   */
  @Override
  public Collection<StudentSheet> getUncompleteStudentSheets() {
    final Set<StudentSheet> res = new HashSet<StudentSheet>();
    Set<Copie> temp = new HashSet<Copie>();
    final DataFactory dF = new DataFactory();
    temp = this.getUncompleteCopies();
    final Set<Copie> _converted_temp = (Set<Copie>)temp;
    int _length = ((Object[])Conversions.unwrapArray(_converted_temp, Object.class)).length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final Set<Copie> _converted_temp_1 = (Set<Copie>)temp;
        final int index = (((Copie[])Conversions.unwrapArray(_converted_temp_1, Copie.class))[(i).intValue()]).getNumCopie();
        final int[] pagesArray = new int[this.nbPagesInSheet];
        final Set<Copie> _converted_temp_2 = (Set<Copie>)temp;
        final String studentName = (((Copie[])Conversions.unwrapArray(_converted_temp_2, Copie.class))[(i).intValue()]).getStudentName();
        ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, this.nbPagesInSheet, true);
        for (final Integer e : _doubleDotLessThan_1) {
          pagesArray[(e).intValue()] = (-1);
        }
        final Set<Copie> _converted_temp_3 = (Set<Copie>)temp;
        int _length_1 = ((Object[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_3, Copie.class))[(i).intValue()]).getPagesCopie(), Object.class)).length;
        ExclusiveRange _doubleDotLessThan_2 = new ExclusiveRange(0, _length_1, true);
        for (final Integer j : _doubleDotLessThan_2) {
          final Set<Copie> _converted_temp_4 = (Set<Copie>)temp;
          final Set<Copie> _converted_temp_5 = (Set<Copie>)temp;
          pagesArray[(((Page[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_4, Copie.class))[(i).intValue()]).getPagesCopie(), Page.class))[(j).intValue()]).getNumPageInSubject()] = 
            (((Page[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_5, Copie.class))[(i).intValue()]).getPagesCopie(), Page.class))[(j).intValue()]).getNumPageInPDF();
        }
        if ((studentName != null)) {
          res.add(dF.createStudentSheet(index, ((List<Integer>)Conversions.doWrapArray(pagesArray)), studentName));
        } else {
          res.add(dF.createStudentSheet(index, ((List<Integer>)Conversions.doWrapArray(pagesArray))));
        }
      }
    }
    return res;
  }
  
  /**
   * Renvoie le nombre de total de pages du PDF de toutes les copies
   * @return le nombre de pages du PDF source
   */
  @Override
  public int getNbPagesPdf() {
    return this.nbPagesInPdf;
  }
  
  /**
   * Renvoie le nombre de pages traitées par la lecture du PDF
   * @return le nombre de pages que le reader a lu du PDF source
   */
  @Override
  public int getNbPagesTreated() {
    return this.treatedSheets;
  }
  
  @Override
  public boolean isFinished() {
    return this.isFinished;
  }
  
  public boolean setFinished(final boolean bool) {
    return this.isFinished = bool;
  }
  
  @Override
  public Collection<Integer> getFailedPages() {
    return this.pagesMalLues;
  }
}
