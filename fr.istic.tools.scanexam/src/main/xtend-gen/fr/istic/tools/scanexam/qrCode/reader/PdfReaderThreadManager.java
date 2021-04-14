package fr.istic.tools.scanexam.qrCode.reader;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class PdfReaderThreadManager extends Thread implements Runnable {
  private int nbPage;
  
  private PDFRenderer pdfRenderer;
  
  private PdfReaderQrCodeImpl reader;
  
  public PdfReaderThreadManager(final int nbPage, final PDFRenderer pdfRenderer, final PdfReaderQrCodeImpl reader) {
    this.nbPage = nbPage;
    this.pdfRenderer = pdfRenderer;
    this.reader = reader;
  }
  
  @Override
  public void run() {
    try {
      final ExecutorService service = Executors.newFixedThreadPool(4);
      final CountDownLatch latchThreads = new CountDownLatch(4);
      PdfReaderQrCodeThread _pdfReaderQrCodeThread = new PdfReaderQrCodeThread(this.reader, 0, (this.nbPage / 4), this.pdfRenderer, latchThreads);
      service.execute(_pdfReaderQrCodeThread);
      PdfReaderQrCodeThread _pdfReaderQrCodeThread_1 = new PdfReaderQrCodeThread(this.reader, (this.nbPage / 4), (this.nbPage / 2), this.pdfRenderer, latchThreads);
      service.execute(_pdfReaderQrCodeThread_1);
      PdfReaderQrCodeThread _pdfReaderQrCodeThread_2 = new PdfReaderQrCodeThread(this.reader, (this.nbPage / 2), ((3 * this.nbPage) / 4), this.pdfRenderer, latchThreads);
      service.execute(_pdfReaderQrCodeThread_2);
      PdfReaderQrCodeThread _pdfReaderQrCodeThread_3 = new PdfReaderQrCodeThread(this.reader, ((3 * this.nbPage) / 4), this.nbPage, this.pdfRenderer, latchThreads);
      service.execute(_pdfReaderQrCodeThread_3);
      latchThreads.await();
      long _count = latchThreads.getCount();
      String _plus = ("Fermeture du Thread Manager : " + Long.valueOf(_count));
      InputOutput.<String>println(_plus);
      this.reader.setFinished(true);
      service.shutdown();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
