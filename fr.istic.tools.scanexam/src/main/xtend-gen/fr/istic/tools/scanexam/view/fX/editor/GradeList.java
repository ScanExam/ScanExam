package fr.istic.tools.scanexam.view.fX.editor;

import com.google.common.base.Objects;
import fr.istic.tools.scanexam.view.fX.editor.ControllerFXEditor;
import fr.istic.tools.scanexam.view.fX.editor.EditorQuestionItem;
import fr.istic.tools.scanexam.view.fX.editor.GradeItem;
import java.util.LinkedList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@SuppressWarnings("all")
public class GradeList extends VBox {
  public GradeList(final ControllerFXEditor controller) {
    this.controller = controller;
    LinkedList<GradeItem> _linkedList = new LinkedList<GradeItem>();
    this.allItems = _linkedList;
    Button _button = new Button("Add");
    this.add = _button;
    HBox topBox = new HBox();
    topBox.getChildren().add(this.add);
    VBox _vBox = new VBox();
    this.itemContainer = _vBox;
    this.getChildren().addAll(topBox, this.itemContainer);
    this.setupEvents(this);
    this.clearDisplay();
  }
  
  private List<GradeItem> allItems;
  
  private Button add;
  
  private VBox itemContainer;
  
  private EditorQuestionItem currentItem;
  
  private ControllerFXEditor controller;
  
  public void showFor(final EditorQuestionItem item) {
    this.clearDisplay();
    this.currentItem = item;
    this.add.setVisible(true);
    for (final GradeItem g : this.allItems) {
      boolean _isGradeOf = this.isGradeOf(g, item);
      if (_isGradeOf) {
        this.display(g);
      }
    }
  }
  
  public Object loadGradeItem(final GradeItem toAdd) {
    return null;
  }
  
  public void newGradeItem() {
    if ((this.currentItem == null)) {
      return;
    }
    GradeItem gradeItem = new GradeItem(this, this.currentItem);
    this.display(gradeItem);
    this.allItems.add(gradeItem);
    this.addGradeItemToModel(gradeItem);
  }
  
  public void updateGradeItem(final GradeItem toUpdate) {
    this.updateGradeItemInModel(toUpdate);
  }
  
  public boolean removeGradeItem(final GradeItem toRemove) {
    boolean _xblockexpression = false;
    {
      this.itemContainer.getChildren().remove(toRemove);
      this.allItems.remove(toRemove);
      _xblockexpression = this.removeGradeItemFromModel(toRemove);
    }
    return _xblockexpression;
  }
  
  public int addGradeItemToModel(final GradeItem toAdd) {
    return toAdd.setGradeItemId(this.controller.getEditor().getPresenter().getPresenterMarkingScheme().addEntry(toAdd.getGradeQuestionItem().getQuestionId(), toAdd.getGradeItemName(), Float.parseFloat(toAdd.getGradeItemPoints())));
  }
  
  public void updateGradeItemInModel(final GradeItem toUpdate) {
    this.controller.getEditor().getPresenter().getPresenterMarkingScheme().modifyEntry(toUpdate.getGradeQuestionItem().getQuestionId(), toUpdate.getGradeItemId(), toUpdate.getGradeItemName(), Float.parseFloat(toUpdate.getGradeItemPoints()));
  }
  
  public boolean removeGradeItemFromModel(final GradeItem toRemove) {
    return this.controller.getEditor().getPresenter().getPresenterMarkingScheme().removeEntry(toRemove.getGradeQuestionItem().getQuestionId(), toRemove.getGradeItemId());
  }
  
  public boolean isGradeOf(final GradeItem gItem, final EditorQuestionItem qItem) {
    EditorQuestionItem _gradeQuestionItem = gItem.getGradeQuestionItem();
    return Objects.equal(_gradeQuestionItem, qItem);
  }
  
  public EditorQuestionItem clearDisplay() {
    EditorQuestionItem _xblockexpression = null;
    {
      this.add.setVisible(false);
      this.itemContainer.getChildren().clear();
      _xblockexpression = this.currentItem = null;
    }
    return _xblockexpression;
  }
  
  public boolean display(final GradeItem item) {
    return this.itemContainer.getChildren().add(item);
  }
  
  public void setupEvents(final GradeList list) {
    this.add.setOnAction(new EventHandler<ActionEvent>() {
      public void handle(final ActionEvent event) {
        GradeList.this.newGradeItem();
      }
    });
  }
}
