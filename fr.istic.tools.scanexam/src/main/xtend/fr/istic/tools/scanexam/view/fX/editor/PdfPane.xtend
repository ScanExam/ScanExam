package fr.istic.tools.scanexam.view.fX.editor

import javafx.scene.layout.Pane
import javafx.scene.image.ImageView
import javafx.scene.image.Image
import javafx.scene.input.MouseEvent
import javafx.event.EventHandler
import javafx.scene.input.ScrollEvent

class PdfPane extends Pane {
	
	//---Controller---//
	new(ControllerFXEditor controller){
		super()
		this.controller = controller
		imageView = new ImageView()
		imageView.fitHeight =  600;
		imageView.fitWidth =  600;
		imageView.preserveRatio = true
		this.children.add(imageView)
		setupEvents
	}
	//----------------//
	
	//---FX vars---//
	ImageView imageView
	Image currentImage
	ControllerFXEditor controller
	//-------------//
	
	//---GETTERS/SETTERS---//
	def setImage(Image image){
		imageView.image = image
		currentImage = image
	}
	
	def getImageViewHeight(){
		if (currentImage.height > currentImage.width) {
			return imageView.fitHeight
		}
		return (currentImage.height / currentImage.width) * imageView.fitHeight
		
	}
	
	def getImageViewWidth(){
		if (currentImage.height > currentImage.width)
		{
			return (currentImage.width / currentImage.height) * imageView.fitWidth
		}else {
			imageView.fitWidth
		}
	}
	//---------------------//
	
	//---Methods---//
	def addZone(Box toAdd){
		this.children.add(toAdd)
	}
	
	def removeZone(Box toRemove){
		this.children.remove(toRemove)
	}
	//-------------//
	
	//---Setups---//
	def setupEvents(){
		onMousePressed = new EventHandler<MouseEvent>(){
			
			override handle(MouseEvent event) {
				controller.mainMouseEvent(event)
			}
			
		}
		onMouseDragged = new EventHandler<MouseEvent>(){
			
			override handle(MouseEvent event) {
				controller.mainMouseEvent(event)
			}
			
		}
		onMouseReleased = new EventHandler<MouseEvent>(){
			
			override handle(MouseEvent event) {
				controller.mainMouseEvent(event)
			}
			
		}
		onScroll = new EventHandler<ScrollEvent>(){
			
			override handle(ScrollEvent event) {
					controller.ZoomImage(event)
			}
			
		}
	}
	//------------//
}