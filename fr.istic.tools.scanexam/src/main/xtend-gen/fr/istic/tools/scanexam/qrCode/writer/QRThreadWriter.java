package fr.istic.tools.scanexam.qrCode.writer;

import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl;
import java.util.concurrent.CountDownLatch;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class QRThreadWriter extends Thread implements Runnable {
  private QRCodeGeneratorImpl generator;
  
  private int borneInf;
  
  private int borneMax;
  
  private PDDocument docSujetMaitre;
  
  private int nbPages;
  
  private CountDownLatch countDown;
  
  private CountDownLatch countDownMain;
  
  private String name;
  
  private String pathImage;
  
  public QRThreadWriter(final QRCodeGeneratorImpl gen, final int inf, final int max, final PDDocument docSujetMaitre, final int nbPages, final CountDownLatch countDown, final CountDownLatch countDownMain, final String name, final String pathImage) {
    this.generator = gen;
    this.borneInf = inf;
    this.borneMax = max;
    this.docSujetMaitre = docSujetMaitre;
    this.nbPages = nbPages;
    this.countDown = countDown;
    this.countDownMain = countDownMain;
    this.name = name;
    this.pathImage = pathImage;
  }
  
  public void run() {
    try {
      this.countDownMain.await();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(this.borneInf, this.borneMax, true);
      for (final Integer i : _doubleDotLessThan) {
        this.generator.insertQRCodeInSubject(this.name, this.docSujetMaitre, (i).intValue(), this.nbPages, this.pathImage);
      }
      this.countDown.countDown();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
