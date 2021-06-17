package fr.istic.tools.scanexam.qrCode.reader;

import fr.istic.tools.scanexam.qrCode.reader.PdfReaderQrCodeImpl;
import java.util.concurrent.CountDownLatch;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class PdfReaderQrCodeThread extends Thread implements Runnable {
  private PdfReaderQrCodeImpl reader;
  
  private PDDocument pdDoc;
  
  private String docPath;
  
  private PDFRenderer pdf;
  
  private int borneInf;
  
  private int borneMax;
  
  private CountDownLatch countDown;
  
  public PdfReaderQrCodeThread(final PdfReaderQrCodeImpl reader, final PDDocument pdDoc, final String docPath, final int inf, final int max, final PDFRenderer pdf, final CountDownLatch countDown) {
    this.reader = reader;
    this.pdDoc = pdDoc;
    this.docPath = docPath;
    this.pdf = pdf;
    this.borneInf = inf;
    this.borneMax = max;
    this.countDown = countDown;
  }
  
  @Override
  public void run() {
    try {
      this.reader.readQRCodeImage(this.pdDoc, this.docPath, this.pdf, this.borneInf, this.borneMax);
      this.countDown.countDown();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
