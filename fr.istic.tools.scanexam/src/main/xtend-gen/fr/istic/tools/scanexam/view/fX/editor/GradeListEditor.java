package fr.istic.tools.scanexam.view.fX.editor;

import com.google.common.base.Objects;
import java.util.LinkedList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

@SuppressWarnings("all")
public class GradeListEditor extends VBox {
  public GradeListEditor(final ControllerFXEditor controller) {
    this.controller = controller;
    LinkedList<GradeItemEditor> _linkedList = new LinkedList<GradeItemEditor>();
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
  
  private List<GradeItemEditor> allItems;
  
  private Button add;
  
  private VBox itemContainer;
  
  private QuestionItemEditor currentItem;
  
  private ControllerFXEditor controller;
  
  public void select(final QuestionItemEditor item) {
    this.clearDisplay();
    this.currentItem = item;
    this.add.setVisible(true);
    for (final GradeItemEditor g : this.allItems) {
      boolean _isGradeOf = this.isGradeOf(g, item);
      if (_isGradeOf) {
        this.display(g);
      }
    }
  }
  
  public boolean isGradeOf(final GradeItemEditor gItem, final QuestionItemEditor qItem) {
    QuestionItemEditor _gradeQuestionItem = gItem.getGradeQuestionItem();
    return Objects.equal(_gradeQuestionItem, qItem);
  }
  
  public QuestionItemEditor clearDisplay() {
    QuestionItemEditor _xblockexpression = null;
    {
      this.add.setVisible(false);
      this.itemContainer.getChildren().clear();
      _xblockexpression = this.currentItem = null;
    }
    return _xblockexpression;
  }
  
  public boolean display(final GradeItemEditor item) {
    return this.itemContainer.getChildren().add(item);
  }
  
  public Object loadGradeItem(final GradeItemEditor toAdd) {
    return null;
  }
  
  public void newGradeItem() {
    if ((this.currentItem == null)) {
      return;
    }
    GradeItemEditor gradeItem = new GradeItemEditor(this, this.currentItem);
    this.display(gradeItem);
    this.allItems.add(gradeItem);
    this.addGradeItemToModel(gradeItem);
  }
  
  public Object updateGradeItem(final GradeItemEditor toUpdate) {
    return this.updateGradeItemInModel(toUpdate);
  }
  
  public Object removeGradeItem(final GradeItemEditor toRemove) {
    Object _xblockexpression = null;
    {
      this.itemContainer.getChildren().remove(toRemove);
      this.allItems.remove(toRemove);
      _xblockexpression = this.removeGradeItemFromModel(toRemove);
    }
    return _xblockexpression;
  }
  
  public void clear() {
    this.clearDisplay();
    this.itemContainer.getChildren().clear();
  }
  
  public int addGradeItemToModel(final GradeItemEditor toAdd) {
    return toAdd.setGradeItemId(this.controller.getEditor().getPresenter().getPresenterMarkingScheme().addEntry(toAdd.getGradeQuestionItem().getQuestionId(), toAdd.getGradeItemName(), Float.parseFloat(toAdd.getGradeItemPoints())));
  }
  
  public Object updateGradeItemInModel(final GradeItemEditor toUpdate) {
    return this.controller.getEditor().getPresenter().getPresenterMarkingScheme().modifyEntry(toUpdate.getGradeQuestionItem().getQuestionId(), toUpdate.getGradeItemId(), toUpdate.getGradeItemName(), Float.parseFloat(toUpdate.getGradeItemPoints()));
  }
  
  public Object removeGradeItemFromModel(final GradeItemEditor toRemove) {
    return this.controller.getEditor().getPresenter().getPresenterMarkingScheme().removeEntry(toRemove.getGradeQuestionItem().getQuestionId(), toRemove.getGradeItemId());
  }
  
  public void setupEvents(final GradeListEditor list) {
    this.add.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(final ActionEvent event) {
        GradeListEditor.this.newGradeItem();
      }
    });
  }
}
