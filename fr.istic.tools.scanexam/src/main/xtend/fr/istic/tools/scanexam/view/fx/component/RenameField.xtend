package fr.istic.tools.scanexam.view.fx.component

import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.control.TextFormatter
import javafx.scene.layout.HBox
import javafx.beans.property.StringProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos

class RenameField extends HBox {
	
	new() {
		label = new Label("Temp");
		
		field = new TextField("Temp");
		
		icon = new Label("\u270E")
		icon.styleClass.add("unicodeLabel")
		this.children.addAll(label,icon);
		field.styleClass.add("mytext-field")
		label.styleClass.add("renameLabel")
		this.styleClass.add("RenameField")
		setAlignment(Pos.CENTER_LEFT)
		setupEvents
	}

	Label label;
	package TextField field;
	Label icon;
	
	/**
	 * Formatter used incase we only want to allow numbers
	 */
	TextFormatter<Number> formatter
	
	/**
	 * Property used to detect renaming of a field
	 */
	var StringProperty prop = new SimpleStringProperty(this, "NewText", "temp");
	
	/**
	 * toggles the visibility of the label or the field
	 */
	private def toggleRename(boolean b) {
		if (b) {
			this.children.remove(label);
			this.children.remove(icon);
			this.children.add(field);
			field.requestFocus
			field.selectAll

		} else {
			if (!this.children.contains(label)) {
				label.text = field.text
				prop.value = field.text
				this.children.remove(field);
				this.children.addAll(label,icon);
			}
		}
	}
	/**
	 * Returns the text contained by this component
	 */
	def getText(){
		label.text
	}
	/**
	 * Sets the text contained by this component
	 */
	def setText(String text){
		label.text = text;
	}
	/**
	 * Returns the TextProperty of the component
	 */
	def getTextProperty(){
		return prop
	}
	
	def setFieldFormatter(TextFormatter<Number> formatter){
		field.textFormatter = formatter
		this.formatter = formatter
	}
	
	def getFormatterValue(){
		formatter.value.floatValue
	}
	/**
	 * Sets up the events for this component, called in the constructor
	 */
	private def setupEvents(){
		field.onAction = [e | toggleRename(false)]
		field.focusedProperty.addListener([obs,oldVal,newVal | !newVal ? toggleRename(false) : field.text = label.text])
		label.onMouseClicked = [e | toggleRename(true)]
		icon.onMouseClicked = [e | toggleRename(true)]
		
	}
}
