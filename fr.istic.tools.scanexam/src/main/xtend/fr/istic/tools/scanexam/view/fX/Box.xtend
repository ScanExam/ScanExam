package fr.istic.tools.scanexam.view.fX

import javafx.scene.shape.Rectangle
import javafx.scene.paint.Color
import javafx.scene.layout.HBox
import javafx.scene.control.TextField

class Box extends Rectangle {
		
		new(BoxType type) {
			super(0,0,0,0);
			name = "box"
			this.type = type
			setFill(Color.rgb(200, 200, 200, 0.2));
			setStroke(Color.BLACK);
			setStrokeWidth(1);
		}
		new(BoxType type,double x, double y) {
			super(x,y,0,0);
			name = "box"
			this.type = type
			setFill(Color.rgb(200, 200, 200, 0.2));
			setStroke(Color.BLACK);
			setStrokeWidth(1);
			
		}
		new(int page ,BoxType type,double x, double y) {
			super(x,y,0,0);
			name = "box"
			this.type = type
			setFill(Color.rgb(200, 200, 200, 0.2));
			setStroke(Color.BLACK);
			setStrokeWidth(1);
			
		}
		new(String name ,int page ,BoxType type,double x, double y) {
			super(x,y,0,0);
			this.name = "name"
			this.type = type
			setFill(Color.rgb(200, 200, 200, 0.2));
			setStroke(Color.BLACK);
			setStrokeWidth(1);
			
		}
		
		def HBox boxItem() {
			var container = new HBox();
			var field = new TextField();
			container.children.add(field);
			field.editable = false
			field.text = name
			
			return container
		}
		enum BoxType {
			QUESTION,
			ID,
			QR
		}
		BoxType type;
		String name;
		int page;

	}