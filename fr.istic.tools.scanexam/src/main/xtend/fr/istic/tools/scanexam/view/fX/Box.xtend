package fr.istic.tools.scanexam.view.fX

import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

class Box extends Rectangle {
	
		static int ID = 0;
		static def int newID(){
			ID++;
		}
	
		new(String name ,int page ,BoxType type,double x, double y) {
			super(x,y,0,0);
			boxId = newID();
			this.page = page
			this.name = name
			this.type = type
			listViewBox = new ListViewBox(name,this);
			setFill(Color.rgb(200, 200, 200, 0.2));
			setStroke(Color.BLACK);
			setStrokeWidth(FXSettings.BOX_BORDER_THICKNESS);
			
		}
		new(int page ,BoxType type,double x, double y) {
			this("box",page,type,x,y);

			
		}
		new(BoxType type,double x, double y) {
			this(0,type,x,y);	
		}
		new(BoxType type) {
			this(type,0,0)
		}
		
		enum BoxType {
			QUESTION,
			ID,
			QR
		}
		
		ListViewBox listViewBox;
		BoxType type;
		String name;
		int boxId;
		int page;
		
		
		
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
		
		def setBoxId(int id){
			this.boxId = id
		}
		
		def void setFocus(boolean b) {
			if (b) {
				setStroke(Color.web("#0093ff"));
			}
			else {
				setStroke(Color.BLACK);
			}
		}

	}