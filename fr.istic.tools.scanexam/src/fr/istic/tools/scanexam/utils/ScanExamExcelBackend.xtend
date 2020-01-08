package fr.istic.tools.scanexam.utils

import fr.istic.tools.scanexam.ScanexamFactory
import fr.istic.tools.scanexam.Question
import java.util.List
import fr.istic.tools.scanexam.GradingData
import java.io.File
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.io.FileInputStream
import org.apache.poi.hssf.usermodel.HSSFSheet
import java.io.FileOutputStream
import fr.istic.tools.scanexam.Exam
import fr.istic.tools.scanexam.instances.PFOExams

class ScanExamExcelBackend {
	
	static HSSFWorkbook workbook
	

	def static load(GradingData data, File f) {
		workbook = new HSSFWorkbook(new FileInputStream(f));
		

		for (studentId : 0..<data.grades.size) {
			val studentGrade = data.grades.get(studentId)
			studentGrade.studentID=getStringAt(1,studentId+2, 0)
			
			for (questionGrade : studentGrade.questionGrades) {
				val int column = data.exam.questions.indexOf(questionGrade.question)
				questionGrade.grade = getStringAt(1,studentId+2, column+1)
				if (questionGrade.grade.length!=0) {
					questionGrade.validated=true
				}
			}	
		}
		
		data
	}

	def static save(File file, GradingData data) {
		workbook = new HSSFWorkbook();
		val summary = workbook.createSheet("Summary")
		val sheet = workbook.createSheet("Grades")
		
		setStringAt(0,0, 0 , "#Images")
		setStringAt(0,0, 1 , ""+data.images.size)
		setStringAt(0,1, 0 , "#Grades")
		setStringAt(0,1, 1 , ""+data.images.size/data.exam.numberOfPages)
		setStringAt(0,1, 0 , "#Questions")
		setStringAt(0,1, 1 , ""+data.exam.questions.size)
		for (i:0..<data.exam.questions.size) {
			setStringAt(0,i, 1 , "#Q"+i)
			val q = data.exam.questions.get(i)
			setStringAt(0,i, 2 , q.label)
			setStringAt(0,i, 3 , ""+q.defaultGradeIndex)
			setStringAt(0,i, 4 , ""+q.grades.size)
			setStringAt(0,i, 5 , "#zone")
			setStringAt(0,i, 6 , ""+q.zone.x)
			setStringAt(0,i, 7 , ""+q.zone.y)
			setStringAt(0,i, 8 , ""+q.zone.w)
			setStringAt(0,i, 9 , ""+q.zone.h)
			setStringAt(0,i, 10 , "#grades")
			for (j:0..<q.grades.size) {
				setStringAt(0,11+j, 0 , ""+q.grades.get(j))
			}
		}

		var col = 1
		for (question : data.exam.questions) {
			setStringAt(1,0, col , question.label)
			col+=1
		}

		val gradeMap = newHashMap(#[
			"A"->5,
			"B"->4,
			"C"->3,
			"D"->2,
			"E"->1,
			"F"->0
		]);
		
		val scale = data.exam.questions.map[weight].reduce[p1, p2|p1+p2]
		
		for (studentId : 0..<data.grades.size) {
			val studentGrade = data.grades.get(studentId)
			setStringAt(1,studentId+2, 0, "Student_"+studentId)
			var grade = 0.0;  
			for (questionGrade : studentGrade.questionGrades) {
				val int column = data.exam.questions.indexOf(questionGrade.question)
				setStringAt(1,studentId+2,  column+1, questionGrade.grade)
				val qgrade =gradeMap.get(questionGrade.grade)
				if (qgrade!==null) {
					grade += gradeMap.get(questionGrade.grade)*questionGrade.question.weight
				} 
			}
			val scaledGrade  = grade/scale;
			
			setStringAt(1,studentId+2,  data.exam.questions.size+3, ""+ scaledGrade)
				
		}
		workbook.write(new FileOutputStream(file))	
	}
	
	
	def static setStringAt(int sheetId, int rowId,int colId, String content) {
		var HSSFSheet sheet = workbook.getSheetAt(sheetId);
		var crow = sheet.getRow(rowId);
		if (crow===null) crow = sheet.createRow(rowId)
		var cell = crow.getCell(colId);
		if (cell===null) cell = crow.createCell(colId)
		cell.cellValue = content 
	}

	def static getStringAt(int sheetId, int rowId, int colId) {
		var HSSFSheet sheet = workbook.getSheetAt(sheetId);
		val crow = sheet.getRow(rowId);
		if (crow!==null) {
			val cell= crow.getCell(colId);
			if (cell!==null) {
				return cell.richStringCellValue.string;
			} 
		}
		null
	}

}