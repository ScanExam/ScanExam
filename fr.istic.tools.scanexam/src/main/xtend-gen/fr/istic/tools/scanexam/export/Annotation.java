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
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class Annotation {
  private static PDColor WHITEPDFBOX = new PDColor(new float[] { (255 / 255f), (255 / 255f), (255 / 255f) }, PDDeviceRGB.INSTANCE);
  
  private static PDColor REDPDFBOX = new PDColor(new float[] { (255 / 255f), (0 / 255f), (0 / 255f) }, PDDeviceRGB.INSTANCE);
  
  private static PDColor GREENPDFBOX = new PDColor(new float[] { (0 / 255f), (200 / 255f), (0 / 255f) }, PDDeviceRGB.INSTANCE);
  
  private static PDColor BLUEPDFBOX = new PDColor(new float[] { (0 / 255f), (0 / 255f), (255 / 255f) }, PDDeviceRGB.INSTANCE);
  
  private static PDColor BLACKPDFBOX = new PDColor(new float[] { (0 / 255f), (0 / 255f), (0 / 255f) }, PDDeviceRGB.INSTANCE);
  
  private static float INCH = 72;
  
  public static void main(final String[] args) {
    try {
      File _file = new File("src/main/resources/resources_annotation/pfo_example.pdf");
      PDDocument document = PDDocument.load(_file);
      Annotation.annotationPDFWithArrow(document, 0, 0, 0, 0, 0);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static void annotationPDFWithArrow(final PDDocument document, final int pageNoted, final float lowerLeftX, final float lowerLeftY, final float upperLeftX, final float upperRightY) {
    try {
      PDPage page = document.getPage(pageNoted);
      List<PDAnnotation> annotations = page.getAnnotations();
      PDBorderStyleDictionary borderThick = new PDBorderStyleDictionary();
      borderThick.setWidth((Annotation.INCH / 20));
      PDBorderStyleDictionary borderThin = new PDBorderStyleDictionary();
      borderThin.setWidth((Annotation.INCH / 72));
      PDBorderStyleDictionary borderULine = new PDBorderStyleDictionary();
      borderULine.setStyle(PDBorderStyleDictionary.STYLE_UNDERLINE);
      borderULine.setWidth((Annotation.INCH / 72));
      float pw = page.getMediaBox().getUpperRightX();
      float ph = page.getMediaBox().getUpperRightY();
      PDRectangle position = new PDRectangle();
      PDAnnotationSquareCircle aSquare = new PDAnnotationSquareCircle(PDAnnotationSquareCircle.SUB_TYPE_SQUARE);
      aSquare.setContents("Square Annotation");
      aSquare.setInteriorColor(Annotation.WHITEPDFBOX);
      aSquare.setColor(Annotation.BLACKPDFBOX);
      aSquare.setBorderStyle(borderThick);
      PDRectangle _pDRectangle = new PDRectangle();
      position = _pDRectangle;
      position.setLowerLeftX((pw - (2 * Annotation.INCH)));
      position.setLowerLeftY(((ph - (3.5f * Annotation.INCH)) - Annotation.INCH));
      position.setUpperRightX((pw - Annotation.INCH));
      position.setUpperRightY((ph - (3.5f * Annotation.INCH)));
      aSquare.setRectangle(position);
      annotations.add(aSquare);
      PDAnnotationLine aLine = new PDAnnotationLine();
      aLine.setEndPointEndingStyle(PDAnnotationLine.LE_OPEN_ARROW);
      aLine.setContents("Circle->Square");
      aLine.setCaption(true);
      PDRectangle _pDRectangle_1 = new PDRectangle();
      position = _pDRectangle_1;
      position.setLowerLeftX((2 * Annotation.INCH));
      position.setLowerLeftY(((ph - (3.5f * Annotation.INCH)) - Annotation.INCH));
      position.setUpperRightX(((pw - Annotation.INCH) - Annotation.INCH));
      position.setUpperRightY((ph - (3 * Annotation.INCH)));
      aLine.setRectangle(position);
      float[] linepos = new float[4];
      linepos[0] = (2 * Annotation.INCH);
      linepos[1] = (ph - (3.5f * Annotation.INCH));
      linepos[2] = (pw - (2 * Annotation.INCH));
      linepos[3] = (ph - (4 * Annotation.INCH));
      aLine.setLine(linepos);
      aLine.setBorderStyle(borderThick);
      aLine.setColor(Annotation.BLACKPDFBOX);
      annotations.add(aLine);
      File file = new File("src/main/resources/resources_annotation/pfo_example_annotation.pdf");
      document.save(file);
      Desktop.getDesktop().open(file);
      document.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static void annotationTextPDF(final PDDocument document, final int pageNoted, final float lowerLeftX, final float lowerLeftY, final float upperLeftX, final float upperRightY) {
    try {
      PDPage page = document.getPage(pageNoted);
      List<PDAnnotation> annotations = page.getAnnotations();
      float inch = 72;
      PDAnnotationText text = new PDAnnotationText();
      PDBorderStyleDictionary borderThick = new PDBorderStyleDictionary();
      borderThick.setWidth((inch / 20));
      PDBorderStyleDictionary borderThin = new PDBorderStyleDictionary();
      borderThin.setWidth((inch / 72));
      PDBorderStyleDictionary borderULine = new PDBorderStyleDictionary();
      borderULine.setStyle(PDBorderStyleDictionary.STYLE_UNDERLINE);
      borderULine.setWidth((inch / 72));
      float pw = page.getMediaBox().getUpperRightX();
      float ph = page.getMediaBox().getUpperRightY();
      text.setContents("test");
      text.setName("test");
      float[] rgb = { (0 / 255f), (0 / 255f), (0 / 255f) };
      PDColor color = new PDColor(rgb, PDDeviceRGB.INSTANCE);
      text.setColor(color);
      PDRectangle position = new PDRectangle();
      position.setLowerLeftX((pw - (2 * inch)));
      position.setLowerLeftY(((ph - (3.5f * inch)) - inch));
      position.setUpperRightX((pw - inch));
      position.setUpperRightY((ph - (3.5f * inch)));
      text.setRectangle(position);
      annotations.add(text);
      File file = new File("src/main/resources/resources_annotation/pfo_example_annotation.pdf");
      document.save(file);
      Desktop.getDesktop().open(file);
      document.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
