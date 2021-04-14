package fr.istic.tools.scanexam.view.fx.component

import java.util.List
import javafx.geometry.Side
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem

class RenameFieldSuggests extends RenameField {
	def getFieldTextProperty(){
		field.textProperty
	}
	new(){
		super();
		entriesPopup = new ContextMenu;
	}
	
	ContextMenu entriesPopup;
	
	def showSuggestion(List<String> strings){
		entriesPopup.show(this,Side.BOTTOM,0,0);
		populatePopup(strings)
	}
	
	def hideSuggestion(){
		entriesPopup.hide
	}
	
	def populatePopup(List<String> list){
		entriesPopup.items.clear
		for (String s : list) {
			var item = new MenuItem(s);
			item.onAction = [e | field.text = s
				hideSuggestion
			]
			entriesPopup.items.add(item);
		}
	}
}
