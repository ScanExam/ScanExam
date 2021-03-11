package fr.istic.tools.scanexam.export;

import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.export.GradesExport;
import fr.istic.tools.scanexam.services.ExamGraduationService;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class GradesExportImpl implements GradesExport {
  private ExamGraduationService service;
  
  public GradesExportImpl(final ExamGraduationService serv) {
    this.service = serv;
  }
  
  /**
   * Methode qui créer un fichier Excel et qui le remplit avec les noms des étudiants et leurs notes
   */
  @Override
  public void exportGrades() {
    final XSSFWorkbook workbook = new XSSFWorkbook();
    final XSSFSheet sheet = workbook.createSheet("export_grades");
    int rowCount = 0;
    int _size = this.service.getStudentSheets().size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final Row row = sheet.createRow(rowCount);
        final Cell cellName = row.createCell(0);
        cellName.setCellValue((((StudentSheet[])Conversions.unwrapArray(this.service.getStudentSheets(), StudentSheet.class))[(i).intValue()]).getStudentName());
        final Cell cellGrade = row.createCell(1);
        cellGrade.setCellValue((((StudentSheet[])Conversions.unwrapArray(this.service.getStudentSheets(), StudentSheet.class))[(i).intValue()]).computeGrade());
      }
    }
    try {
      String _examName = this.service.getExamName();
      String _plus = (_examName + ".xslx");
      final FileOutputStream outStream = new FileOutputStream(_plus);
      workbook.write(outStream);
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        e.printStackTrace();
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
}
