package fr.istic.tools.scanexam.view.fx.graduation

import javafx.event.EventHandler
import javafx.geometry.Rectangle2D
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.Pane

class PdfPaneWithAnotations extends Pane {
	
	new(ControllerFxGraduation controller){
		this.controller = controller
		imageView = new ImageView()
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
		this.children.add(new TextAnotation(x,y,50,50,"New Anotation"))
	}
	
	def addAnotation(double x, double y, double height, double width, String text) {
		this.children.add(new TextAnotation(x,y,height,width,text))
	}
	
	def removeAnotation(TextAnotation anotation){
		children.remove(anotation)
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