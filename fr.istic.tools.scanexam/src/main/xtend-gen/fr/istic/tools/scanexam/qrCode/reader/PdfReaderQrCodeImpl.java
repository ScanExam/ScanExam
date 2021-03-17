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
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderQrCodeThread;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
  
  private File pdfFile;
  
  public PdfReaderQrCodeImpl(final File pFile, final int nbPages, final int nbCopies) {
    this.pdfFile = pFile;
    this.nbPagesInSheet = nbPages;
    this.nbSheetsTotal = nbCopies;
    HashSet<Copie> _hashSet = new HashSet<Copie>();
    this.sheets = _hashSet;
  }
  
  public boolean readPDf() {
    try {
      final PDDocument doc = PDDocument.load(this.pdfFile);
      final PDFRenderer pdf = new PDFRenderer(doc);
      this.createThread(doc.getNumberOfPages(), pdf);
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
        int _parseInt = Integer.parseInt(items[1]);
        int _parseInt_1 = Integer.parseInt(items[2]);
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
    final ExecutorService service = Executors.newFixedThreadPool(4);
    PdfReaderQrCodeThread _pdfReaderQrCodeThread = new PdfReaderQrCodeThread(this, 0, (nbPage / 4), pdfRenderer);
    service.execute(_pdfReaderQrCodeThread);
    PdfReaderQrCodeThread _pdfReaderQrCodeThread_1 = new PdfReaderQrCodeThread(this, (nbPage / 4), (nbPage / 2), pdfRenderer);
    service.execute(_pdfReaderQrCodeThread_1);
    PdfReaderQrCodeThread _pdfReaderQrCodeThread_2 = new PdfReaderQrCodeThread(this, (nbPage / 2), (3 * (nbPage / 4)), pdfRenderer);
    service.execute(_pdfReaderQrCodeThread_2);
    PdfReaderQrCodeThread _pdfReaderQrCodeThread_3 = new PdfReaderQrCodeThread(this, ((3 * nbPage) / 4), nbPage, pdfRenderer);
    service.execute(_pdfReaderQrCodeThread_3);
    service.shutdown();
  }
  
  public boolean isExamenComplete() {
    boolean ret = true;
    int _length = ((Object[])Conversions.unwrapArray(this.sheets, Object.class)).length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      ret = (ret && (((Copie[])Conversions.unwrapArray(this.sheets, Copie.class))[(i).intValue()]).isCopyComplete(this.nbPagesInSheet));
    }
    return (ret && (this.nbSheetsTotal == ((Object[])Conversions.unwrapArray(this.sheets, Object.class)).length));
  }
  
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
  
  public Set<Copie> getCompleteCopies() {
    Set<Copie> completeCopies = new HashSet<Copie>();
    final Predicate<Copie> _function = new Predicate<Copie>() {
      public boolean test(final Copie copie) {
        return copie.isCopyComplete(PdfReaderQrCodeImpl.this.nbPagesInSheet);
      }
    };
    completeCopies = this.sheets.stream().filter(_function).collect(Collectors.<Copie>toSet());
    return completeCopies;
  }
  
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
  
  public Set<Copie> getSheets() {
    return this.sheets;
  }
  
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
        final List<Integer> pages = new ArrayList<Integer>();
        final Set<Copie> _converted_temp_2 = (Set<Copie>)temp;
        int _length_1 = ((Object[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_2, Copie.class))[(i).intValue()]).getPagesCopie(), Object.class)).length;
        ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(0, _length_1, true);
        for (final Integer j : _doubleDotLessThan_1) {
          final Set<Copie> _converted_temp_3 = (Set<Copie>)temp;
          final Set<Copie> _converted_temp_4 = (Set<Copie>)temp;
          pages.add((((Page[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_3, Copie.class))[(i).intValue()]).getPagesCopie(), Page.class))[(j).intValue()]).getNumPageInSubject(), Integer.valueOf((((Page[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_4, Copie.class))[(i).intValue()]).getPagesCopie(), Page.class))[(j).intValue()]).getNumPageInPDF()));
        }
        res.add(dF.createStudentSheet(index, pages));
      }
    }
    return res;
  }
  
  public Collection<StudentSheet> getUncompleteStudentSheets() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
  
  public Object getStudentSheets() {
    return null;
  }
  
  public int getNbPagesPdf() {
    try {
      final PDDocument doc = PDDocument.load(this.pdfFile);
      final int nbPages = doc.getNumberOfPages();
      doc.close();
      return nbPages;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public int getNbPagesTreated() {
    int res = 0;
    int _length = ((Object[])Conversions.unwrapArray(this.sheets, Object.class)).length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      int _res = res;
      int _length_1 = ((Object[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(this.sheets, Copie.class))[(i).intValue()]).getSetPages(), Object.class)).length;
      res = (_res + _length_1);
    }
    return res;
  }
  
  /**
   * FIXME
   * C'est parti en sucette je crois, boucle infini, il a pas trouvé de QRCode dans une des pages
   * impossible de comprendre pour le moment, à check
   */
  public static void main(final String[] arg) {
    try {
      final File pdf = new File("pfo_example_Inserted.pdf");
      final PdfReaderQrCodeImpl qrcodeReader = new PdfReaderQrCodeImpl(pdf, 8, 200);
      qrcodeReader.readPDf();
      int progress = 0;
      while ((progress != 100)) {
        {
          TimeUnit.SECONDS.sleep(1);
          int _nbPagesTreated = qrcodeReader.getNbPagesTreated();
          int _multiply = (_nbPagesTreated * 100);
          int _nbPagesPdf = qrcodeReader.getNbPagesPdf();
          int _divide = (_multiply / _nbPagesPdf);
          progress = _divide;
          InputOutput.<String>println(("Progress : " + Integer.valueOf(progress)));
        }
      }
      int _length = ((Object[])Conversions.unwrapArray(qrcodeReader.sheets, Object.class)).length;
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
      for (final Integer i : _doubleDotLessThan) {
        InputOutput.<String>println((((Copie[])Conversions.unwrapArray(qrcodeReader.sheets, Copie.class))[(i).intValue()]).toString());
      }
      InputOutput.<Boolean>println(Boolean.valueOf(qrcodeReader.isExamenComplete()));
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
