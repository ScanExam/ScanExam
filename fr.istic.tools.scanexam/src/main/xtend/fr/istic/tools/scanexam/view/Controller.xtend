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
    public ListView leftList;
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
    
    def void dragBottom(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            bottomPane.setPrefHeight(Math.max(0,Math.min(bottomPane.getScene().getHeight()-100,bottomPane.getScene().getHeight()-event.getSceneY())));

        }
    }
    
    
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