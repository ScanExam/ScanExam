package fr.istic.tools.scanexam.view.fx.students

import fr.istic.tools.scanexam.services.api.ServiceGraduation
import javafx.fxml.FXML
import javafx.scene.control.ScrollPane
import java.util.Collections
import java.util.List
import fr.istic.tools.scanexam.core.StudentSheet
import java.util.LinkedList
import javafx.scene.layout.GridPane
import javafx.scene.control.Label
import javafx.geometry.Insets
import fr.istic.tools.scanexam.config.LanguageManager
import java.util.ArrayList

class ControllerFxStudents {
	
	@FXML
	ScrollPane mainPane
	
	GridPane grille
	
	ServiceGraduation serviceGrad
	
	def init(ServiceGraduation serviceG){
		this.serviceGrad = serviceG
		
		this.grille = new GridPane
		
	}
	
	def update(){
		
		grille.children.clear
		
		updateQuestionList
		updateStudentsList
		
		grille.padding = new Insets(10,10,10,10)
		
		mainPane.content = grille
		
	}
	
	def updateStudentsList(){
		val List<StudentSheet> sheets = new LinkedList(serviceGrad.studentSheets.toList)
		Collections.sort(sheets, [s1, s2|s1.id - s2.id])
		//studentsList.children.clear
		for(sheet : sheets){
						
			var questions = new ArrayList
			var studentNote = 0f
			for(int g : 0 ..< sheet.grades.size){
				
				var pointsGrade = 0f
				for(entry : sheet.grades.get(g).entries){
					pointsGrade += entry.step
					studentNote += entry.step
				}
				var Label someGrade
				
				if(g == 0){
					var name = sheet.studentName
					if (name === null || name === "") {
						name = LanguageManager.translate("name.default") + " " + sheet.id
					}
					someGrade = new Label(name)
					
				}
				else
					someGrade = new Label(pointsGrade.toString)
				someGrade.padding = new Insets(5, 5, 5, 5)
				 
				questions.add(someGrade)
				
			}		
			
			//ajout du total
			var total = new Label(studentNote.toString)
			total.padding = new Insets(5, 5, 5, 5)
			questions.add(total)
			
			//ajout de l'étudiant à la grille
			for(question : questions){
				grille.add(question, questions.indexOf(question), sheet.id + 1)
			}
		}
		
		//mainPane.content = studentsList
	}
	
	def updateQuestionList(){
		for(int i :0 ..< serviceGrad.numberOfQuestions){
			var tete = new Label(serviceGrad.getQuestion(i).name)
			tete.padding = new Insets(5,5,5,5)
			grille.add(tete, i, 0)
		}
		/* TODO à changer un jour car pour le moment
		 * on part du principe qu'on sélectionne toujours une zone
		 * pour l'identifiant (eq. à la question 0)
		 * Conflit d'intérêt avec uniquement les QRCodes pour étudiants et pas d'entête
		 */
		var total = new Label(LanguageManager.translate("studentsTab.total"))
		total.padding = new Insets(5,5,5,5)
		grille.add(total, serviceGrad.numberOfQuestions, 0)
	}
	
}