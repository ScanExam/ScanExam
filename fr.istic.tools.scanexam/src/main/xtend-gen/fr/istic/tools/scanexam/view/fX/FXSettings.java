package fr.istic.tools.scanexam.view.fx;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

@SuppressWarnings("all")
public class FXSettings {
  public static int BOX_BORDER_THICKNESS = 3;
  
  public static Color BOX_BORDER_NORMAL_COLOR = Color.BLACK;
  
  public static Color BOX_BORDER_HIGHLIGHT_COLOR = Color.web("#0093ff");
  
  public static int BUTTON_ICON_SIZE = 16;
  
  public static double BOX_TEXT_OFFSET_X = 25.0;
  
  public static double BOX_TEXT_OFFSET_Y = 25.0;
  
  public static Color BOX_HIGHLIGHT_COLOR = Color.web("#0093ff", 0.2);
  
  public static Color ITEM_HIGHLIGHT_COLOR = Color.web("#0093ff");
  
  public static Color ITEM_NORMAL_COLOR = Color.TRANSPARENT;
  
  public static Color BOX_NORMAL_COLOR = Color.rgb(200, 200, 200, 0.2);
  
  public static Color LIST_BACKGROUND_COLOR = Color.WHITE;
  
  public static int MINIMUM_ZONE_SIZE = 20;
  
  public static int ZONE_RESIZE_TOLERANCE = 10;
  
  public static KeyCode BUTTON_NEXT_STUDENT = KeyCode.RIGHT;
  
  public static KeyCode BUTTON_PREV_STUDENT = KeyCode.LEFT;
  
  public static KeyCode BUTTON_NEXT_QUESTION = KeyCode.DOWN;
  
  public static KeyCode BUTTON_PREV_QUESTION = KeyCode.UP;
}
