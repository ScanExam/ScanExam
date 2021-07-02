package fr.istic.tools.scanexam.view.fx.students

import javafx.scene.layout.HBox
import javafx.scene.control.Label
import javafx.geometry.Insets
import java.util.List
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.config.LanguageManager
import java.util.ArrayList

class StudentItem extends HBox {
	
	Label labelName

	List<Label> questions
	
	new(StudentSheet sheet){
		var name = sheet.studentName
		if (name === null || name === "") {
				name = LanguageManager.translate("name.default") + " " + sheet.id

		}
		this.labelName = new Label(name)
		this.labelName.padding = new Insets(5, 5, 5, 5)
		this.children.add(this.labelName)
		
		questions = new ArrayList
		var studentNote = 0f
		for(grade : sheet.grades){
			
			var pointsGrade = 0f
			for(entry : grade.entries){
				pointsGrade += entry.step
				studentNote += entry.step
			}
			var Label someGrade = new Label(pointsGrade.toString)
			someGrade.padding = new Insets(5, 5, 5, 5)
			 
			questions.add(someGrade)
			
		}		
		
		//ajout du total
		var total = new Label(studentNote.toString)
		total.padding = new Insets(5, 5, 5, 5)
		questions.add(total)
		
		this.children.addAll(this.questions)
	}
	
	
}