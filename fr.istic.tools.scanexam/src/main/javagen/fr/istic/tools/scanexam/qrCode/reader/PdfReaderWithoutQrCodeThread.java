package fr.istic.tools.scanexam.qrCode.reader;

import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeImpl;
import org.apache.pdfbox.rendering.PDFRenderer;

@SuppressWarnings("all")
public class PdfReaderWithoutQrCodeThread extends Thread implements Runnable {
  private PdfReaderWithoutQrCodeImpl reader;
  
  private PDFRenderer pdf;
  
  private int borneInf;
  
  private int borneMax;
  
  public PdfReaderWithoutQrCodeThread(final PdfReaderWithoutQrCodeImpl reader, final int inf, final int max, final PDFRenderer pdf) {
    this.reader = reader;
    this.pdf = pdf;
    this.borneInf = inf;
    this.borneMax = max;
  }
  
  @Override
  public void run() {
    this.reader.readPartition(this.pdf, this.borneInf, this.borneMax);
  }
}
