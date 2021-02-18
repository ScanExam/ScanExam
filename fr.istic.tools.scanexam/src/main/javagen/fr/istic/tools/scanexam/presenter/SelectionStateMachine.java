package fr.istic.tools.scanexam.presenter;

@SuppressWarnings("all")
public class SelectionStateMachine {
  public static final int IDLE = 0;
  
  public static final int CREATE = 1;
  
  public static final int RESIZE = 2;
  
  public static final int MOVE = 3;
  
  public static final int DELETE = 4;
  
  public static final int LOOK = 5;
  
  private static int currentState = SelectionStateMachine.IDLE;
  
  public static int getState() {
    return SelectionStateMachine.currentState;
  }
  
  public static void setState(final int state) {
    SelectionStateMachine.currentState = state;
  }
}
