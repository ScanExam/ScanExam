package fr.istic.tools.scanexam.qrCode.reader;

import fr.istic.tools.scanexam.qrCode.QrCodeType;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderQrCodeImpl;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderQrCodeThread;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.util.Pair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class PdfReaderThreadManager extends Thread implements Runnable {
  private int nbPage;
  
  private PDDocument doc;
  
  private String docPath;
  
  private Pair<Float, Float> qrPos;
  
  private final int qrCodeType;
  
  private final int nbThread;
  
  private PdfReaderQrCodeImpl reader;
  
  public PdfReaderThreadManager(final int nbPage, final PDDocument doc, final String docPath, final Pair<Float, Float> qrPos, final int qrCodeType, final PdfReaderQrCodeImpl reader) {
    this.nbPage = nbPage;
    this.doc = doc;
    this.docPath = docPath;
    this.qrPos = qrPos;
    this.qrCodeType = qrCodeType;
    if ((this.qrCodeType == QrCodeType.PAGE)) {
      this.nbThread = 1;
    } else {
      this.nbThread = 4;
    }
    this.reader = reader;
  }
  
  @Override
  public void run() {
    try {
      final ExecutorService service = Executors.newFixedThreadPool(this.nbThread);
      final CountDownLatch latchThreads = new CountDownLatch(this.nbThread);
      final PDFRenderer pdf = new PDFRenderer(this.doc);
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, this.nbThread, true);
      for (final Integer i : _doubleDotLessThan) {
        PdfReaderQrCodeThread _pdfReaderQrCodeThread = new PdfReaderQrCodeThread(this.reader, this.doc, this.docPath, (((i).intValue() * this.nbPage) / this.nbThread), ((((i).intValue() + 1) * this.nbPage) / this.nbThread), pdf, this.qrPos, this.qrCodeType, latchThreads);
        service.execute(_pdfReaderQrCodeThread);
      }
      latchThreads.await();
      this.reader.setFinished(true);
      service.shutdown();
      this.doc.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
