

package fr.istic.tools.scanexam.view.fx.graduation

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.utils.ResourcesUtils
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation.SelectedTool
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.geometry.Pos
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.ScrollPane.ScrollBarPolicy
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Region
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager

import static fr.istic.tools.scanexam.view.fx.graduation.HTMLView.*

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
		scrollp.fitToWidth = true;

		itemContainer = new VBox();
		add = new Button(LanguageManager.translate("grader.button.addEntry"));

		add.styleClass.add("InfinityButton")

		editMode = new Button(LanguageManager.translate("grader.button.enterEdit"));
		editMode.styleClass.add("InfinityButton")

		scrollp.content = itemContainer
		
		this.cursor = Cursor.MOVE
		itemContainer.cursor = Cursor.DEFAULT
		add.cursor = Cursor.DEFAULT
		editMode.cursor = Cursor.DEFAULT
		currentPoints.cursor = Cursor.DEFAULT
		maxPoints.cursor = Cursor.DEFAULT
		scrollp.cursor = Cursor.DEFAULT
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
	
	
	/**
	 * Met a jours les information presenter par le grader,
	 * @param la question pour recupere les entries
	 * @param l'etudiant pour recupere les entries selectionne
	 */
	def changeGrader(QuestionItemGraduation qItem, StudentItemGraduation sItem) {
		if (editable) {
			toggleEditMode(false)
		}
		clearDisplay()
		curentQuestion = qItem;
		currentStudent = sItem;
		
		if ( curentQuestion !== null && currentStudent !== null ) {
			maxPoints.text = qItem.worth + "";
	
			// Loads all the gradeEntries from the model
			var ids = controller.getEntryIds(qItem.questionId);
			logger.info("All ids are :"  + ids.size())
			// Finds all the selected entries for this student/question
			var sids = controller.getSelectedEntryIds(qItem.questionId)
			logger.info("selected ids are :"  + sids.size())
			
			for (Integer i : ids) {
				var g = new GradeItem(this);
				g.setItemId(i);
				g.setText(controller.getEntryText(i, qItem.questionId))
				g.setWorth(controller.getEntryWorth(i, qItem.questionId))
				itemContainer.children.add(g);
				if (sids.contains(i)) {
					g.selected = true;
				} else {
					g.selected = false;
				}
				g.leaveEditMode
			}
			updateCurrentPoints
		}
		else {
			logger.warn("The current Question or current Student is null")
		}

	// create each gradeEntry from the model for the question item for the correct student
	}

	/**
	 * Cree une nouvelle grade entry et l'ajoute au modele.
	 * L'entrie est associer a la question courrante
	 * 
	 */
	def createNewGradeEntry() {
		logger.info("Creating new GradeEntry")
		var entry = new GradeItem(this);
		itemContainer.children.add(entry)
		addEntryToModel(entry, controller.questionList.currentItem);
	}
	
	/**
	 * Removes une grade entry et l'enleve du modele.
	 * L'entrie est associer a la question courrante
	 * 
	 */
	def removeGradeEntry(GradeItem item) {
		logger.log(Level.INFO, "Removing GradeEntry")
		itemContainer.children.remove(item);
		removeEntryFromModel(item, controller.questionList.currentItem)
		updateCurrentPoints

	}
	
	def prepForTabChange(){
		if (editable) {
			toggleEditMode(false)
		}
	}
	
	/**
	 * Met a jour l'indicateur des point courrant pour l'etudiant/question courrante
	 */
	def updateCurrentPoints(){
		currentPoints.text = "" + controller.service.getQuestionSelectedGradeEntriesTotalWorth(controller.questionList.currentItem.questionId)
		controller.updateStudentDetails
	}
	
	/**
	 * ajoute un entry au modele d'edition
	 */
	def void addEntryToModel(GradeItem item, QuestionItemGraduation qItem) {
		item.itemId = controller.addEntry(qItem.questionId, item.getText,Float.parseFloat(item.getWorth));
	}

	/**
	 * Modifier un entry dans le modele d'edition
	 */
	def updateEntryInModel(GradeItem item, QuestionItemGraduation qItem) {
		logger.log(Level.INFO, "Updating GradeEntry")
		controller.modifyEntry(qItem.questionId, item.itemId, item.getText,Float.parseFloat(item.getWorth));
	}
	
	def updateEntryWorthInModel(){
		
	}
	
	/**
	 * Supprime un entry du modele d'edition
	 */
	def removeEntryFromModel(GradeItem item, QuestionItemGraduation qItem) {
		controller.removeEntry(qItem.questionId, item.id);
	}
	
	/**
	 * Ajoute les point d'un entry a un etudiant pour une question
	 * @param l'entry a ajouter
	 */
	def addPoints(GradeItem item) {
		logger.info("Adding points for Student ID :" + controller.studentList.currentItem.studentId + ", for Questions ID :" + controller.questionList.currentItem.questionId + ", for Entry ID :" +  item.id)
		var over = controller.applyGrade(controller.questionList.currentItem.questionId, item.id)
		if (over) {
			item.displaySuccess
			updateCurrentPoints
		}else {
			item.displayError
			item.selected = false
		}
		
	}

	/**
	 * Supprime les point d'un entry a un etudiant pour une question
	 * @param l'entry a ajouter
	 */
	def removePoints(GradeItem item) {
		logger.log(Level.INFO, "Removing points for Student ID :" + controller.studentList.currentItem.studentId + ", for Questions ID :" + controller.questionList.currentItem.questionId + ", for Entry ID :" +  item.id)
		
		var over = controller.removeGrade(controller.questionList.currentItem.questionId, item.id)
		if (over) {
			item.displaySuccess
			updateCurrentPoints
		}else {
			item.displayError
			item.selected = true
		}
	}
	
	/**
	 * Met a zero le grader
	 */
	def clearDisplay() {
		itemContainer.children.clear();
	}
	
	/**
	 * Toggles between "editable" states, when edtiable, we can add, remove and modify the worth of a grader entry in the model
	 */
	def toggleEditMode(boolean active) {
		editable = active
		if (active) {
			editMode.text = LanguageManager.translate("grader.button.leaveEdit")
			for (Node n : itemContainer.children) {
				(n as GradeItem).enterEditMode
			}
			this.children.add(add)
		} else {
			editMode.text = LanguageManager.translate("grader.button.enterEdit")
			for (Node n : itemContainer.children) {
				(n as GradeItem).leaveEditMode
				updateEntryInModel((n as GradeItem), controller.questionList.currentItem);
			}
			this.children.remove(add)
		}
	}
	
	/**
	 * interacts with the checkbox for the entry in the index in params
	 * @param the index of the grade entry
	 */
	def interactUsingIndex(int index){
		if (index < 1) {
			logger.info("Cant select an entry below 1") 
			return
		}
		if (index > itemContainer.children.size) {
			logger.info("Cant select entry with index :" + index + ", there is only " + itemContainer.children.size + " entries in the grader") 
			return
		}
		var item  = itemContainer.children.get(index-1) as GradeItem
		if (!item.checkDisabled) {
			item.selected =  !item.selected
			item.checkBoxUsed
		}
	}

	static class GradeItem extends VBox {
		new(Grader grader) {
			this.grader = grader
			topRow = new HBox()
			

			/* 
			textArea = new TextArea(text.text)
			textArea.wrapText = true
			textArea.maxWidth = 130
			textArea.margin = new Insets(10, 0, 0, 10)
			*/
			
			stackPane = new StackPane()
			
			text = LanguageManager.translate("grader.defaultText")
			
			webView = new WebView()
			webEngine = webView.getEngine();
			webEngine.loadContent(text);
			//webView.maxWidth = 130
			//webView.maxHeight = 130
			//webView.setMaxSize(130, 110);	
			webView.setPrefSize(Region.USE_COMPUTED_SIZE,Region.USE_COMPUTED_SIZE)
    		webView.setMinSize(100,100)
			
			stackPane.getChildren().add(webView)

			check = new CheckBox()

			worth = new Label("1");
			worthField = new TextField(worth.text)
			worthField.styleClass.add("mytext-field")
			remove = new Button(LanguageManager.translate("grader.button.removeEntry"));
			topRow.children.addAll(check, worthField)
			this.children.addAll(topRow, stackPane,remove)
			
			topRow.styleClass.add("GradeItemTopRow")
			webView.styleClass.add("WebView")
			this.styleClass.add("GradeItem")
			this.alignment = Pos.CENTER
			setupEvents
		}

		int id;
		String text;
		HBox topRow
		Label worth;
		CheckBox check;
		Grader grader;
		StackPane stackPane
		WebView webView
		WebEngine webEngine
		TextField worthField;
		Button remove;

		def getText() {
			text
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
		 * Changes the color of the checkbox to represent a unsuccesfull action
		 */
		def displayError(){
			check.styleClass.remove("goodCheckBox")
			check.styleClass.add("badCheckBox")
		}
		
		/**
		 * Changes the color of the checkbox to represent a successfull action
		 */
		def displaySuccess(){
			check.styleClass.remove("badCheckBox")
			check.styleClass.add("goodCheckBox")
		}
		
		/**
		 * Changes the color of the checkbox to represent a default state
		 */
		def displayDefault(){
			check.styleClass.remove("badCheckBox")
			check.styleClass.remove("goodCheckBox")
		}

		/**
		 * Change le text modifié par le HTML Editor
		 */
		def setText(String text) {
			//webEngine = webView.getEngine();
			this.text = text;
			webEngine.loadContent(text);
		}
		
		/**
		 * Changes the text present in as a description of this entry
		 * and updates it in the model
		 */
		def changeText(String text){
			setText(text)
			grader.updateEntryInModel(this,grader.controller.questionList.currentItem)
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
		
		def getCheckDisabled()
		{
			check.disabled
		}
		
		
		/**
		 * Enters an "Editable" state, where the worth label is replace by a text field, and the checkbox is disabled
		 */
		def enterEditMode() {
			topRow.children.remove(worth)
			topRow.children.add(worthField);
			check.disable = true
			//this.children.remove(text);
			this.children.add(remove)
		
		}
		
		/**
		 * Leaves an "Editable" state, where the worth label is replace by a text field, and the checkbox is disabled
		 */
		def leaveEditMode() {
			topRow.children.remove(worthField)
			this.children.remove(remove)
			topRow.children.add(worth);
			check.disable = false
			worth.text = worthField.text
			//this.children.remove(textArea);
			//this.children.add(text)
		}

		/**
		 * checks the state of the checkbox and updates the point counter
		 */
		def checkBoxUsed(){
			if (check.selected) {
				grader.addPoints(this)
			} else {
				grader.removePoints(this)
			}
		}

		def setupEvents() {
			val me = this
			check.onAction = new EventHandler<ActionEvent>() {

				override handle(ActionEvent event) {
					checkBoxUsed
				}

			}
			remove.onAction = new EventHandler<ActionEvent>() {
				override handle(ActionEvent event) {
					grader.removeGradeEntry(me)
				}

			}
			/* Quand on clique sur un texte du barême */
			webView.onMouseClicked = new EventHandler<MouseEvent>() {
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
			HTMLView.item = this
			var stage = new Stage();
			stage.initStyle(StageStyle.DECORATED);
			stage.setResizable(false)
			stage.setTitle("Editeur HTML");
			// layout = ClassLoader.getSystemResource("resources_utils/HTML.FXML");
			var inputLayout = ResourcesUtils.getInputStreamResource("viewResources/HTML.fxml")
			var fxmlLoader = new FXMLLoader
			fxmlLoader.setResources(LanguageManager.currentBundle)
			var root = fxmlLoader.load(inputLayout);
			var scene = new Scene(root, 640, 360);
			stage.icons.add(new Image(ResourcesUtils.getInputStreamResource("logo.png")))
			stage.setScene(scene);
			HTMLView.stage = stage
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
		this.onMousePressed = [event |
			{ controller.currentTool = SelectedTool.MOVE_GRADER_TOOL;
			
		}]

	}
}
