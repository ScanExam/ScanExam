package fr.istic.tools.scanexam.utils;

import com.google.common.base.Objects;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.Normalizer;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class ExcelModel {
  private HSSFWorkbook workbook;
  
  private File file;
  
  public ExcelModel(final File f) {
    try {
      this.file = f;
      FileInputStream _fileInputStream = new FileInputStream(this.file);
      HSSFWorkbook _hSSFWorkbook = new HSSFWorkbook(_fileInputStream);
      this.workbook = _hSSFWorkbook;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public int getNumberOfUsefulColumns(final int sheedId) {
    int _xblockexpression = (int) 0;
    {
      int i = 0;
      while (((!Objects.equal(this.getStringAt(sheedId, 0, i), null)) && (!Objects.equal(this.getStringAt(sheedId, 0, i), "")))) {
        i++;
      }
      _xblockexpression = i;
    }
    return _xblockexpression;
  }
  
  public int getNumberOfUsefulRows(final int sheedId) {
    int _xblockexpression = (int) 0;
    {
      int i = 0;
      while (((!Objects.equal(this.getStringAt(sheedId, i, 0), null)) && (!Objects.equal(this.getStringAt(sheedId, i, 0), "")))) {
        i++;
      }
      _xblockexpression = i;
    }
    return _xblockexpression;
  }
  
  public String getStringAt(final int sheetId, final int rowId, final int colId) {
    Object _xblockexpression = null;
    {
      HSSFSheet sheet = this.workbook.getSheetAt(sheetId);
      final HSSFRow crow = sheet.getRow(rowId);
      boolean _notEquals = (!Objects.equal(crow, null));
      if (_notEquals) {
        final HSSFCell cell = crow.getCell(colId);
        boolean _notEquals_1 = (!Objects.equal(cell, null));
        if (_notEquals_1) {
          return cell.getRichStringCellValue().getString();
        }
      }
      _xblockexpression = null;
    }
    return ((String)_xblockexpression);
  }
  
  public void setStringAt(final int sheetId, final int rowId, final int colId, final String content) {
    HSSFSheet sheet = this.workbook.getSheetAt(sheetId);
    final HSSFRow crow = sheet.getRow(rowId);
    final HSSFCell cell = crow.getCell(colId);
    cell.setCellValue(content);
  }
  
  public void save() {
    try {
      FileOutputStream _fileOutputStream = new FileOutputStream(this.file);
      this.workbook.write(_fileOutputStream);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public void saveAs(final String name) {
    try {
      final FileOutputStream out = new FileOutputStream(name);
      this.workbook.write(out);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  public static String removeAccent(final String source) {
    final String src = source.replace(" ", "-");
    return Normalizer.normalize(src, Normalizer.Form.NFD).replaceAll("[̀-ͯ]", "");
  }
}
