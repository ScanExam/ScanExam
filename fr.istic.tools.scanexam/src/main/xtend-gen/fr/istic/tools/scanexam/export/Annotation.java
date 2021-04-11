package fr.istic.tools.scanexam.export;

import java.awt.Desktop;
import java.io.File;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLine;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationSquareCircle;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class Annotation {
  public static void main(final String[] args) {
    try {
      File _file = new File("src/main/resources/resources_annotation/pfo_example.pdf");
      PDDocument document = PDDocument.load(_file);
      Annotation.annotationPDF(document, 0, 0, 0, 0, 0);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static void annotationPDF(final PDDocument document, final int pageNoted, final float lowerLeftX, final float lowerLeftY, final float upperLeftX, final float upperRightY) {
    try {
      float inch = 72;
      float[] rgb = { (0 / 255f), (0 / 255f), (0 / 255f) };
      PDColor color = new PDColor(rgb, PDDeviceRGB.INSTANCE);
      PDPage page = document.getPage(pageNoted);
      List<PDAnnotation> annotations = page.getAnnotations();
      PDBorderStyleDictionary borderThick = new PDBorderStyleDictionary();
      borderThick.setWidth((inch / 20));
      PDBorderStyleDictionary borderThin = new PDBorderStyleDictionary();
      borderThin.setWidth((inch / 72));
      PDBorderStyleDictionary borderULine = new PDBorderStyleDictionary();
      borderULine.setStyle(PDBorderStyleDictionary.STYLE_UNDERLINE);
      borderULine.setWidth((inch / 72));
      float pw = page.getMediaBox().getUpperRightX();
      float ph = page.getMediaBox().getUpperRightY();
      PDRectangle position = new PDRectangle();
      float[] snow = { (255 / 255f), (250 / 255f), (250 / 255f) };
      PDColor interieurColor = new PDColor(snow, PDDeviceRGB.INSTANCE);
      PDAnnotationSquareCircle aSquare = new PDAnnotationSquareCircle(PDAnnotationSquareCircle.SUB_TYPE_SQUARE);
      aSquare.setContents("Square Annotation");
      aSquare.setInteriorColor(interieurColor);
      aSquare.setColor(color);
      aSquare.setBorderStyle(borderThick);
      PDRectangle _pDRectangle = new PDRectangle();
      position = _pDRectangle;
      position.setLowerLeftX((pw - (2 * inch)));
      position.setLowerLeftY(((ph - (3.5f * inch)) - inch));
      position.setUpperRightX((pw - inch));
      position.setUpperRightY((ph - (3.5f * inch)));
      aSquare.setRectangle(position);
      annotations.add(aSquare);
      PDAnnotationLine aLine = new PDAnnotationLine();
      aLine.setEndPointEndingStyle(PDAnnotationLine.LE_OPEN_ARROW);
      aLine.setContents("Circle->Square");
      aLine.setCaption(true);
      PDRectangle _pDRectangle_1 = new PDRectangle();
      position = _pDRectangle_1;
      position.setLowerLeftX((2 * inch));
      position.setLowerLeftY(((ph - (3.5f * inch)) - inch));
      position.setUpperRightX(((pw - inch) - inch));
      position.setUpperRightY((ph - (3 * inch)));
      aLine.setRectangle(position);
      float[] linepos = new float[4];
      linepos[0] = (2 * inch);
      linepos[1] = (ph - (3.5f * inch));
      linepos[2] = (pw - (2 * inch));
      linepos[3] = (ph - (4 * inch));
      aLine.setLine(linepos);
      aLine.setBorderStyle(borderThick);
      aLine.setColor(color);
      annotations.add(aLine);
      File file = new File("src/main/resources/resources_annotation/pfo_example_annotation.pdf");
      document.save(file);
      Desktop.getDesktop().open(file);
      document.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
