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
import javafx.scene.control.TableRow
import javafx.scene.control.MenuItem
import javafx.event.EventHandler
import javafx.event.ActionEvent
import fr.istic.tools.scanexam.view.fx.ControllerRoot

class ControllerFxStudents {
	
	@FXML
	ScrollPane mainPane
	
	TableView<StudentSheet> table
	
	ServiceGraduation serviceGrad
	
	ControllerRoot controllerRoot
	
	
	def init(ServiceGraduation serviceG, ControllerRoot rootController){
		this.serviceGrad = serviceG
		
		this.controllerRoot = rootController
			
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
		
		var TableColumn<StudentSheet, String> idCol = new TableColumn(LanguageManager.translate("studentsTab.tableView.ID"))
		idCol.setCellValueFactory(new PropertyValueFactory<StudentSheet, String>("studentName"))
		table.columns.add(idCol)
		/*
		 * TODO
		 * Si la question est sur 0 pts, ne pas la mettre, remplacer par une colonne ou deux :
		 * - une colonne si c'est un numéro d'étudiant
		 * - deux colonnes si c'est nom / prénom
		 * 
		 * => dans le modèle, se baser sur l'id de la student sheet comme numéro
		 * 	ET séparer le studentName par deux champs nom prénoms ===> Trouver comment faire dans le modèle
		 */
		
		for(int i : 0 ..< serviceGrad.numberOfQuestions){

			if(serviceGrad.getQuestion(i).gradeScale.maxPoint != 0f){
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
		table.rowFactory = new Callback<TableView<StudentSheet>, TableRow<StudentSheet>>(){
			
			override TableRow<StudentSheet> call(TableView<StudentSheet> tableView){
				val TableRow<StudentSheet> row = new TableRow<StudentSheet>()
				val ContextMenu sheetMenu = new ContextMenu
				val MenuItem goToSheet = new MenuItem(LanguageManager.translate("studentsTab.contextMenu.goToSheet"))
				
				//ajout de l'action d'aller à un sheet spécifique
				goToSheet.setOnAction(new EventHandler<ActionEvent>(){
					override handle(ActionEvent action){
						gotToSheet(row.item.id)
					}
				})
				
				sheetMenu.items.add(goToSheet)
				
				//display the contextual menu on non empty rows
				row.contextMenuProperty().bind(Bindings.when(row.emptyProperty).then(null as ContextMenu).otherwise(sheetMenu))
				
				return row
				
			}
		}
	}
	
	def gotToSheet(int id){
		controllerRoot.goToCorrectorTab(id)
	}

}