package fr.istic.tools.scanexam.view.fX.corrector

import java.util.LinkedList
import java.util.List
import javafx.scene.control.Label
import javafx.scene.control.ToggleButton
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class Grader extends VBox {

	new(){
		items = new LinkedList<GradeItem>();
	}
	List<GradeItem> items;
	
	def void display(int questionId,List<Integer> ids){
		this.children.clear();
		for (GradeItem item : items) {
			if (item.checkVisible(questionId)) {
				this.children.add(item)
				item.checkPressed(ids);
			}
 			
		}
	}
	
	def void add(String name,String points,int itemId,int questionId) {
		items.add(new GradeItem(name,points,itemId,questionId))
	}
	
	
	
	static class GradeItem extends HBox {
		
		new(String name,String points,int itemId,int questionId) {
			selected = new ToggleButton("X");
			name = new Label(name);
			worth = new Label(points);
			this.itemId = itemId
			this.questionId = questionId
			
			this.children.addAll(selected,this.name,worth)
			
		}
		def checkPressed(List<Integer> ids){
			if (ids.contains(this.itemId)) {
				selected.selected = true;
			}
			else 
				selected.selected = false;
		}
		
		def checkVisible(int questionId) {
			if (this.questionId == questionId)
			return true
			else 
			return false
		}
		
		def toggleVisible(boolean visible) {
			this.visible = visible
		}
		
		
		ToggleButton selected;
		int itemId;
		int questionId
		Label name;
		Label worth;
	}
}