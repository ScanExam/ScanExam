package fr.istic.tools.scanexam.gui

import fr.istic.tools.scanexam.GradingData
import fr.istic.tools.scanexam.Question
import fr.istic.tools.scanexam.utils.ScanExamExcelBackend
import fr.istic.tools.scanexam.utils.ScanExamXtendUtils
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import javax.swing.filechooser.FileNameExtensionFilter

class ScanExamController {

	int currentStudentIndex =0;
	public int currentQuestionIndex = 0
	int currentGradeValueIndex = 0

	GradingData data;
	BufferedImage image
	ScanExamPanel panel
	ExcelTableViewer tableView;
	
	Question prevQuestion
	
	new(GradingData data) {
		this.data=data
	}

	def setPanel(ScanExamPanel panel) {
		this.panel=panel
	}

	def setTableView(ExcelTableViewer  panel) {
		this.tableView=panel
	}

	def getGradingData() { data}
	
	def extractQuestionZone() {
		val q = currentQuestion
		image.getSubimage(q.zone.x, q.zone.y, q.zone.w, q.zone.h)
	}

	def getCurrentQuestion() {
		data.exam.questions.get(currentQuestionIndex)
	}

	def getCurrentPageIndex() {
		data.exam.numberOfPages*currentStudentIndex + currentQuestion.zone.page-1
	}

	def loadCurrentPage() {
		println('''Loading «data.images.get(currentPageIndex).name»''')
		image= ImageIO.read(data.images.get(currentPageIndex))
		println('''	->  image «image.width»x«image.height»''')
					
	}

	def updateQuestionGradeIndex() {
		val grades = currentQuestion.grades
		val grade = currentQuestionGrade.grade
		currentGradeValueIndex=(grades).indexOf(grade)
		println(">> " + currentGradeValueIndex + " " + currentQuestion.grades.size + " " + currentQuestion.defaultGradeIndex)
		if (currentGradeValueIndex==-1) {
			if (currentQuestionGrade.validated) {
				currentQuestionGrade.validated=false
				currentGradeValueIndex=currentQuestion.defaultGradeIndex
				//throw new UnsupportedOperationException('''Invalid grade «grade» not found in «grades»''')
			} else {
				currentGradeValueIndex=currentQuestion.defaultGradeIndex 
			}
		} 
	}
	
	def updateQuestionInfo() {
		updateQuestionGradeIndex		
		val newq= currentQuestion
		if (newq.zone.page!=prevQuestion.zone.page) {
			loadCurrentPage
		}
		panel.updateQuestionZone
		
	} 

	def getNextQuestion() {
		prevQuestion= currentQuestion
		currentQuestionIndex=(currentQuestionIndex+1)%data.exam.questions.size
		updateQuestionInfo
	}

	def getPrevQuestion() {
		prevQuestion= currentQuestion
		currentQuestionIndex=(currentQuestionIndex+(data.exam.questions.size-1))%data.exam.questions.size
		updateQuestionInfo
	}

	def increaseGrade() {
		currentGradeValueIndex = (currentGradeValueIndex +1) %  currentQuestion.grades.size
		currentQuestionGrade.validated=false
		panel.repaint
	}

	def setGrade(int value) {
		if (value<currentQuestion.grades.size) {
			currentGradeValueIndex = value
			currentQuestionGrade.validated=false
		}
		panel.repaint
	}

	def decreaseGrade() {
		currentGradeValueIndex = (currentGradeValueIndex + currentQuestion.grades.size-1) %  currentQuestion.grades.size
		currentQuestionGrade.validated=false
		panel.repaint
	}
  
	def validateGrade() {
		currentQuestionGrade.grade=currentQuestion.grades.get(currentGradeValueIndex)
		currentQuestionGrade.validated=true
		panel.repaint
		tableView.updateTable()
	}

	def getNbStudent() {
		(data.images.size/data.exam.numberOfPages)
	}
	
	def gotoStudent(long i) {
		var index = 0
		for (exam : data.grades) {
			if (exam.numAnonymat==i) {
				currentStudentIndex=index
				panel.repaint
				tableView.updateTable()
				return
			} else {
			}
			index++
		}
		JOptionPane.showMessageDialog(null, 
			'''Anonymous number "«i»" not found''', 
			"Format error", 
			JOptionPane.ERROR_MESSAGE);
	
	}
	
	def nextExam() {
		currentStudentIndex+=1
		if (currentPageIndex>=data.images.size) {
			currentStudentIndex=0
		}
		updateQuestionGradeIndex		
		loadCurrentPage
		panel.updateQuestionZone
		tableView.updateTable()
		
	}

	def prevExam() {
		currentStudentIndex-=1
		if (currentPageIndex<0) {
			currentStudentIndex=(data.images.size/data.exam.numberOfPages)-1
		}
		updateQuestionGradeIndex		
		loadCurrentPage
		panel.updateQuestionZone
		tableView.updateTable()
	}
	
	
	def saveExcel() {
		val chooser = new JFileChooser(System.getProperty("user.dir"));
		val filter = new FileNameExtensionFilter("Excel file", "xls");
		chooser.setFileFilter(filter);
		val returnVal = chooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
			ScanExamExcelBackend.save(chooser.getSelectedFile(), data)
		}
	}

	def saveExcel(File file) {
			ScanExamExcelBackend.save(file, data)
	}
	def loadXMI() {

		val chooser = new JFileChooser(System.getProperty("user.dir"));
		val filter = new FileNameExtensionFilter("XMI grading file", "xmi");
		chooser.setFileFilter(filter);
		val returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
			data = ScanExamXtendUtils.load(chooser.getSelectedFile())
		}
	}

	def loadExcel() {

		val chooser = new JFileChooser(System.getProperty("user.dir"));
		val filter = new FileNameExtensionFilter("Excel file", "xls");
		chooser.setFileFilter(filter);
		val returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
			data = ScanExamExcelBackend.load(data,chooser.getSelectedFile())
		}
	}

	def saveXMI() {

		val chooser = new JFileChooser(System.getProperty("user.dir"));
		val filter = new FileNameExtensionFilter("XMI grading file", "xmi");
		chooser.setFileFilter(filter);
		val returnVal = chooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
			ScanExamXtendUtils.save(chooser.getSelectedFile(), data)
		}
	}

	def getCurrentStudent() {
		data.grades.get(currentStudentIndex)
	}
	
	def getCurrentQuestionGrade() {
		currentStudent.questionGrades.get(currentQuestionIndex)
	}

	def getCurrentGradeValue() {
		currentQuestion.grades.get(currentGradeValueIndex) 
	}
	
	def getCurrentGradeIndex() {
		currentGradeValueIndex
	}
	
	
	
}