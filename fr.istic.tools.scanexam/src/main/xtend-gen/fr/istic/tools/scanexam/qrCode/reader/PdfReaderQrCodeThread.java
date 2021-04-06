package fr.istic.tools.scanexam.qrCode.reader;

import fr.istic.tools.scanexam.qrCode.reader.PdfReaderQrCodeImpl;
import java.util.concurrent.CountDownLatch;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class PdfReaderQrCodeThread extends Thread implements Runnable {
  private PdfReaderQrCodeImpl reader;
  
  private PDFRenderer pdf;
  
  private int borneInf;
  
  private int borneMax;
  
  private CountDownLatch countDown;
  
  public PdfReaderQrCodeThread(final PdfReaderQrCodeImpl reader, final int inf, final int max, final PDFRenderer pdf, final CountDownLatch countDown) {
    this.reader = reader;
    this.pdf = pdf;
    this.borneInf = inf;
    this.borneMax = max;
    this.countDown = countDown;
  }
  
  @Override
  public void run() {
    try {
      this.reader.readQRCodeImage(this.pdf, this.borneInf, this.borneMax);
      this.countDown.countDown();
      long _count = this.countDown.getCount();
      String _plus = ("Fermeture du Thread de Lecture: " + Long.valueOf(_count));
      InputOutput.<String>println(_plus);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
