package fr.istic.tools.scanexam.view.swing;

import fr.istic.tools.scanexam.box.Box;
import fr.istic.tools.scanexam.box.BoxList;
import fr.istic.tools.scanexam.presenter.SelectionStateMachine;
import fr.istic.tools.scanexam.view.AdapterBox;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;
import javax.swing.JPanel;

/**
 * Permet de dessiner des boîtes de sélection avec Swing
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class AdapterSwingBox extends AdapterBox {
  /**
   * Dernière boîte sélectionné par l'utilisateur
   */
  private Box lastBoxSelected;
  
  /**
   * Indique si la croix d'une boîte à été cliquée
   */
  private boolean crossCliked;
  
  /**
   * Handler pour les événements liés à la souris
   */
  private MouseAdapter mouseHandler;
  
  /**
   * Dernier point cliqué par l'utilisateur
   */
  private Optional<Point> lastClickPoint;
  
  /**
   * Vue
   */
  private Optional<JPanel> view;
  
  /**
   * Constructeur
   * @param windowWidth Largeur de la fenêtre
   * @param windowHeight Hauteur de la fenêtre
   * @param scale Echelle pour l'affichage
   * @param originX Point d'origine sur l'axe X
   * @param originY Point d'origine sur l'axe Y
   * @param selectionBoxes Boîtes de sélection
   */
  public AdapterSwingBox(final int windowWidth, final int windowHeight, final int scale, final int originX, final int originY, final BoxList selectionBoxes) {
    super(windowWidth, windowHeight, scale, originX, originY, selectionBoxes);
    this.mouseHandler = new MouseAdapter() {
      @Override
      public void mousePressed(final MouseEvent e) {
        int _button = e.getButton();
        boolean _tripleEquals = (_button == 1);
        if (_tripleEquals) {
          AdapterSwingBox.this.crossCliked = false;
          AdapterSwingBox.this.lastClickPoint = Optional.<Point>of(e.getPoint());
          Optional<Box> pointedBox = AdapterSwingBox.this.checkPoint(e.getPoint());
          boolean _isPresent = pointedBox.isPresent();
          boolean _not = (!_isPresent);
          if (_not) {
            SelectionStateMachine.setState(SelectionStateMachine.CREATE);
            AdapterSwingBox.this.createBox(e);
            SelectionStateMachine.setState(SelectionStateMachine.RESIZE);
            AdapterSwingBox.this.resizeBox(e, AdapterSwingBox.this.lastBoxSelected);
          } else {
            AdapterSwingBox.this.lastBoxSelected = pointedBox.get();
            if ((!AdapterSwingBox.this.crossCliked)) {
              SelectionStateMachine.setState(SelectionStateMachine.MOVE);
              AdapterSwingBox.this.moveBox(e, AdapterSwingBox.this.lastBoxSelected);
            } else {
              SelectionStateMachine.setState(SelectionStateMachine.DELETE);
              AdapterSwingBox.this.deleteBox(AdapterSwingBox.this.lastBoxSelected);
            }
          }
        }
      }
      
      @Override
      public void mouseReleased(final MouseEvent e) {
        SelectionStateMachine.setState(SelectionStateMachine.IDLE);
        AdapterSwingBox.this.lastClickPoint = Optional.<Point>empty();
      }
      
      @Override
      public void mouseDragged(final MouseEvent e) {
        int _state = SelectionStateMachine.getState();
        boolean _tripleEquals = (_state == SelectionStateMachine.RESIZE);
        if (_tripleEquals) {
          AdapterSwingBox.this.resizeBox(e, AdapterSwingBox.this.lastBoxSelected);
        }
        int _state_1 = SelectionStateMachine.getState();
        boolean _tripleEquals_1 = (_state_1 == SelectionStateMachine.MOVE);
        if (_tripleEquals_1) {
          AdapterSwingBox.this.moveBox(e, AdapterSwingBox.this.lastBoxSelected);
        }
      }
    };
  }
  
  /**
   * Retourne la boîte se trouvant au point indiqué
   * @param point endroit à observer
   * @return la boîte
   */
  private Optional<Box> checkPoint(final Point point) {
    double _x = point.getX();
    double _minus = (_x - this.originX);
    double _divide = (_minus / this.windowWidth);
    double x = (_divide * this.scale);
    double _y = point.getY();
    double _minus_1 = (_y - this.originY);
    double _divide_1 = (_minus_1 / this.windowHeight);
    double y = (_divide_1 * this.scale);
    List<Box> _list = this.selectionBoxes.getList();
    for (final Box box : _list) {
      if ((((x >= box.getX()) && (x <= (box.getX() + box.getWidth()))) && ((y >= (box.getY() - ((((double) this.titleHeight) / this.windowHeight) * this.scale))) && (y <= box.getY())))) {
        double _x_1 = box.getX();
        double _width = box.getWidth();
        double _plus = (_x_1 + _width);
        double _minus_2 = (_plus - ((((double) this.titleHeight) / this.windowWidth) * this.scale));
        boolean _greaterEqualsThan = (x >= _minus_2);
        if (_greaterEqualsThan) {
          this.crossCliked = true;
        } else {
          this.crossCliked = false;
        }
        return Optional.<Box>of(box);
      }
    }
    return Optional.<Box>empty();
  }
  
  /**
   * Ajoute une nouvelle boîte
   * @param e Event
   */
  private void createBox(final MouseEvent e) {
    double _x = this.lastClickPoint.get().getX();
    double _minus = (_x - this.originX);
    double _divide = (_minus / this.windowWidth);
    double x = (_divide * this.scale);
    double _y = this.lastClickPoint.get().getY();
    double _minus_1 = (_y - this.originY);
    double _divide_1 = (_minus_1 / this.windowHeight);
    double y = (_divide_1 * this.scale);
    this.createBox(x, y);
    int _size = this.selectionBoxes.size();
    int _minus_2 = (_size - 1);
    this.lastBoxSelected = this.selectionBoxes.getBox(_minus_2);
    this.repaint();
  }
  
  /**
   * Change la taille d'une boîte de ces coordonnées à l'endroit pointé par la souris
   * @param e Event
   * @param box Boîte à modifer
   */
  private void resizeBox(final MouseEvent e, final Box box) {
    Point dragPoint = e.getPoint();
    double _min = Math.min(this.lastClickPoint.get().getX(), dragPoint.x);
    double _minus = (_min - this.originX);
    double x = (_minus / (this.windowWidth / this.scale));
    double _min_1 = Math.min(this.lastClickPoint.get().getY(), dragPoint.y);
    double _minus_1 = (_min_1 - this.originY);
    double y = (_minus_1 / (this.windowHeight / this.scale));
    double _x = this.lastClickPoint.get().getX();
    double _minus_2 = (_x - dragPoint.x);
    double _x_1 = this.lastClickPoint.get().getX();
    double _minus_3 = (dragPoint.x - _x_1);
    double _max = Math.max(_minus_2, _minus_3);
    double width = (_max / (this.windowWidth / this.scale));
    double _y = this.lastClickPoint.get().getY();
    double _minus_4 = (_y - dragPoint.y);
    double _y_1 = this.lastClickPoint.get().getY();
    double _minus_5 = (dragPoint.y - _y_1);
    double _max_1 = Math.max(_minus_4, _minus_5);
    double height = (_max_1 / (this.windowHeight / this.scale));
    this.resizeBox(box, x, y, width, height);
    this.repaint();
  }
  
  /**
   * Change la position d'une boîte à l'endroit pointé par la souris
   * @param e Event
   * @param box Boîte à bouger
   */
  private void moveBox(final MouseEvent e, final Box box) {
    Point dragPoint = e.getPoint();
    double _x = box.getX();
    double initialX = (_x - this.originX);
    double _y = box.getY();
    double initialY = (_y - this.originY);
    double _x_1 = this.lastClickPoint.get().getX();
    double _minus = (dragPoint.x - _x_1);
    double dragX = (_minus / ((this.windowWidth - this.originX) / this.scale));
    double _y_1 = this.lastClickPoint.get().getY();
    double _minus_1 = (dragPoint.y - _y_1);
    double dragY = (_minus_1 / ((this.windowHeight - this.originY) / this.scale));
    this.moveBox(box, (initialX + dragX), (initialY + dragY));
    this.repaint();
    this.lastClickPoint = Optional.<Point>of(e.getPoint());
  }
  
  /**
   * Supprime une boîte
   * @param box Boîte à retirer
   */
  @Override
  protected void deleteBox(final Box box) {
    this.selectionBoxes.removeBox(box);
    this.repaint();
  }
  
  /**
   * Actualise la vue
   */
  private void repaint() {
    boolean _isPresent = this.view.isPresent();
    if (_isPresent) {
      this.view.get().repaint();
    }
  }
  
  /**
   * GETTERS
   */
  public MouseAdapter getMouseHandler() {
    return this.mouseHandler;
  }
  
  /**
   * SETTERS
   */
  public void setView(final JPanel view) {
    this.view = Optional.<JPanel>of(view);
  }
  
  public void setWindowWidth(final int width) {
    this.windowWidth = width;
  }
  
  public void setWindowHeight(final int height) {
    this.windowHeight = height;
  }
}
