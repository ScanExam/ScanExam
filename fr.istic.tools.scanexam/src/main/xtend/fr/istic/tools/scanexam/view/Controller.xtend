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

class Controller {
	boolean topShow = false;
    boolean botShow = false;
    public Pane topPane;
    public Button topButtonHidden;
    public Button topButtonActive;
    public Button botButtonHidden;
    public Button botButtonActive;
    public Pane bottomPane;
    public VBox leftList;
    public ListView rightList;


    def void toggleTop() throws IOException {
        topPane.setVisible(!topShow);
        topButtonHidden.setVisible(topShow);
        topShow = !topShow;
    }

    def void toggleBottom() throws IOException {
        bottomPane.setVisible(!botShow);
        botButtonHidden.setVisible(botShow);
        botShow = !botShow;
    }
    
    double startY ;

    def void dragBottom(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            bottomPane.setPrefHeight(Math.max(0,Math.min(bottomPane.getScene().getHeight()-100,bottomPane.getScene().getHeight()-event.getSceneY())));

        }
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            startY = event.getY();
        }
    }
    
    def void savePressed() {
    	println("Saving method");
    }
    
    def void saveAsPressed() {
    	println("Saving as method");
    }
    
    def void loadPressed() {
    	println("Load method");
    }
    
    def void importPressed() {
    	println("Import method");
    	rightList.getItems().add(new Button("11"));
    	rightList.getItems().add("1");
    	rightList.getItems().add("2");
    	addQuestionList();
    }
    
    def void exportPressed() {
    	println("Export method");
    }
    
    def void nextQuestionPressed(){
    	println("Next question method");
    }
    
    def void prevQuestionPressed(){
    	println("Prev question method");
    }
    
    def void nextStudentPressed(){
    	println("Next student method");
    }
    
    def void prevStudentPressed(){
    	println("Prev student method");
    }
    
    def void addBaremeList() {
    	
    }
    
    def void addQuestionList() {
    	leftList.children.add(new CopieItem());
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