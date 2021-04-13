package fr.istic.tools.scanexam.qrCode.writer;

import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl;
import fr.istic.tools.scanexam.qrCode.writer.QRThreadWriter;
import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class PdfThreadManagerWriter extends Thread implements Runnable {
  private int nbPage;
  
  private PDDocument docSujetMaitre;
  
  private QRCodeGeneratorImpl writer;
  
  private int nbCopie;
  
  private String examID;
  
  private String output;
  
  public PdfThreadManagerWriter(final int nbPage, final PDDocument docSujetMaitre, final QRCodeGeneratorImpl writer, final int nbCopie, final String examID, final String output) {
    this.nbPage = nbPage;
    this.docSujetMaitre = docSujetMaitre;
    this.writer = writer;
    this.nbCopie = nbCopie;
    this.examID = examID;
    this.output = output;
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
      QRThreadWriter _qRThreadWriter = new QRThreadWriter(this.writer, 0, (this.nbCopie / 4), this.docSujetMaitre, this.nbPage, latchThreads, this.examID, _absolutePath);
      service.execute(_qRThreadWriter);
      String _absolutePath_1 = qrcode1.getAbsolutePath();
      QRThreadWriter _qRThreadWriter_1 = new QRThreadWriter(this.writer, (this.nbCopie / 4), (this.nbCopie / 2), this.docSujetMaitre, this.nbPage, latchThreads, this.examID, _absolutePath_1);
      service.execute(_qRThreadWriter_1);
      String _absolutePath_2 = qrcode2.getAbsolutePath();
      QRThreadWriter _qRThreadWriter_2 = new QRThreadWriter(this.writer, (this.nbCopie / 2), ((3 * this.nbCopie) / 4), this.docSujetMaitre, this.nbPage, latchThreads, this.examID, _absolutePath_2);
      service.execute(_qRThreadWriter_2);
      String _absolutePath_3 = qrcode3.getAbsolutePath();
      QRThreadWriter _qRThreadWriter_3 = new QRThreadWriter(this.writer, ((3 * this.nbCopie) / 4), this.nbCopie, this.docSujetMaitre, this.nbPage, latchThreads, this.examID, _absolutePath_3);
      service.execute(_qRThreadWriter_3);
      latchThreads.await();
      long _count = latchThreads.getCount();
      String _plus = ("Fermeture du Thread Manager : " + Long.valueOf(_count));
      InputOutput.<String>println(_plus);
      this.writer.setFinished(true);
      service.shutdown();
      this.docSujetMaitre.save(this.output);
      qrcode0.deleteOnExit();
      qrcode1.deleteOnExit();
      qrcode2.deleteOnExit();
      qrcode3.deleteOnExit();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}