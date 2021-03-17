package fr.istic.tools.scanexam.view.swing;

import fr.istic.tools.scanexam.view.swing.AdapterSwingBox;
import fr.istic.tools.scanexam.view.swing.AdapterSwingPdfAndBoxPanel;
import fr.istic.tools.scanexam.view.swing.Box;
import fr.istic.tools.scanexam.view.swing.BoxList;
import fr.istic.tools.scanexam.view.swing.PdfPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

@SuppressWarnings("all")
public class PdfAndBoxPanel extends PdfPanel {
  /**
   * Adaptateur swing du gestionnaire de boîtes de sélection
   */
  public AdapterSwingBox adapterBox;
  
  /**
   * Couleur de l'intérieur des boîtes
   */
  private final Color selectionColor = new Color(255, 255, 255, 64);
  
  /**
   * Couleur de l'intérieur des titres
   */
  private final Color titleColor = new Color(191, 191, 191, 191);
  
  /**
   * Couleur des contours des boîtes
   */
  private final Color outLineColor = Color.BLACK;
  
  /**
   * Constructeur
   * @param adapter Adaptateur de le classe
   */
  public PdfAndBoxPanel(final AdapterSwingPdfAndBoxPanel adapter) {
    super(adapter);
    this.adapter.setView(this);
    this.adapterBox = adapter.getAdapterBox();
    this.adapterBox.getSelectionBoxes().setPanel(this);
    this.addMouseListener(this.adapterBox.getMouseHandler());
    this.addMouseMotionListener(this.adapterBox.getMouseHandler());
  }
  
  /**
   * Affichage graphique
   */
  protected void paintComponent(final Graphics g) {
    super.paintComponent(g);
    Graphics _create = g.create();
    Graphics2D g2d = ((Graphics2D) _create);
    this.paintSelectionBoxes(g2d, this.adapterBox.getSelectionBoxes());
    this.paintBoxesTitle(g2d, this.adapterBox.getSelectionBoxes());
    g2d.dispose();
  }
  
  /**
   * Dessine les boîtes de sélection
   * @param g2d
   * @param boxes Boîtes à dessiner
   */
  public void paintSelectionBoxes(final Graphics2D g2d, final BoxList boxes) {
    g2d.setColor(this.selectionColor);
    double _minWidth = boxes.getMinWidth();
    double _minHeight = boxes.getMinHeight();
    double _maxWidth = boxes.getMaxWidth();
    double _maxHeight = boxes.getMaxHeight();
    BoxList paintedBoxes = new BoxList(_minWidth, _minHeight, _maxWidth, _maxHeight);
    List<Box> _list = boxes.getList();
    for (final Box box : _list) {
      int _nbPage = box.getNbPage();
      int _currentPdfPageNumber = this.adapter.presenterPdf.getPresenterPdf().currentPdfPageNumber();
      boolean _equals = (_nbPage == _currentPdfPageNumber);
      if (_equals) {
        double _x = box.getX();
        int _originX = this.adapter.getOriginX();
        double _width = box.getWidth();
        double _minus = (_originX - _width);
        double _divide = (_minus / this.width);
        double _plus = (_x + _divide);
        double _y = box.getY();
        int _originY = this.adapter.getOriginY();
        double _height = box.getHeight();
        double _minus_1 = (_originY - _height);
        double _divide_1 = (_minus_1 / this.height);
        double _plus_1 = (_y + _divide_1);
        paintedBoxes.addBox(_plus, _plus_1, box.getWidth(), box.getHeight(), box.getTitle());
      }
    }
    List<Box> _list_1 = paintedBoxes.getList();
    for (final Box paintedBox : _list_1) {
      g2d.fill(this.convertBoxToRectangle(paintedBox));
    }
    g2d.setColor(this.outLineColor);
    List<Box> _list_2 = paintedBoxes.getList();
    for (final Box paintedBox_1 : _list_2) {
      g2d.draw(this.convertBoxToRectangle(paintedBox_1));
    }
  }
  
