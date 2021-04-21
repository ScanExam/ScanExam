package fr.istic.tools.scanexam.view.fx.graduation

import javafx.event.EventHandler
import javafx.geometry.Rectangle2D
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.Pane
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation.SelectedTool

class PdfPaneWithAnotations extends Pane {
	
	new(ControllerFxGraduation controller){
		this.controller = controller
		imageView = new ImageView()
		imageView.fitHeight =  600;
		imageView.fitWidth =  600;
		imageView.preserveRatio = true
		this.children.add(imageView)
		this.styleClass.add("mainPane")
		setupEvents
	}
	
	ControllerFxGraduation controller;
	ImageView imageView
	Image currentImage
	
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
	
	def addNewAnotation(double x, double y){
		var anot = new TextAnotation(x,y,100,50,"New Anotation",this)
		this.children.addAll(anot.allParts)
	}
	
	def addAnotation(double x, double y, double height, double width, String text) {
		var anot = new TextAnotation(x,y,height,width,text,this)
		this.children.addAll(anot.allParts)
	}
	
	def removeAnotation(TextAnotation anotation){
		children.remove(anotation.allParts)
	}
	
	def removeAllAnotations(){
		children.clear
		children.add(imageView)
	}
	
	def zoomTo(double x, double y, double h, double w){
		imageView.viewport = new Rectangle2D(x,y,w,h)
	}
	
	def unZoom(){
		imageView.viewport = null;
	}
	
	def handleMoveAnnotation(TextAnotation anot, MouseEvent e) {
		controller.currentTool = SelectedTool.MOVE_ANOTATION_TOOL
		controller.currentAnotation = anot;
	}
	
	def handleMovePointer(TextAnotation anot, MouseEvent e) {
		controller.currentTool = SelectedTool.MOVE_POINTER_TOOL
		controller.currentAnotation = anot;
	}
	
	
	
	
	
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
}