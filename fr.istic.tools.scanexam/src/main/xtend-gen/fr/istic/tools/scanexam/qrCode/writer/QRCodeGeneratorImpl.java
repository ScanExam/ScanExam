package fr.istic.tools.scanexam.qrCode.writer;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import fr.istic.tools.scanexam.qrCode.writer.PdfThreadManagerWriter;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator;
import fr.istic.tools.scanexam.qrCode.writer.QRThreadWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
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

@SuppressWarnings("all")
public class QRCodeGeneratorImpl implements QRCodeGenerator {
  private boolean isFinished;
  
  /**
   * Créer toutes les copies d'examen en y insérant les QrCodes correspondant dans chaque pages
   * 
   * @param inputFile Chemin du sujet maitre
   * @param outputPath chemin de sortie
   * @param idExam l'id de l'examen
   * @param nbCopies Nombre de copies de l'examen souhaité
   */
  @Override
  public void createAllExamCopies(final InputStream inputFile, final File outFile, final String idExam, final int nbCopie) {
    try {
      final byte[] byteArray = new byte[inputFile.available()];
      inputFile.read(byteArray);
      final File temp = File.createTempFile("pdfTemp", ".pdf");
      temp.deleteOnExit();
      final OutputStream oS = new FileOutputStream(temp);
      oS.write(byteArray);
      oS.close();
      final PDDocument doc = PDDocument.load(temp);
      final int nbPages = doc.getNumberOfPages();
      final MemoryUsageSetting memUsSett = MemoryUsageSetting.setupMainMemoryOnly();
      PDFMergerUtility ut = new PDFMergerUtility();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, nbCopie, true);
      for (final Integer i : _doubleDotLessThan) {
        ut.addSource(temp);
      }
      ut.setDestinationFileName(outFile.getAbsolutePath());
      memUsSett.setTempDir(temp);
      ut.mergeDocuments(memUsSett);
      final PDDocument docSujetMaitre = PDDocument.load(outFile);
      FileOutputStream _fileOutputStream = new FileOutputStream(outFile);
      this.createThread(idExam, nbCopie, docSujetMaitre, doc, nbPages, _fileOutputStream);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  /**
   * CrÃ©e un QRCode (21 * 21 carrÃ©s) de taille width * height encryptant la chaine text.
   * Un fichier PNG du QRCode est crÃ©e en suivant le filePath
   * @param text Le texte a encoder
   * @param width  Largeur de l'image
   * @param height Hauteur de l'image
   * @param filePath Chemin du nouveau fichier
   * 
   * @throws WriterException
   * @throws IOException
   */
  public void generateQRCodeImage(final String text, final int width, final int height, final String filePath) {
    try {
      final QRCodeWriter qrCodeWriter = new QRCodeWriter();
      final BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
      final Path path = FileSystems.getDefault().getPath(filePath);
      MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    } catch (final Throwable _t) {
      if (_t instanceof WriterException || _t instanceof IOException) {
        final Exception e = (Exception)_t;
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
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
  public void createPdfFromImageInAllPages(final String inputFile, final String imagePath, final String outputFile) {
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
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  /**
   * @param name l'intitulé du document
   * @param nbCopies nombre de copies dÃ©sirÃ©es
   * @param docSujetMaitre document dans lequel insÃ©rer les Codes
   * @param nbPages nombre de pages du sujet Maitre
   */
  public void createThread(final String examID, final int nbCopie, final PDDocument docSujetMaitre, final PDDocument doc, final int nbPage, final OutputStream output) {
    try {
      if ((nbCopie < 4)) {
        final ExecutorService service = Executors.newFixedThreadPool(1);
        File qrcode = File.createTempFile("qrcode", ".png");
        final CountDownLatch LatchThreads = new CountDownLatch(1);
        String _absolutePath = qrcode.getAbsolutePath();
        QRThreadWriter _qRThreadWriter = new QRThreadWriter(this, 0, nbCopie, docSujetMaitre, nbPage, LatchThreads, examID, _absolutePath);
        service.execute(_qRThreadWriter);
        LatchThreads.await();
        service.shutdown();
        docSujetMaitre.save(output);
        qrcode.deleteOnExit();
      } else {
        final PdfThreadManagerWriter manager = new PdfThreadManagerWriter(nbPage, docSujetMaitre, doc, this, nbCopie, examID, output);
        manager.start();
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * InsÃ¨re le QRCode sur chaque pages d'un sujet (en changeant le numÃ©ro de page sur chacunes des pages)
   * 
   * @param name l'intitulé du document
   * @param docSujetMaitre le sujet maitre
   * @param numCopie le nombre de copies souhaitées
   * @param numThread le nombre de threads à executer
   * @param nbPagesSuject le nombre de page du sujet maître
   */
  public void insertQRCodeInSubject(final String name, final PDDocument docSujetMaitre, final int numCopie, final int nbPagesSujet, final String pathImage) {
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, nbPagesSujet, true);
    for (final Integer i : _doubleDotLessThan) {
      this.insertQRCodeInPage(name, (i).intValue(), docSujetMaitre, numCopie, nbPagesSujet, pathImage);
    }
  }
  
  /**
   * InÃ¨sre un QRCode sur une page
   * @param name l'intitulé du document
   * @param docSujetMaitre le sujet maitre
   * @param numCopie le nombre de copies souhaitées
   * @param numThread le nombre de threads à executer
   * @param nbPagesSuject le nombre de page du sujet maître
   */
  public void insertQRCodeInPage(final String name, final int numPage, final PDDocument doc, final int numCopie, final int nbPagesSujet, final String pathImage) {
    try {
      final String stringAEncoder = ((((name + "_") + Integer.valueOf(numCopie)) + "_") + Integer.valueOf(numPage));
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
  
  @Override
  public boolean isFinished() {
    return this.isFinished;
  }
  
  public boolean setFinished(final boolean bool) {
    return this.isFinished = bool;
  }
  
  public static void main(final String[] arg) {
    try {
      final QRCodeGeneratorImpl gen = new QRCodeGeneratorImpl();
      byte[] _readAllBytes = Files.readAllBytes(Path.of("D:/dataScanExam/in/pfo_example.pdf"));
      final InputStream input2 = new ByteArrayInputStream(_readAllBytes);
      final File output = new File("D:/dataScanExam/out/melanie.pdf");
      gen.createAllExamCopies(input2, output, "42PFO2021", 8);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
