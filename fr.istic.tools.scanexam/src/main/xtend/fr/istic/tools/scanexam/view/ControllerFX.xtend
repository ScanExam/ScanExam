package fr.istic.tools.scanexam.view

import java.util.Objects
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.input.MouseEvent
import java.io.IOException
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.layout.HBox
import javafx.scene.control.Label
import javafx.scene.control.SelectionMode
import javafx.scene.image.ImageView
import javafx.geometry.Rectangle2D
import javafx.scene.input.ScrollEvent
import javafx.scene.Node
import javafx.fxml.FXML
import javafx.scene.Scene
import javafx.scene.input.KeyEvent
import javafx.scene.input.KeyCode
import javafx.scene.control.ScrollPane
import java.util.List
import java.util.ArrayList
import java.util.LinkedList

/**
 * Class used by the JavaFX library as a controller for the view. 
 * @author Benjamin Danlos
 */
class ControllerFX {

	/**
	 * High level Controllers to access the Presenters
	 */
	ControllerVueCreation controllerCreation
	ControllerVueCorrection controllerCorrection

	/**
	 * setter for the ControllerVueCreation attribute
	 * @param {@link ControllerVueCreation} controller instance of ControllerVueCreation (not null) 
	 */
	def setControllerVueCreation(ControllerVueCreation controller) {
		Objects.requireNonNull(controller)
		controllerCreation = controller
	}

	/**
	 * @return current {@link ControllerVueCreation} 
	 */
	def getControllerVueCreation() {
		controllerCreation
	}

	/**
	 * setter for the ControllerVueCorrection attribute
	 * @param {@link ControllerVueCorrection} controller instance of ControllerVueCorrection (not null) 
	 */
	def setControllerVueCorrection(ControllerVueCorrection controller) {
		Objects.requireNonNull(controller)
		controllerCorrection = controller
	}

	/**
	 * @return current {@link ControllerVueCorrection} 
	 */
	def getControllerVueCorrection() {
		controllerCorrection
	}

	boolean botShow = false;
	@FXML
	public Pane topPane;
	@FXML
	public Button topButtonHidden;
	@FXML
	public Button topButtonActive;
	@FXML
	public Button botButtonHidden;
	@FXML
	public Button botButtonActive;
	@FXML
	public Pane bottomPane;
	@FXML
	public Pane imagePane;
	@FXML
	public Pane parentPane;
	@FXML
	public ListView leftList;
	@FXML
	public ListView rightList;
	@FXML
	public ImageView imview;

	public ScrollPane scrollMain;

	public ScrollPane scrollBis;
	
	public VBox studentDetails;
	
	public VBox questionDetails;

	// ***********************//
	// ***** UI CONTROLS *****//
	// ***********************//
	/**
	 * Toggles the visibility of the bottom window
	 */
	def void toggleBottom() throws IOException {
		bottomPane.setVisible(!botShow);
		botButtonHidden.setVisible(botShow);
		botShow = !botShow;
	}

