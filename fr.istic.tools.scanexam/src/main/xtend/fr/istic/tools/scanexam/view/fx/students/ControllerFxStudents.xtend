package fr.istic.tools.scanexam.view.fx.students

import fr.istic.tools.scanexam.services.api.ServiceGraduation
import javafx.fxml.FXML
import javafx.scene.control.ScrollPane
import java.util.Collections
import java.util.List
import fr.istic.tools.scanexam.core.StudentSheet
import java.util.LinkedList
import javafx.scene.control.ContextMenu
import javafx.scene.control.TableView
import javafx.scene.control.TableColumn
import javafx.scene.control.cell.PropertyValueFactory
import javafx.beans.value.ObservableValue
import javafx.beans.binding.Bindings
import javafx.util.Callback
import fr.istic.tools.scanexam.config.LanguageManager

class ControllerFxStudents {
	
	@FXML
	ScrollPane mainPane
	
	TableView<StudentSheet> table
	
	ServiceGraduation serviceGrad
	
	ContextMenu menu
	
	
	def init(ServiceGraduation serviceG){
		this.serviceGrad = serviceG
		
		this.menu = new ContextMenu
			
	}
	
	def update(){
		
		initTable()
		
		updateQuestionList
		updateStudentsList
		addContextMenuOnEachLines()
		
		mainPane.content = table
		
	}
	
	def updateStudentsList(){
		val List<StudentSheet> sheets = new LinkedList(serviceGrad.studentSheets.toList)
		Collections.sort(sheets, [s1, s2|s1.id - s2.id])
		
		for(sheet : sheets){
			table.items.add(sheet)	
		}	
	}
	
	def updateQuestionList(){
		for(int i : 0 ..< serviceGrad.numberOfQuestions){
			
			if(i == 0){
				var TableColumn<StudentSheet, String> col = new TableColumn(serviceGrad.getQuestion(i).name)
				col.setCellValueFactory(new PropertyValueFactory<StudentSheet, String>("studentName"))
				table.columns.add(col)
			}
			else{
				var TableColumn<StudentSheet, Float> col = new TableColumn(serviceGrad.getQuestion(i).name)
				col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StudentSheet, Float>, ObservableValue<Float>>{
					override ObservableValue<Float> call(TableColumn.CellDataFeatures<StudentSheet, Float> cd){
						Bindings.createObjectBinding(| cd.value.grades.get(i).gradeValue)
					}
				})
				
				table.columns.add(col)
			}
			
		}
		var TableColumn<StudentSheet, Float> col = new TableColumn(LanguageManager.translate("studentsTab.total"))
		col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<StudentSheet, Float>, ObservableValue<Float>>{
					override ObservableValue<Float> call(TableColumn.CellDataFeatures<StudentSheet, Float> cd){
						Bindings.createObjectBinding(| cd.value.computeGrade)
					}
				})
		table.columns.add(col)
	}
	
	def initTable(){
		table = new TableView
		table.prefHeight = 720
		table.prefWidth = 720
	}
	
	def addContextMenuOnEachLines(){
	
	}
	
}