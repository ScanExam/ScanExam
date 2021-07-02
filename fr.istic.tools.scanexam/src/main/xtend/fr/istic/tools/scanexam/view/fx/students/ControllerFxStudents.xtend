package fr.istic.tools.scanexam.view.fx.students

import fr.istic.tools.scanexam.services.api.ServiceGraduation
import javafx.fxml.FXML
import javafx.scene.control.ScrollPane
import java.util.Collections
import java.util.List
import fr.istic.tools.scanexam.core.StudentSheet
import java.util.LinkedList
import fr.istic.tools.scanexam.services.api.ServiceEdition

class ControllerFxStudents {
	
	@FXML
	ScrollPane mainPane
	
	QuestionList questList
	
	StudentItemList studentsList
	
	ServiceGraduation serviceGrad
	ServiceEdition serviceEdition
	
	def init(ServiceGraduation serviceG){
		this.serviceGrad = serviceG
		
		//this.serviceEdition = serviceEd
		
		this.studentsList = new StudentItemList
		
		this.questList = new QuestionList
		
	}
	
	def update(){
		updateQuestionList
		updateStudentsList
		
	}
	
	def updateStudentsList(){
		val List<StudentSheet> sheets = new LinkedList(serviceGrad.studentSheets.toList)
		Collections.sort(sheets, [s1, s2|s1.id - s2.id])
		studentsList.children.clear
		for(sheet : sheets){
			studentsList.addItem(new StudentItem(sheet))
		}
		
		mainPane.content = studentsList
	}
	
	def updateQuestionList(){
		//for(int i :0 ..< serviceEdition.)
		//TODO faire une méthode qui renvoie la liste des questions
	}
	
}