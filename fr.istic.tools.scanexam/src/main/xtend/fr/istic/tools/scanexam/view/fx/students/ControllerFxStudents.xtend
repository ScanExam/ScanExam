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

/**
 * Classe qui gère l'onglet du récapitulatif de la correction courrante
 */
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
	
	/**
	 * Focntion d'update des éléments de la table view
	 */
	def update(){
		
		initTable()
		
		updateQuestionList
		updateStudentsList
		addContextMenuOnEachLines()
		
		mainPane.content = table
		
	}
	
	/**
	 * Ajout des studentSheets dans la table
	 */
	def updateStudentsList(){
		val List<StudentSheet> sheets = new LinkedList(serviceGrad.studentSheets.toList)
		Collections.sort(sheets, [s1, s2|s1.id - s2.id])
		
		for(sheet : sheets){
			table.items.add(sheet)	
		}	
	}
	
	/**
	 * Définition de chacune des colonnes, et binding des valeurs
	 */
	def updateQuestionList(){
		
		var TableColumn<StudentSheet, String> idCol = new TableColumn(LanguageManager.translate("studentsTab.tableView.ID"))
		idCol.setCellValueFactory(new PropertyValueFactory<StudentSheet, String>("studentID"))
		table.columns.add(idCol)
		
		var TableColumn<StudentSheet, String> lNCol = new TableColumn(LanguageManager.translate("studentsTab.tableView.lastName"))
		lNCol.setCellValueFactory(new PropertyValueFactory<StudentSheet, String>("lastName"))
		table.columns.add(lNCol)
		
		var TableColumn<StudentSheet, String> fNCol = new TableColumn(LanguageManager.translate("studentsTab.tableView.firstName"))
		fNCol.setCellValueFactory(new PropertyValueFactory<StudentSheet, String>("firstName"))
		table.columns.add(fNCol)

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
	
	/**
	 * Définition/redéfinition de la table
	 */
	def initTable(){
		table = new TableView
		table.prefWidth = 720
		table.prefHeight = 720
	}
	
	/**
	 * Définition du menu contextuel
	 */
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
	
	/**
	 * Méthode appellée dans le menu contextuel pour aller à une copie spécifique
	 */
	def gotToSheet(int id){
		controllerRoot.goToCorrectorTab(id)
	}

}