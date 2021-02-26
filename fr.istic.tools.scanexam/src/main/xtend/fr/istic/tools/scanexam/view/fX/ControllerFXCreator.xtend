package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.view.fX.Box.BoxType
import javafx.fxml.FXML
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.Pane
import org.apache.logging.log4j.LogManager
import javafx.stage.FileChooser
import java.util.Arrays
import javafx.stage.FileChooser.ExtensionFilter
import java.io.File
import java.awt.event.ActionEvent
import java.awt.Event

class ControllerFXCreator {
	
	
	
	EditorAdapterFX editor;
	
	@FXML
	var Pane mainPane;
	
	@FXML
	var ImageView PDFView;
	
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
	Box currentRectangle = null;
	def void CreateBox(MouseEvent e){
		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) { //TODO add type checks
			mouseOriginX = e.x
			mouseOriginY = e.y
			var source = e.source as Pane
			currentRectangle = createBox(e.x,e.y);
			
			source.children.add(currentRectangle);
			logger.debug("Created Box")
		}
		if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			
			var xDelta = e.x - mouseOriginX;
			var yDelta =  e.y - mouseOriginY;
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
	}
	
	
	
	
	
	def void MoveImage(MouseEvent e) {

		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) { //TODO add type checks
			mouseOriginX = e.screenX
			mouseOriginY = e.screenY
			var source = e.source as Node
			println(source)
			objectOriginX = source.layoutX
			objectOriginY = source.layoutY
		}
		if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			var source = e.source as Node
			source.layoutX = objectOriginX + (e.screenX - mouseOriginX)
			source.layoutY = objectOriginY + (e.screenY - mouseOriginY)
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
	
	var currentTool = SelectedTool.MOVE_TOOL;
	def void setToMoveTool(){
		mainPane.cursor = Cursor.OPEN_HAND
		currentTool = SelectedTool.MOVE_TOOL;
	}
	def void setToQuestionAreaTool(){
		mainPane.cursor = Cursor.DEFAULT
		currentTool = SelectedTool.QUESTION_AREA;
	}
	def void setToIDAreaTool(){
		mainPane.cursor = Cursor.DEFAULT
		currentTool = SelectedTool.ID_AREA;
	}
	
	def void setToQRAreaTool(){
		mainPane.cursor = Cursor.DEFAULT
		currentTool = SelectedTool.QR_AREA;
	}
	
	def Box createBox(double x,double y){
		switch currentTool {
			case QUESTION_AREA: {
				new Box(BoxType.QUESTION,x,y);
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
	
	def renderDocument()
	{
		
	}
	
	
	
	def DisplayPDF(Image pdf) {
		PDFView.setImage(pdf);
	}
	
	
	def void setEditorAdapterFX(EditorAdapterFX editor) {
		this.editor = editor;
	}
}
