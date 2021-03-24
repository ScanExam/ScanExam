package fr.istic.tools.scanexam.export;

import fr.istic.tools.scanexam.core.StudentSheet;
import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class ExportExamToPdf {
  public static void exportToPdf(final PDDocument pdf, final StudentSheet sheet, final File outputPdfFile) {
    try {
      boolean _exists = outputPdfFile.exists();
      if (_exists) {
        return;
      }
      PDDocument document = new PDDocument();
      EList<Integer> _posPage = sheet.getPosPage();
      for (final Integer i : _posPage) {
        document.addPage(pdf.getPage((i).intValue()));
      }
      document.save(outputPdfFile);
      document.close();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
