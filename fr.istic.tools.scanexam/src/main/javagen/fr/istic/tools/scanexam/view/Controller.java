package fr.istic.tools.scanexam.view;

import java.io.IOException;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class Controller {
  public static class CopieItem /* implements HBox  */{
    public CopieItem() {
      throw new Error("Unresolved compilation problems:"
        + "\nThe method super() is undefined");
    }
    
    public void add(final String s) {
      throw new Error("Unresolved compilation problems:"
        + "\nThe method or field children is undefined for the type CopieItem"
        + "\nLabel cannot be resolved."
        + "\nadd cannot be resolved");
    }
  }
  
  private boolean topShow = false;
  
  private boolean botShow = false;
  
  public /* Pane */Object topPane;
  
  public /* Button */Object topButtonHidden;
  
  public /* Button */Object topButtonActive;
  
  public /* Button */Object botButtonHidden;
  
  public /* Button */Object botButtonActive;
  
  public /* Pane */Object bottomPane;
  
  public /* ListView */Object leftList;
  
  public /* ListView */Object rightList;
  
  /**
   * Toggles the visibility of the bottom window
   */
  public void toggleBottom() throws IOException {
    throw new Error("Unresolved compilation problems:"
      + "\nThe field Controller.bottomPane refers to the missing type Pane"
      + "\nThe field Controller.botButtonHidden refers to the missing type Button"
      + "\nsetVisible cannot be resolved"
      + "\nsetVisible cannot be resolved");
  }
  
  /**
   * Used to resize the window containing the corrected exam
   */
  public void dragBottom(final /* MouseEvent */Object event) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method or field MouseEvent is undefined"
      + "\nThe field Controller.bottomPane refers to the missing type Pane"
      + "\nThe field Controller.bottomPane refers to the missing type Pane"
      + "\nThe field Controller.bottomPane refers to the missing type Pane"
      + "\ngetEventType cannot be resolved"
      + "\n== cannot be resolved"
      + "\nMOUSE_DRAGGED cannot be resolved"
      + "\nsetPrefHeight cannot be resolved"
      + "\ngetScene cannot be resolved"
      + "\ngetHeight cannot be resolved"
      + "\n- cannot be resolved"
      + "\ngetScene cannot be resolved"
      + "\ngetHeight cannot be resolved"
      + "\n- cannot be resolved"
      + "\ngetSceneY cannot be resolved");
  }
  
  private double mouseOriginX = 0d;
  
  private double mouseOriginY = 0d;
  
  private double objectOriginX = 0d;
  
  private double objectOriginY = 0d;
  
  public void MoveImage(final /* MouseEvent */Object e) {
    throw new Error("Unresolved compilation problems:"
      + "\nNode cannot be resolved to a type."
      + "\nNode cannot be resolved to a type."
      + "\nThe method or field MouseEvent is undefined"
      + "\nThe method or field MouseEvent is undefined"
      + "\ngetEventType cannot be resolved"
      + "\n== cannot be resolved"
      + "\nMOUSE_PRESSED cannot be resolved"
      + "\nscreenX cannot be resolved"
      + "\nscreenY cannot be resolved"
      + "\nsource cannot be resolved"
      + "\nlayoutX cannot be resolved"
      + "\nlayoutY cannot be resolved"
      + "\ngetEventType cannot be resolved"
      + "\n== cannot be resolved"
      + "\nMOUSE_DRAGGED cannot be resolved"
      + "\nsource cannot be resolved"
      + "\nlayoutX cannot be resolved"
      + "\nscreenX cannot be resolved"
      + "\n- cannot be resolved"
      + "\nlayoutY cannot be resolved"
      + "\nscreenY cannot be resolved"
      + "\n- cannot be resolved"
      + "\n+ cannot be resolved"
      + "\n+ cannot be resolved"
      + "\nlayoutX cannot be resolved"
      + "\n+ cannot be resolved"
      + "\n+ cannot be resolved"
      + "\nlayoutY cannot be resolved");
  }
  
  public void ZoomImage(final /* ScrollEvent */Object e) {
    throw new Error("Unresolved compilation problems:"
      + "\nNode cannot be resolved to a type."
      + "\nsource cannot be resolved"
      + "\ndeltaY cannot be resolved"
      + "\n> cannot be resolved"
      + "\nscaleX cannot be resolved"
      + "\nscaleX cannot be resolved"
      + "\n* cannot be resolved"
      + "\nscaleY cannot be resolved"
      + "\nscaleY cannot be resolved"
      + "\n* cannot be resolved"
      + "\nscaleX cannot be resolved"
      + "\nscaleX cannot be resolved"
      + "\n* cannot be resolved"
      + "\nscaleY cannot be resolved"
      + "\nscaleY cannot be resolved"
      + "\n* cannot be resolved");
  }
  
  /**
   * Called when a <b>save</b> button is pressed
   */
  public void savePressed() {
    InputOutput.<String>println("Saving method");
  }
  
  /**
   * Called when a <b>save a</b> button is pressed
   */
  public void saveAsPressed() {
    InputOutput.<String>println("Saving as method");
  }
  
  /**
   * Called when a <b>load</b> button is pressed
   */
  public void loadPressed() {
    InputOutput.<String>println("Load method");
  }
  
  /**
   * Called when a <b>import</b> button is pressed
   */
  public void importPressed() {
    throw new Error("Unresolved compilation problems:"
      + "\nButton cannot be resolved."
      + "\nThe field Controller.rightList refers to the missing type ListView"
      + "\nThe field Controller.rightList refers to the missing type ListView"
      + "\nThe field Controller.rightList refers to the missing type ListView"
      + "\ngetItems cannot be resolved"
      + "\nadd cannot be resolved"
      + "\ngetItems cannot be resolved"
      + "\nadd cannot be resolved"
      + "\ngetItems cannot be resolved"
      + "\nadd cannot be resolved");
  }
  
  /**
   * Called when a <b>export</b> button is pressed
   */
  public void exportPressed() {
    InputOutput.<String>println("Export method");
  }
  
  /**
   * Called when a <b>next question</b> button is pressed
   */
  public void nextQuestionPressed() {
    InputOutput.<String>println("Next question method");
  }
  
  /**
   * Called when a <b>previous question pressed</b> button is pressed
   */
  public void prevQuestionPressed() {
    InputOutput.<String>println("Prev question method");
  }
  
  /**
   * Called when a <b>next student</b> button is pressed
   */
  public void nextStudentPressed() {
    InputOutput.<String>println("Next student method");
  }
  
  /**
   * Called when a <b>previous studend</b> button is pressed
   */
  public void prevStudentPressed() {
    InputOutput.<String>println("Prev student method");
  }
  
  public void addBaremeList() {
  }
  
  public void addQuestionList() {
  }
}
