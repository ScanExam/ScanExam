package fr.istic.tools.scanexam.view.fx.editor;

import javafx.scene.control.TextField;

@SuppressWarnings("all")
public class NumberTextField extends TextField {
  @Override
  public void replaceText(final int start, final int end, final String text) {
    boolean _validate = this.validate(text);
    if (_validate) {
      super.replaceText(start, end, text);
    }
  }
  
  @Override
  public void replaceSelection(final String text) {
    boolean _validate = this.validate(text);
    if (_validate) {
      super.replaceSelection(text);
    }
  }
  
  public boolean validate(final String text) {
    return text.matches("[0-9]*");
  }
}
