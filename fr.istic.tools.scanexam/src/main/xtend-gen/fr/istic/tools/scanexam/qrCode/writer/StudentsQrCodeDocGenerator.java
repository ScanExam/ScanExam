package fr.istic.tools.scanexam.qrCode.writer;

import fr.istic.tools.scanexam.qrCode.writer.QRCodeGeneratorImpl;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

/**
 * Classe pour générer un document contenant le nom, prénom de chaque élève accompagné d'un qr code.
 * Le qr code contient des informations pour pouvoir identifier par la suite à qui appartient sa copie.
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class StudentsQrCodeDocGenerator {
  /**
   * Nombre de qr codes par ligne
   */
  private final int qrCodeByLine = 3;
  
  /**
   * Marge autour des qr codes en pixel
   */
  private final int qrCodeMargin = 0;
  
  /**
   * Limite de caractères par ligne
   */
  private final int lineLimit = 32;
  
  /**
   * Produit le document contenant les qr codes d'identification d'élèves à partir d'un fichier XLS
   * @param file Fichier XLS contenant les identifiants des étudiants
   * @param outputFile Fichier pdf où enregistrer le pdf généré
   */
  public void generateDocument(final File file, final File outputFile) {
    try {
      final List<String> students = this.loadStudentsFromFile(file);
      final PDDocument document = new PDDocument();
      int studentIndex = 0;
      while ((studentIndex < ((Object[])Conversions.unwrapArray(students, Object.class)).length)) {
        studentIndex = this.addPage(document, students, studentIndex);
      }
      document.save(outputFile);
      document.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Produit le document contenant les qr codes d'identification d'élèves à partir d'une liste d'étudiants
   * @param students Noms/identifiants des élèves
   * @param outputFile Fichier pdf où enregistrer le pdf généré
   */
  public void generateDocument(final List<String> students, final File outputFile) {
    try {
      final PDDocument document = new PDDocument();
      int studentIndex = 0;
      while ((studentIndex < ((Object[])Conversions.unwrapArray(students, Object.class)).length)) {
        studentIndex = this.addPage(document, students, studentIndex);
      }
      document.save(outputFile);
      document.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Charge la liste des étudiants à partir d'un fichier XLS
   * @param file Fichier XLS contenant les identifiants des étudiants
   * @return Identifiants des étudiants sous forme de liste
   */
  private List<String> loadStudentsFromFile(final File file) {
    final List<String> students = new ArrayList<String>();
    try (final Workbook workbook = new Function0<Workbook>() {
      @Override
      public Workbook apply() {
        try {
          FileInputStream _fileInputStream = new FileInputStream(file);
          return WorkbookFactory.create(_fileInputStream);
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    }.apply()) {
      final Sheet sheet = workbook.getSheetAt(0);
      int rowIndex = 0;
      Row row = sheet.getRow(rowIndex);
      final DataFormatter formatter = new DataFormatter();
      while ((row != null)) {
        {
          final Cell cell = row.getCell(0);
          final String student = formatter.formatCellValue(cell);
          students.add(student);
          rowIndex++;
          row = sheet.getRow(rowIndex);
        }
      }
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return students;
  }
  
  /**
   * Ajoute à un document, une page contenant les noms, prénoms et qr codes des élèves
   * @param document Document auquel ajouter la page
   * @param students Noms/identifiants des élèves à inscrire sur cette page
   * @param firstStudent Index de l'étudiant par lequel commencer
   * @return Si tout les qr codes n'ont pas pu être mis, index du prochain élève à insérer ; taille de la collection d'élèves sinon
   */
  private int addPage(final PDDocument document, final List<String> students, final int firstStudent) {
    try {
      final PDPage page = new PDPage();
      document.addPage(page);
      final PDPageContentStream contentStream = new PDPageContentStream(document, page);
      float _width = page.getMediaBox().getWidth();
      final int qrCodeSize = Float.valueOf((_width / (this.qrCodeByLine * 2))).intValue();
      int _intValue = Float.valueOf(page.getMediaBox().getHeight()).intValue();
      int qrCodePosY = (_intValue - qrCodeSize);
      int studentIndex = firstStudent;
      while (((qrCodePosY >= 0) && (studentIndex < ((Object[])Conversions.unwrapArray(students, Object.class)).length))) {
        {
          int qrCodeOnLine = 0;
          while (((qrCodeOnLine < this.qrCodeByLine) && (studentIndex < ((Object[])Conversions.unwrapArray(students, Object.class)).length))) {
            {
              final int qrCodePosX = ((qrCodeOnLine * 2) * qrCodeSize);
              final String data = students.get(studentIndex);
              this.insertQrCode(contentStream, document, qrCodePosX, qrCodePosY, qrCodeSize, data);
              int _intValue_1 = Float.valueOf((qrCodeSize * 0.8f)).intValue();
              int _plus = (qrCodePosY + _intValue_1);
              this.insertText(contentStream, document, (qrCodePosX + qrCodeSize), _plus, data);
              qrCodeOnLine++;
              studentIndex++;
            }
          }
          int _qrCodePosY = qrCodePosY;
          qrCodePosY = (_qrCodePosY - qrCodeSize);
        }
      }
      contentStream.close();
      return studentIndex;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Insère un qr code sur une page
   * @param contentStream Stream entre le document et la page où insérer le qr code
   * @param document Document auquel ajouter le qr code
   * @param posX Position du qr code sur l'axe X
   * @param posX Position du qr code sur l'axe Y
   * @param size Hauteur/largeur des qr codes en pixel
   * @param data Information à inscrire dans le qr code
   */
  private void insertQrCode(final PDPageContentStream contentStream, final PDDocument document, final int posX, final int posY, final int size, final String data) {
    try {
      if ((data != "")) {
        final File qrCodeFile = File.createTempFile("qrcode", ".png");
        final QRCodeGeneratorImpl qrCodeGen = new QRCodeGeneratorImpl();
        qrCodeGen.generateQRCodeImage(data, size, size, this.qrCodeMargin, qrCodeFile.getPath());
        final PDImageXObject qrCode = PDImageXObject.createFromFile(qrCodeFile.getPath(), document);
        contentStream.drawImage(qrCode, posX, posY);
        qrCodeFile.delete();
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Insère du texte sur une page
   * @param contentStream Stream entre le document et la page où insérer le texte
   * @param document Document auquel ajouter le texte
   * @param posX Position du texte sur l'axe X
   * @param posX Position du texte sur l'axe Y
   * @param text Texte à écrire
   */
  private void insertText(final PDPageContentStream contentStream, final PDDocument document, final int posX, final int posY, final String text) {
    try {
      contentStream.beginText();
      contentStream.setFont(
        PDType0Font.load(document, ResourcesUtils.getInputStreamResource("resources_annotation/arial.ttf")), 6);
      contentStream.setLeading(8.0f);
      contentStream.newLineAtOffset(posX, posY);
      final String[] splitedTexts = ((String[])Conversions.unwrapArray(this.splitString(text, this.lineLimit), String.class));
      for (final String splitedText : splitedTexts) {
        {
          contentStream.showText(splitedText);
          contentStream.newLine();
        }
      }
      contentStream.endText();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  /**
   * Sépare un texte avec un nombre maximal de caractères à la suite
   * @param text Texte à séparer
   * @param charByLine Nombre maximal de caractères par string
   * @return Le texte séparé sous forme de liste
   */
  private List<String> splitString(final String text, final int charByLine) {
    final String[] words = text.split(" ");
    final List<String> texts = new ArrayList<String>();
    String tmpText = "";
    int _length = words.length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        String _tmpText = tmpText;
        String _get = words[(i).intValue()];
        String _plus = (_get + " ");
        tmpText = (_tmpText + _plus);
        if (((((i).intValue() + 1) >= words.length) || ((tmpText.length() + (words[((i).intValue() + 1)]).length()) > charByLine))) {
          texts.add(tmpText);
          tmpText = "";
        }
      }
    }
    return texts;
  }
}
