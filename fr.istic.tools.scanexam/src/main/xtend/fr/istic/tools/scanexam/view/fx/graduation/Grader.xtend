package fr.istic.tools.scanexam.view.fx.graduation

import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.ScrollPane.ScrollBarPolicy
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.input.MouseEvent
import javafx.stage.Stage
import fr.istic.tools.scanexam.utils.ResourcesUtils
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.input.MouseButton
import fr.istic.tools.scanexam.view.fx.editor.HTMLView
import javafx.stage.StageStyle
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Level

class Grader extends VBox {

	static val logger = LogManager.logger

	new(ControllerFxGraduation controller) {
		this.controller = controller

		currentPoints = new Label("0");
		var slash = new Label("/");
		maxPoints = new Label("0");
		var pointsBox = new HBox();
		pointsBox.children.addAll(currentPoints, slash, maxPoints);

		editable = false;

		var scrollp = new ScrollPane();

		scrollp.hbarPolicy = ScrollBarPolicy.NEVER
		scrollp.styleClass.add("GradeList")

		itemContainer = new VBox();
		add = new Button("Add new Grade Entry");

		add.styleClass.add("InfinityButton")

		editMode = new Button("Toggle Editable");
		editMode.styleClass.add("InfinityButton")

		scrollp.content = itemContainer
		this.children.addAll(pointsBox, scrollp, editMode);
		this.prefWidth = 170;
		this.maxHeight = 500;
		this.prefHeight = USE_COMPUTED_SIZE
		this.styleClass.add("Grader")
		setupEvents
	}

	Label currentPoints;
	Label maxPoints;
	Button add;
	Button editMode;
	VBox itemContainer;
	ControllerFxGraduation controller;
	boolean editable;
	
	QuestionItemGraduation curentQuestion;
	StudentItemGraduation currentStudent;
	def changeGrader(QuestionItemGraduation qItem, StudentItemGraduation sItem) {
		clearDisplay()
		curentQuestion = qItem;
		currentStudent = sItem;
		
		if ( curentQuestion !== null && currentStudent !== null ) {
			maxPoints.text = qItem.worth + "";
	
			// Loads all the gradeEntries from the model
			var ids = controller.getEntryIds(qItem.questionId);
			// Finds all the selected entries for this student/question
			var sids = controller.getSelectedEntryIds(qItem.questionId)
			for (Integer i : ids) {
				var g = new GradeItem(this);
				g.setItemId(i);
				g.setText(controller.getEntryText(i, qItem.questionId))
				g.setWorth(controller.getEntryWorth(i, qItem.questionId))
				itemContainer.children.add(g);
				if (sids.contains(i)) {
					g.selected = true;
					addPointsOf(g);
				} else {
					g.selected = false;
				}
				g.leaveEditMode
			}
		}
		else {
			logger.warn("The current Question or current Student is null")
		}

	// create each gradeEntry from the model for the question item for the correct student
	}

	def createNewGradeEntry() {
		logger.info("Creating new GradeEntry")
		var entry = new GradeItem(this);
		itemContainer.children.add(entry)
		addEntryToModel(entry, controller.questionList.currentItem);
	}

	def removeGradeEntry(GradeItem item) {
		logger.log(Level.INFO, "Removing GradeEntry")
		itemContainer.children.remove(item);
		if (item.selected) {
			removePointsOf(item)
		}
		removeEntryFromModel(item, controller.questionList.currentItem)

	}

	// used to add and rmeove the points of the selected items to the current points label
	def addPointsOf(GradeItem item) {
		/*var current = Float.parseFloat(currentPoints.text);
		 * current = current +  Float.parseFloat(item.getWorth)
		 * */
		currentPoints.text = "FIX"
	}

	def removePointsOf(GradeItem item) {
		/*var current = Float.parseFloat(currentPoints.text);
		 * current = current -  Float.parseFloat(item.getWorth)
		 * 
		 */
		currentPoints.text = "FIX"
	}

	// ---Model intecations 
	def void addEntryToModel(GradeItem item, QuestionItemGraduation qItem) {
		item.itemId = controller.addEntry(qItem.questionId, item.getText,
			Float.parseFloat(item.getWorth));
	}

	def updateEntryInModel(GradeItem item, QuestionItemGraduation qItem) {
		controller.modifyEntry(qItem.questionId, item.itemId, item.getText,
			Float.parseFloat(item.getWorth));
	}

	def removeEntryFromModel(GradeItem item, QuestionItemGraduation qItem) {
		controller.removeEntry(qItem.questionId, item.id);
	}

	def addPoints(GradeItem item) {
		logger.info("Adding points for Student ID :" + controller.studentList.currentItem.studentId + ", for Questions ID :" + controller.questionList.currentItem.questionId + ", for Entry ID :" +  item.id)
		addPointsOf(item)
		controller.applyGrade(controller.questionList.currentItem.questionId, item.id)
		
	}

