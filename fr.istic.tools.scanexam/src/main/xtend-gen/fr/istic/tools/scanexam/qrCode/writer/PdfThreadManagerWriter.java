package fr.istic.tools.scanexam.qrCode.writer;

import fr.istic.tools.scanexam.core.QrCodeZone;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl;
import fr.istic.tools.scanexam.qrCode.writer.QRThreadWriter;
import java.io.File;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class PdfThreadManagerWriter extends Thread implements Runnable {
  private int nbPage;
  
  /**
   * Zone sur le document où insérer le qrcode
   */
  private QrCodeZone qrCodeZone;
  
  /**
   * Type de qr code à insérer
   */
  private int qrCodeType;
  
  private PDDocument docSujetMaitre;
  
  private PDDocument doc;
  
  private QRCodeGeneratorImpl writer;
  
  private int nbCopie;
  
  private OutputStream output;
  
  public PdfThreadManagerWriter(final int nbPage, final QrCodeZone qrCodeZone, final int qrCodeType, final PDDocument docSujetMaitre, final PDDocument doc, final QRCodeGeneratorImpl writer, final int nbCopie, final OutputStream output) {
    this.nbPage = nbPage;
    this.qrCodeZone = qrCodeZone;
    this.qrCodeType = qrCodeType;
    this.docSujetMaitre = docSujetMaitre;
    this.writer = writer;
    this.nbCopie = nbCopie;
    this.output = output;
    this.doc = doc;
  }
  
  @Override
  public void run() {
    try {
      final ExecutorService service = Executors.newFixedThreadPool(4);
      final CountDownLatch latchThreads = new CountDownLatch(4);
      File qrcode0 = File.createTempFile("qrcode0", ".png");
      File qrcode1 = File.createTempFile("qrcode1", ".png");
      File qrcode2 = File.createTempFile("qrcode2", ".png");
      File qrcode3 = File.createTempFile("qrcode3", ".png");
      String _absolutePath = qrcode0.getAbsolutePath();
      QRThreadWriter _qRThreadWriter = new QRThreadWriter(this.writer, this.qrCodeType, 0, (this.nbCopie / 4), this.qrCodeZone, this.docSujetMaitre, this.nbPage, latchThreads, _absolutePath);
      service.execute(_qRThreadWriter);
      String _absolutePath_1 = qrcode1.getAbsolutePath();
      QRThreadWriter _qRThreadWriter_1 = new QRThreadWriter(this.writer, this.qrCodeType, (this.nbCopie / 4), (this.nbCopie / 2), this.qrCodeZone, this.docSujetMaitre, this.nbPage, latchThreads, _absolutePath_1);
      service.execute(_qRThreadWriter_1);
      String _absolutePath_2 = qrcode2.getAbsolutePath();
      QRThreadWriter _qRThreadWriter_2 = new QRThreadWriter(this.writer, this.qrCodeType, (this.nbCopie / 2), ((3 * this.nbCopie) / 4), this.qrCodeZone, this.docSujetMaitre, this.nbPage, latchThreads, _absolutePath_2);
      service.execute(_qRThreadWriter_2);
      String _absolutePath_3 = qrcode3.getAbsolutePath();
      QRThreadWriter _qRThreadWriter_3 = new QRThreadWriter(this.writer, this.qrCodeType, ((3 * this.nbCopie) / 4), this.nbCopie, this.qrCodeZone, this.docSujetMaitre, this.nbPage, latchThreads, _absolutePath_3);
      service.execute(_qRThreadWriter_3);
      latchThreads.await();
      this.writer.setFinished(true);
      service.shutdown();
      this.docSujetMaitre.save(this.output);
      this.doc.close();
      this.docSujetMaitre.close();
      qrcode0.deleteOnExit();
      qrcode1.deleteOnExit();
      qrcode2.deleteOnExit();
      qrcode3.deleteOnExit();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
