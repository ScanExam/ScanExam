package fr.istic.tools.scanexam.view.fx.graduation;

import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.QuestionItemGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.StudentItemGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.TextAnotation;
import java.util.List;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
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
  
  /**
   * Displays annotations of a question and student,
   * @param qItem A question
   * @param sItem A student
   */
  public void displayAnnotationsFor(final QuestionItemGraduation qItem, final StudentItemGraduation sItem) {
    this.removeAllAnotations();
    List<Integer> ids = this.controller.getService().getAnnotationIds(qItem.getQuestionId(), sItem.getStudentId());
    for (final int id : ids) {
      this.addAnotation(
        this.controller.getService().getAnnotationX(id, qItem.getQuestionId(), sItem.getStudentId()), this.controller.getService().getAnnotationY(id, qItem.getQuestionId(), sItem.getStudentId()), 
        this.controller.getService().getAnnotationHeight(id, qItem.getQuestionId(), sItem.getStudentId()), this.controller.getService().getAnnotationWidth(id, qItem.getQuestionId(), sItem.getStudentId()), 
        this.controller.getService().getAnnotationPointerX(id, qItem.getQuestionId(), sItem.getStudentId()), this.controller.getService().getAnnotationPointerY(id, qItem.getQuestionId(), sItem.getStudentId()), 
        this.controller.getService().getAnnotationText(id, qItem.getQuestionId(), sItem.getStudentId()), id);
    }
  }
  
  /**
   * Changes the displayed Image
   */
  public Image setImage(final Image image) {
    Image _xblockexpression = null;
    {
      this.imageView.setImage(image);
      _xblockexpression = this.currentImage = image;
    }
    return _xblockexpression;
  }
  
  /**
   * Gets the current height of the imageView contained in this
   */
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
  
  /**
   * Gets the current width of the imageView contained in this
   */
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
  
  /**
   * Adds a new annotation to the display
   */
  public TextAnotation addNewAnotation(final double x, final double y) {
    TextAnotation _xblockexpression = null;
    {
      TextAnotation anot = new TextAnotation(x, y, "New Anotation", this);
      this.getChildren().addAll(anot.getAllParts());
      _xblockexpression = anot;
    }
    return _xblockexpression;
  }
  
  public boolean addAnotation(final double x, final double y, final double height, final double width, final double pointerX, final double pointerY, final String text, final int id) {
    boolean _xblockexpression = false;
    {
      TextAnotation anot = new TextAnotation(x, y, text, this);
      anot.move(x, y);
      anot.movePointer(pointerX, pointerY);
      anot.setAnnotId(id);
      _xblockexpression = this.getChildren().addAll(anot.getAllParts());
    }
    return _xblockexpression;
  }
  
  /**
   * Removes an annotation from the display
   * @param annotation to remove
   */
  public boolean removeAnotation(final TextAnotation anotation) {
    return this.getChildren().removeAll(anotation.getAllParts());
  }
  
  /**
   * Removes all annotation from the display
   */
  public boolean removeAllAnotations() {
    boolean _xblockexpression = false;
    {
      this.getChildren().clear();
      _xblockexpression = this.getChildren().add(this.imageView);
    }
    return _xblockexpression;
  }
  
  /**
   * Zooms to a part of the imamge
   * @param the coordinates (in pixels of the image) where to zoom in
   */
  public void zoomTo(final double x, final double y, final double h, final double w) {
    Rectangle2D _rectangle2D = new Rectangle2D(x, y, w, h);
    this.imageView.setViewport(_rectangle2D);
  }
  
  /**
   * UnZooms the image
   */
  public void unZoom() {
    this.imageView.setViewport(null);
  }
  
  /**
   * Handles the move anotation event call
   */
  public void handleMoveAnnotation(final TextAnotation anot, final MouseEvent e) {
    this.controller.setCurrentTool(ControllerFxGraduation.SelectedTool.MOVE_ANOTATION_TOOL);
    this.controller.setCurrentAnotation(anot);
  }
  
  /**
   * Handles the move pointer event call
   */
  public void handleMovePointer(final TextAnotation anot, final MouseEvent e) {
    this.controller.setCurrentTool(ControllerFxGraduation.SelectedTool.MOVE_POINTER_TOOL);
    this.controller.setCurrentAnotation(anot);
  }
  
  /**
   * Handles the rename anotation event call
   */
  public void handleRename(final TextAnotation anot) {
    this.controller.updateAnnotation(anot);
  }
  
  /**
   * Handles the remove anotation event call
   */
  public boolean handleRemove(final TextAnotation anot) {
    boolean _xblockexpression = false;
    {
      this.controller.removeAnnotation(anot);
      _xblockexpression = this.removeAnotation(anot);
    }
    return _xblockexpression;
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
