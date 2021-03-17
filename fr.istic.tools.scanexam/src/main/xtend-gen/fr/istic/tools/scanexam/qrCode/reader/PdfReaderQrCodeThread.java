package fr.istic.tools.scanexam.qrCode.reader;

import fr.istic.tools.scanexam.qrCode.reader.PdfReaderQrCodeImpl;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class PdfReaderQrCodeThread extends Thread implements Runnable {
  private PdfReaderQrCodeImpl reader;
  
  private PDFRenderer pdf;
  
  private int borneInf;
  
  private int borneMax;
  
  public PdfReaderQrCodeThread(final PdfReaderQrCodeImpl reader, final int inf, final int max, final PDFRenderer pdf) {
    this.reader = reader;
    this.pdf = pdf;
    this.borneInf = inf;
    this.borneMax = max;
  }
  
  public void run() {
    try {
      this.reader.readQRCodeImage(this.pdf, this.borneInf, this.borneMax);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
