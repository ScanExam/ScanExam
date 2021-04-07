package fr.istic.tools.scanexam.qrCode.reader;

import fr.istic.tools.scanexam.api.DataFactory;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.qrCode.reader.Copie;
import fr.istic.tools.scanexam.qrCode.reader.Page;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCode;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeThread;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class PdfReaderWithoutQrCodeImpl implements PdfReaderWithoutQrCode {
  private Set<Copie> sheets;
  
  private int nbSheetsTotal;
  
  private int nbPagesInSheet;
  
  private int nbPagesInPdf;
  
  private PDDocument doc;
  
  public PdfReaderWithoutQrCodeImpl(final PDDocument doc, final int nbPages, final int nbCopies) {
    this.doc = doc;
    this.nbPagesInSheet = nbPages;
    this.nbSheetsTotal = nbCopies;
    HashSet<Copie> _hashSet = new HashSet<Copie>();
    this.sheets = _hashSet;
  }
  
  /**
   * Lit le PDF spécifié
   */
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
  
  /**
   * Méthode qui créer 4 threads pour lire le pdf
   * @param pdfRenderer pdf en source de lecture
   * @param nbPage nombre de pages du sujet Maitre
   */
  public void createThread(final int nbPage, final PDFRenderer pdfRenderer) {
    try {
      final ExecutorService service = Executors.newFixedThreadPool(4);
      final CountDownLatch latchThreads = new CountDownLatch(4);
      final CountDownLatch latchMain = new CountDownLatch(1);
      PdfReaderWithoutQrCodeThread _pdfReaderWithoutQrCodeThread = new PdfReaderWithoutQrCodeThread(this, 0, (nbPage / 4), pdfRenderer, latchThreads, latchMain);
      service.execute(_pdfReaderWithoutQrCodeThread);
      PdfReaderWithoutQrCodeThread _pdfReaderWithoutQrCodeThread_1 = new PdfReaderWithoutQrCodeThread(this, (nbPage / 4), (nbPage / 2), pdfRenderer, latchThreads, latchMain);
      service.execute(_pdfReaderWithoutQrCodeThread_1);
      PdfReaderWithoutQrCodeThread _pdfReaderWithoutQrCodeThread_2 = new PdfReaderWithoutQrCodeThread(this, (nbPage / 2), (3 * (nbPage / 4)), pdfRenderer, latchThreads, latchMain);
      service.execute(_pdfReaderWithoutQrCodeThread_2);
      PdfReaderWithoutQrCodeThread _pdfReaderWithoutQrCodeThread_3 = new PdfReaderWithoutQrCodeThread(this, ((3 * nbPage) / 4), nbPage, pdfRenderer, latchThreads, latchMain);
      service.execute(_pdfReaderWithoutQrCodeThread_3);
      latchMain.countDown();
      latchThreads.await();
      service.shutdown();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void verificationClosureThreads(final ExecutorService service, final CountDownLatch lThreads) {
    long _count = lThreads.getCount();
    boolean _equals = (_count == 0);
    if (_equals) {
      service.shutdown();
    }
  }
  
  private final Lock lock = new ReentrantLock();
  
  /**
   * Méthode qui lit une certaine partie d'un pdf
   * @param renderer le pdf lu
   * @param i la borne inférieure
   * @param j la borne supérieure
   */
  public void readPartition(final PDFRenderer renderer, final int i, final int j) {
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(i, j, true);
    for (final Integer page : _doubleDotLessThan) {
      {
        final Copie cop = new Copie(((page).intValue() / this.nbPagesInSheet), (page).intValue(), ((page).intValue() % this.nbPagesInSheet));
        this.lock.lock();
        this.addCopie(cop);
        this.lock.unlock();
      }
    }
  }
  
  /**
   * Ajoute une copie lu au tas de copies déjà lues. Si la copie existe déjà, on merge les pages
   * @param sheet la copie à ajouter
   */
  public Boolean addCopie(final Copie sheet) {
    boolean _xblockexpression = false;
    {
      boolean trouve = false;
      int i = 0;
      int len = ((Object[])Conversions.unwrapArray(this.sheets, Object.class)).length;
      while (((!trouve) && (i < len))) {
        {
          int _numCopie = (((Copie[])Conversions.unwrapArray(this.sheets, Copie.class))[i]).getNumCopie();
          int _numCopie_1 = sheet.getNumCopie();
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
        (((Copie[])Conversions.unwrapArray(this.sheets, Copie.class))[i]).addInSet(sheet.getPagesCopie());
      } else {
        _xifexpression = this.sheets.add(sheet);
      }
      _xblockexpression = _xifexpression;
    }
    return Boolean.valueOf(_xblockexpression);
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
   * Renvoie une collection de toutes les copies lues, complètes ou non
   * @return une collection de toutes les copies lues
   */
  public Set<Copie> getSheets() {
    return this.sheets;
  }
  
  @Override
  public boolean isFinished() {
    return true;
  }
  
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
          pagesArray[(((Page[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_3, Copie.class))[(i).intValue()]).getPagesCopie(), Page.class))[(j).intValue()]).getNumPageInSubject()] = (((Page[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_4, Copie.class))[(i).intValue()]).getPagesCopie(), Page.class))[(j).intValue()]).getNumPageInPDF();
        }
        res.add(dF.createStudentSheet(index, ((List<Integer>)Conversions.doWrapArray(pagesArray))));
      }
    }
    return res;
  }
  
  @Override
  public Collection<StudentSheet> getUncompleteStudentSheets() {
    final Set<StudentSheet> res = new HashSet<StudentSheet>();
    Set<Copie> temp = new HashSet<Copie>();
    final DataFactory dF = new DataFactory();
    temp = this.getUncompleteCopies();
    Set<Copie> temp2 = new HashSet<Copie>();
    temp2 = this.getCompleteCopies();
    InputOutput.<String>println(temp2.toString());
    InputOutput.<String>println(temp.toString());
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
          pagesArray[(((Page[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_3, Copie.class))[(i).intValue()]).getPagesCopie(), Page.class))[(j).intValue()]).getNumPageInSubject()] = (((Page[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(_converted_temp_4, Copie.class))[(i).intValue()]).getPagesCopie(), Page.class))[(j).intValue()]).getNumPageInPDF();
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
    int _length = ((Object[])Conversions.unwrapArray(this.sheets, Object.class)).length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      int _res = res;
      int _length_1 = ((Object[])Conversions.unwrapArray((((Copie[])Conversions.unwrapArray(this.sheets, Copie.class))[(i).intValue()]).getSetPages(), Object.class)).length;
      res = (_res + _length_1);
    }
    return res;
  }
  
  public static void main(final String[] arg) {
  }
}
