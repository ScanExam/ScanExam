package fr.istic.tools.scanexam.view.fX

import javafx.scene.shape.Rectangle
import javafx.scene.paint.Color

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
		enum BoxType {
			QUESTION,
			ID,
			QR
		}
		BoxType type;
		String name;

	}