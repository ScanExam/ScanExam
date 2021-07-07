package fr.istic.tools.scanexam.qrCode.reader;

import fr.istic.tools.scanexam.qrCode.reader.PdfReaderQrCodeImpl;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderQrCodeV2Impl;
import java.util.concurrent.CountDownLatch;
import javafx.util.Pair;
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
  
  private Pair<Float, Float> qrPos;
  
  private final int qrCodeType;
  
  private CountDownLatch countDown;
  
  public PdfReaderQrCodeThread(final PdfReaderQrCodeImpl reader, final PDDocument pdDoc, final String docPath, final int inf, final int max, final PDFRenderer pdf, final Pair<Float, Float> qrPos, final int qrCodeType, final CountDownLatch countDown) {
    this.reader = reader;
    this.pdDoc = pdDoc;
    this.docPath = docPath;
    this.pdf = pdf;
    this.borneInf = inf;
    this.borneMax = max;
    this.qrPos = qrPos;
    this.qrCodeType = qrCodeType;
    this.countDown = countDown;
  }
  
  @Override
  public void run() {
    try {
      ((PdfReaderQrCodeV2Impl) this.reader).readQRCodeImage(this.pdDoc, this.docPath, this.pdf, this.borneInf, 
        this.borneMax, this.qrPos, this.qrCodeType);
      this.countDown.countDown();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
