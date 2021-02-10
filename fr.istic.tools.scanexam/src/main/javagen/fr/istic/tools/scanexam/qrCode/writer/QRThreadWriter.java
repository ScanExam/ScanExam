package fr.istic.tools.scanexam.qrCode.writer;

import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class QRThreadWriter extends Thread implements Runnable {
  private QRCodeGeneratorImpl generator;
  
  private int borneInf;
  
  private int borneMax;
  
  private PDDocument docSujetMaitre;
  
  private int numThread;
  
  private int nbPages;
  
  public QRThreadWriter(final QRCodeGeneratorImpl gen, final int inf, final int max, final PDDocument docSujetMaitre, final int numThread, final int nbPages) {
    this.generator = gen;
    this.borneInf = inf;
    this.borneMax = max;
    this.docSujetMaitre = docSujetMaitre;
    this.numThread = numThread;
    this.nbPages = nbPages;
  }
  
  @Override
  public void run() {
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(this.borneInf, this.borneMax, true);
    for (final Integer i : _doubleDotLessThan) {
      this.generator.insertQRCodeInSubject(this.docSujetMaitre, (i).intValue(), this.numThread, this.nbPages);
    }
  }
}
