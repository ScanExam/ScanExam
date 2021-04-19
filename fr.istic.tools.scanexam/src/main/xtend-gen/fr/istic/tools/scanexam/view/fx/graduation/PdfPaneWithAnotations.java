package fr.istic.tools.scanexam.view.fx.graduation;

import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.TextAnotation;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

@SuppressWarnings("all")
public class PdfPaneWithAnotations extends Pane {
  public PdfPaneWithAnotations(final ControllerFxGraduation controller) {
    this.controller = controller;
    ImageView _imageView = new ImageView();
    this.imageView = _imageView;
    this.imageView.setFitHeight(600);
    this.imageView.setFitWidth(600);
    this.imageView.setPreserveRatio(true);
    this.getChildren().add(this.imageView);
    this.getStyleClass().add("mainPane");
    this.setupEvents();
  }
  
  private ControllerFxGraduation controller;
  
  private ImageView imageView;
  
  private Image currentImage;
  
  public Image setImage(final Image image) {
    Image _xblockexpression = null;
    {
      this.imageView.setImage(image);
      _xblockexpression = this.currentImage = image;
    }
    return _xblockexpression;
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
  
  public boolean addNewAnotation(final double x, final double y) {
    ObservableList<Node> _children = this.getChildren();
    TextAnotation _textAnotation = new TextAnotation(x, y, 50, 50, "New Anotation");
    return _children.add(_textAnotation);
  }
  
  public boolean addAnotation(final double x, final double y, final double height, final double width, final String text) {
    ObservableList<Node> _children = this.getChildren();
    TextAnotation _textAnotation = new TextAnotation(x, y, height, width, text);
    return _children.add(_textAnotation);
  }
  
  public boolean removeAnotation(final TextAnotation anotation) {
    return this.getChildren().remove(anotation);
  }
  
  public boolean removeAllAnotations() {
    boolean _xblockexpression = false;
    {
      this.getChildren().clear();
      _xblockexpression = this.getChildren().add(this.imageView);
    }
    return _xblockexpression;
  }
  
  public void zoomTo(final double x, final double y, final double h, final double w) {
    Rectangle2D _rectangle2D = new Rectangle2D(x, y, w, h);
    this.imageView.setViewport(_rectangle2D);
  }
  
  public void unZoom() {
    this.imageView.setViewport(null);
  }
  
  public void setupEvents() {
    this.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        PdfPaneWithAnotations.this.controller.mainMouseEvent(event);
      }
    });
    this.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        PdfPaneWithAnotations.this.controller.mainMouseEvent(event);
      }
    });
    this.setOnMouseReleased(new EventHandler<MouseEvent>() {
      @Override
      public void handle(final MouseEvent event) {
        PdfPaneWithAnotations.this.controller.mainMouseEvent(event);
      }
    });
    this.setOnScroll(new EventHandler<ScrollEvent>() {
      @Override
      public void handle(final ScrollEvent event) {
        PdfPaneWithAnotations.this.controller.ZoomImage(event);
      }
    });
  }
}
