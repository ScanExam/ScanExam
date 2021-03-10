package fr.istic.tools.scanexam.view.fX

import javafx.scene.layout.VBox
import javafx.scene.control.Label

class StudentItem extends VBox {
	int studentId;
	new(int studentId) {
		super()
		this.studentId = studentId;
		this.children.add(new Label(studentId + ""));
	}
	
}