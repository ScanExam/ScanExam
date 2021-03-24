package fr.istic.tools.scanexam.view.fX.editor;

import fr.istic.tools.scanexam.config.LanguageManager
import fr.istic.tools.scanexam.launcher.LauncherFX
import fr.istic.tools.scanexam.view.fX.EditorAdapterFX
import fr.istic.tools.scanexam.view.fX.FXSettings
import java.io.File
import java.util.Arrays
import javafx.embed.swing.SwingFXUtils
import javafx.fxml.FXML
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import org.apache.logging.log4j.LogManager
import javafx.scene.layout.AnchorPane
import javafx.scene.control.ScrollPane
import javafx.scene.control.ToggleButton

class ControllerFXEditor {

	
	EditorAdapterFX editor;

	def void setEditorAdapterFX(EditorAdapterFX editor) {
		this.editor = editor
	}
	
	def getEditor(){
		editor
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
	
	def getSelectedTool(){
		this.currentTool
	}

	// ** FXML TAGS **//	
	
	PdfPane mainPane;

	@FXML
	ScrollPane questionListContainer;
	
	@FXML
	ScrollPane gradeListContainer;

	@FXML
	ChoiceBox<Integer> pageChoice;


	@FXML
	Label pageNumberLabel;
	
	@FXML
	ToggleButton createBoxButton;
	
	QuestionListEditor questionList;
	
	QuestionOptionsEditor questionEditor;
	
	@FXML
	AnchorPane mainPaneContainer

	@FXML
	def void pressed() {
	}

	@FXML
	def void questionAreaPressed() {
		if (createBoxButton.selected) {
			setToQuestionAreaTool
		}else {
			setToNoTool
		}
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
	
	def getMainPane(){
		mainPane
	}
	
	
	
	def getQuestionList(){
		questionList
	}
	
	
	/**
	 * Called When we decide to focus on a specific question
	 */
	def void selectQuestion(QuestionItemEditor item){
		if (item === null) {
			questionList.removeFocus
			questionEditor.hideAll
			return
		}
		questionList.select(item)
		questionEditor.select(item)
	}
	
	
	def void init(){
		
		mainPane = new PdfPane(this);
		mainPaneContainer.children.add(mainPane)
		
		questionList = new QuestionListEditor(this);
		questionListContainer.content = questionList
		
		questionEditor = new QuestionOptionsEditor(this);
		gradeListContainer.content = questionEditor
		
		
		//Permet de définir pour chaque item de pageChoice une action : aller à la page sélectionnée
		pageChoice.setOnAction([ event |
			var pdfPresenter = editor.presenter.getPresenterPdf()
		    var selectedIndex = pageChoice.getSelectionModel().getSelectedIndex();
		    //var selectedItem = pageChoice.getSelectionModel().getSelectedItem();
		    
		    pdfPresenter.goToPage(selectedIndex)
		    renderDocument
		]);
		
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

	Box currentRectangle = null;

	/**
	 * Called when we click and drag on the pdf with the create question too selected
	 * will not create the question if the zone is too small
	 */
	def void createBox(MouseEvent e) {
		var mousePositionX = Math.max(FXSettings.BOX_BORDER_THICKNESS,
								Math.min(e.x, maxX - FXSettings.BOX_BORDER_THICKNESS));
		var mousePositionY = Math.max(FXSettings.BOX_BORDER_THICKNESS,
							Math.min(e.y, maxY - FXSettings.BOX_BORDER_THICKNESS));
		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) { // TODO add type checks
			mouseOriginX = mousePositionX
			mouseOriginY = mousePositionY
		
			
			currentRectangle = createZone(mousePositionX, mousePositionY);
			mainPane.addZone(currentRectangle);

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
		if (e.getEventType() == MouseEvent.MOUSE_RELEASED) 
		{
			if (currentRectangle.width > FXSettings.MINIMUM_ZONE_SIZE && currentRectangle.height > FXSettings.MINIMUM_ZONE_SIZE)
			{
				questionList.newQuestion(currentRectangle)
			}
			else
			{
				mainPane.removeZone(currentRectangle);
			}
			setToNoTool
			createBoxButton.selected = false
		}
	}

/**
 * OLD CODE
 * Called when we click on a pdf with the move tool selected
 * the box is limited to inside the pdf
 */
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
			//resizeBox(currentRectangle)
		}
	}

	/**
	 * Used to move around the image in the parent pane
	 * Called when we right click on the pdf
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
			mainPane.cursor = Cursor.DEFAULT
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
		currentTool = SelectedTool.MOVE_CAMERA_TOOL
	}

	def void setToQuestionAreaTool() {
		mainPane.cursor = Cursor.DEFAULT
		
		currentTool = SelectedTool.QUESTION_AREA
	}

	def void setToIDAreaTool() {
		mainPane.cursor = Cursor.DEFAULT
		

		currentTool = SelectedTool.ID_AREA
	}

	def void setToQRAreaTool() {
		mainPane.cursor = Cursor.DEFAULT
	
		currentTool = SelectedTool.QR_AREA
	}

	def void setToMoveTool() {
		mainPane.cursor = Cursor.DEFAULT
		
		currentTool = SelectedTool.MOVE_TOOL
	}

	def void setToResizeTool() {
		mainPane.cursor = Cursor.DEFAULT
		
		currentTool = SelectedTool.RESIZE_TOOL
	}

	def void setToNoTool() {
		mainPane.cursor = Cursor.DEFAULT
		
		currentTool = SelectedTool.NO_TOOL
	}

	/**
	 * returns a new Box with the right type corresponding to the current tool //TODO maybe move to box as a static method
	 */

	def Box createZone(double x, double y) {
		
		new Box(x, y,0,0);
		
	}




	
	/**
	 * Called when we press create template
	 * load a new pdf to start the creation of a new template
	 */
	def loadPdf() {
		clearVue
		var fileChooser = new FileChooser();
		fileChooser.extensionFilters.add(new ExtensionFilter("PDF files", Arrays.asList("*.pdf")));
		fileChooser.initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator") +
			"Documents");
		var file = fileChooser.showOpenDialog(mainPane.scene.window)

		if (file !== null) {
			clearVue
			editor.presenter.getPresenterPdf.create(file);
			renderDocument();
		} else {
			logger.warn("File not chosen")
		}

	}

