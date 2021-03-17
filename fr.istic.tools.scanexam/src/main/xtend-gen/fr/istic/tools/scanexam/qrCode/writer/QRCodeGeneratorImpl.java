package fr.istic.tools.scanexam.qrCode.writer;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator;
import fr.istic.tools.scanexam.qrCode.writer.QRThreadWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class QRCodeGeneratorImpl implements QRCodeGenerator {
  /**
   * @param text Le texte a encoder
   * @param width  Largeur de l'image
   * @param height Hauteur de l'image
   * @param filePath Chemin du nouveau fichier
   * 
   * CrÃ©e un QRCode (21 * 21 carrÃ©s) de taille width * height encryptant la chaine text.
   * Un fichier PNG du QRCode est crÃ©e en suivant le filePath
   * 
   * @throws WriterException
   * @throws IOException
   */
  public void generateQRCodeImage(final String text, final int width, final int height, final String filePath) throws WriterException, IOException {
    final QRCodeWriter qrCodeWriter = new QRCodeWriter();
    final BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
    final Path path = FileSystems.getDefault().getPath(filePath);
    MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
  }
  
  /**
   * Ajoute une image imagePath sur toutes les pages du pdf inputFile puis
   * l'enregistre dans outputFile
   * 
   * @param inputFile  Chemin du pdf cible
   * @param imagePath  Chemin de l'image a insÃ©rer
   * @param outputFile Chemin du fichier a Ã©crire
   * 
   * @throws IOException If there is an error writing the data.
   */
  public void createPdfFromImageInAllPages(final String inputFile, final String imagePath, final String outputFile) throws IOException {
    try (final PDDocument doc = new Function0<PDDocument>() {
      @Override
      public PDDocument apply() {
        try {
          File _file = new File(inputFile);
          return PDDocument.load(_file);
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    }.apply()) {
      final float scale = 0.3f;
      final PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);
      PDPageTree _pages = doc.getPages();
      for (final PDPage page : _pages) {
        try (final PDPageContentStream contentStream = new Function0<PDPageContentStream>() {
          @Override
          public PDPageContentStream apply() {
            try {
              return new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, 
                true);
            } catch (Throwable _e) {
              throw Exceptions.sneakyThrow(_e);
            }
          }
        }.apply()) {
          int _width = pdImage.getWidth();
          float _multiply = (_width * scale);
          int _height = pdImage.getHeight();
          float _multiply_1 = (_height * scale);
          contentStream.drawImage(pdImage, 0, 0, _multiply, _multiply_1);
        }
      }
      doc.save(outputFile);
    }
  }
  
  @Override
  public void createAllExamCopies(final String inputFile, final int nbCopie) {
    try {
      final String base = inputFile.substring(0, inputFile.lastIndexOf("."));
      final String outputFile = "pfo_Inserted.pdf";
      File _file = new File((base + ".pdf"));
      final PDDocument doc = PDDocument.load(_file);
      final int nbPages = doc.getNumberOfPages();
      final PDFMergerUtility PDFmerger = new PDFMergerUtility();
      PDFmerger.setDestinationFileName("pfo_Duplicated.pdf");
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, nbCopie, true);
      for (final Integer i : _doubleDotLessThan) {
        PDFmerger.addSource(inputFile);
      }
      final File f2 = new File("pfo_Duplicated.pdf");
      final MemoryUsageSetting memUsSett = MemoryUsageSetting.setupMainMemoryOnly();
      memUsSett.setTempDir(f2);
      PDFmerger.mergeDocuments(memUsSett);
      final PDDocument docSujetMaitre = PDDocument.load(f2);
      this.createThread(nbCopie, docSujetMaitre, nbPages);
      docSujetMaitre.save(outputFile);
      ExclusiveRange _doubleDotLessThan_1 = new ExclusiveRange(1, 5, true);
      for (final Integer i_1 : _doubleDotLessThan_1) {
        {
          final File png = new File((("./QRCode" + i_1) + ".png"));
          boolean _delete = png.delete();
          if (_delete) {
            InputOutput.<String>println(("Deleted png " + i_1));
          }
        }
      }
      final File f2test = new File("pfo_example_Duplicated.pdf");
      boolean _delete = f2test.delete();
      if (_delete) {
        InputOutput.<String>println("Deleted dupli");
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * @param nbCopies nombre de copies dÃ©sirÃ©es
   * @param docSujetMaitre document dans lequel insÃ©rer les Codes
   * @param nbPages nombre de pages du sujet Maitre
   */
  public void createThread(final int nbCopie, final PDDocument docSujetMaitre, final int nbPage) {
    try {
      final ExecutorService service = Executors.newFixedThreadPool(4);
      final CountDownLatch LatchMain = new CountDownLatch(1);
      if ((nbCopie <= 4)) {
        final CountDownLatch LatchThreads = new CountDownLatch(1);
        QRThreadWriter _qRThreadWriter = new QRThreadWriter(this, 0, nbCopie, docSujetMaitre, 1, nbPage, LatchThreads, LatchMain);
        service.execute(_qRThreadWriter);
        LatchMain.countDown();
        LatchThreads.await();
        service.shutdown();
      } else {
        final CountDownLatch LatchThreads_1 = new CountDownLatch(4);
        QRThreadWriter _qRThreadWriter_1 = new QRThreadWriter(this, 0, (nbCopie / 4), docSujetMaitre, 1, nbPage, LatchThreads_1, LatchMain);
        service.execute(_qRThreadWriter_1);
        QRThreadWriter _qRThreadWriter_2 = new QRThreadWriter(this, (nbCopie / 4), (nbCopie / 2), docSujetMaitre, 2, nbPage, LatchThreads_1, LatchMain);
        service.execute(_qRThreadWriter_2);
        QRThreadWriter _qRThreadWriter_3 = new QRThreadWriter(this, (nbCopie / 2), (3 * (nbCopie / 4)), docSujetMaitre, 3, nbPage, LatchThreads_1, LatchMain);
        service.execute(_qRThreadWriter_3);
        QRThreadWriter _qRThreadWriter_4 = new QRThreadWriter(this, ((3 * nbCopie) / 4), nbCopie, docSujetMaitre, 4, nbPage, LatchThreads_1, LatchMain);
        service.execute(_qRThreadWriter_4);
        LatchMain.countDown();
        LatchThreads_1.await();
        service.shutdown();
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * InsÃ¨re le QRCode sur chacun des pages (en changeant le numÃ©ro de page sur chacunes des pages)
   * 
   * @param nameExam nom de l'examen Ã  insÃ©rer dans le QRCode
   * @param numSubject numÃ©ro de l'examen Ã  insÃ©rer dans le QRCode
   */
  public void insertQRCodeInSubject(final PDDocument docSujetMaitre, final int numCopie, final int numThread, final int nbPagesSujet) {
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, nbPagesSujet, true);
    for (final Integer i : _doubleDotLessThan) {
      this.insertQRCodeInPage((i).intValue(), docSujetMaitre, Integer.valueOf(numThread).toString(), numCopie, nbPagesSujet);
    }
  }
  
  /**
   * InÃ¨sre un QRCode sur une page
   */
  public void insertQRCodeInPage(final int numPage, final PDDocument doc, final String nbThread, final int numCopie, final int nbPagesSujet) {
    try {
      final String stringAEncoder = ((("PFO2019_" + Integer.valueOf(numCopie)) + "_") + Integer.valueOf(numPage));
      String _string = nbThread.toString();
      String _plus = ("./QRCode" + _string);
      final String pathImage = (_plus + ".png");
      this.generateQRCodeImage(stringAEncoder, 350, 350, pathImage);
      final PDImageXObject pdImage = PDImageXObject.createFromFile(pathImage, doc);
      final float scale = 0.3f;
      try (final PDPageContentStream contentStream = new Function0<PDPageContentStream>() {
        @Override
        public PDPageContentStream apply() {
          try {
            PDPage _page = doc.getPage((numPage + (numCopie * nbPagesSujet)));
            return new PDPageContentStream(doc, _page, PDPageContentStream.AppendMode.APPEND, true, 
              true);
          } catch (Throwable _e) {
            throw Exceptions.sneakyThrow(_e);
          }
        }
      }.apply()) {
        int _width = pdImage.getWidth();
        float _multiply = (_width * scale);
        int _height = pdImage.getHeight();
        float _multiply_1 = (_height * scale);
        contentStream.drawImage(pdImage, 0, 0, _multiply, _multiply_1);
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static void main(final String[] arg) {
    try {
      final QRCodeGeneratorImpl gen = new QRCodeGeneratorImpl();
      final String input = "./pfo_example.pdf";
      gen.createAllExamCopies(input, 3);
      final String in = "./pfo_Inserted.pdf";
      final File f = new File(in);
      final PDDocument doc = PDDocument.load(f);
      final File desti = new File("./pfo_Dirty.pdf");
      doc.removePage(12);
      doc.save(desti);
      InputOutput.<String>println("Done");
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
