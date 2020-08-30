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
import static extension fr.istic.tools.scanexam.utils.ScanExamXtendUtils.*
import javax.swing.JOptionPane
import java.util.Comparator
import fr.istic.tools.scanexam.StudentGrade

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
				val grade = questionGrade.grade
				if (grade!==null && grade.length!=0) {
					questionGrade.validated=true
				}
			}
			
			val anonNumb = getStringAt(1,studentId+2,  data.exam.questions.size+4)
			if (anonNumb!==null && anonNumb.length>0) {
				try {
					studentGrade.numAnonymat = Long.parseLong(anonNumb)	
				} catch (NumberFormatException e) {
	        		JOptionPane.showMessageDialog(null, 
                              '''Wrong format, integer expected but "«anonNumb»" found''', 
                              "Format error", 
                              JOptionPane.ERROR_MESSAGE);
	        	}
			}
			
			
			
				
		}
		
		data
	}

	def static save(File file, GradingData data) {
		workbook = new HSSFWorkbook();
		val summary = workbook.createSheet("Summary")
		val sheet = workbook.createSheet("Grades")
		val scol = workbook.createSheet("Final")
		
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

		
		for (studentId : 0..<data.grades.size) {
			val studentGrade = data.grades.get(studentId)
			setStringAt(1,studentId+2, 0, "Student_"+studentId)
			for (questionGrade : studentGrade.questionGrades) {
				val int column = data.exam.questions.indexOf(questionGrade.question)
				setStringAt(1,studentId+2,  column+1, questionGrade.grade)
			}
			setStringAt(1,studentId+2,  data.exam.questions.size+3, ""+ studentGrade.computeGrade)
				
		}
		
		for (studentId : 0..<data.grades.size) {
			val studentGrade = data.grades.get(studentId)
			setStringAt(1,studentId+2,  data.exam.questions.size+3, ""+ studentGrade.computeGrade())
			setStringAt(1,studentId+2,  data.exam.questions.size+4, ""+ studentGrade.numAnonymat)
		}
		
		val r= data.grades.sortBy[it.numAnonymat]
		for (i : 0..<r.size) {
			val studentGrade = r.get(i)
			setStringAt(2,i+1,  1, ""+ studentGrade.numAnonymat)
			setStringAt(2,i+1,  2, ""+ studentGrade.computeGrade())
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

	def static String getStringAt(int sheetId, int rowId, int colId) {
		var HSSFSheet sheet = workbook.getSheetAt(sheetId);
		try {
			val crow = sheet.getRow(rowId);
			if (crow!==null) {
				val cell= crow.getCell(colId);
				
				if (cell!==null) {
					switch(cell.cellType) {
						case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC :{
							return cell.numericCellValue.intValue.toString;
						}
						case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING :{
							return cell.stringCellValue;
						}
						case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK:{
							return ""
						}
						
					}
				} 
			}
			null
		} catch (Exception exception) {
			throw new RuntimeException("Cannot read string at sheet "+sheetId+" cell ("+rowId+","+colId+"), error "+exception.message)
		}
	}

}