	/**
	 * Saves the current model to a XMI file
	 */
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
	/**
	 * Loads new model from an xmi file
	 */
	def loadTemplate() {
		
		var fileChooser = new FileChooser();
		fileChooser.extensionFilters.add(new ExtensionFilter("XMI Files", Arrays.asList("*.xmi")));
		fileChooser.initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator") +
			"Documents");
		var file = fileChooser.showOpenDialog(mainPane.scene.window)

		if (file !== null) {
			clearVue
			editor.presenter.load(file.path);
			renderDocument();
			loadBoxes();
		} else {
			logger.warn("File not chosen")
		}
	}
	/**
	 * called to load each question from the model into the vue
	 */
	def loadBoxes() {
		
		for (var p = 0;p < editor.presenter.getPresenterPdf.totalPdfPageNumber;p++) {
			var ids = editor.presenter.presenterQuestionZone.initLoading(p)
			for (int i:ids) {
				
				var box = new Box(
					editor.presenter.presenterQuestionZone.questionX(i) * maxX,
					editor.presenter.presenterQuestionZone.questionY(i) * maxY,
					editor.presenter.presenterQuestionZone.questionWidth(i) * maxX,
					editor.presenter.presenterQuestionZone.questionHeight(i) * maxY		
				)
				print("loading width for " + i + " = " + editor.presenter.presenterQuestionZone.questionWidth(i));
				mainPane.addZone(box);
				questionList.loadQuestion(box,editor.presenter.presenterQuestionZone.questionName(i),p,i)
			}
		}
		
		questionList.showOnlyPage(editor.presenter.getPresenterPdf.currentPdfPageNumber)
	}
	

	/**
	 * initialise the choicebox containing all the page numbers of the pdf
	 * to call whenever we load a new pdf into the editor
	 */
	def initPageSelection() {
		pageChoice.items.clear
		var pdfPresenter = editor.presenter.getPresenterPdf()
		for (var i = 1; i<=pdfPresenter.totalPdfPageNumber(); i++) {
			//println(i)
			pageChoice.getItems().add(i);
		}
	}

	/**
	 * feches the current buffered image in the presenter representing the pdf and converts it and loads into the imageview
	 */
	def renderDocument() {

		pageNumberLabel.text = editor.presenter.getPresenterPdf.currentPdfPageNumber + 1 + "/" + editor.presenter.getPresenterPdf.totalPdfPageNumber
		val image = editor.presenter.getPresenterPdf.currentPdfPage
		mainPane.image = SwingFXUtils.toFXImage(image, null);
		maxX = mainPane.imageViewWidth
		maxY = mainPane.imageViewHeight
		pdfLoaded = true
		//Initialise le selecteur de page (pageChoice)
		initPageSelection()
	}

	/**
	 * changes the selected page to load and then renders it
	 */
	def selectPage(int pageNumber) {
		editor.presenter.getPresenterPdf.choosePdfPage(pageNumber);
		renderDocument();
		questionList.showOnlyPage(editor.presenter.getPresenterPdf.currentPdfPageNumber)
	}

	/**
	 * goes to the next page of the current pdf
	 */
	def nextPage() {
		editor.presenter.getPresenterPdf.nextPdfPage();
		renderDocument
		questionList.showOnlyPage(editor.presenter.getPresenterPdf.currentPdfPageNumber)
	}

	def previousPage() {
		editor.presenter.getPresenterPdf.previousPdfPage();
		renderDocument
		questionList.showOnlyPage(editor.presenter.getPresenterPdf.currentPdfPageNumber)
	}
	
	def double getMaxY(){
		maxY
	}
	def double getMaxX(){
		maxX
	}
	
	def void clearVue(){
		mainPane.clear
		questionList.clear
		questionEditor.hideAll
	}
}
