package fr.istic.tools.scanexam.view.fX.editor

import javafx.scene.layout.VBox
import javafx.scene.layout.Priority
import javafx.scene.Node

class QuestionListEditor extends VBox {
	
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
	def loadQuestion(Box box, String name,int page,int id,float questionWorth) {
		var item = new QuestionItemEditor(this,box,name,page,id);
		item.scale = questionWorth
		add(item);
	}

	/*
	 * Create new question and add to model
	 */
	def void newQuestion(Box box) {
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
		var item = new QuestionItemEditor(this, box, type,
			controller.editor.presenter.presenterPdf.currentPdfPageNumber);

		addToModel(item)
		add(item)
	}

	def removeQuestion(QuestionItemEditor item) {
		removeFocus
		controller.mainPane.removeZone(item.zone)
		controller.mainPane.children.remove(item.zone)
		remove(item)
		//removeFromModel(item) //TODO FIX
	}
	
	def void select(QuestionItemEditor item) {
		for (Node n : children) {
			var question = n as QuestionItemEditor
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
			if (e instanceof QuestionItemEditor) {
				(e as QuestionItemEditor).focus = false
			}
		}
	}

	def void showOnlyPage(int page) {
		for (Node e : children) {
			if (e instanceof QuestionItemEditor) {
				checkAndDisplay(page, e as QuestionItemEditor)
			}

		}
	}

	def void checkAndDisplay(int page, QuestionItemEditor item) {
		if (item.page == page) {
			item.zone.isVisible(true)
		} else {
			item.zone.isVisible(false)
		}

	}

	def add(QuestionItemEditor item) {
		this.children.add(item)
	}

	def remove(QuestionItemEditor item) {
		this.children.remove(item)
	}
	
	def clear(){
		controller.selectQuestion(null)
		children.clear
	}
	//-------------//

	//---Model Interactions---//
	def addToModel(QuestionItemEditor item) {
		item.questionId = controller.editor.presenter.presenterQuestionZone.createQuestion(item.zone.x/controller.maxX, item.zone.y/controller.maxY,item.zone.height/controller.maxY, item.zone.width/controller.maxX) // TODO convert
		updateInModel(item)
	}


	def updateInModel(QuestionItemEditor item) {
		controller.editor.presenter.presenterQuestionZone.moveQuestion(item.questionId, item.zone.x/controller.maxX, item.zone.y/controller.maxY) 
		controller.editor.presenter.presenterQuestionZone.resizeQuestion(item.questionId, item.zone.height/controller.maxY,item.zone.width/controller.maxX)
		controller.editor.presenter.presenterQuestionZone.renameQuestion(item.questionId,item.name)
		controller.editor.presenter.presenterQuestionZone.changeQuestionWorth(item.questionId,item.scale)
	}

	def removeFromModel(QuestionItemEditor item) {
		controller.editor.presenter.presenterQuestionZone.removeQuestion(item.questionId)
	}
	//------------------------//
	
	
	
	

}
