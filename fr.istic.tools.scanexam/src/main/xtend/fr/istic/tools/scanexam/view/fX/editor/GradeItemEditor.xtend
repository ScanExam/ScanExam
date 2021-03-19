package fr.istic.tools.scanexam.view.fX.editor

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority

class GradeItemEditor extends HBox {
		//---Constructor---//
		new(GradeListEditor list,QuestionItemEditor item){
			super();
			this.questionItem = item
			this.list = list
			var h = new HBox();
			
			this.children.add(h)
			h.hgrow = Priority.ALWAYS
			
			
			
			remove = new Button("-");
			this.children.add(remove)
			
			name = new TextField("Temp Grade Item")
			name.editable = false;
			

			points = new NumberTextField();
			points.text = "0"
			points.editable = false
			
			h.children.addAll(name,points);
			
			this.styleClass.add("GradeItem")
			setupContextMenu
			setupEvents
		}
		//-----------------//
		
		GradeListEditor list;
		QuestionItemEditor questionItem;
		int gradeItemId;
		Button remove;
		NumberTextField points;
		TextField name;
		
		def String getPoints(){
			return points.text
		}
		
		def void setButtonAction(EventHandler<ActionEvent> handler) {
			remove.onAction = handler
		}
		
		def getGradeQuestionItem(){
			questionItem
		}
		
		def getGradeItemId(){
			gradeItemId
		}
		
		def setGradeItemId(int id) {
			gradeItemId = id
		}
		
		def setGradeItemName(String name){
			this.name.text = name
		}
		def getGradeItemName(){
			this.name.text
		}
		
		def getGradeItemPoints(){
			this.points.text
		}
		
		def setGradeItemPoints(String points) {
			this.points.text = points
		}
		
		
		def setNameEditable(){
			name.editable = true
			name.selectAll
		}
		
		def setPointsEditable(){
			points.editable = true
			points.selectAll
		}
		
		def commitNameChange(){
			name.editable = false
		}
		 
		def commitPointsChange(){
			points.editable = false
		}

		def setupContextMenu(){
			{
				var menu = new ContextMenu();
				name.contextMenu = menu
				var menuItem1 = new MenuItem("Rename Grade Item");//TODO translate
				menu.items.add(menuItem1);
				menuItem1.onAction = new EventHandler<ActionEvent>(){
					
					override handle(ActionEvent event) {
						setNameEditable
					}
					
				}
			}
			{
				var menu = new ContextMenu();
				points.contextMenu = menu
				var menuItem1 = new MenuItem("Change points for Grade Item");//TODO translate
				menu.items.add(menuItem1);
				menuItem1.onAction = new EventHandler<ActionEvent>(){
					
					override handle(ActionEvent event) {
						setPointsEditable
					}
					
				}
			}
		}
		
		def setupEvents(){
			val item = this
			name.onAction = new EventHandler<ActionEvent>(){
				
				override handle(ActionEvent event) {
					commitNameChange
				}
			}
			points.onAction = new EventHandler<ActionEvent>(){
				
				override handle(ActionEvent event) {
					commitPointsChange
				}
			}
			remove.onAction = new EventHandler<ActionEvent>(){
				
				override handle(ActionEvent event) {
					list.removeGradeItem(item)
				}
			}
			
		}
		
		
	}