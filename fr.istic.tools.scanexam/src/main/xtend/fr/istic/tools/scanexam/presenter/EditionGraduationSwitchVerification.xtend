package fr.istic.tools.scanexam.presenter

import fr.istic.tools.scanexam.core.Exam

class EditionGraduationSwitchVerification {
	
	static Exam instanceOfExam;

	
	def static saveExamInstance(Exam exam){
		instanceOfExam = exam
	}
	
	def static Exam loadExamInstance(){
		return instanceOfExam
	}
	
}