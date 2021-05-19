package fr.istic.tools.scanexam.view.fx.editor

import javafx.scene.layout.VBox
import javafx.scene.layout.Priority
import javafx.scene.Node



/**
 * This Component is used to store all the questions created in the edition.
 * To add questions to this list, we have 2 methods : 
 * 	-loadQuestion, adds a question to the list without adding it to the model, used when we load a question from the model.
 * 	-newQuestion, adds a question to the list and the model, used when we create a new question.
 * 
 * To manage focus, we use select and removeFocus
 * select will look for a specific item in the list and highlight it, while removing focus on other items.
 * removeFocus will remove the focus from all the elements in the list.
 * 
 * We can also manage witch box is present by using the showOnlyPane method, that checks for each item in the list if we are on its page, and if so will show it.
 * 
 * 
 * FIXES TO DO : Change add remove to private.
 */
class QuestionListEdition extends VBox {
	
	
	//---Controller---//
	new(ControllerFxEdition controller) {
		this.controller = controller
		this.vgrow = Priority.ALWAYS
	}
	//----------------//
	
	//---FX vars---//
	ControllerFxEdition controller
	//-------------//
	
	//---GETTERS/SETTERS---//
	def getController(){
		controller
	}
	//---------------------//
	
	//---Methods---//
	def loadQuestion(Box box, String name,int page,int id,float questionWorth) {
		var item = new QuestionItemEdition(this,box,name,page+1,id);
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
		var item = new QuestionItemEdition(this, box, type,
			controller.pdfManager.currentPdfPageNumber+1);
		addToModel(item)
		add(item)
		this.select(item)
		this.controller.selectQuestion(item)
	}

	def removeQuestion(QuestionItemEdition item) {
		removeFocus
		controller.mainPane.removeZone(item.zone)
		remove(item)
		removeFromModel(item)
	}
	
	def void select(QuestionItemEdition item) {
		for (Node n : children) {
			var question = n as QuestionItemEdition
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
			if (e instanceof QuestionItemEdition) {
				(e as QuestionItemEdition).focus = false
			}
		}
	}

	def void showOnlyPage(int page) {
		for (Node e : children) {
			if (e instanceof QuestionItemEdition) {
				checkAndDisplay(page+1, e as QuestionItemEdition)
			}

		}
	}

	def void checkAndDisplay(int page, QuestionItemEdition item) {
		if (item.page == page) {
			item.zone.isVisible(true)
		} else {
			item.zone.isVisible(false)
		}

	}

	def add(QuestionItemEdition item) {
		this.children.add(item)
	}

	def remove(QuestionItemEdition item) {
		this.children.remove(item)
	}
	
	def clear(){
		controller.selectQuestion(null)
		children.clear
	}
	//-------------//

	//---Model Interactions---//
	def addToModel(QuestionItemEdition item) {
		item.questionId = controller.createQuestion(item.zone.x/controller.maxX, item.zone.y/controller.maxY,item.zone.height/controller.maxY, item.zone.width/controller.maxX) // TODO convert
		updateInModel(item)
	}


	def updateInModel(QuestionItemEdition item) {
		controller.moveQuestion(item.questionId, item.zone.x/controller.maxX, item.zone.y/controller.maxY) 
		controller.resizeQuestion(item.questionId, item.zone.height/controller.maxY,item.zone.width/controller.maxX)
		controller.renameQuestion(item.questionId,item.name)
		controller.changeQuestionWorth(item.questionId,item.scale)
	}

	def removeFromModel(QuestionItemEdition item) {
		controller.removeQuestion(item.questionId)
	}
	//------------------------//
	
	
	

}
