package fr.istic.tools.scanexam.qrCode.writer;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator;
import fr.istic.tools.scanexam.qrCode.writer.QRThreadWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.io.IOUtils;
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
  /**
   * Créer toutes les copies d'examen en y insérant les QrCodes correspondant dans chaque pages
   * 
   * @param inputFile Chemin du sujet maitre
   * @param outputPath chemin de sortie
   * @param idExam l'id de l'examen
   * @param nbCopies Nombre de copies de l'examen souhaité
   */
  public void createAllExamCopies(final InputStream inputFile, final OutputStream outputPath, final String idExam, final int nbCopie) {
    try {
      final StringWriter stringWriterInput = new StringWriter();
      IOUtils.copy(inputFile, stringWriterInput, "UTF-8");
      final String input = stringWriterInput.toString();
      File _file = new File(input);
      final PDDocument doc = PDDocument.load(_file);
      final int nbPages = doc.getNumberOfPages();
      final PDFMergerUtility PDFmerger = new PDFMergerUtility();
      final MemoryUsageSetting memUsSett = MemoryUsageSetting.setupMainMemoryOnly();
      String _string = outputPath.toString();
      final File outpath = new File(_string);
      String output = outpath.getAbsolutePath();
      String _output = output;
      output = (_output + (("/" + idExam) + ".pdf"));
      PDFmerger.setDestinationFileName(output);
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, nbCopie, true);
      for (final Integer i : _doubleDotLessThan) {
        PDFmerger.addSource(input);
      }
      final File f2 = new File(output);
      memUsSett.setTempDir(f2);
      PDFmerger.mergeDocuments(memUsSett);
      final PDDocument docSujetMaitre = PDDocument.load(f2);
      this.createThread(idExam, nbCopie, docSujetMaitre, nbPages);
      docSujetMaitre.save(output);
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
    List<Throwable> _ts = new ArrayList<Throwable>();
    PDDocument doc = null;
    try {
      doc = new Function0<PDDocument>() {
        public PDDocument apply() {
          try {
            File _file = new File(inputFile);
            return PDDocument.load(_file);
          } catch (Throwable _e) {
            throw Exceptions.sneakyThrow(_e);
          }
        }
      }.apply();
      final float scale = 0.3f;
      final PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, doc);
      PDPageTree _pages = doc.getPages();
      for (final PDPage page : _pages) {
        List<Throwable> _ts_1 = new ArrayList<Throwable>();
        PDPageContentStream contentStream = null;
        try {
          contentStream = new Function0<PDPageContentStream>() {
            public PDPageContentStream apply() {
              try {
                return new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, 
                  true);
              } catch (Throwable _e) {
                throw Exceptions.sneakyThrow(_e);
              }
            }
          }.apply();
          int _width = pdImage.getWidth();
          float _multiply = (_width * scale);
          int _height = pdImage.getHeight();
          float _multiply_1 = (_height * scale);
          contentStream.drawImage(pdImage, 0, 0, _multiply, _multiply_1);
        } finally {
          if (contentStream != null) {
            try {
              contentStream.close();
            } catch (Throwable _t) {
              _ts_1.add(_t);
            }
          }
          if(!_ts_1.isEmpty()) throw Exceptions.sneakyThrow(_ts_1.get(0));
        }
      }
      doc.save(outputFile);
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        e.printStackTrace();
      } else {
        _ts.add(_t);
        throw Exceptions.sneakyThrow(_t);
      }
    } finally {
      if (doc != null) {
        try {
          doc.close();
        } catch (Throwable _t_1) {
          _ts.add(_t_1);
        }
      }
      if(!_ts.isEmpty()) throw Exceptions.sneakyThrow(_ts.get(0));
    }
  }
  
  /**
   * @param name l'intitulé du document
   * @param nbCopies nombre de copies dÃ©sirÃ©es
   * @param docSujetMaitre document dans lequel insÃ©rer les Codes
   * @param nbPages nombre de pages du sujet Maitre
   */
  public void createThread(final String name, final int nbCopie, final PDDocument docSujetMaitre, final int nbPage) {
    try {
      final ExecutorService service = Executors.newFixedThreadPool(4);
      final CountDownLatch LatchMain = new CountDownLatch(1);
      if ((nbCopie <= 4)) {
        File qrcode = File.createTempFile("qrcode", ".png");
        final CountDownLatch LatchThreads = new CountDownLatch(1);
        String _absolutePath = qrcode.getAbsolutePath();
        QRThreadWriter _qRThreadWriter = new QRThreadWriter(this, 0, nbCopie, docSujetMaitre, nbPage, LatchThreads, LatchMain, name, _absolutePath);
        service.execute(_qRThreadWriter);
        LatchMain.countDown();
        LatchThreads.await();
        service.shutdown();
        qrcode.deleteOnExit();
      } else {
        final CountDownLatch LatchThreads_1 = new CountDownLatch(4);
        File qrcode1 = File.createTempFile("qrcode1", ".png");
        File qrcode2 = File.createTempFile("qrcode2", ".png");
        File qrcode3 = File.createTempFile("qrcode3", ".png");
        File qrcode4 = File.createTempFile("qrcode4", ".png");
        String _absolutePath_1 = qrcode1.getAbsolutePath();
        QRThreadWriter _qRThreadWriter_1 = new QRThreadWriter(this, 0, (nbCopie / 4), docSujetMaitre, nbPage, LatchThreads_1, LatchMain, name, _absolutePath_1);
        service.execute(_qRThreadWriter_1);
        String _absolutePath_2 = qrcode2.getAbsolutePath();
        QRThreadWriter _qRThreadWriter_2 = new QRThreadWriter(this, (nbCopie / 4), (nbCopie / 2), docSujetMaitre, nbPage, LatchThreads_1, LatchMain, name, _absolutePath_2);
        service.execute(_qRThreadWriter_2);
        String _absolutePath_3 = qrcode3.getAbsolutePath();
        QRThreadWriter _qRThreadWriter_3 = new QRThreadWriter(this, (nbCopie / 2), ((3 * nbCopie) / 4), docSujetMaitre, nbPage, LatchThreads_1, LatchMain, name, _absolutePath_3);
        service.execute(_qRThreadWriter_3);
        String _absolutePath_4 = qrcode4.getAbsolutePath();
        QRThreadWriter _qRThreadWriter_4 = new QRThreadWriter(this, ((3 * nbCopie) / 4), nbCopie, docSujetMaitre, nbPage, LatchThreads_1, LatchMain, name, _absolutePath_4);
        service.execute(_qRThreadWriter_4);
        LatchMain.countDown();
        LatchThreads_1.await();
        service.shutdown();
        qrcode1.deleteOnExit();
        qrcode2.deleteOnExit();
        qrcode3.deleteOnExit();
        qrcode4.deleteOnExit();
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
      List<Throwable> _ts = new ArrayList<Throwable>();
      PDPageContentStream contentStream = null;
      try {
        contentStream = new Function0<PDPageContentStream>() {
          public PDPageContentStream apply() {
            try {
              PDPage _page = doc.getPage((numPage + (numCopie * nbPagesSujet)));
              return new PDPageContentStream(doc, _page, PDPageContentStream.AppendMode.APPEND, true, 
                true);
            } catch (Throwable _e) {
              throw Exceptions.sneakyThrow(_e);
            }
          }
        }.apply();
        int _width = pdImage.getWidth();
        float _multiply = (_width * scale);
        int _height = pdImage.getHeight();
        float _multiply_1 = (_height * scale);
        contentStream.drawImage(pdImage, 0, 0, _multiply, _multiply_1);
      } finally {
        if (contentStream != null) {
          try {
            contentStream.close();
          } catch (Throwable _t) {
            _ts.add(_t);
          }
        }
        if(!_ts.isEmpty()) throw Exceptions.sneakyThrow(_ts.get(0));
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static void main(final String[] arg) {
    try {
      final QRCodeGeneratorImpl gen = new QRCodeGeneratorImpl();
      byte[] _bytes = "D:/dataScanExam/in/pfo_example.pdf".getBytes();
      final InputStream input = new ByteArrayInputStream(_bytes);
      final OutputStream output = new ByteArrayOutputStream();
      output.write("D:/dataScanExam/out".getBytes());
      gen.createAllExamCopies(input, output, "42PFO2021", 8);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
