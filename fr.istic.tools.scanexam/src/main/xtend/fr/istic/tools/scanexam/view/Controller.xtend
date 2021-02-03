package fr.istic.tools.scanexam.view

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

class Controller {
	boolean topShow = false;
    boolean botShow = false;
    public Pane topPane;
    public Button topButtonHidden;
    public Button topButtonActive;
    public Button botButtonHidden;
    public Button botButtonActive;
    public Pane bottomPane;
    public ListView leftList;
    public ListView rightList;

	//***********************//
   	//***** UI CONTROLS *****//
   	//***********************//
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
            bottomPane.setPrefHeight(Math.max(0,Math.min(bottomPane.getScene().getHeight()-100,bottomPane.getScene().getHeight()-event.getSceneY())));

        }
    }
    
    var mouseOriginX = 0d;
    var mouseOriginY = 0d;
    var objectOriginX = 0d;
    var objectOriginY = 0d;
    
    def void MoveImage(MouseEvent e) {
    	
    	if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
    		println("Starting to move")
    		mouseOriginX = e.screenX
    		mouseOriginY = e.screenY
    		var source =  e.source as Node
    		println(source)
    		objectOriginX = source.layoutX
    		objectOriginY = source.layoutY
    	}
    	if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
    		println("moving")
    		var source =  e.source as Node
    		
    		source.layoutX = objectOriginX + (e.screenX - mouseOriginX)
    		source.layoutY = objectOriginY + (e.screenY - mouseOriginY)
    		println(source + " " + source.layoutX + " " + source.layoutY )
    	}
    }
    
    def void ZoomImage(ScrollEvent e) {
    		var source = e.source as Node
    		if (e.deltaY > 0 ) {
    			source.scaleX = source.scaleX * 0.95
    			source.scaleY = source.scaleY * 0.95
    		}else {
    			source.scaleX = source.scaleX * 1.05
    			source.scaleY = source.scaleY * 1.05
    		}
    	}
    
    /* 
    var mouseOriginX = 0d;
    var mouseOriginY = 0d;
    var objectOriginX = 0d;
    var objectOriginY = 0d;
    
    def void MoveImage(MouseEvent e) {
    	println("trying to move")
    	if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
    		mouseOriginX = e.x
    		mouseOriginY = e.y
    		var source =  e.source as ImageView
    		var vp = source.viewport
    		objectOriginX = vp.minX
    		objectOriginY = vp.minY
    	}
    	if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
    		var source =  e.source as ImageView
    		var vp = source.viewport
    		var rect = new Rectangle2D(objectOriginX - (e.x - mouseOriginX),objectOriginY - (e.y - mouseOriginY),vp.height,vp.width )
    		source.viewport = rect
    	}
    }
    
    def void ZoomImage(ScrollEvent e) {
    	var source = e.source as ImageView
    	if (e.deltaY > 0) {
    		var vp = source.viewport
    		source.viewport = new Rectangle2D(vp.minX,vp.minY,vp.height * 0.95,vp.width*0.95)
    	}
    	
    	if (e.deltaY < 0) {
    		var vp = source.viewport
    		source.viewport = new Rectangle2D(vp.minX,vp.minY,vp.height * 1.05,vp.width*1.05)
    	}
    }
    
    */
    
    
    
    
    
    
    
    //***********************//
   	//* PRESENTER CONTROLS **//
   	//***********************//
    
    /**
     * Called when a <b>save</b> button is pressed
     */
    def void savePressed() {
    	println("Saving method");
    }
    /**
     * Called when a <b>save a</b> button is pressed
     */
    def void saveAsPressed() {
    	println("Saving as method");
    }
    
     /**
     * Called when a <b>load</b> button is pressed
     */
    def void loadPressed() {
    	println("Load method");
    }
    
     /**
     * Called when a <b>import</b> button is pressed
     */
    def void importPressed() {
    	println("Import method");
    	rightList.getItems().add(new Button("11"));
    	rightList.getItems().add("1");
    	rightList.getItems().add("2");
    	addQuestionList();
    }
    
     /**
     * Called when a <b>export</b> button is pressed
     */
    def void exportPressed() {
    	println("Export method");
    }
     /**
     * Called when a <b>next question</b> button is pressed
     */
    def void nextQuestionPressed(){
    	println("Next question method");
    }
     /**
     * Called when a <b>previous question pressed</b> button is pressed
     */
    def void prevQuestionPressed(){
    	println("Prev question method");
    }
     /**
     * Called when a <b>next student</b> button is pressed
     */
    def void nextStudentPressed(){
    	println("Next student method");
    }
     /**
     * Called when a <b>previous studend</b> button is pressed
     */
    def void prevStudentPressed(){
    	println("Prev student method");
    }
    
    
    def void addBaremeList() {
    	
    }
    
    def void addQuestionList() {
  
    }
    
    
    static class CopieItem extends HBox {
    	new(){
    		super();
    		add("question something");
    		
    	}
    	
    	def void add(String s) {
    		this.children.add(new Label(s));
    	}
    	
    }
    
    
    
    
    
    
}