package fr.istic.tools.scanexam.view.fX;

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.launcher.LauncherFX
import fr.istic.tools.scanexam.view.fX.Box.BoxType
import java.io.File
import java.util.Arrays
import java.util.LinkedList
import javafx.embed.swing.SwingFXUtils
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import org.apache.logging.log4j.LogManager

class ControllerFXEditor {

	EditorAdapterFX editor;

	def void setEditorAdapterFX(EditorAdapterFX editor) {
		this.editor = editor
	}

	double maxX;
	double maxY;
	var pdfLoaded = false;

	var logger = LogManager.logger

	enum SelectedTool {
		NO_TOOL,
		QUESTION_AREA,
		ID_AREA,
		QR_AREA,
		MOVE_CAMERA_TOOL,
		MOVE_TOOL,
		RESIZE_TOOL

	}

	// ** FXML TAGS **//	
	@FXML
	Pane mainPane;

	@FXML
	ImageView pdfView;

	@FXML
	ListView<VBox> questionList;

	@FXML
	ChoiceBox<Integer> pageChoice;

	@FXML
	Label introLabel;

	@FXML
	Label currentToolLabel;

	@FXML
	Label pageNumberLabel;

	@FXML
	def void pressed() {
	}

	@FXML
	def void questionAreaPressed() {
		setToQuestionAreaTool
	}

	@FXML
	def void iDAreaPressed() {
		setToIDAreaTool
	}

	@FXML
	def void qRArearessed() {
		setToQRAreaTool
	}

	@FXML
	def void movePressed() {
		setToMoveCameraTool
	}

	@FXML
	def void nextPagePressed() {
		nextPage
	}

	@FXML
	def void newTemplatePressed() {
		loadPdf();
	}

	@FXML
	def void saveTemplatePressed() {
		saveTemplate();
	}

	@FXML
	def void loadTemplatePressed() {
		loadTemplate();
	}

	@FXML
	def void previousPagePressed() {
		previousPage
	}

	@FXML
	def void switchToCorrectorPressed() {
		LauncherFX.swapToGraduator
	}

	@FXML
	def void mainMouseEvent(MouseEvent e) { // check if rightclick or not, maybe add to choose mouse actuioin
		if (pdfLoaded) {
			if (e.button == MouseButton.SECONDARY) {
				moveImage(e)
			} else {
				chooseMouseAction(e);
			}
		}
	}

	def void chooseMouseAction(MouseEvent e) {
		switch currentTool {
			case QUESTION_AREA: {
				createBox(e);
			}
			case ID_AREA: {
				createBox(e);
			}
			case QR_AREA: {
				createBox(e);
			}
			case MOVE_TOOL: {
				moveBox(e)
			}
			case RESIZE_TOOL: {
				resizeBox(e)
			}
			case MOVE_CAMERA_TOOL: {
				moveImage(e);
			}
			case NO_TOOL: {
			}
		}
	}

	var mouseOriginX = 0d;
	var mouseOriginY = 0d;
	var objectOriginX = 0d;
	var objectOriginY = 0d;

	var boxes = new LinkedList<Box>();
	Box currentRectangle = null;

