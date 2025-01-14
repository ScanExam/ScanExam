package fr.istic.tools.scanexam.view.fx.graduation

import javafx.scene.layout.VBox
import javafx.scene.Node
import org.apache.logging.log4j.LogManager

class StudentListGraduation extends VBox {
	static val logger = LogManager.logger

	new(ControllerFxGraduation controller) {
		this.controller = controller
		currentIndex = 0;
		this.spacing = 5;
		setupEvents
	}

	ControllerFxGraduation controller;

	int currentIndex;

	// ---GETTERS/SETTERS---//
	def getController() {
		controller
	}

	def getCurrentIndex() {
		currentIndex
	}

	def getCurrentItem() {
		if(noItems) return null
		children.get(currentIndex) as StudentItemGraduation
	}

	// ---------------------//
	// ---METHODS---//
	def boolean noItems() {
		return children.isEmpty
	}

	def addItem(StudentItemGraduation item) {
		item.list = this
		children.add(item)
	}

	def removeItem(StudentItemGraduation item) {
		children.remove(item)
	}

	def clearItems() {
		currentIndex = 0;
		children.clear
	}

	def void updateInModel(StudentItemGraduation item) {
		logger.info("Updating " + item.studentId + " to model")
		controller.currentStudentUserId = item.studentName
	}

	/**
	 * Method used for highlighting
	 */
	def focusItem(StudentItemGraduation item) {
		if(item === null) return void
		for (Node n : children) {
			var question = n as StudentItemGraduation;
			question.focus = false
		}
		item.focus = true
	}

	def selectNextItem() {
		if (currentIndex + 1 < children.size) {
			currentIndex++
		} else {
			currentIndex = 0
		}
	}

	def selectPreviousItem() {
		if (currentIndex > 0) {
			currentIndex--
		} else {
			currentIndex = children.size - 1
		}
	}

	def selectItem(StudentItemGraduation item) {
		currentIndex = children.indexOf(item);
	}

	def selectItemWithId(int id) {
		var i = 0
		var trouve = false
		while (i < children.size && !trouve) {
			val item = children.get(i) as StudentItemGraduation
			if (item.studentId == id)
				trouve = true
			else
				i++
		}
		currentIndex = i
	}

	// -------------//
	def setupEvents() {
	}
}
