package fr.istic.tools.scanexam.controller;

import fr.istic.tools.scanexam.box.BoxList;
import fr.istic.tools.scanexam.controller.SelectionPresenterSwing;
import fr.istic.tools.scanexam.presenter.PdfPresenter;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import javax.swing.JPanel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.eclipse.xtext.xbase.lib.Exceptions;

/**
 * Controlleur Swing du pdf avec swing
 * @author Julien Cochet
 */
@SuppressWarnings("all")
public class PdfPresenterSwing extends PdfPresenter {
  /**
   * Indique si la mise à l'échelle se base sur la largueur ou non
   */
  private final boolean SCALE_ON_WIDTH = false;
  
  /**
   * PDF a afficher
   */
  private Image pdf;
  
  /**
   * Controlleur des boîtes de sélection
   */
  private SelectionPresenterSwing selectionController;
  
  /**
   * Echelle pour l'affichage
   */
  protected int scale;
  
  /**
   * Point d'origine sur l'axe X
   */
  protected int originX;
  
  /**
   * Point d'origine sur l'axe Y
   */
  protected int originY;
  
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
   * @param width Largeur de la fenêtre
   * @param height Hauteur de la fenêtre
   * @param pdfPath Chemin vers le pdf
   * @param selectionBoxes Objet contenant les boîtes de sélection
   */
  public PdfPresenterSwing(final int width, final int height, final String pdfPath, final BoxList selectionBoxes) {
    super(width, height, pdfPath, selectionBoxes);
    this.pdf = this.getImageFromPDF(pdfPath, 0);
    if (this.SCALE_ON_WIDTH) {
      int _width = this.pdf.getWidth(null);
      int _divide = (_width / width);
      int _plus = (_divide + 1);
      this.scale = _plus;
    } else {
      int _height = this.pdf.getHeight(null);
      int _divide_1 = (_height / height);
      int _plus_1 = (_divide_1 + 1);
      this.scale = _plus_1;
    }
    this.originX = 0;
    this.originY = 0;
    int selectWidth = this.pdf.getWidth(null);
    int selectHeight = this.pdf.getHeight(null);
    SelectionPresenterSwing _selectionPresenterSwing = new SelectionPresenterSwing(selectWidth, selectHeight, this.scale, this.originX, this.originY, selectionBoxes);
    this.selectionController = _selectionPresenterSwing;
    this.lastClickPoint = Optional.<Point>empty();
    this.view = Optional.<JPanel>empty();
    this.mouseHandler = new MouseAdapter() {
      @Override
      public void mousePressed(final MouseEvent e) {
        if (((e.getButton() == 2) || (e.getButton() == 3))) {
          PdfPresenterSwing.this.lastClickPoint = Optional.<Point>of(e.getPoint());
        }
      }
      
      @Override
      public void mouseReleased(final MouseEvent e) {
        PdfPresenterSwing.this.lastClickPoint = Optional.<Point>empty();
      }
      
      @Override
      public void mouseDragged(final MouseEvent e) {
        PdfPresenterSwing.this.moveOrigin(e);
      }
      
      @Override
      public void mouseWheelMoved(final MouseWheelEvent e) {
        PdfPresenterSwing.this.incrScale(e.getWheelRotation());
      }
    };
  }
  
  /**
   * Déplacement de le contenu
   * @param e Mouvement de la souris
   */
  private void moveOrigin(final MouseEvent e) {
    boolean _isPresent = this.lastClickPoint.isPresent();
    if (_isPresent) {
      Point dragPoint = e.getPoint();
      int _originX = this.originX;
      double _x = this.lastClickPoint.get().getX();
      double _minus = (dragPoint.x - _x);
      this.originX = (_originX + ((int) _minus));
      int _originY = this.originY;
      double _y = this.lastClickPoint.get().getY();
      double _minus_1 = (dragPoint.y - _y);
      this.originY = (_originY + ((int) _minus_1));
      this.selectionController.setOriginX(this.originX);
      this.selectionController.setOriginY(this.originY);
      this.repaint();
      this.lastClickPoint = Optional.<Point>of(dragPoint);
    }
  }
  
  /**
   * Incremente l'échelle
   * @param value Valeur à ajouter
   */
  private void incrScale(final int value) {
    if (((this.scale + value) < 1)) {
      this.scale = 1;
    } else {
      int _scale = this.scale;
      this.scale = (_scale + value);
    }
    this.selectionController.setScale(this.scale);
    this.repaint();
  }
  
  private BufferedImage getImageFromPDF(final String filePath, final int pageindex) {
    try {
      File _file = new File(filePath);
      PDDocument document = PDDocument.load(_file);
      PDFRenderer renderer = new PDFRenderer(document);
      BufferedImage img = renderer.renderImageWithDPI(pageindex, 300);
      document.close();
      return img;
    } catch (final Throwable _t) {
      if (_t instanceof IOException) {
        final IOException e = (IOException)_t;
        e.printStackTrace();
        return null;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
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
  public int getScale() {
    return this.scale;
  }
  
  public int getOriginX() {
    return this.originX;
  }
  
  public int getOriginY() {
    return this.originY;
  }
  
  public MouseAdapter getMouseHandler() {
    return this.mouseHandler;
  }
  
  public Image getPdf() {
    return this.pdf;
  }
  
  public SelectionPresenterSwing getSelectionController() {
    return this.selectionController;
  }
  
  /**
   * SETTERS
   */
  public void setView(final JPanel view) {
    this.view = Optional.<JPanel>of(view);
    this.selectionController.setView(this.view.get());
  }
}