  /**
   * Ajoute des titres aux boîtes de sélection
   * @param g2d
   * @param boxes Boîtes de sélection auquelles ajouter des titres
   */
  public void paintBoxesTitle(final Graphics2D g2d, final BoxList boxes) {
    double _minWidth = this.adapterBox.getMinWidth();
    double _divide = (_minWidth / this.width);
    double _titleHeight = this.adapterBox.getTitleHeight();
    double _divide_1 = (_titleHeight / this.height);
    double _titleHeight_1 = this.adapterBox.getTitleHeight();
    double _divide_2 = (_titleHeight_1 / this.height);
    BoxList boxesTitle = new BoxList(_divide, _divide_1, (-1.0), _divide_2);
    double _minWidth_1 = this.adapterBox.getMinWidth();
    double _divide_3 = (_minWidth_1 / this.width);
    int _scale = this.adapter.getScale();
    double _divide_4 = (_divide_3 / _scale);
    double _minHeight = this.adapterBox.getMinHeight();
    double _divide_5 = (_minHeight / this.height);
    boxesTitle.updateBounds(_divide_4, _divide_5, (-1.0), (-1.0));
    List<Box> _list = boxes.getList();
    for (final Box box : _list) {
      int _nbPage = box.getNbPage();
      int _currentPdfPageNumber = this.adapter.presenterPdf.getPresenterPdf().currentPdfPageNumber();
      boolean _equals = (_nbPage == _currentPdfPageNumber);
      if (_equals) {
        double _x = box.getX();
        int _originX = this.adapter.getOriginX();
        double _width = box.getWidth();
        double _minus = (_originX - _width);
        double _divide_6 = (_minus / this.width);
        double _plus = (_x + _divide_6);
        double _y = box.getY();
        int _originY = this.adapter.getOriginY();
        double _titleHeight_2 = this.adapterBox.getTitleHeight();
        double _minus_1 = (_originY - _titleHeight_2);
        double _divide_7 = (_minus_1 / this.height);
        double _plus_1 = (_y + _divide_7);
        double _width_1 = box.getWidth();
        double _titleHeight_3 = this.adapterBox.getTitleHeight();
        double _divide_8 = (_titleHeight_3 / this.height);
        boxesTitle.addBox(_plus, _plus_1, _width_1, _divide_8, box.getTitle());
      }
    }
    g2d.setColor(this.titleColor);
    List<Box> _list_1 = boxesTitle.getList();
    for (final Box box_1 : _list_1) {
      g2d.fill(this.convertBoxToRectangle(box_1));
    }
    g2d.setColor(this.outLineColor);
    List<Box> _list_2 = boxesTitle.getList();
    for (final Box box_2 : _list_2) {
      {
        Rectangle crtRec = this.convertBoxToRectangle(box_2);
        g2d.draw(crtRec);
        String _title = box_2.getTitle();
        double _titleHeight_4 = this.adapterBox.getTitleHeight();
        double _divide_9 = (_titleHeight_4 / 4);
        int _plus_2 = (crtRec.x + ((int) _divide_9));
        double _titleHeight_5 = this.adapterBox.getTitleHeight();
        double _divide_10 = (_titleHeight_5 / 1.5);
        int _plus_3 = (crtRec.y + ((int) _divide_10));
        g2d.drawString(_title, _plus_2, _plus_3);
      }
    }
  }
  
  /**
   * Converti un objet de type Box en objet de type Rectangle
   * @param box
   * @return La boîtes en Rectangle
   */
  public Rectangle convertBoxToRectangle(final Box box) {
    double _x = box.getX();
    double _multiply = (_x * this.width);
    int x = ((int) _multiply);
    double _y = box.getY();
    double _multiply_1 = (_y * this.height);
    int y = ((int) _multiply_1);
    double _width = box.getWidth();
    double _multiply_2 = (_width * this.width);
    int width = ((int) _multiply_2);
    double _height = box.getHeight();
    double _multiply_3 = (_height * this.height);
    int height = ((int) _multiply_3);
    return new Rectangle(x, y, width, height);
  }
}
