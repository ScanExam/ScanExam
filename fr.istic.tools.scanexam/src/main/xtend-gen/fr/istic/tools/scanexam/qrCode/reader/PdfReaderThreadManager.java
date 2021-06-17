package fr.istic.tools.scanexam.qrCode.reader;

import fr.istic.tools.scanexam.qrCode.reader.PdfReaderQrCodeImpl;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderQrCodeThread;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.util.Pair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class PdfReaderThreadManager extends Thread implements Runnable {
  private int nbPage;
  
  private PDDocument doc;
  
  private String docPath;
  
  private Pair<Float, Float> qrPos;
  
  private PdfReaderQrCodeImpl reader;
  
  public PdfReaderThreadManager(final int nbPage, final PDDocument doc, final String docPath, final Pair<Float, Float> qrPos, final PdfReaderQrCodeImpl reader) {
    this.nbPage = nbPage;
    this.doc = doc;
    this.docPath = docPath;
    this.qrPos = qrPos;
    this.reader = reader;
  }
  
  @Override
  public void run() {
    try {
      final ExecutorService service = Executors.newFixedThreadPool(4);
      final CountDownLatch latchThreads = new CountDownLatch(4);
      final PDFRenderer pdf = new PDFRenderer(this.doc);
      PdfReaderQrCodeThread _pdfReaderQrCodeThread = new PdfReaderQrCodeThread(this.reader, this.doc, this.docPath, 0, (this.nbPage / 4), pdf, this.qrPos, latchThreads);
      service.execute(_pdfReaderQrCodeThread);
      PdfReaderQrCodeThread _pdfReaderQrCodeThread_1 = new PdfReaderQrCodeThread(this.reader, this.doc, this.docPath, (this.nbPage / 4), (this.nbPage / 2), pdf, this.qrPos, latchThreads);
      service.execute(_pdfReaderQrCodeThread_1);
      PdfReaderQrCodeThread _pdfReaderQrCodeThread_2 = new PdfReaderQrCodeThread(this.reader, this.doc, this.docPath, (this.nbPage / 2), ((3 * this.nbPage) / 4), pdf, this.qrPos, latchThreads);
      service.execute(_pdfReaderQrCodeThread_2);
      PdfReaderQrCodeThread _pdfReaderQrCodeThread_3 = new PdfReaderQrCodeThread(this.reader, this.doc, this.docPath, ((3 * this.nbPage) / 4), this.nbPage, pdf, this.qrPos, latchThreads);
      service.execute(_pdfReaderQrCodeThread_3);
      latchThreads.await();
      this.reader.setFinished(true);
      service.shutdown();
      this.doc.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
