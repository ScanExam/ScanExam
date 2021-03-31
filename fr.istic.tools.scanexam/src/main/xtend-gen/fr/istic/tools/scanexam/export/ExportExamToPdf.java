package fr.istic.tools.scanexam.export;

import fr.istic.tools.scanexam.core.StudentSheet;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class ExportExamToPdf {
  public static Object exportToPdf(final PDDocument pdf, final StudentSheet sheet, final File outputPdfFile) {
    try {
      boolean _exists = outputPdfFile.exists();
      if (_exists) {
        return null;
      }
      PDDocument document = new PDDocument();
      EList<Integer> _posPage = sheet.getPosPage();
      for (final Integer i : _posPage) {
        document.addPage(pdf.getPage((i).intValue()));
      }
      document.save(outputPdfFile);
      document.close();
      return null;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static InputStream exportToInputStream(final PDDocument pdf, final StudentSheet sheet) {
    try {
      PDDocument document = new PDDocument();
      EList<Integer> _posPage = sheet.getPosPage();
      for (final Integer i : _posPage) {
        document.addPage(pdf.getPage((i).intValue()));
      }
      PDStream ps = new PDStream(document);
      InputStream is = ps.createInputStream();
      document.close();
      return is;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static OutputStream exportToOutputStream(final PDDocument pdf, final StudentSheet sheet) {
    try {
      PDDocument document = new PDDocument();
      EList<Integer> _posPage = sheet.getPosPage();
      for (final Integer i : _posPage) {
        document.addPage(pdf.getPage((i).intValue()));
      }
      PDStream ps = new PDStream(document);
      OutputStream is = ps.createOutputStream();
      document.close();
      return is;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static File exportToTempFile(final PDDocument pdf, final StudentSheet sheet) {
    try {
      PDDocument document = new PDDocument();
      EList<Integer> _posPage = sheet.getPosPage();
      for (final Integer i : _posPage) {
        document.addPage(pdf.getPage((i).intValue()));
      }
      File file = File.createTempFile(sheet.getStudentName(), ".pdf");
      document.save(file);
      document.close();
      return file;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static Collection<File> exportToCollection(final PDDocument pdf, final Collection<StudentSheet> sheets) {
    final Function<StudentSheet, File> _function = (StudentSheet s) -> {
      return ExportExamToPdf.exportToTempFile(pdf, s);
    };
    return sheets.stream().<File>map(_function).collect(Collectors.<File>toList());
  }
}