	def void createBox(MouseEvent e) {
		var mousePositionX = Math.max(FXSettings.BOX_BORDER_THICKNESS,
			Math.min(e.x, maxX - FXSettings.BOX_BORDER_THICKNESS));
		var mousePositionY = Math.max(FXSettings.BOX_BORDER_THICKNESS,
			Math.min(e.y, maxY - FXSettings.BOX_BORDER_THICKNESS));
		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) { // TODO add type checks
			mouseOriginX = mousePositionX
			mouseOriginY = mousePositionY
			var source = e.source as Pane
			currentRectangle = createBox(mousePositionX, mousePositionY);
			currentRectangle.listViewBox.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				override handle(MouseEvent event) {
					highlightBox((event.source as ListViewBox).parentBox);
				}

			})
			
			source.children.add(currentRectangle);
			source.children.add(currentRectangle.getText());

		}
		if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {

			var xDelta = mousePositionX - mouseOriginX;
			var yDelta = mousePositionY - mouseOriginY;
			if (xDelta > 0) {
				currentRectangle.width(xDelta)
			} else {
				currentRectangle.width(Math.abs(xDelta))
				currentRectangle.x(mouseOriginX - Math.abs(xDelta))
			}

			if (yDelta > 0) {
				currentRectangle.height(yDelta)
			} else {
				currentRectangle.height(Math.abs(yDelta))
				currentRectangle.y(mouseOriginY - Math.abs(yDelta))

			}

		}
		if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
			addBox(currentRectangle);
		}
	}

	def void moveBox(MouseEvent e) {
		var mousePositionX = Math.max(FXSettings.BOX_BORDER_THICKNESS,
			Math.min(e.x, maxX - FXSettings.BOX_BORDER_THICKNESS));
		var mousePositionY = Math.max(FXSettings.BOX_BORDER_THICKNESS,
			Math.min(e.y, maxY - FXSettings.BOX_BORDER_THICKNESS));

		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
		}
		if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			currentRectangle.x(Math.min(mousePositionX,
				maxX - FXSettings.BOX_BORDER_THICKNESS - currentRectangle.width))
			currentRectangle.y(Math.min(mousePositionY,
				maxY - FXSettings.BOX_BORDER_THICKNESS - currentRectangle.height))
		}
		if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
		}
	}

	def void resizeBox(MouseEvent e) {
		var mousePositionX = Math.max(FXSettings.BOX_BORDER_THICKNESS,
			Math.min(e.x, maxX - FXSettings.BOX_BORDER_THICKNESS));
		var mousePositionY = Math.max(FXSettings.BOX_BORDER_THICKNESS,
			Math.min(e.y, maxY - FXSettings.BOX_BORDER_THICKNESS));
		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
			
		}
		if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			currentRectangle.width(Math.abs(currentRectangle.x - mousePositionX))
			currentRectangle.height(Math.abs(currentRectangle.y - mousePositionY))
		}
		if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
			resizeBox(currentRectangle)
		}
	}

	/**
	 * Used to move around the image in the parent pane
	 */
	def void moveImage(MouseEvent e) {

		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) { // TODO add type checks
			mouseOriginX = e.screenX
			mouseOriginY = e.screenY
			var source = e.source as Node

			objectOriginX = source.layoutX
			objectOriginY = source.layoutY

			mainPane.cursor = Cursor.CLOSED_HAND
		}
		if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			var source = e.source as Node
			source.layoutX = objectOriginX + (e.screenX - mouseOriginX)
			source.layoutY = objectOriginY + (e.screenY - mouseOriginY)
		}
		if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
			mainPane.cursor = Cursor.OPEN_HAND
		}
	}

	/**
	 * Used to zoom in and out the pdf image
	 * 
	 * Using the scale allows the children of the pane to also scale accordingly
	 */
	@FXML
	def void ZoomImage(ScrollEvent e) {
		var source = e.source as Node
		if (e.deltaY < 0) {
			source.scaleX = source.scaleX * 0.95
			source.scaleY = source.scaleY * 0.95
		} else {
			source.scaleX = source.scaleX * 1.05
			source.scaleY = source.scaleY * 1.05
		}
	}

	// ------ TOOL SELECTORS ------//
	/**
	 * Setters for the current tool selected
	 */
	var currentTool = SelectedTool.NO_TOOL

	def void setToMoveCameraTool() {
		mainPane.cursor = Cursor.OPEN_HAND
		currentToolLabel.text = LanguageManager.translate("label.tool.moveCamera")
		currentTool = SelectedTool.MOVE_CAMERA_TOOL
	}

	def void setToQuestionAreaTool() {
		mainPane.cursor = Cursor.DEFAULT
		currentToolLabel.text = LanguageManager.translate("label.tool.questionZone")
		currentTool = SelectedTool.QUESTION_AREA
	}

	def void setToIDAreaTool() {
		mainPane.cursor = Cursor.DEFAULT
		currentToolLabel.text = LanguageManager.translate("label.tool.idZone")

		currentTool = SelectedTool.ID_AREA
	}

	def void setToQRAreaTool() {
		mainPane.cursor = Cursor.DEFAULT
		currentToolLabel.text = LanguageManager.translate("label.tool.qrZone")
		currentTool = SelectedTool.QR_AREA
	}

	def void setToMoveTool() {
		mainPane.cursor = Cursor.DEFAULT
		currentToolLabel.text = LanguageManager.translate("label.tool.moveZone")
		currentTool = SelectedTool.MOVE_TOOL
	}

	def void setToResizeTool() {
		mainPane.cursor = Cursor.DEFAULT
		currentToolLabel.text = LanguageManager.translate("label.tool.resizeZone")
		currentTool = SelectedTool.RESIZE_TOOL
	}

	def void setToNoTool() {
		mainPane.cursor = Cursor.DEFAULT
		currentToolLabel.text = LanguageManager.translate("label.tool.none")
		currentTool = SelectedTool.NO_TOOL
	}

	/**
	 * returns a new Box with the right type corresponding to the current tool //TODO maybe move to box as a static method
	 */
	var questionCounter = 1;

	def Box createBox(double x, double y) {
		switch currentTool {
			case QUESTION_AREA: {
				new Box("Question " + questionCounter++, editor.presenter.getPresenterPdf.currentPdfPageNumber, BoxType.QUESTION, x, y);
			}
			case ID_AREA: {
				new Box("ID Zone", editor.presenter.getPresenterPdf.currentPdfPageNumber, BoxType.ID, x, y);
			}
			case QR_AREA: {
				new Box("QR Zone", editor.presenter.getPresenterPdf.currentPdfPageNumber, BoxType.QR, x, y);
			}
			default: {
			}
		}
	}

	/**
	 * notifies the rest of the program to the addition of a new box
	 * 
	 * Called when we finish creating a new box (Mouse release)
	 */
	def addBox(Box box) {
		editor.addBox(box);
		renameBox(box,box.name)//TODO fix
		var lb = box.listViewBox;
		/*lb.upAction = new EventHandler<ActionEvent>() {

			override handle(ActionEvent event) {
			}

		}*/
		/*lb.downAction = new EventHandler<ActionEvent>() {

			override handle(ActionEvent event) {
			}

		}*/
		lb.removeAction = new EventHandler<ActionEvent>() {

			override handle(ActionEvent event) {
				System.out.println("Remove "+box.getName());
				removeBox(box);
			}

		}

		lb.textCommit = new EventHandler<ActionEvent>() {

			override handle(ActionEvent event) {
				renameBox(box, (event.source as TextField).text)
				box.listViewBox.toggleRenaming
			}

		}
		lb.pointsCommit = new EventHandler<ActionEvent>() {

			override handle(ActionEvent event) {
				changePoints(box, (event.source as TextField).text)
				box.listViewBox.togglePointChange
			}

		}
		lb.moveAction = new EventHandler<ActionEvent>() {

			override handle(ActionEvent event) {
				setToMoveTool
				currentRectangle = box
			}

		}
		lb.resizeAction = new EventHandler<ActionEvent>() {

			override handle(ActionEvent event) {
				setToResizeTool
				currentRectangle = box
			}

		}
		
		lb.renameOption = new EventHandler<ActionEvent>() {

			override handle(ActionEvent event) {
				box.listViewBox.toggleRenaming
			}

		}
		lb.changePoints = new EventHandler<ActionEvent>() {

			override handle(ActionEvent event) {
				box.listViewBox.togglePointChange
			}

		}
		questionList.items.add(lb);

		System.out.println(mainPane.getChildren());
		boxes.add(box);
	}

	// --- ACTIONS ON THE MODEL ---//
	def renameBox(Box box, String newName) {
		box.listViewBox.labelText = newName
		editor.presenter.presenterQuestionZone.renameQuestion(box.boxId, box.name)
	}

	def moveBox(Box box) {
		editor.presenter.presenterQuestionZone.moveQuestion(box.boxId, box.x, box.y);
	}

	def resizeBox(Box box) {
		editor.presenter.presenterQuestionZone.resizeQuestion(box.boxId, box.height, box.width);
	}

	/**
	 * notifies the rest of the program to the removal of a box
	 */
	def removeBox(Box box) {
		questionList.items.remove(box.listViewBox)
		mainPane.children.remove(box.getText());
		mainPane.children.remove(box)
		boxes.remove(box)
		editor.removeBox(box);
		setToNoTool
	}
	
	def changePoints(Box box,String points) {
		box.listViewBox.pointsText = points
		var number = Integer.parseInt(points);
		editor.presenter.presenterQuestionZone.changeQuestionWorth(box.boxId,number);
	}

	// ------------//
	/**
	 * load a new pdf to start the creation of a new template
	 */
	def loadPdf() {

		var fileChooser = new FileChooser();
		fileChooser.extensionFilters.add(new ExtensionFilter("PDF files", Arrays.asList("*.pdf")));
		fileChooser.initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator") +
			"Documents");
		var file = fileChooser.showOpenDialog(mainPane.scene.window)

		if (file !== null) {
			editor.presenter.getPresenterPdf.create(file);
			renderDocument();
		} else {
			logger.warn("File not chosen")
		}

	}

	def saveTemplate() {
		var fileChooser = new FileChooser();
		fileChooser.extensionFilters.add(new ExtensionFilter("XMI files", Arrays.asList("*.xmi")));
		fileChooser.initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator") +
			"Documents");
		var file = fileChooser.showSaveDialog(mainPane.scene.window)

		if (file !== null) {
			editor.presenter.save(file.path);
		} else {
			logger.warn("File not chosen")
		}
	}

	def loadTemplate() {
		var fileChooser = new FileChooser();
		fileChooser.extensionFilters.add(new ExtensionFilter("XMI Files", Arrays.asList("*.xmi")));
		fileChooser.initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator") +
			"Documents");
		var file = fileChooser.showOpenDialog(mainPane.scene.window)

		if (file !== null) {
			editor.presenter.load(file.path);
			loadBoxes();
			renderDocument();
		} else {
			logger.warn("File not chosen")
		}
	}

	def loadBoxes() {
		/*editor.presenter.presenterQuestionZone.initLoading
		while (editor.presenter.presenterQuestionZone.loadNextQuestion) {
			var box = new Box(
				editor.presenter.presenterQuestionZone.currentQuestionName,
				editor.presenter.presenterQuestionZone.currentQuestionPage,
				BoxType.QUESTION,
				editor.presenter.presenterQuestionZone.currentQuestionX,
				editor.presenter.presenterQuestionZone.currentQuestionY,
				editor.presenter.presenterQuestionZone.currentQuestionHeight,
				editor.presenter.presenterQuestionZone.currentQuestionWidth
			);
			addBox(box);
		}*/
		
		for (var p = 0;p < editor.presenter.getPresenterPdf.totalPdfPageNumber;p++) {
			var ids = editor.presenter.presenterQuestionZone.initLoading(p)
			for (int i:ids) {
				var box = new Box(
					editor.presenter.presenterQuestionZone.questionName(i),
					p,
					BoxType.QUESTION,
					editor.presenter.presenterQuestionZone.questionX(i),
					editor.presenter.presenterQuestionZone.questionY(i),
					editor.presenter.presenterQuestionZone.questionHeight(i),
					editor.presenter.presenterQuestionZone.questionWidth(i)
				)
				addBox(box)
				boxes.add(box)
				mainPane.children.add(box)
			}
		}
		
		showOnlyPage(0)
	}
	

	/**
	 * initialise the choicebox containing all the page numbers of the pdf
	 * to call whenever we load a new pdf into the editor
	 */
	def initPageSelection() {
		pageChoice.items.clear
	}

	/**
	 * feches the current buffered image in the presenter representing the pdf and converts it and loads into the imageview
	 */
	def renderDocument() {

		pageNumberLabel.text = editor.presenter.getPresenterPdf.currentPdfPageNumber + 1 + "/" + editor.presenter.getPresenterPdf.totalPdfPageNumber
		introLabel.visible = false
		val image = editor.presenter.getPresenterPdf.currentPdfPage
		pdfView.image = SwingFXUtils.toFXImage(image, null);
		var fitW = pdfView.fitWidth
		var fitH = pdfView.fitHeight
		if (image.height > image.width) { // calculates the actual image coordinates
			maxY = fitH
			maxX = (pdfView.image.width / pdfView.image.height) * fitW
		} else {
			maxY = (pdfView.image.height / pdfView.image.width) * fitH
			maxX = fitW
		}
		pdfLoaded = true
	}

	/**
	 * changes the selected page to load and then renders it
	 */
	def selectPage(int pageNumber) {
		editor.presenter.getPresenterPdf.choosePdfPage(pageNumber);
		renderDocument();
		showOnlyPage(editor.presenter.getPresenterPdf.currentPdfPageNumber);
	}

	/**
	 * goes to the next page of the current pdf
	 */
	def nextPage() {
		editor.presenter.getPresenterPdf.nextPdfPage();
		renderDocument
		showOnlyPage(editor.presenter.getPresenterPdf.currentPdfPageNumber);
	}

	def previousPage() {
		editor.presenter.getPresenterPdf.previousPdfPage();
		renderDocument
		showOnlyPage(editor.presenter.getPresenterPdf.currentPdfPageNumber);
	}

	def showOnlyPage(int page) {
		for (Box b : boxes) {
			if (b.pageNumber == page) {
				b.visible = true;

			} else {
				b.visible = false;
			}
		}
	}

	/**
	 * Highlights the Box box, called when we click on a box on the listview
	 */
	Box highlightedBox = null;

	def highlightBox(Box box) {
		if (highlightedBox !== null) {
			highlightedBox.focus = false;
		}
		highlightedBox = box;
		highlightedBox.focus = true
	}

}
