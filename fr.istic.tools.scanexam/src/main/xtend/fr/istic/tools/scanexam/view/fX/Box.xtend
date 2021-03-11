package fr.istic.tools.scanexam.view.fX

import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import java.lang.LiveStackFrame.PrimitiveSlot
import javafx.scene.text.Text

class Box extends Rectangle {
	
	
		new(String name ,int page ,BoxType type,double x, double y,double h, double w) {
			super(x,y,w,h);

			this.page = page
			this.type = type
			listViewBox = new ListViewBox(name,this);
			setFill(Color.rgb(200, 200, 200, 0.2));
			setStroke(Color.BLACK);
			setStrokeWidth(FXSettings.BOX_BORDER_THICKNESS);
			this.text = new Text(x+FXSettings.BOX_TEXT_OFFSET_X,y+FXSettings.BOX_TEXT_OFFSET_Y,name);
			text.fill = FXSettings.BOX_NORMAL_COLOR
			text.textProperty.bind(listViewBox.nameLabel.textProperty);
			
		}
	
		new(String name,int page ,BoxType type,double x, double y) {
			this(name,page,type,x,y,0,0);
			
		}
		new(int page ,BoxType type,double x, double y) {
			this("box",page,type,x,y);

			
		}
		new(BoxType type,double x, double y) {
			this(0,type,x,y);	
		}
		new(int id,BoxType type) {
			this(id,type,0,0)
		}
		
		enum BoxType {
			QUESTION,
			ID,
			QR
		}
		
		ListViewBox listViewBox;
		BoxType type;
		int boxId;
		int page;
	
		Text text
		
		
		def getText(){
			text
		}
		
		def getListViewBox(){
			listViewBox
		}
		
		def getPageNumber(){
			page
		}
		
		def getType(){
			type
		}
		def getBoxId(){
			boxId
		}
		def String getName(){
			listViewBox.name
		}
			
		
		def setBoxId(int id){
			this.boxId = id
		}
		
		def void setFocus(boolean b) {
			if (b) {
				setColor(FXSettings.BOX_HIGHLIGHT_COLOR);
			}
			else {
				setColor(FXSettings.BOX_NORMAL_COLOR);
			}
		}
		
		def void setColor(Color color) {
			stroke = color
			text.fill = color
		}
		
		def void x(double x) {
			setX(x);
			text.x = x+FXSettings.BOX_TEXT_OFFSET_X
		}
		def void y(double y) {
			setY(y);
			text.y = y+FXSettings.BOX_TEXT_OFFSET_Y
		}
		
		def void height(double h) {
			setHeight(h)
		}
		
		def void width(double w) {
			setWidth(w);
		}
		
		

	}