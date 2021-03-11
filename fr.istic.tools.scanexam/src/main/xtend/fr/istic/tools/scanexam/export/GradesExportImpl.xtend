package fr.istic.tools.scanexam.export

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fr.istic.tools.scanexam.services.ExamGraduationService

class GradesExportImpl implements GradesExport {
	ExamGraduationService service
	
	new(ExamGraduationService serv){
		service = serv
	}
	
	override exportGrades() {
		
		val XSSFWorkbook workbook = new XSSFWorkbook()
		val XSSFSheet sheet = workbook.createSheet("export_grades")
		
		var int rowCount = 0
		
		for(i : 0 ..< service.studentSheets.size){
			
		}
		
		/*
		 * Dans l'idée on peut get les StudentSheets depuis le service
		 * Puis on suppose qu'il existe un .getStudentName et .getStudentGrade
		 * On créé le fichier excel
		 * On le remplit
		 */
	}
	
	
}