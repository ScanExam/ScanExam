package fr.istic.tools.scanexam.qrCode.reader;

import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeImpl;
import java.util.concurrent.CountDownLatch;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class PdfReaderWithoutQrCodeThread extends Thread implements Runnable {
  private PdfReaderWithoutQrCodeImpl reader;
  
  private PDFRenderer pdf;
  
  private int borneInf;
  
  private int borneMax;
  
  private CountDownLatch countDown;
  
  private CountDownLatch countDownMain;
  
  public PdfReaderWithoutQrCodeThread(final PdfReaderWithoutQrCodeImpl reader, final int inf, final int max, final PDFRenderer pdf, final CountDownLatch countDown, final CountDownLatch countDownMain) {
    this.reader = reader;
    this.pdf = pdf;
    this.borneInf = inf;
    this.borneMax = max;
    this.countDown = countDown;
    this.countDownMain = countDownMain;
  }
  
  @Override
  public void run() {
    try {
      this.countDownMain.await();
      this.reader.readPartition(this.pdf, this.borneInf, this.borneMax);
      this.countDown.countDown();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
