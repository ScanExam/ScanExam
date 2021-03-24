package fr.istic.tools.scanexam.view.fX

import javafx.scene.input.KeyCode
import javafx.scene.paint.Color

class FXSettings {
	
	public static var BOX_BORDER_THICKNESS = 3
	public static var BOX_BORDER_NORMAL_COLOR = Color.BLACK
	public static var BOX_BORDER_HIGHLIGHT_COLOR = Color.web("#0093ff")
	public static var BUTTON_ICON_SIZE = 16;
	public static var BOX_TEXT_OFFSET_X = 25.0;
	public static var BOX_TEXT_OFFSET_Y = 25.0;
	public static var BOX_HIGHLIGHT_COLOR = Color.web("#0093ff",0.2);
	public static var ITEM_HIGHLIGHT_COLOR = Color.web("#0093ff");
	public static var ITEM_NORMAL_COLOR = Color.TRANSPARENT;
	public static var BOX_NORMAL_COLOR = Color.rgb(200, 200, 200, 0.2);
	public static var LIST_BACKGROUND_COLOR = Color.WHITE
	public static var  MINIMUM_ZONE_SIZE =  20
	
	public static var  ZONE_RESIZE_TOLERANCE =  10
	
	
	//CONTROLS
	public static var BUTTON_NEXT_STUDENT = KeyCode.RIGHT;
	public static var BUTTON_PREV_STUDENT = KeyCode.LEFT;
	public static var BUTTON_NEXT_QUESTION = KeyCode.DOWN;
	public static var BUTTON_PREV_QUESTION = KeyCode.UP;
}