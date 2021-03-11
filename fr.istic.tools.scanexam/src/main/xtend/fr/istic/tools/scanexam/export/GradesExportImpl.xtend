package fr.istic.tools.scanexam.export

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fr.istic.tools.scanexam.services.ExamGraduationService
import java.io.FileOutputStream
import java.io.IOException

class GradesExportImpl implements GradesExport {
	ExamGraduationService service
	
	new(ExamGraduationService serv){
		service = serv
	}
	
	/**
	 * Methode qui créer un fichier Excel et qui le remplit avec les noms des étudiants et leurs notes
	 */
	override exportGrades() {
		
		val XSSFWorkbook workbook = new XSSFWorkbook()
		val XSSFSheet sheet = workbook.createSheet("export_grades")
		
		var int rowCount = 0
		
		for(i : 0 ..< service.studentSheets.size){
			val Row row = sheet.createRow(rowCount)
			
			val Cell cellName = row.createCell(0)
			cellName.cellValue = service.studentSheets.get(i).studentName
			
			val Cell cellGrade = row.createCell(1)
			cellGrade.cellValue = service.studentSheets.get(i).computeGrade
		}
		
		try{
			val FileOutputStream outStream = new FileOutputStream(service.examName +".xslx")
			workbook.write(outStream)
		}
		catch(IOException e){
			e.printStackTrace
		}

	}
	
	
}