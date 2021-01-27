package fr.istic.tools.scanexam.view

import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.input.MouseEvent
import java.io.IOException
import javafx.scene.layout.Pane

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
    double startY ;

    def void dragBottom(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            bottomPane.setPrefHeight(Math.max(0,Math.min(bottomPane.getScene().getHeight()-100,bottomPane.getScene().getHeight()-event.getSceneY())));

        }
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            startY = event.getY();
        }
    }
}