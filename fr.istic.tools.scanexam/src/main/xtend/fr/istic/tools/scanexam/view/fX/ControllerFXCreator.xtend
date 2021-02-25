package fr.istic.tools.scanexam.view.fX;

import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.Pane
import javafx.scene.shape.Rectangle
import org.apache.logging.log4j.LogManager
import fr.istic.tools.scanexam.view.fX.ControllerFXCreator.Box.BoxType
import javafx.scene.Cursor

class ControllerFXCreator {
	
	
	
	@FXML
	var Pane mainPane;
	
	
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
			mouseOriginX = e.screenX
			mouseOriginY = e.screenY
			var source = e.source as Pane
			currentRectangle = createBox(e.x,e.y);
			
			source.children.add(currentRectangle);
			logger.debug("Created Box")
		}
		if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			currentRectangle.height = e.screenY - mouseOriginY;
			currentRectangle.width = e.screenX - mouseOriginX;
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
	
	static class Box extends Rectangle {
		
		new(BoxType type) {
			super(0,0,0,0);
			name = "box"
			this.type = type
			
		}
		new(BoxType type,double x, double y) {
			super(x,y,0,0);
			name = "box"
			this.type = type
			
		}
		enum BoxType {
			QUESTION,
			ID,
			QR
		}
		BoxType type;
		String name;
	}
}
