package fr.istic.tools.scanexam.export;

import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.export.GradesExport;
import fr.istic.tools.scanexam.services.ServiceGraduation;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;

@SuppressWarnings("all")
public class GradesExportImpl implements GradesExport {
  private ServiceGraduation service;
  
  public GradesExportImpl(final ServiceGraduation serv) {
    this.service = serv;
  }
  
  /**
   * Méthode qui créer un fichier Excel et qui le remplit avec les noms des étudiants et leurs notes
   */
  @Override
  public void exportGrades() {
    this.exportGradesPrivate(this.service.getStudentSheets(), this.service.getExamName());
  }
  
  public void exportGradesPrivate(final Collection<StudentSheet> studentSheets, final String examName) {
    final XSSFWorkbook workbook = new XSSFWorkbook();
    final XSSFSheet sheet = workbook.createSheet("export_grades");
    int rowCount = 0;
    final Row row1 = sheet.createRow(rowCount);
    final Cell name = row1.createCell(0);
    name.setCellValue("Nom");
    rowCount++;
    final Cell grade = row1.createCell(1);
    grade.setCellValue("Note");
    int _size = studentSheets.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      {
        final Row row = sheet.createRow(rowCount);
        final Cell cellName = row.createCell(0);
        cellName.setCellValue((((StudentSheet[])Conversions.unwrapArray(studentSheets, StudentSheet.class))[(i).intValue()]).getStudentName());
        final Cell cellGrade = row.createCell(1);
        cellGrade.setCellValue((((StudentSheet[])Conversions.unwrapArray(studentSheets, StudentSheet.class))[(i).intValue()]).computeGrade());
        rowCount++;
      }
    }
    try {
      final FileOutputStream outStream = new FileOutputStream((examName + ".xlsx"));
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
