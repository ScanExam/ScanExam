package fr.istic.tools.scanexam.qrCode.writer;

import java.util.concurrent.CountDownLatch;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class QRThreadWriter extends Thread implements Runnable {
  private QRCodeGeneratorImpl writer;
  
  private int borneInf;
  
  private int borneMax;
  
  private PDDocument docSujetMaitre;
  
  private int nbPages;
  
  private CountDownLatch countDown;
  
  private String name;
  
  private String pathImage;
  
  public QRThreadWriter(final QRCodeGeneratorImpl writer, final int inf, final int max, final PDDocument docSujetMaitre, final int nbPages, final CountDownLatch countDown, final String name, final String pathImage) {
    this.writer = writer;
    this.borneInf = inf;
    this.borneMax = max;
    this.docSujetMaitre = docSujetMaitre;
    this.nbPages = nbPages;
    this.countDown = countDown;
    this.name = name;
    this.pathImage = pathImage;
  }
  
  @Override
  public void run() {
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(this.borneInf, this.borneMax, true);
    for (final Integer i : _doubleDotLessThan) {
      this.writer.insertQRCodeInSubject(this.name, this.docSujetMaitre, (i).intValue(), this.nbPages, this.pathImage);
    }
    this.countDown.countDown();
    InputOutput.<Long>println(Long.valueOf(this.countDown.getCount()));
  }
}
