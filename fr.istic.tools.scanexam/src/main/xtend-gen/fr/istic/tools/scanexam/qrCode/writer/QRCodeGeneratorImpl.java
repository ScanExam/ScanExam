package fr.istic.tools.scanexam.qrCode.writer;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import fr.istic.tools.scanexam.core.QrCodeZone;
import fr.istic.tools.scanexam.qrCode.QrCodeType;
import fr.istic.tools.scanexam.qrCode.writer.PdfThreadManagerWriter;
import fr.istic.tools.scanexam.qrCode.writer.QRCodeGenerator;
import fr.istic.tools.scanexam.qrCode.writer.QRThreadWriter;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

@SuppressWarnings("all")
public class QRCodeGeneratorImpl implements QRCodeGenerator {
  private boolean isFinished;
  
  private int nbTreated = 0;
  
  private int numberPagesAllSheets;
  
  private final Logger logger = LogManager.getLogger();
  
  /**
   * Créer toutes les copies d'examen en y insérant les QrCodes correspondant dans chaque pages
   * 
   * @param inputFile Chemin du sujet maitre
   * @param outputPath chemin de sortie
   * @param qrCodeZone zone sur le document où insérer le qrcode
   * @param qrCodeType Type de qr code à insérer
   * @param nbCopies Nombre de copies de l'examen souhaité
   */
  @Override
  public void createAllExamCopies(final InputStream inputFile, final File outFile, final QrCodeZone qrCodeZone, final int qrCodeType, final int nbCopie) {
    try {
      final byte[] byteArray = new byte[inputFile.available()];
      inputFile.read(byteArray);
      final File temp = File.createTempFile("pdfTemp", ".pdf");
      temp.deleteOnExit();
      final OutputStream oS = new FileOutputStream(temp);
      oS.write(byteArray);
      oS.close();
      final PDDocument doc = PDDocument.load(temp);
      int _numberOfPages = doc.getNumberOfPages();
      int _multiply = (nbCopie * _numberOfPages);
      this.numberPagesAllSheets = _multiply;
      final int nbPages = doc.getNumberOfPages();
      final MemoryUsageSetting memUsSett = MemoryUsageSetting.setupMainMemoryOnly();
      PDFMergerUtility ut = new PDFMergerUtility();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, nbCopie, true);
      for (final Integer i : _doubleDotLessThan) {
        ut.addSource(temp);
      }
      ut.setDestinationFileName(outFile.getAbsolutePath());
      boolean _contains = ut.getDestinationFileName().contains(".pdf");
      boolean _not = (!_contains);
      if (_not) {
        String _destinationFileName = ut.getDestinationFileName();
        String _plus = (_destinationFileName + ".pdf");
        ut.setDestinationFileName(_plus);
      }
      memUsSett.setTempDir(temp);
      ut.mergeDocuments(memUsSett);
      final PDDocument docSujetMaitre = PDDocument.load(outFile);
      FileOutputStream _fileOutputStream = new FileOutputStream(outFile);
      this.createThread(nbCopie, qrCodeZone, qrCodeType, docSujetMaitre, doc, nbPages, _fileOutputStream);
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        this.logger.error("Cannot insert QR codes", e);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  /**
   * Créé un qr code d'une taille correspondante à une zone de qr code.
   * Un fichier PNG du QRCode est créé en suivant le filePath
   * @param qrCodeZone Zone du qr code
   * @param doc Sujet où se trouve la zone
   * @param numPage Numéro de la page où se trouve la zone de qr code
   * @param data Données à inscrire dans le qr code
   * @param pathImage Chemin vers l'image du qr code générée
   */
  private void generateQrCodeImageWithQrCodeZone(final QrCodeZone qrCodeZone, final PDDocument doc, final int numPage, final String data, final String pathImage) {
    final float docWidth = doc.getPage(numPage).getMediaBox().getWidth();
    final float docHeight = doc.getPage(numPage).getMediaBox().getHeight();
    float _width = qrCodeZone.getWidth();
    final float qrCodeWidth = (_width * docWidth);
    float _height = qrCodeZone.getHeight();
    final float qrCodeHeight = (_height * docHeight);
    float _xifexpression = (float) 0;
    if ((qrCodeWidth < qrCodeHeight)) {
      _xifexpression = qrCodeWidth;
    } else {
      _xifexpression = qrCodeHeight;
    }
    final float qrCodeSize = _xifexpression;
    this.generateQRCodeImage(data, ((int) (qrCodeSize * 4)), ((int) (qrCodeSize * 4)), 0, pathImage);
  }
  
  /**
   * Créé un QRCode (21 * 21 carrés) de taille width * height chiffrant la chaine text.
   * Un fichier PNG du QRCode est créé en suivant le filePath
   * @param text Le texte a encoder
   * @param width  Largeur de l'image
   * @param height Hauteur de l'image
   * @param margin Marge autour de l'image
   * @param filePath Chemin du nouveau fichier
   * 
   * @throws WriterException
   * @throws IOException
   */
  public void generateQRCodeImage(final String text, final int width, final int height, final int margin, final String filePath) {
    try {
      final QRCodeWriter qrCodeWriter = new QRCodeWriter();
      final Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
      hintMap.put(EncodeHintType.MARGIN, Integer.valueOf(margin));
      final BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hintMap);
      final Path path = FileSystems.getDefault().getPath(filePath);
      MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    } catch (final Throwable _t) {
      if (_t instanceof WriterException || _t instanceof IOException) {
        final Exception e = (Exception)_t;
        this.logger.error("Cannot generate QR code", e);
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
   * @param imagePath  Chemin de l'image a insérer
   * @param outputFile Chemin du fichier a écrire
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
        this.logger.error("Cannot print QR code in page", e);
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
  
  /**
   * @param nbCopies nombre de copies désirées
   * @param qrCodeZone zone sur le document où insérer le qrcode
   * @param qrCodeType Type de qr code à insérer
   * @param docSujetMaitre document dans lequel insérer les Codes
   * @param nbPages nombre de pages du sujet Maitre
   */
  public void createThread(final int nbCopie, final QrCodeZone qrCodeZone, final int qrCodeType, final PDDocument docSujetMaitre, final PDDocument doc, final int nbPage, final OutputStream output) {
    try {
      if ((nbCopie < 4)) {
        final ExecutorService service = Executors.newFixedThreadPool(1);
        File qrcode = File.createTempFile("qrcode", ".png");
        final CountDownLatch LatchThreads = new CountDownLatch(1);
        String _absolutePath = qrcode.getAbsolutePath();
        QRThreadWriter _qRThreadWriter = new QRThreadWriter(this, qrCodeType, 0, nbCopie, qrCodeZone, docSujetMaitre, nbPage, LatchThreads, _absolutePath);
        service.execute(_qRThreadWriter);
        LatchThreads.await();
        service.shutdown();
        docSujetMaitre.save(output);
        qrcode.deleteOnExit();
      } else {
        final PdfThreadManagerWriter manager = new PdfThreadManagerWriter(nbPage, qrCodeZone, qrCodeType, docSujetMaitre, doc, this, nbCopie, output);
        manager.start();
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Insère le QRCode sur chaque pages d'un sujet (en changeant le numéro de page sur chacunes des pages)
   * 
   * @param qrCodeZone zone sur le document où insérer le qrcode
   * @param docSujetMaitre le sujet maitre
   * @param numCopie le nombre de copies souhaitées
   * @param nbPagesSuject le nombre de page du sujet maître
   * @param qrCodeType Type de qr code à insérer
   * @param pathImage chemin vers l'image du qr code générée
   */
  public void insertQRCodeInSubject(final QrCodeZone qrCodeZone, final PDDocument docSujetMaitre, final int numCopie, final int nbPagesSujet, final int qrCodeType, final String pathImage) {
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, nbPagesSujet, true);
    for (final Integer i : _doubleDotLessThan) {
      this.insertQRCodeInPage(qrCodeZone, docSujetMaitre, (i).intValue(), numCopie, nbPagesSujet, qrCodeType, pathImage);
    }
  }
  
  /**
   * Insère sur une page un QRCode
   * @param qrCodeZone zone sur le document où insérer le qrcode
   * @param doc le sujet maitre
   * @param numPage numéro de la page où insérer le qr code
   * @param numCopie uméro de la copie d'examen
   * @param nbPagesSuject le nombre de page du sujet maître
   * @param qrCodeType Type de qr code à insérer
   * @param pathImage chemin vers l'image du qr code générée
   */
  public void insertQRCodeInPage(final QrCodeZone qrCodeZone, final PDDocument doc, final int numPage, final int numCopie, final int nbPagesSujet, final int qrCodeType, final String pathImage) {
    if ((qrCodeType == QrCodeType.SHEET_PAGE)) {
      this.insertSheetAndPageQRCodeInPage(qrCodeZone, doc, numPage, numCopie, nbPagesSujet, pathImage);
    } else {
      if ((qrCodeType == QrCodeType.PAGE)) {
        this.insertPageQRCodeInPage(qrCodeZone, doc, numPage, pathImage);
      }
    }
  }
  
  /**
   * Insère sur une page un QRCode contenant l'identifiant de la copie et le numéro de la page
   * @param qrCodeZone zone sur le document où insérer le qrcode
   * @param doc le sujet maitre
   * @param numPage numéro de la page où insérer le qr code
   * @param numCopie uméro de la copie d'examen
   * @param nbPagesSuject le nombre de page du sujet maître
   * @param pathImage chemin vers l'image du qr code générée
   */
  private void insertSheetAndPageQRCodeInPage(final QrCodeZone qrCodeZone, final PDDocument doc, final int numPage, final int numCopie, final int nbPagesSujet, final String pathImage) {
    String _plus = (Integer.valueOf(QrCodeType.SHEET_PAGE) + "_");
    String _plus_1 = (_plus + Integer.valueOf(numCopie));
    String _plus_2 = (_plus_1 + "_");
    final String data = (_plus_2 + Integer.valueOf(numPage));
    this.generateQrCodeImageWithQrCodeZone(qrCodeZone, doc, numPage, data, pathImage);
    final PDPage page = doc.getPage((numPage + (numCopie * nbPagesSujet)));
    this.insertQrCodeImageInPage(doc, page, numPage, qrCodeZone, pathImage, data);
  }
  
  /**
   * Insère sur une page un QRCode contenant le numéro de la page uniquement
   * @param qrCodeZone zone sur le document où insérer le qrcode
   * @param doc le sujet maitre
   * @param numPage numéro de la page où insérer le qr code
   * @param pathImage chemin vers l'image du qr code générée
   */
  private void insertPageQRCodeInPage(final QrCodeZone qrCodeZone, final PDDocument doc, final int numPage, final String pathImage) {
    String _plus = (Integer.valueOf(QrCodeType.PAGE) + "_");
    final String data = (_plus + Integer.valueOf(numPage));
    this.generateQrCodeImageWithQrCodeZone(qrCodeZone, doc, numPage, data, pathImage);
    final PDPage page = doc.getPage(numPage);
    this.insertQrCodeImageInPage(doc, page, numPage, qrCodeZone, pathImage, data);
  }
  
  /**
   * Insère l'image d'un qr code sur une page ainsi que ses données sous forme de texte aux 4 coins de la page
   * @param doc le sujet maitre
   * @param page la page où insérer l'image
   * @param numPage numéro de la page où insérer le qr code
   * @param qrCodeZone zone sur le document où insérer le qrcode
   * @param pathImage chemin vers l'image du qr code générée
   * @param data donnée inscrite dans le qr code
   */
  private void insertQrCodeImageInPage(final PDDocument doc, final PDPage page, final int numPage, final QrCodeZone qrCodeZone, final String pathImage, final String data) {
    try {
      final PDImageXObject pdImage = PDImageXObject.createFromFile(pathImage, doc);
      try (final PDPageContentStream contentStream = new Function0<PDPageContentStream>() {
        @Override
        public PDPageContentStream apply() {
          try {
            return new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true);
          } catch (Throwable _e) {
            throw Exceptions.sneakyThrow(_e);
          }
        }
      }.apply()) {
        final float docWidth = doc.getPage(numPage).getMediaBox().getWidth();
        final float docHeight = doc.getPage(numPage).getMediaBox().getHeight();
        float _x = qrCodeZone.getX();
        final float qrCodeX = (_x * docWidth);
        float _y = qrCodeZone.getY();
        float _multiply = (_y * docHeight);
        float _minus = (docHeight - _multiply);
        float _height = qrCodeZone.getHeight();
        float _multiply_1 = (_height * docHeight);
        final float qrCodeY = (_minus - _multiply_1);
        float _width = qrCodeZone.getWidth();
        final float qrCodeWidth = (_width * docWidth);
        float _height_1 = qrCodeZone.getHeight();
        final float qrCodeHeight = (_height_1 * docHeight);
        float _xifexpression = (float) 0;
        if ((qrCodeWidth < qrCodeHeight)) {
          _xifexpression = qrCodeWidth;
        } else {
          _xifexpression = qrCodeHeight;
        }
        final float qrCodeSize = _xifexpression;
        contentStream.drawImage(pdImage, qrCodeX, qrCodeY, qrCodeSize, qrCodeSize);
        this.insertTextDataInPage(doc, numPage, contentStream, data);
        this.incrementTreated();
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Insert des données sous forme textuelle aux 4 coins d'une page
   * @param doc PDDocument où inscrire les données
   * @param numPage Numéro de la page où inscrire les données
   * @param contentStream PDPageContentStream servant à écrire les données
   * @param data Données à inscrire
   */
  private void insertTextDataInPage(final PDDocument doc, final int numPage, final PDPageContentStream contentStream, final String data) {
    try {
      final float width = doc.getPage(numPage).getMediaBox().getWidth();
      final float height = doc.getPage(numPage).getMediaBox().getHeight();
      contentStream.setFont(
        PDType0Font.load(doc, ResourcesUtils.getInputStreamResource("resources_annotation/arial.ttf")), 6);
      contentStream.beginText();
      contentStream.newLineAtOffset(60, 4);
      contentStream.showText(data);
      contentStream.endText();
      contentStream.beginText();
      contentStream.newLineAtOffset(60, (height - 12));
      contentStream.showText(data);
      contentStream.endText();
      contentStream.beginText();
      contentStream.newLineAtOffset((width - 92), 4);
      contentStream.showText(data);
      contentStream.endText();
      contentStream.beginText();
      contentStream.newLineAtOffset((width - 92), (height - 12));
      contentStream.showText(data);
      contentStream.endText();
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
  
  @Override
  public int getNbTreated() {
    return this.nbTreated;
  }
  
  public int incrementTreated() {
    return this.nbTreated = (this.nbTreated + 1);
  }
  
  @Override
  public int getNumberPagesAllSheets() {
    return this.numberPagesAllSheets;
  }
  
  public int getPercentage() {
    int _nbTreated = this.getNbTreated();
    int _multiply = (_nbTreated * 100);
    int _numberPagesAllSheets = this.getNumberPagesAllSheets();
    return (_multiply / _numberPagesAllSheets);
  }
  
  public static void main(final String[] arg) {
    try {
      final QRCodeGeneratorImpl gen = new QRCodeGeneratorImpl();
      byte[] _readAllBytes = Files.readAllBytes(Path.of("D:/dataScanExam/in/pfo_example.pdf"));
      final InputStream input2 = new ByteArrayInputStream(_readAllBytes);
      final File output = new File("D:/dataScanExam/out/melanie.pdf");
      gen.createAllExamCopies(input2, output, null, QrCodeType.SHEET_PAGE, 100);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
