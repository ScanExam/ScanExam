package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.view.fx.ControllerLinkManuallySheets;
import fr.istic.tools.scanexam.view.fx.FailedPageItem;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class FailedPageItemList extends VBox {
  private ControllerLinkManuallySheets controller;
  
  public FailedPageItemList(final ControllerLinkManuallySheets controller) {
    this.controller = controller;
    this.initList();
  }
  
  public ControllerLinkManuallySheets getController() {
    return this.controller;
  }
  
  public boolean addItem(final FailedPageItem item) {
    boolean _xblockexpression = false;
    {
      item.setList(this);
      _xblockexpression = this.getChildren().add(item);
    }
    return _xblockexpression;
  }
  
  public boolean removeItem(final FailedPageItem item) {
    return this.getChildren().remove(item);
  }
  
  public void clearItems() {
    this.getChildren().clear();
  }
  
  public FailedPageItem getElement(final int i) {
    Node _get = this.getChildren().get(i);
    return ((FailedPageItem) _get);
  }
  
  public void initList() {
    int _size = this.controller.getFailedPages().size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer page : _doubleDotLessThan) {
      {
        Integer _get = this.controller.getFailedPages().get((page).intValue());
        FailedPageItem item = new FailedPageItem(_get, this);
        this.getChildren().add(item);
        int _indexCurrentPage = this.controller.getIndexCurrentPage();
        boolean _equals = ((page).intValue() == _indexCurrentPage);
        if (_equals) {
          item.setFocus(true);
        } else {
          item.setFocus(false);
        }
      }
    }
  }
  
  public boolean updateList() {
    boolean _xblockexpression = false;
    {
      List<Node> children = new ArrayList<Node>();
      int _size = this.getChildren().size();
      String _plus = ("children size : " + Integer.valueOf(_size));
      InputOutput.<String>println(_plus);
      int _size_1 = this.controller.getFailedPages().size();
      ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size_1, true);
      for (final Integer page : _doubleDotLessThan) {
        {
          Node _get = this.getChildren().get((page).intValue());
          FailedPageItem item = ((FailedPageItem) _get);
          children.add(item);
          int _indexCurrentPage = this.controller.getIndexCurrentPage();
          boolean _equals = ((page).intValue() == _indexCurrentPage);
          if (_equals) {
            item.setFocus(true);
          } else {
            item.setFocus(false);
          }
        }
      }
      this.clearItems();
      _xblockexpression = this.getChildren().addAll(children);
    }
    return _xblockexpression;
  }
}