	def removePoints(GradeItem item) {
		logger.log(Level.INFO, "Removing points for Student ID :" + controller.studentList.currentItem.studentId + ", for Questions ID :" + controller.questionList.currentItem.questionId + ", for Entry ID :" +  item.id)
		removePointsOf(item)
		controller.removeGrade(controller.questionList.currentItem.questionId, item.id)
	}

	def clearDisplay() {
		itemContainer.children.clear();
	}

	def toggleEditMode(boolean active) {
		editable = !editable
		if (active) {
			for (Node n : itemContainer.children) {
				(n as GradeItem).enterEditMode
			}
			this.children.add(add)
		} else {
			for (Node n : itemContainer.children) {
				(n as GradeItem).commitChanges
				updateEntryInModel((n as GradeItem), controller.questionList.currentItem);
			}
			this.children.remove(add)
		}
	}

	static class GradeItem extends VBox {
		new(Grader grader) {
			this.grader = grader
			topRow = new HBox()
			text = new Label(
				"This is a test Grate entry name, here you will be able to add a description of this entry, and eventually have some latec/html")
			text.wrapText = true
			text.maxWidth = 130
			text.margin = new Insets(0, 0, 0, 10)

			textArea = new TextArea(text.text)
			textArea.wrapText = true
			textArea.maxWidth = 130
			textArea.margin = new Insets(10, 0, 0, 10)

			check = new CheckBox()

			worth = new Label("5.0");
			worth.padding = new Insets(0, 0, 0, 10)

			worthField = new TextField(worth.text)
			worthField.padding = new Insets(0, 0, 0, 10)
			worthField.maxWidth = 25;
			worthField.styleClass.add("mytext-field")

			remove = new Button("Remove entry");

			this.margin = new Insets(0, 0, 10, 0)
			topRow.children.addAll(check, worthField, remove)
			this.children.addAll(topRow, textArea)

			setupEvents
		}

		int id;
		HBox topRow
		Label text;
		Label worth;
		CheckBox check;
		Grader grader;
		TextArea textArea;
		TextField worthField;
		Button remove;

		def getText() {
			text.text
		}

		def getWorth() {
			worth.text
		}

		def getItemId() {
			id
		}

		def setItemId(int id) {
			this.id = id;
		}

		/**
		 * Change le text modifié par le HTML Editor
		 */
		def setText(String text) {
			this.text.text = text
			this.textArea.text = text
		}

		def setWorth(float worth) {
			this.worth.text = worth + ""
			this.worthField.text = worth + ""
		}

		def setSelected(Boolean b) {
			check.selected = b;
		}

		def getSelected() {
			check.selected
		}

		def enterEditMode() {
			topRow.children.remove(worth)
			topRow.children.add(worthField);
			topRow.children.add(remove)
			this.children.remove(text);
			this.children.add(textArea)
		}

		def leaveEditMode() {
			topRow.children.remove(worthField)
			topRow.children.remove(remove)
			topRow.children.add(worth);
			this.children.remove(textArea);
			this.children.add(text)
		}

		def commitChanges() {
			text.text = textArea.text
			worth.text = worthField.text
			leaveEditMode
		}

		def setupEvents() {
			val me = this
			check.onAction = new EventHandler<ActionEvent>() {

				override handle(ActionEvent event) {
					if (check.selected) {
						grader.addPoints(me)
					} else {
						grader.removePoints(me)
					}
				}

			}
			remove.onAction = new EventHandler<ActionEvent>() {
				override handle(ActionEvent event) {
					grader.removeGradeEntry(me)
				}

			}
			/* Quand on clique sur un texte du barême */
			text.onMouseClicked = new EventHandler<MouseEvent>() {
				override handle(MouseEvent event) {
					if (!HTMLView.isHTMLEditorOpen) {
						if (event.getButton().equals(MouseButton.PRIMARY)) {
							if (event.getClickCount() == 2) {
								renderHTMLView
							}
						}
					}
				}

			}
		}

		/**
		 * Render the HTML editor
		 */
		def renderHTMLView() {
			HTMLView.isHTMLEditorOpen = true
			var stage = new Stage();
			stage.initStyle(StageStyle.DECORATED);
			stage.setTitle("Editeur HTML");
			// layout = ClassLoader.getSystemResource("resources_utils/HTML.FXML");
			var inputLayout = ResourcesUtils.getInputStreamResource("viewResources/HTML.FXML")
			var fxmlLoader = new FXMLLoader
			var root = fxmlLoader.load(inputLayout);
			var scene = new Scene(root, 640, 480);
			stage.setScene(scene);
			stage.show();
			stage.setOnHiding(event|HTMLView.isHTMLEditorOpen = false)
		}

	}

	def setupEvents() {
		add.onAction = new EventHandler<ActionEvent>() {

			override handle(ActionEvent event) {
				createNewGradeEntry
			}

		}

		editMode.onAction = new EventHandler<ActionEvent>() {

			override handle(ActionEvent event) {
				toggleEditMode(!editable);
			}

		}

	}
}
