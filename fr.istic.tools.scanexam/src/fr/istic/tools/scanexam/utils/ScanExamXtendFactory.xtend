package fr.istic.tools.scanexam.utils

import fr.istic.tools.scanexam.ScanexamFactory
import fr.istic.tools.scanexam.Question
import java.util.List
import fr.istic.tools.scanexam.Exam
import java.io.File
import java.io.FilenameFilter

class ScanExamXtendFactory {
	
	def  static exam(String title) {
		val e= ScanexamFactory.eINSTANCE.createExam
		e.label = title
		e
	}

	def static exam(String title, String path, int nPages, Iterable<Question> questions) {
		val e= exam(title)

		e.numberOfPages= nPages
		e.folderPath=path
		e.questions+=questions
		e
	}
 

	def static gradingData(Exam exam) {
		
		val e= ScanexamFactory.eINSTANCE.createGradingData
		e.excelFileName = exam.label+".xls"
		e.exam =  exam
		val File dir = new File(exam.folderPath);
		e.images+= dir.listFiles(new FilenameFilter() {
			override boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".png");
			}
		}).sortInplace([File a, File b | a.name.compareTo(b.name)]);
		
		if (e.images.size%exam.numberOfPages!=0) {
			throw new UnsupportedOperationException('''Uneven number of scans ''')
		}
		println("Nb images "+e.images.size)
		val int nbStudents= e.images.size/exam.numberOfPages
		for (i:0..<nbStudents) {
			val grade = studentGrade
			grade.studentID="student_"+i
			e.grades+=grade
			for (q : exam.questions) {
				val questionGrade = questionGrade(q)
				grade.questionGrades+= questionGrade
				questionGrade.filename=e.images.get(i*exam.numberOfPages+ q.zone.page-1).path
			}
		}
		e
	}


	def static studentGrade() {
		ScanexamFactory.eINSTANCE.createStudentGrade
	
	}

	def static questionGrade(Question q) {
		val e= ScanexamFactory.eINSTANCE.createQuestionGrade
		e.question=q
		e.grade=""
		e.validated=false
		e
	}

	def static question(String label) {  
		val r =ScanexamFactory.eINSTANCE.createQuestion
		r.label=label
		r  
	}
 
	def static question(String label, List<Integer> z,List<Integer> markZ, List<String> grades, int defaultGrade) {
		val r =question(label)
		r.zone=zone(z.get(0),z.get(1),z.get(2)-z.get(0),z.get(3)-z.get(1),z.get(4))
		r.markZone=zone(markZ.get(0),markZ.get(1),markZ.get(2)-markZ.get(0),markZ.get(3)-markZ.get(1),markZ.get(4))
		r.grades+=grades
		r.defaultGradeIndex=defaultGrade
		r
	}

	def static zone(int x, int y, int w, int h, int page) {
		val r = ScanexamFactory.eINSTANCE.createScanZone
		r.x=x
		r.y=y
		r.w=w
		r.h=h
		r.page=page
		r
	}

}