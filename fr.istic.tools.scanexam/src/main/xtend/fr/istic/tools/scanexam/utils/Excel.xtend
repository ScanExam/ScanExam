package fr.istic.tools.scanexam.utils

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.Normalizer
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook

class ExcelModel {
	
	var	HSSFWorkbook workbook;
	File file;

	new(File f) {
		file = f;
		workbook = new HSSFWorkbook(new FileInputStream(file));
	}


	def getNumberOfUsefulColumns(int sheedId) {
		var i=0
		while (getStringAt(sheedId,0,i)!=null && getStringAt(sheedId,0,i)!="") {
			i++
		} 
		i
	}
	def getNumberOfUsefulRows(int sheedId) {
		var i=0
		while (getStringAt(sheedId,i,0)!=null && getStringAt(sheedId,i,0)!="") {
			i++
		}
		i
	}
	
	def getStringAt(int sheetId, int rowId,int colId) {
		var HSSFSheet sheet = workbook.getSheetAt(sheetId);
		val crow = sheet.getRow(rowId);
		if (crow!=null) {
			val cell= crow.getCell(colId);
			if (cell!=null) {
				return cell.richStringCellValue.string;
			} 
		}
		null
	}

	def setStringAt(int sheetId, int rowId,int colId, String content) {
		var HSSFSheet sheet = workbook.getSheetAt(sheetId);
		val crow = sheet.getRow(rowId);
		val cell = crow.getCell(colId);
		cell.cellValue = content 

	}

	def save() {
		workbook.write(new FileOutputStream(file))
	}
	
	def public saveAs(String name) {	
 		val out = new FileOutputStream(name);
		workbook.write(out);
	}
	
	def	public static String removeAccent(String source) {
		val src = source.replace(" ", "-");
		return Normalizer.normalize(src, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
	}
	
	
}

