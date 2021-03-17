package fr.istic.tools.scanexam.view.fX.editor

import javafx.scene.layout.VBox
import javafx.scene.layout.Priority
import javafx.scene.Node

class QuestionList extends VBox {
	
	//---Controller---//
	new(ControllerFXEditor controller) {
		this.controller = controller
		this.vgrow = Priority.ALWAYS
	}
	//----------------//
	
	//---FX vars---//
	ControllerFXEditor controller
	//-------------//
	
	//---GETTERS/SETTERS---//
	def getController(){
		controller
	}
	//---------------------//
	
	//---Methods---//
	def loadQuestion(EditorQuestionItem item) {
		add(item);
		controller.addZone(item.zone);
	}

	/*
	 * Create new question and add to model
	 */
	def newQuestion(Box box) {
		var type = BoxType.QUESTION
		switch controller.selectedTool {
			case QUESTION_AREA: {
				type = BoxType.QUESTION
			}
			case ID_AREA: {
				type = BoxType.ID
			}
			case QR_AREA: {
				type = BoxType.QR
			}
			default: {
			}
		}
		var item = new EditorQuestionItem(this, box, type,
			controller.editor.presenter.presenterPdf.currentPdfPageNumber);

		addToModel(item)
		add(item)
	}

	def removeQuestion(EditorQuestionItem item) {
		removeFocus
		controller.mainPane.removeZone(item.zone)
		controller.mainPane.children.remove(item.zone)
		remove(item)
		//removeFromModel(item) //TODO FIX
	}
	
	def void changeFocus(EditorQuestionItem item) {
		for (Node n : children) {
			var question = n as EditorQuestionItem
			if (question == item) {
				question.setFocus(true)
			}
			else {
				question.setFocus(false)
			}
		}
	}

	def void removeFocus() {
		for (Node e : children) {
			if (e instanceof EditorQuestionItem) {
				(e as EditorQuestionItem).focus = false
			}
		}
	}

	def void changeGradeItems(EditorQuestionItem newItem) {
		controller.changeFocus(newItem)
	}

	def void showOnlyPage(int page) {
		for (Node e : children) {
			if (e instanceof EditorQuestionItem) {
				checkAndDisplay(page, e as EditorQuestionItem)
			}

		}
	}

	def void checkAndDisplay(int page, EditorQuestionItem item) {
		if (item.page == page) {
			item.zone.isVisible(true)
		} else {
			item.zone.isVisible(false)
		}

	}

	def add(EditorQuestionItem item) {
		this.children.add(item)
	}

	def remove(EditorQuestionItem item) {
		this.children.remove(item)
	}
	//-------------//

	//---Model Interactions---//
	def addToModel(EditorQuestionItem item) {
		item.questionId = controller.editor.presenter.presenterQuestionZone.createQuestion(item.zone.x, item.zone.y,
			item.zone.height, item.zone.width) // TODO convert
	}


	def updateInModel(EditorQuestionItem item) {
		controller.editor.presenter.presenterQuestionZone.moveQuestion(item.questionId, item.zone.x, item.zone.y) // TODO convert
		controller.editor.presenter.presenterQuestionZone.resizeQuestion(item.questionId, item.zone.height,
			item.zone.width) // TODO convert
	}

	def removeFromModel(EditorQuestionItem item) {
		controller.editor.presenter.presenterQuestionZone.removeQuestion(item.questionId)
	}
	//------------------------//
	
	
	
	

}
