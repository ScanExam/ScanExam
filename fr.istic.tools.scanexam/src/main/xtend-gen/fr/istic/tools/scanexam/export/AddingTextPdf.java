package fr.istic.tools.scanexam.export;

import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class AddingTextPdf {
  public static void addingText(final File file, final String text, final int x, final int y, final PDFont font, final int size) {
    try {
      PDDocument doc = PDDocument.load(file);
      PDPage page = doc.getPage(1);
      PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, true);
      contentStream.beginText();
      contentStream.newLineAtOffset(x, y);
      contentStream.setFont(font, size);
      contentStream.showText(text);
      contentStream.endText();
      contentStream.close();
      System.out.println("J\'ai reussi");
      doc.save("/Users/leo/Desktop/test.pdf");
      doc.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static void main(final String[] args) {
    File file = new File("/Users/leo/git/ScanGrading/fr.istic.tools.scanexam/target/test-classes/resource_service/pfo_example.pdf");
    String text = "Here is a test of sacré Qian";
    AddingTextPdf.addingText(file, text, 150, 800, PDType1Font.TIMES_ROMAN, 12);
  }
}
