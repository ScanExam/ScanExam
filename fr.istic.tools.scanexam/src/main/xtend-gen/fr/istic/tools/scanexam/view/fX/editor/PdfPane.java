package fr.istic.tools.scanexam.view.fX.editor;

import fr.istic.tools.scanexam.view.fX.editor.Box;
import fr.istic.tools.scanexam.view.fX.editor.ControllerFXEditor;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

@SuppressWarnings("all")
public class PdfPane extends Pane {
  public PdfPane(final ControllerFXEditor controller) {
    super();
    this.controller = controller;
    ImageView _imageView = new ImageView();
    this.imageView = _imageView;
    this.imageView.setFitHeight(600);
    this.imageView.setFitWidth(600);
    this.imageView.setPreserveRatio(true);
    this.getChildren().add(this.imageView);
    this.setupEvents();
  }
  
  private ImageView imageView;
  
  private Image currentImage;
  
  private ControllerFXEditor controller;
  
  public Image setImage(final Image image) {
    Image _xblockexpression = null;
    {
      this.imageView.setImage(image);
      _xblockexpression = this.currentImage = image;
    }
    return _xblockexpression;
  }
  
  public boolean addZone(final Box toAdd) {
    return this.getChildren().add(toAdd);
  }
  
  public boolean removeZone(final Box toRemove) {
    return this.getChildren().remove(toRemove);
  }
  
  public double getImageViewHeight() {
    double _height = this.currentImage.getHeight();
    double _width = this.currentImage.getWidth();
    boolean _greaterThan = (_height > _width);
    if (_greaterThan) {
      return this.imageView.getFitHeight();
    }
    double _height_1 = this.currentImage.getHeight();
    double _width_1 = this.currentImage.getWidth();
    double _divide = (_height_1 / _width_1);
    double _fitHeight = this.imageView.getFitHeight();
    return (_divide * _fitHeight);
  }
  
  public double getImageViewWidth() {
    double _xifexpression = (double) 0;
    double _height = this.currentImage.getHeight();
    double _width = this.currentImage.getWidth();
    boolean _greaterThan = (_height > _width);
    if (_greaterThan) {
      double _width_1 = this.currentImage.getWidth();
      double _height_1 = this.currentImage.getHeight();
      double _divide = (_width_1 / _height_1);
      double _fitWidth = this.imageView.getFitWidth();
      return (_divide * _fitWidth);
    } else {
      _xifexpression = this.imageView.getFitWidth();
    }
    return _xifexpression;
  }
  
  public void setupEvents() {
    this.setOnMousePressed(new EventHandler<MouseEvent>() {
      public void handle(final MouseEvent event) {
        PdfPane.this.controller.mainMouseEvent(event);
      }
    });
    this.setOnMouseDragged(new EventHandler<MouseEvent>() {
      public void handle(final MouseEvent event) {
        PdfPane.this.controller.mainMouseEvent(event);
      }
    });
    this.setOnMouseReleased(new EventHandler<MouseEvent>() {
      public void handle(final MouseEvent event) {
        PdfPane.this.controller.mainMouseEvent(event);
      }
    });
    this.setOnScroll(new EventHandler<ScrollEvent>() {
      public void handle(final ScrollEvent event) {
        PdfPane.this.controller.ZoomImage(event);
      }
    });
  }
}
