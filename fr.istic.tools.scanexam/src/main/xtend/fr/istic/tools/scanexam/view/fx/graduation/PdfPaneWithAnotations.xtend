package fr.istic.tools.scanexam.view.fx.graduation

import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation.SelectedTool
import javafx.event.EventHandler
import javafx.geometry.Rectangle2D
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.Pane

/**
 * Affiche un pdf avec des annotations
 * //TODO Ajouter la possibilité de dessiner sur le pdf 
 */
class PdfPaneWithAnotations extends Pane {
	new(ControllerFxGraduation controller) {
		this.controller = controller
		imageView = new ImageView()
		imageView.fitHeight = 600;
		imageView.fitWidth = 600;
		imageView.preserveRatio = true
		this.children.add(imageView)
		this.styleClass.add("mainPane")
		setupEvents
	}

	ControllerFxGraduation controller;
	ImageView imageView
	Image currentImage

	/**
	 * Displays annotations of a question and student,
	 * @param qItem A question
	 * @param sItem A student
	 */
	def void displayAnnotationsFor(QuestionItemGraduation qItem, StudentItemGraduation sItem) {
		removeAllAnotations
		var ids = controller.service.getAnnotationIds(qItem.questionId, sItem.studentId)
		for (int id : ids) {
			addAnotation(
				controller.service.getAnnotationX(id, qItem.questionId, sItem.studentId),
				controller.service.getAnnotationY(id, qItem.questionId, sItem.studentId),
				controller.service.getAnnotationHeight(id, qItem.questionId, sItem.studentId),
				controller.service.getAnnotationWidth(id, qItem.questionId, sItem.studentId),
				controller.service.getAnnotationPointerX(id, qItem.questionId, sItem.studentId),
				controller.service.getAnnotationPointerY(id, qItem.questionId, sItem.studentId),
				controller.service.getAnnotationText(id, qItem.questionId, sItem.studentId),
				id
			)
		}
	}

	/**
	 * Changes the displayed Image
	 */
	def setImage(Image image) {
		imageView.image = image
		currentImage = image
	}

	/**
	 * Gets the current height of the imageView contained in this
	 */
	def getImageViewHeight() {
		if (currentImage.height > currentImage.width) {
			return imageView.fitHeight
		}
		return (currentImage.height / currentImage.width) * imageView.fitHeight

	}

	/**
	 * Gets the current width of the imageView contained in this
	 */
	def getImageViewWidth() {
		if (currentImage.height > currentImage.width) {
			return (currentImage.width / currentImage.height) * imageView.fitWidth
		} else {
			imageView.fitWidth
		}
	}

	/**
	 * Adds a new annotation to the display
	 */
	def addNewAnotation(double x, double y) {
		var anot = new TextAnotation(x, y, "New Anotation", this)
		this.children.addAll(anot.allParts)
		anot
	}

	def addAnotation(double x, double y, double height, double width, double pointerX, double pointerY, String text,
		int id) {
		var anot = new TextAnotation(x, y, text, this)
		anot.move(x, y);
		anot.movePointer(pointerX, pointerY)
		anot.annotId = id
		this.children.addAll(anot.allParts)
	}

	/**
	 * Removes an annotation from the display
	 * @param annotation to remove
	 */
	def removeAnotation(TextAnotation anotation) {
		children.removeAll(anotation.allParts)
	}

	/**
	 * Removes all annotation from the display
	 */
	def removeAllAnotations() {
		children.clear
		children.add(imageView)
	}

	/**
	 * Zooms to a part of the imamge
	 * @param the coordinates (in pixels of the image) where to zoom in
	 */
	def zoomTo(double x, double y, double h, double w) {
		imageView.viewport = new Rectangle2D(x, y, w, h)
	}

	/**
	 * UnZooms the image
	 */
	def unZoom() {
		imageView.viewport = null;
	}

	/**
	 * Handles the move anotation event call
	 */
	def handleMoveAnnotation(TextAnotation anot, MouseEvent e) {
		controller.currentTool = SelectedTool.MOVE_ANOTATION_TOOL
		controller.currentAnotation = anot;
	}

	/**
	 * Handles the move pointer event call
	 */
	def handleMovePointer(TextAnotation anot, MouseEvent e) {
		controller.currentTool = SelectedTool.MOVE_POINTER_TOOL
		controller.currentAnotation = anot;
	}

	/**
	 * Handles the rename anotation event call
	 */
	def handleRename(TextAnotation anot) {
		controller.updateAnnotation(anot)
	}

	/**
	 * Handles the remove anotation event call
	 */
	def handleRemove(TextAnotation anot) {
		controller.removeAnnotation(anot)
		removeAnotation(anot)
	}

	def setupEvents() {
		onMousePressed = new EventHandler<MouseEvent>() {

			override handle(MouseEvent event) {
				controller.mainMouseEvent(event)
			}

		}
		onMouseDragged = new EventHandler<MouseEvent>() {

			override handle(MouseEvent event) {
				controller.mainMouseEvent(event)
			}

		}
		onMouseReleased = new EventHandler<MouseEvent>() {

			override handle(MouseEvent event) {
				controller.mainMouseEvent(event)
			}

		}
		onScroll = new EventHandler<ScrollEvent>() {

			override handle(ScrollEvent event) {
				controller.ZoomImage(event)
			}

		}
	}
}
