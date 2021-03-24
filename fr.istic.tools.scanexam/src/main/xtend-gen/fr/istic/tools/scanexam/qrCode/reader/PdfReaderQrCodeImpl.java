package fr.istic.tools.scanexam.qrCode.reader;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import fr.istic.tools.scanexam.api.DataFactory;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.qrCode.reader.Copie;
import fr.istic.tools.scanexam.qrCode.reader.Page;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderQrCode;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderThreadManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class PdfReaderQrCodeImpl implements PdfReaderQrCode {
  private Set<Copie> sheets;
  
  private int nbSheetsTotal;
  
  private int nbPagesInSheet;
  
  private int nbPagesInPdf;
  
  private PDDocument doc;
  
  public PdfReaderQrCodeImpl(final PDDocument doc, final int nbPages, final int nbCopies) {
    this.doc = doc;
    this.nbPagesInSheet = nbPages;
    this.nbSheetsTotal = nbCopies;
    HashSet<Copie> _hashSet = new HashSet<Copie>();
    this.sheets = _hashSet;
  }
  
  @Override
  public boolean readPDf() {
    try {
      this.nbPagesInPdf = this.doc.getNumberOfPages();
      final PDFRenderer pdf = new PDFRenderer(this.doc);
      this.createThread(this.doc.getNumberOfPages(), pdf);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        e.printStackTrace();
        return false;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return true;
  }
  
  public void readQRCodeImage(final PDFRenderer pdfRenderer, final int startPages, final int endPages) throws IOException {
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(startPages, endPages, true);
    for (final Integer page : _doubleDotLessThan) {
      {
        final BufferedImage bim = pdfRenderer.renderImageWithDPI((page).intValue(), 300, ImageType.RGB);
        final Pattern pattern = Pattern.compile("_");
        final String[] items = pattern.split(this.decodeQRCodeBuffered(bim));
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
      }
    }
  }
  
  /**
   * @param qrCodeimage la bufferedImage a décoder
   * @return le texte decode du QRCOde se trouvant dans qrCodeImage
   * @throws IOException
   * 
   *                     Décode le contenu de qrCodeImage et affiche le contenu
   *                     décodé dans le system.out
   */
  public String decodeQRCodeBuffered(final BufferedImage bufferedImage) throws IOException {
    final LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
    HybridBinarizer _hybridBinarizer = new HybridBinarizer(source);
    final BinaryBitmap bitmap = new BinaryBitmap(_hybridBinarizer);
    final Map<DecodeHintType, Object> map = new HashMap<DecodeHintType, Object>();
    map.put(DecodeHintType.ALLOWED_EAN_EXTENSIONS, BarcodeFormat.QR_CODE);
    try {
      final MultiFormatReader mfr = new MultiFormatReader();
      mfr.setHints(map);
      final Result result = mfr.decodeWithState(bitmap);
      return result.getText();
    } catch (final Throwable _t) {
      if (_t instanceof NotFoundException) {
        System.out.println("There is no QR code in the image");
        return "";
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  /**
   * @param nbCopies nombre de copies désirées
   * @param docSujetMaitre document dans lequel insérer les Codes
   */
  public void createThread(final int nbPage, final PDFRenderer pdfRenderer) {
    final PdfReaderThreadManager manager = new PdfReaderThreadManager(nbPage, pdfRenderer, this);
    manager.start();
  }
  
  /**
   * Dis si un examen est complet ou non
   * @return true si l'examen est complet (contient toutes les pages de toutes les copies), false sinon
   */
  public boolean isExamenComplete() {
    boolean ret = true;
    int _length = ((Object[])Conversions.unwrapArray(this.sheets, Object.class)).length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      ret = (ret && (((Copie[])Conversions.unwrapArray(this.sheets, Copie.class))[(i).intValue()]).isCopyComplete(this.nbPagesInSheet));
    }
    return (ret && (this.nbSheetsTotal == ((Object[])Conversions.unwrapArray(this.sheets, Object.class)).length));
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
  public Boolean addCopie(final Copie copie) {
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
        (((Copie[])Conversions.unwrapArray(this.sheets, Copie.class))[i]).addInSet(copie.getPagesCopie());
      } else {
        _xifexpression = this.sheets.add(copie);
      }
      _xblockexpression = _xifexpression;
    }
    return Boolean.valueOf(_xblockexpression);
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
        int _length_1 = ((Object[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_2, Copie.class))[(i).intValue()]).getPagesCopie(), Object.class)).length;
        ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _length_1, true);
        for (final Integer j : _doubleDotLessThan_1) {
          final Set<Copie> _converted_temp_3 = (Set<Copie>)temp;
          final Set<Copie> _converted_temp_4 = (Set<Copie>)temp;
          pagesArray[(((Page[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_3, Copie.class))[(i).intValue()]).getPagesCopie(), Page.class))[(j).intValue()]).getNumPageInSubject()] = 
            (((Page[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_4, Copie.class))[(i).intValue()]).getPagesCopie(), Page.class))[(j).intValue()]).getNumPageInPDF();
        }
        res.add(dF.createStudentSheet(index, ((List<Integer>)Conversions.doWrapArray(pagesArray))));
      }
    }
    return res;
  }
  
  /**
   * Renvoie la collection des copies incomplètes uniquement au format de l'API
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
        int _length_1 = ((Object[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_2, Copie.class))[(i).intValue()]).getPagesCopie(), Object.class)).length;
        ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _length_1, true);
        for (final Integer j : _doubleDotLessThan_1) {
          final Set<Copie> _converted_temp_3 = (Set<Copie>)temp;
          final Set<Copie> _converted_temp_4 = (Set<Copie>)temp;
          pagesArray[(((Page[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_3, Copie.class))[(i).intValue()]).getPagesCopie(), Page.class))[(j).intValue()]).getNumPageInSubject()] = 
            (((Page[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_4, Copie.class))[(i).intValue()]).getPagesCopie(), Page.class))[(j).intValue()]).getNumPageInPDF();
        }
        res.add(dF.createStudentSheet(index, ((List<Integer>)Conversions.doWrapArray(pagesArray))));
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
    int res = 0;
    synchronized (this.sheets) {
      int _length = ((Object[])Conversions.unwrapArray(this.sheets, Object.class)).length;
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
      for (final Integer i : _doubleDotLessThan) {
        int _res = res;
        int _length_1 = ((Object[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(this.sheets, Copie.class))[(i).intValue()]).getSetPages(), Object.class)).length;
        res = (_res + _length_1);
      }
    }
    return res;
  }
  
  public static void main(final String[] arg) {
    try {
      final File pdf = new File("./src/main/resources/QRCode/pfo_example_Inserted.pdf");
      final PDDocument doc = PDDocument.load(pdf);
      final PdfReaderQrCodeImpl qrcodeReader = new PdfReaderQrCodeImpl(doc, 8, 32);
      qrcodeReader.readPDf();
      InputOutput.<String>println("le threads principal continue");
      while ((qrcodeReader.getNbPagesTreated() != qrcodeReader.getNbPagesPdf())) {
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
