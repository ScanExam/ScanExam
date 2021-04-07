package fr.istic.tools.scanexam.export;

import java.awt.Desktop;
import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class ExportPdfWithAnnotations {
  public static void main(final String[] args) {
    try {
      File _file = new File("/Users/leo/Documents/pfo_example.pdf");
      PDDocument document = PDDocument.load(_file);
      PDPage page = document.getPage(0);
      PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
      int startX = 200;
      int startY = 350;
      int bullX = 400;
      int bullY = 400;
      int weight = 100;
      int height = 100;
      contentStream.addRect((bullX - 2), (bullY - 2), (weight + 4), (height + 4));
      contentStream.setNonStrokingColor((36 / 255f), (35 / 255f), (35 / 255f));
      contentStream.fill();
      contentStream.addRect(bullX, bullY, weight, height);
      contentStream.setNonStrokingColor((248 / 255f), (244 / 255f), (243 / 255f));
      contentStream.fill();
      contentStream.moveTo(startX, startY);
      contentStream.lineTo(bullX, bullY);
      contentStream.stroke();
      contentStream.fill();
      contentStream.setNonStrokingColor((36 / 255f), (35 / 255f), (35 / 255f));
      contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
      contentStream.beginText();
      contentStream.newLineAtOffset(bullX, (bullY + (weight / 2)));
      contentStream.showText("Here is a test");
      contentStream.endText();
      contentStream.fill();
      contentStream.close();
      File file1 = new File("/Users/leo/Documents/pfo_example_annotation.pdf");
      document.save(file1);
      Desktop.getDesktop().open(file1);
      document.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
