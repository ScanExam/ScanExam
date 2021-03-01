package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.view.fX.Box.BoxType
import java.io.File
import java.util.Arrays
import java.util.LinkedList
import javafx.embed.swing.SwingFXUtils
import javafx.fxml.FXML
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.control.ListView
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import org.apache.logging.log4j.LogManager
import javafx.scene.control.ChoiceBox
import javafx.event.EventHandler
import javafx.scene.control.Label

class ControllerFXCreator {

	EditorAdapterFX editor;
	
	@FXML
	Pane mainPane;
	
	@FXML
	ImageView pdfView;
	
	@FXML
	ListView<HBox> questionList;
	
	@FXML
	ChoiceBox<Integer> pageChoice;
	
	@FXML 
	Label introLabel;
	
	double maxX;
	double maxY;
	
	var logger = LogManager.logger
	enum SelectedTool {
		QUESTION_AREA,
		ID_AREA,
		QR_AREA,
		MOVE_TOOL
		
	}
	

	@FXML 
	def void pressed(){
		
	}
	
	@FXML 
	def void questionAreaPressed(){
		setToQuestionAreaTool
	}
	
	@FXML 
	def void iDAreaPressed(){
		setToIDAreaTool
	}
	@FXML 
	def void qRArearessed(){
		setToQRAreaTool
	}
	@FXML 
	def void movePressed(){
		setToMoveTool
	}
	
	@FXML 
	def void nextPagePressed(){
		nextPage
	}
	
	@FXML
	def void previousPagePressed(){
		previousPage
	}	
	@FXML 
	def void mainMouseEvent(MouseEvent e) {
		chooseMouseAction(e);
	}
	
	
	def void chooseMouseAction(MouseEvent e) {
		switch currentTool {
			case QUESTION_AREA: {
				CreateBox(e);
			}
			case ID_AREA: {
			}
			case QR_AREA: {
			}
			case MOVE_TOOL: {
				MoveImage(e);
			}
		}
	}
	var mouseOriginX = 0d;
	var mouseOriginY = 0d;
	var objectOriginX = 0d;
	var objectOriginY = 0d;
	
	var boxes = new LinkedList<Box>();
	Box currentRectangle = null;
	def void CreateBox(MouseEvent e){
		var mousePositionX = Math.max(FXSettings.BOX_BORDER_THICKNESS,Math.min(e.x,maxX));
		var mousePositionY = Math.max(FXSettings.BOX_BORDER_THICKNESS,Math.min(e.y,maxY));
		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) { //TODO add type checks
			mouseOriginX = mousePositionX
			mouseOriginY = mousePositionY
			var source = e.source as Pane
			currentRectangle = createBox(mousePositionX,mousePositionY);
			currentRectangle.listViewBox.addEventFilter(MouseEvent.MOUSE_CLICKED,new EventHandler<MouseEvent>() {
			
			override handle(MouseEvent event) {
				highlightBox((event.source as ListViewBox).parentBox);
			}
			
		})
			source.children.add(currentRectangle);
			logger.debug("Created Box")
		}
		if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			
			var xDelta = mousePositionX - mouseOriginX;
			var yDelta = mousePositionY - mouseOriginY;
			if (xDelta > 0 ) {
				currentRectangle.width = xDelta;
			}
			else {
				currentRectangle.width = Math.abs(xDelta);
				currentRectangle.x = mouseOriginX - Math.abs(xDelta);
			}
			
