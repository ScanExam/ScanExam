package fr.istic.tools.scanexam.exportation;

import fr.istic.tools.scanexam.config.LanguageManager;
import fr.istic.tools.scanexam.core.StudentSheet;
import java.io.File;
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
  /**
   * Méthode qui créer un fichier Excel et qui le remplit avec les noms des étudiants et leurs notes
   */
  @Override
  public void exportGrades(final Collection<StudentSheet> studentSheets, final File file) {
    final XSSFWorkbook workbook = new XSSFWorkbook();
    final XSSFSheet sheet = workbook.createSheet("export_grades");
    int rowCount = 0;
    final Row row1 = sheet.createRow(rowCount);
    final Cell name = row1.createCell(0);
    name.setCellValue(LanguageManager.translate("exportExcel.name"));
    rowCount++;
    final Cell grade = row1.createCell(1);
    grade.setCellValue(LanguageManager.translate("exportExcel.grade"));
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
      final FileOutputStream outStream = new FileOutputStream(file);
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
