package fr.istic.tools.scanexam.view.fx;

import fr.istic.tools.scanexam.view.fx.ControllerLinkManuallySheets;
import fr.istic.tools.scanexam.view.fx.FailedPageItem;
import javafx.scene.layout.VBox;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class FailedPageItemList extends VBox {
  private ControllerLinkManuallySheets controller;
  
  public FailedPageItemList(final ControllerLinkManuallySheets controller) {
    this.controller = controller;
    this.updateList();
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
  
  public void updateList() {
    this.clearItems();
    int _indexCurrentPage = this.controller.getIndexCurrentPage();
    String _plus = ("current index : " + Integer.valueOf(_indexCurrentPage));
    InputOutput.<String>println(_plus);
    int _size = this.controller.getFailedPages().size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer page : _doubleDotLessThan) {
      {
        Integer _get = this.controller.getFailedPages().get((page).intValue());
        FailedPageItem item = new FailedPageItem(_get, this);
        this.getChildren().add(item);
        int _indexCurrentPage_1 = this.controller.getIndexCurrentPage();
        boolean _equals = ((page).intValue() == _indexCurrentPage_1);
        if (_equals) {
          item.setFocus(true);
        } else {
          item.setFocus(false);
        }
      }
    }
  }
}