			if (yDelta > 0) {
				currentRectangle.height = yDelta ;
			}else {
				currentRectangle.height = Math.abs(yDelta);
				currentRectangle.y = mouseOriginY - Math.abs(yDelta);
				
			}
			
			
		}
		if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
			addBox(currentRectangle);
		}
	}
	
	
	
	
	/**
	 * Used to move around the image in the parent pane
	 */
	def void MoveImage(MouseEvent e) {

		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) { //TODO add type checks
			mouseOriginX = e.screenX
			mouseOriginY = e.screenY
			var source = e.source as Node

			objectOriginX = source.layoutX
			objectOriginY = source.layoutY
			
			mainPane.cursor = Cursor.CLOSED_HAND
		}
		if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			var source = e.source as Node
			source.layoutX = objectOriginX + (e.screenX - mouseOriginX)
			source.layoutY = objectOriginY + (e.screenY - mouseOriginY)
		}
		if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
			mainPane.cursor = Cursor.OPEN_HAND
		}
	}
	/**
	 * Used to zoom in and out the pdf image
	 * 
	 * Using the scale allows the children of the pane to also scale accordingly
	 */
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
	
	
	/**
	 * Setters for the current tool selected
	 */
	var currentTool = SelectedTool.MOVE_TOOL
	def void setToMoveTool(){
		mainPane.cursor = Cursor.OPEN_HAND
		currentTool = SelectedTool.MOVE_TOOL
	}
	def void setToQuestionAreaTool(){
		mainPane.cursor = Cursor.DEFAULT
		currentTool = SelectedTool.QUESTION_AREA
	}
	def void setToIDAreaTool(){
		mainPane.cursor = Cursor.DEFAULT
		currentTool = SelectedTool.ID_AREA
	}
	
	def void setToQRAreaTool(){
		mainPane.cursor = Cursor.DEFAULT
		currentTool = SelectedTool.QR_AREA
	}
	
	/**
	 * returns a new Box with the right type corresponding to the current tool //TODO maybe move to box as a static method
	 */
	var questionCounter = 1;
	def Box createBox(double x,double y){
		switch currentTool {
			case QUESTION_AREA: {
				new Box("Question "+ questionCounter++,editor.presenter.currentPdfPageNumber,BoxType.QUESTION,x,y);
			}
			case ID_AREA: {
				new Box(BoxType.ID,x,y);
			}
			case QR_AREA: {
				new Box(BoxType.QR,x,y);
			}
			case MOVE_TOOL: {
				
			}
		}
	}
	
	/**
	 * notifies the rest of the program to the addition of a new box
	 * 
	 * Called when we finish creating a new box (Mouse release)
	 */
	def addBox(Box box){
		editor.addBox(box);
		questionList.items.add(box.listViewBox)
		boxes.add(box);
	}
	
	def renameBox(Box box){
		
	}
	
	def moveBox(Box box){
		
	}
	
	def resizeBox(Box box){
		
	}
	/**
	 * notifies the rest of the program to the removal of a box
	 */
	def removeBox(Box box) {
		editor.removeBox(box);
	}
	
	/**
	 * load a new pdf to start the creation of a new template
	 */
	@FXML
	def onCreateClick()
	{
		
		var fileChooser = new FileChooser();
		fileChooser.extensionFilters.add(new ExtensionFilter("PDF files",Arrays.asList("*.pdf")));
		fileChooser.initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Documents");
		var file = fileChooser.showOpenDialog(mainPane.scene.window)
		
		if (file !== null) 
		{
			editor.presenter.create(file);
			renderDocument();
		}
		else 
		{
			logger.warn("File not chosen")
		}
		
		
	}
	/**
	 * initialise the choicebox containing all the page numbers of the pdf
	 * to call whenever we load a new pdf into the editor
	 */
	def initPageSelection(){
		pageChoice.items.clear
	}
	/**
	 * feches the current buffered image in the presenter representing the pdf and converts it and loads into the imageview
	 */
	def renderDocument()
	{
		introLabel.visible = false
		val image = editor.presenter.currentPdfPage
		
		
	
		pdfView.image = image
		var fitW = pdfView.fitWidth
		var fitH = pdfView.fitHeight
		if (image.height > image.width) {
			maxY = fitH
			maxX = (image.width / image.height) *  fitW
		}
		else {
			maxY = (image.height / image.width) * fitH - FXSettings.BOX_BORDER_THICKNESS
			maxX = fitW - FXSettings.BOX_BORDER_THICKNESS
		}
		
	
		
	}
	/**
	 * changes the selected page to load and then renders it
	 */
	def selectPage(int pageNumber) {
		 editor.presenter.choosePdfPage(pageNumber);
		renderDocument();
	}
	
	/**
	 * goes to the next page of the current pdf
	 */
	def nextPage(){
		editor.presenter.nextPdfPage();
		renderDocument
		showOnlyPage(editor.presenter.currentPdfPageNumber);
	}
	
	def previousPage(){
		editor.presenter.previousPdfPage();
		renderDocument
		showOnlyPage(editor.presenter.currentPdfPageNumber);
	}
	
	def showOnlyPage(int page) {
		for (Box b : boxes) {
			if (b.pageNumber == page) {
				b.visible = true;
	
			}else {
				b.visible = false;
			}
		}
	}
	/**
	 * Highlights the Box box, called when we click on a box on the listview
	 */
	Box highlightedBox = null;
	def highlightBox(Box box) {
		if (highlightedBox !== null) {
			highlightedBox.focus = false;
		}
		highlightedBox = box;
		highlightedBox.focus = true
	}
	
	def void setEditorAdapterFX(EditorAdapterFX editor) {
		this.editor = editor
	}
}