	/**
	 * Used to resize the window containing the corrected exam
	 */
	def void dragBottom(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			bottomPane.setPrefHeight(
				Math.max(0,
					Math.min(bottomPane.getScene().getHeight() - 100,
						bottomPane.getScene().getHeight() - event.getSceneY())));

		}
	}

	var mouseOriginX = 0d;
	var mouseOriginY = 0d;
	var objectOriginX = 0d;
	var objectOriginY = 0d;

	@FXML
	def void MoveImage(MouseEvent e) {

		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
			println("Starting to move")
			mouseOriginX = e.screenX
			mouseOriginY = e.screenY
			var source = e.source as Node
			println(source)
			objectOriginX = source.layoutX
			objectOriginY = source.layoutY
		}
		if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			println("moving")
			var source = e.source as Node

			source.layoutX = objectOriginX + (e.screenX - mouseOriginX)
			source.layoutY = objectOriginY + (e.screenY - mouseOriginY)
			println(source + " " + source.layoutX + " " + source.layoutY + " " + source.scaleX)
		}
	}

	@FXML
	def void ZoomImage(ScrollEvent e) {
		var source = e.source as Node
		if (e.deltaY > 0) {
			source.scaleX = source.scaleX * 0.95
			source.scaleY = source.scaleY * 0.95
		} else {
			source.scaleX = source.scaleX * 1.05
			source.scaleY = source.scaleY * 1.05
		}
	}

	/* 
	 * var mouseOriginX = 0d;
	 * var mouseOriginY = 0d;
	 * var objectOriginX = 0d;
	 * var objectOriginY = 0d;
	 * 
	 * def void MoveImage(MouseEvent e) {
	 * 	println("trying to move")
	 * 	if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
	 * 		mouseOriginX = e.x
	 * 		mouseOriginY = e.y
	 * 		var source =  e.source as ImageView
	 * 		var vp = source.viewport
	 * 		objectOriginX = vp.minX
	 * 		objectOriginY = vp.minY
	 * 	}
	 * 	if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
	 * 		var source =  e.source as ImageView
	 * 		var vp = source.viewport
	 * 		var rect = new Rectangle2D(objectOriginX - (e.x - mouseOriginX),objectOriginY - (e.y - mouseOriginY),vp.height,vp.width )
	 * 		source.viewport = rect
	 * 	}
	 * }
	 * 
	 * def void ZoomImage(ScrollEvent e) {
	 * 	var source = e.source as ImageView
	 * 	if (e.deltaY > 0) {
	 * 		var vp = source.viewport
	 * 		source.viewport = new Rectangle2D(vp.minX,vp.minY,vp.height * 0.95,vp.width*0.95)
	 * 	}
	 * 	
	 * 	if (e.deltaY < 0) {
	 * 		var vp = source.viewport
	 * 		source.viewport = new Rectangle2D(vp.minX,vp.minY,vp.height * 1.05,vp.width*1.05)
	 * 	}
	 * }
	 * 
	 */
	// ***********************//
	// * PRESENTER CONTROLS **//
	// ***********************//
	/**
	 * Called when a <b>save</b> button is pressed
	 */
	@FXML
	def void savePressed() {
		println("Saving method");
	}

	/**
	 * Called when a <b>save a</b> button is pressed
	 */
	@FXML
	def void saveAsPressed() {
		println("Saving as method");
	}

	/**
	 * Called when a <b>load</b> button is pressed
	 */
	@FXML
	def void loadPressed() {
		// TODO TEST CONTENT REMOVE FOR PROD
		println("Load method");
		initQuestions(10);
		initStudents(10)
		setKeybinds
	// TODO TEST CONTENT REMOVE FOR PROD
	}

	/**
	 * Called when a <b>import</b> button is pressed
	 */
	@FXML
	def void importPressed() {
		println("Import method");
	}

	/**
	 * Called when a <b>export</b> button is pressed
	 */
	@FXML
	def void exportPressed() {
		println("Export method");
	}

	/**
	 * Called when a <b>next question</b> button is pressed
	 */
	@FXML
	def void nextQuestionPressed() {
		println("Next question method");
		selectedQuestion = rightList.selectionModel.selectedIndex + 1
	}

	/**
	 * Called when a <b>previous question pressed</b> button is pressed
	 */
	@FXML
	def void prevQuestionPressed() {
		println("Previous question method");
		selectedQuestion = rightList.selectionModel.selectedIndex - 1
	}

	/**
	 * Called when a <b>next student</b> button is pressed
	 */
	@FXML
	def void nextStudentPressed() {
		println("Next student method");
	}

	/**
	 * Called when a <b>previous student</b> button is pressed
	 */
	@FXML
	def void prevStudentPressed() {
		println("Previous student method");
	}

	@FXML
	def void addBaremeList() {
	}

	@FXML
	def void addQuestionList() {
	}

	def void zoomTest() {
		setZoomArea(0, 0, 100, 200)
	}

	@FXML
	def void setZoomArea(int x, int y, int height, int width) {
		/*imagePane.scaleX=1;
		 * imagePane.scaleY=1;
		 * var pX = parentPane.width;
		 * var ratio = pX/width;
		 * imagePane.scaleX = ratio
		 * imagePane.scaleY = ratio
		 * 
		 * var posX=parentPane.height/2 - imagePane.height/2
		 * imagePane.layoutX = posX * ratio
		 * 
		 * 
		 * var PosY=parentPane.width/2 - imagePane.width/2
		 imagePane.layoutY = PosY * ratio*/
		var newrect = new Rectangle2D(100, 300, 400, 100);
		imview.viewport = newrect
	}

	@FXML
	def void resetPosition() {
		imagePane.scaleX = 1;
		imagePane.scaleY = 1;
		imagePane.layoutX = 0;
		imagePane.layoutY = 0;
		imview.viewport = null;
	}
	
	
	//---------------------------------//
	
	List<Question> questions;
	def void initQuestions(int nbQuestion) {//TODO complete, called on load of a new exam template
		questions = new LinkedList()
		for (var i = 0; i < nbQuestion; i++) {
			var number = i + 1;
			var q = new Question(number + "");
			questions.add(q);
			rightList.items.add(q.label)
		}
	}

	
	def void initStudents(int nbStudents) {//TODO complete, called on load of a new exam template
	}

	def void selectedQuestion() {
	}

	def void setSelectedQuestion(int selectedQuestion) {//Called by presenter when we change the selected question, maybe we should get details on the question on this call and not stock in list
		var index = selectedQuestion;
		if (index < 0 ) {
			index = questions.size-1;
		}
		if (index >= questions.size ) {
			index = 0;
		}
		
		rightList.selectionModel.select(index);
		questionDetails.children.clear
		if (rightList.selectionModel.selectedItem !== null) {
			questionDetails.children.add(questions.get(index).details)
		}
	}

	static class Question extends Label {
		int id;
		String name;
		double x;
		double y;
		double h;
		double w;
		List<Integer> bareme;
		new(String name) {
			super(name)
			this.name = name;
			id = 0;
			x = 0;
			y = 0;
			h = 0;
			w = 0;
			bareme = new ArrayList();
		}
		
		def Label getLabel() {
			return new Label("Question :" + name);
		}
		
		def VBox getDetails() {
			var container = new VBox();
			container.children.add(new Label("Question : " + name))
			container.children.add(new Label("ID :" + id))
			container.children.add(new Label("x:"+x+" y:"+y+" h:"+h+" w:"+w))
			container.children.add(new Label("Bareme: " + bareme))
			return container;
			
		}
		
	}
	
	
	static class StudentItem extends Label {
		int id;
		new(int s,ControllerFX c) {
			super("Student: " + s)
			id = s;
		}

	}
	def void Binds(Node n){
		n.setOnKeyPressed([ event |
			{
				switch event.code {
				case KeyCode.RIGHT : nextQuestionPressed
				case KeyCode.LEFT : prevQuestionPressed
				case KeyCode.UP : nextStudentPressed
				case KeyCode.DOWN : prevStudentPressed
				}
				event.consume
			}
		])
	}
	def void setKeybinds() {
		var s = imagePane.scene
		s.setOnKeyPressed([ event |
			{
				switch event.code {
				case KeyCode.RIGHT : nextQuestionPressed
				case KeyCode.LEFT : prevQuestionPressed
				case KeyCode.UP : nextStudentPressed
				case KeyCode.DOWN : prevStudentPressed
				}
				event.consume
			}
		])
		Binds(scrollMain);
		Binds(scrollBis);
	}
}
