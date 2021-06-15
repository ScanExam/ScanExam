package fr.istic.tools.scanexam.qrCode.writer;

import fr.istic.tools.scanexam.core.QrCodeZone;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl;
import java.util.concurrent.CountDownLatch;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class QRThreadWriter extends Thread implements Runnable {
  private QRCodeGeneratorImpl writer;
  
  private int borneInf;
  
  private int borneMax;
  
  /**
   * Zone sur le document où insérer le qrcode
   */
  private QrCodeZone qrCodeZone;
  
  private PDDocument docSujetMaitre;
  
  private int nbPages;
  
  private CountDownLatch countDown;
  
  private String pathImage;
  
  public QRThreadWriter(final QRCodeGeneratorImpl writer, final int inf, final int max, final QrCodeZone qrCodeZone, final PDDocument docSujetMaitre, final int nbPages, final CountDownLatch countDown, final String pathImage) {
    this.writer = writer;
    this.borneInf = inf;
    this.borneMax = max;
    this.qrCodeZone = qrCodeZone;
    this.docSujetMaitre = docSujetMaitre;
    this.nbPages = nbPages;
    this.countDown = countDown;
    this.pathImage = pathImage;
  }
  
  @Override
  public void run() {
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(this.borneInf, this.borneMax, true);
    for (final Integer i : _doubleDotLessThan) {
      this.writer.insertQRCodeInSubject(this.qrCodeZone, this.docSujetMaitre, (i).intValue(), this.nbPages, this.pathImage);
    }
    this.countDown.countDown();
  }
}
