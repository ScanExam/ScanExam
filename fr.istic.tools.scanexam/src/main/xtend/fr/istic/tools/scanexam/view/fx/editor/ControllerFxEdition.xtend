package fr.istic.tools.scanexam.view.fx.editor;

import fr.istic.tools.scanexam.core.Question
import fr.istic.tools.scanexam.services.api.ServiceEdition
import fr.istic.tools.scanexam.view.fx.FxSettings
import fr.istic.tools.scanexam.view.fx.PdfManager
import fr.istic.tools.scanexam.view.fx.utils.DialogMessageSender
import java.io.File
import java.util.Arrays
import java.util.LinkedList
import java.util.List
import java.util.Objects
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.embed.swing.SwingFXUtils
import javafx.fxml.FXML
import javafx.scene.Cursor
import javafx.scene.Node
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.ToggleButton
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.AnchorPane
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import org.apache.logging.log4j.LogManager
import org.eclipse.xtend.lib.annotations.Accessors

import static fr.istic.tools.scanexam.config.LanguageManager.translate

/**
 * This controller controlls the main components of the edition
 * This controlle is associated with a FXML, the static version of the ui.
 * This FX contains "containers" where we will place our dynamic components.
 * These components are initiated during the init method, that is called after the loading of the FXML in the launcher.
 * 
 * This class has differenc sections : 
 * 	-Mouse events (mouse inputs for moving the pdf, moving boxes ect).
 * 	-Box management(creating, resizing ect).
 * 	-loading/saving (loading a new model, saving).
 * 	-pdf page management.
 * 	-interactions with the service.
 */
class ControllerFxEdition {

	var logger = LogManager.logger

	var ServiceEdition service;

	double maxX;
	double maxY;
	var pdfLoaded = false;

	/* Permet d'éviter de render plusieurs fois lorsque la valeur de la ChoiceBox est mise à jour par le code 
	 * (et non l'utilisateur) et qu'elle entraîne un render à cause des bndings.
	 */
	boolean renderFlag = true

	var mouseOriginX = 0d;
	var mouseOriginY = 0d;
	var objectOriginX = 0d;
	var objectOriginY = 0d;

	Box currentRectangle = null;
	EdgeLocation edge = null;

	double offsetX;
	double offsetY;

	List<Question> questions

	QrCodeZone qrCodeZone

	// ------ TOOL SELECTORS ------//
	/**
	 * Setters for the current tool selected
	 */
	var currentTool = SelectedTool.NO_TOOL

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
	// **FXML CONTROLS**//
	@FXML
	ToggleButton createBoxButton;
	@FXML
	ToggleButton createQrButton;
	@FXML
	Button nextPageButton;
	@FXML
	Button previousPageButton;
	@FXML
	ScrollPane questionListContainer;
	@FXML
	ScrollPane gradeListContainer;
	@FXML
	ChoiceBox<Integer> pageChoice;
	@FXML
	Label pageNumberLabel;
	@FXML
	AnchorPane mainPaneContainer

	@Accessors
	BooleanProperty loadedModel = new SimpleBooleanProperty(this, "Is a model loaded", false);

	@Accessors
	QuestionListEdition questionList;

	@Accessors
	QuestionOptionsEdition questionEditor;

	@Accessors
	PdfManager pdfManager

	@Accessors
	PdfPane mainPane;

	def void init(ServiceEdition serviceEdition) {

		service = serviceEdition

		pdfManager = new PdfManager

		mainPane = new PdfPane(this);
		mainPaneContainer.children.add(mainPane)

		questionList = new QuestionListEdition(this);
		questionListContainer.content = questionList

		questionEditor = new QuestionOptionsEdition(this);
		gradeListContainer.content = questionEditor

		// Permet de définir pour chaque item de pageChoice une action : aller à la page sélectionnée
		pageChoice.setOnAction([ event |
			var selectedIndex = pageChoice.getSelectionModel().getSelectedIndex();
			if (!renderFlag)
				selectPage(selectedIndex)
			renderFlag = false
		]);

		nextPageButton.disableProperty.bind(loadedModel.not)
		previousPageButton.disableProperty.bind(loadedModel.not)
		createBoxButton.disableProperty.bind(loadedModel.not)
		createQrButton.disableProperty.bind(loadedModel.not)
		pageChoice.disableProperty.bind(loadedModel.not)
	}

	/* --LOADING NEW TEMPLATE--  */
	def List<Integer> initLoading(int pageNumber) {
		questions = service.getQuestionAtPage(pageNumber) // replace with method that gives a list of pages corresponding to questions at same index
		var ids = new LinkedList<Integer>();
		for (Question q : questions) {
			ids.add(q.id)
		}
		ids
	}

	def save(File path) {
		val outputStream = pdfManager.getPdfOutputStream();
		service.saveEdition(outputStream, path);
	}

	def close() {
		System.exit(0)
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

	/**
	 * Called When we decide to focus on a specific question
	 */
	def void selectQuestion(QuestionItemEdition item) {
		if (item === null) {
			questionList.removeFocus
			questionEditor.hideAll
			return
		}
		currentRectangle = item.zone
		if (qrCodeZone !== null) {
			qrCodeZone.zone.setFocus(false)
		}
		questionList.select(item)
		questionEditor.select(item)
	}

	/**
	 * Sélectionne la zone de qr code
	 */
	def void selectQr() {
		questionList.removeFocus
		questionEditor.hideAll
		currentRectangle = qrCodeZone.zone
		currentRectangle.setFocus(true)
	}

	/**
	 * Called when we click and drag on the pdf with the create question too selected
	 * will not create the question if the zone is too small
	 */
	def void createBox(MouseEvent e) {
		var mousePositionX = Math.max(FxSettings.BOX_BORDER_THICKNESS,
			Math.min(e.x, maxX - FxSettings.BOX_BORDER_THICKNESS));
		var mousePositionY = Math.max(FxSettings.BOX_BORDER_THICKNESS,
			Math.min(e.y, maxY - FxSettings.BOX_BORDER_THICKNESS));
		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
			mouseOriginX = mousePositionX
			mouseOriginY = mousePositionY

			currentRectangle = createZone(mousePositionX, mousePositionY);
			mainPane.addZone(currentRectangle);

		}
		if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			edge = EdgeLocation.SOUTHEAST
			switch (currentRectangle.type) {
				case BoxType.QUESTION: {
					draggedQuestion(mousePositionX, mousePositionY)
				}
				case BoxType.QR: {
					draggedQrCode(mousePositionX, mousePositionY)
				}
				default: {
				}
			}
		}
		if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
			if (currentRectangle.width > FxSettings.MINIMUM_ZONE_SIZE &&
				currentRectangle.height > FxSettings.MINIMUM_ZONE_SIZE) {

				if (currentTool == SelectedTool.QUESTION_AREA) {
					questionList.newQuestion(currentRectangle)
				} else {
					if (currentTool == SelectedTool.QR_AREA) {
						qrCodeZone = new QrCodeZone(currentRectangle, this)
					} else {
						if (currentRectangle.type == BoxType.QR) {
							qrCodeZone.updateInModel
						}
					}
				}

			} else {
				mainPane.removeZone(currentRectangle);
			}
			if (currentTool == SelectedTool.QUESTION_AREA) {
				createBoxButton.selected = false
			} else {
				if (currentTool == SelectedTool.QR_AREA) {
					createQrButton.selected = false
				}
			}
			setToNoTool
		// createBoxButton.selected = false
		}
	}

	/**
	 * OLD CODE
	 * Called when we click on a pdf with the move tool selected
	 * the box is limited to inside the pdf
	 */
	def void moveBox(MouseEvent e) {
		var mousePositionX = Math.max(FxSettings.BOX_BORDER_THICKNESS,
			Math.min(e.x, maxX - FxSettings.BOX_BORDER_THICKNESS));
		var mousePositionY = Math.max(FxSettings.BOX_BORDER_THICKNESS,
			Math.min(e.y, maxY - FxSettings.BOX_BORDER_THICKNESS));

		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
		}
		if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			currentRectangle.x(Math.min(mousePositionX + offsetX,
				maxX - FxSettings.BOX_BORDER_THICKNESS - currentRectangle.width))
			currentRectangle.y(Math.min(mousePositionY + offsetY,
				maxY - FxSettings.BOX_BORDER_THICKNESS - currentRectangle.height))
		}
		if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
		}
	}

	def void resizeBox(MouseEvent e) {
		var mousePositionX = Math.max(FxSettings.BOX_BORDER_THICKNESS,
			Math.min(e.x, maxX - FxSettings.BOX_BORDER_THICKNESS));
		var mousePositionY = Math.max(FxSettings.BOX_BORDER_THICKNESS,
			Math.min(e.y, maxY - FxSettings.BOX_BORDER_THICKNESS));
		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
			mouseOriginX = mousePositionX
			mouseOriginY = mousePositionY
			objectOriginX = currentRectangle.width
			objectOriginY = currentRectangle.height
			offsetX = mousePositionX - currentRectangle.x
			offsetY = mousePositionY - currentRectangle.y
		}
		if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			switch (currentRectangle.type) {
				case BoxType.QUESTION: {
					draggedQuestion(mousePositionX, mousePositionY)
				}
				case BoxType.QR: {
					draggedQrCode(mousePositionX, mousePositionY)
				}
				default: {
				}
			}
		}
		if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
			setToNoTool
			switch (currentRectangle.type) {
				case BoxType.QUESTION: {
					questionList.updateInModel(currentRectangle.questionItem)
				}
				case BoxType.QR: {
					qrCodeZone.updateInModel
				}
				default: {
				}
			}
		}
	}

	/**
	 * Gère le redimensionnage des questions
	 * @param mouse PositionX Position sur l'axe X de la souris
	 * @param mouse PositionX Position sur l'axe Y de la souris
	 */
	private def void draggedQuestion(double mousePositionX, double mousePositionY) {
		switch edge {
			case SOUTH: {
				if (mousePositionY > currentRectangle.y) {
					currentRectangle.height(mousePositionY - currentRectangle.y)
				} else {
					edge = EdgeLocation.NORTH
				}
			}
			case EAST: {
				if (mousePositionX > currentRectangle.x) {
					currentRectangle.width(mousePositionX - currentRectangle.x)
				} else {
					edge = EdgeLocation.WEST
				}
			}
			case NORTH: {
				if (mousePositionY < currentRectangle.y + currentRectangle.height) {
					val double initialY = currentRectangle.y
					currentRectangle.y(mousePositionY)
					currentRectangle.height(currentRectangle.height - (mousePositionY - initialY))
				} else {
					edge = EdgeLocation.SOUTH
				}
			}
			case WEST: {
				if (mousePositionX < currentRectangle.x + currentRectangle.width) {
					val double initialX = currentRectangle.x
					currentRectangle.x(mousePositionX)
					currentRectangle.width(currentRectangle.width - (mousePositionX - initialX))
				} else {
					edge = EdgeLocation.EAST
				}
			}
			case NORTHEAST: {
				if (mousePositionY < currentRectangle.y + currentRectangle.height) {
					val double initialY = currentRectangle.y
					currentRectangle.y(mousePositionY)
					currentRectangle.height(currentRectangle.height - (mousePositionY - initialY))
				} else {
					edge = EdgeLocation.SOUTHEAST
				}
				if (mousePositionX > currentRectangle.x) {
					currentRectangle.width(mousePositionX - currentRectangle.x)
				} else {
					edge = EdgeLocation.NORTHWEST
				}
			}
			case NORTHWEST: {
				if (mousePositionY < currentRectangle.y + currentRectangle.height) {
					val double initialY = currentRectangle.y
					currentRectangle.y(mousePositionY)
					currentRectangle.height(currentRectangle.height - (mousePositionY - initialY))
				} else {
					edge = EdgeLocation.SOUTHWEST
				}
				if (mousePositionX < currentRectangle.x + currentRectangle.width) {
					val double initialX = currentRectangle.x
					currentRectangle.x(mousePositionX)
					currentRectangle.width(currentRectangle.width - (mousePositionX - initialX))
				} else {
					edge = EdgeLocation.NORTHEAST
				}
			}
			case SOUTHEAST: {
				if (mousePositionY > currentRectangle.y) {
					currentRectangle.height(mousePositionY - currentRectangle.y)
				} else {
					edge = EdgeLocation.NORTHEAST
				}
				if (mousePositionX > currentRectangle.x) {
					currentRectangle.width(mousePositionX - currentRectangle.x)
				} else {
					edge = EdgeLocation.SOUTHWEST
				}
			}
			case SOUTHWEST: {
				if (mousePositionY > currentRectangle.y) {
					currentRectangle.height(mousePositionY - currentRectangle.y)
				} else {
					edge = EdgeLocation.NORTHWEST
				}
				if (mousePositionX < currentRectangle.x + currentRectangle.width) {
					val double initialX = currentRectangle.x
					currentRectangle.x(mousePositionX)
					currentRectangle.width(currentRectangle.width - (mousePositionX - initialX))
				} else {
					edge = EdgeLocation.SOUTHEAST
				}
			}
			case NONE: {
				currentRectangle.x(
					Math.max(
						Math.min(mousePositionX - offsetX, maxX - FxSettings.BOX_BORDER_THICKNESS -
							currentRectangle.width), FxSettings.BOX_BORDER_THICKNESS))
				currentRectangle.y(
					Math.max(
						Math.min(mousePositionY - offsetY, maxY - FxSettings.BOX_BORDER_THICKNESS -
							currentRectangle.height), FxSettings.BOX_BORDER_THICKNESS))
			}
		}
	}

	/**
	 * Gère le redimensionnage du qr code
	 * @param mouse PositionX Position sur l'axe X de la souris
	 * @param mouse PositionX Position sur l'axe Y de la souris
	 */
	private def void draggedQrCode(double mousePositionX, double mousePositionY) {
		switch edge {
			case SOUTH: {
				if (mousePositionY > currentRectangle.y) {
					val double initialX = currentRectangle.x
					val double initialHeight = currentRectangle.height
					currentRectangle.height(mousePositionY - currentRectangle.y)
					currentRectangle.x(initialX - (currentRectangle.height - initialHeight))
					currentRectangle.width(currentRectangle.height)
				} else {
					edge = EdgeLocation.NORTH
				}
			}
			case EAST: {
				if (mousePositionX > currentRectangle.x) {
					currentRectangle.width(mousePositionX - currentRectangle.x)
					currentRectangle.height(currentRectangle.width)
				} else {
					edge = EdgeLocation.WEST
				}
			}
			case NORTH: {
				if (mousePositionY < currentRectangle.y + currentRectangle.height) {
					val double initialY = currentRectangle.y
					currentRectangle.y(mousePositionY)
					currentRectangle.height(currentRectangle.height - (mousePositionY - initialY))
					currentRectangle.width(currentRectangle.height)
				} else {
					edge = EdgeLocation.SOUTH
				}
			}
			case WEST: {
				if (mousePositionX < currentRectangle.x + currentRectangle.width) {
					val double initialX = currentRectangle.x
					val double initialY = currentRectangle.y
					val double initialWidth = currentRectangle.width
					currentRectangle.x(mousePositionX)
					currentRectangle.width(currentRectangle.width - (mousePositionX - initialX))
					currentRectangle.y(initialY - (currentRectangle.width - initialWidth))
					currentRectangle.height(currentRectangle.width)
				} else {
					edge = EdgeLocation.EAST
				}
			}
			case NORTHEAST: {
				if ((mousePositionY < currentRectangle.y + currentRectangle.height) &&
					(mousePositionX > currentRectangle.x)) {
					val double initialY = currentRectangle.y
					currentRectangle.y(mousePositionY)
					currentRectangle.height(currentRectangle.height - (mousePositionY - initialY))
					currentRectangle.width(currentRectangle.height)
				} else {
					edge = EdgeLocation.SOUTHWEST
				}
			}
			case NORTHWEST: {
				if ((mousePositionY < currentRectangle.y + currentRectangle.height) &&
					(mousePositionX < currentRectangle.x + currentRectangle.width)) {
					val double initialX = currentRectangle.x
					val double initialY = currentRectangle.y
					val double initialWidth = currentRectangle.width
					currentRectangle.x(mousePositionX)
					currentRectangle.width(currentRectangle.width - (mousePositionX - initialX))
					currentRectangle.y(initialY - (currentRectangle.width - initialWidth))
					currentRectangle.height(currentRectangle.width)
				} else {
					edge = EdgeLocation.SOUTHEAST
				}

			}
			case SOUTHEAST: {
				if ((mousePositionY > currentRectangle.y) && (mousePositionX > currentRectangle.x)) {
					currentRectangle.width(mousePositionX - currentRectangle.x)
					currentRectangle.height(currentRectangle.width)
				} else {
					edge = EdgeLocation.NORTHWEST
				}
			}
			case SOUTHWEST: {
				if ((mousePositionY > currentRectangle.y) &&
					(mousePositionX < currentRectangle.x + currentRectangle.width)) {
					val double initialX = currentRectangle.x
					val double initialHeight = currentRectangle.height
					currentRectangle.height(mousePositionY - currentRectangle.y)
					currentRectangle.x(initialX - (currentRectangle.height - initialHeight))
					currentRectangle.width(currentRectangle.height)
				} else {
					edge = EdgeLocation.NORTHEAST
				}
			}
			case NONE: {
				currentRectangle.x(
					Math.max(
						Math.min(mousePositionX - offsetX, maxX - FxSettings.BOX_BORDER_THICKNESS -
							currentRectangle.width), FxSettings.BOX_BORDER_THICKNESS))
				currentRectangle.y(
					Math.max(
						Math.min(mousePositionY - offsetY, maxY - FxSettings.BOX_BORDER_THICKNESS -
							currentRectangle.height), FxSettings.BOX_BORDER_THICKNESS))
			}
		}
	}

	/**
	 * Used to move around the image in the parent pane
	 * Called when we right click on the pdf
	 */
	def void moveImage(MouseEvent e) {

		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
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
	 * returns a new Box with the right type corresponding to the current tool //TODO maybe move to box as a static method
	 */
	def Box createZone(double x, double y) {
		if (currentTool == SelectedTool.QUESTION_AREA) {
			new Box(BoxType.QUESTION, x, y, 0, 0)
		} else {
			if (currentTool == SelectedTool.QR_AREA) {
				new Box(BoxType.QR, x, y, 0, 0)
			}
		}
	}

	/**
	 * Envoie le nom du modèle au service
	 * @param templateName Nom du modèle
	 */
	def createExamTemplate(String templateName) {
		service.initializeEdition(pdfManager.pdfPageCount)
		service.setExamName(templateName)
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
			if (!file.getName().contains(".xmi")) {
				file = new File(file.getAbsolutePath() + ".xmi")
			}
			save(file);
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

			loadTemplate(file)

		} else {
			logger.warn("File not chosen")
		}
	}

	/**
	 * Essaye de charger le fichier passé en paramètre comme Template, affiche un DialogMessage en cas d'erreur
	 * @param file un fichier à charger
	 */
	def boolean loadTemplate(File file) {
		Objects.requireNonNull(file)
		val success = load(file.path)
		if (!success)
			DialogMessageSender.sendTranslateDialog(AlertType.ERROR,
				"studentSheetLoader.templateConfirmationDialog.title",
				"studentSheetLoader.templateConfirmationDialog.fail", null)
		else {
			render()
		}
		success
	}

	def boolean load(String path) {
		val stream = service.open(path)

		if (stream.isPresent) {
			pdfManager.create(stream.get);
		}

		return stream.isPresent;
	}

	def render() {
		clearVue
		// Initialise le selecteur de page (pageChoice)
		initPageSelection
		renderDocument();
		loadQrCodeZone
		loadBoxes();
		postLoad
	}

	/**
	 * called to load each question from the model into the vue
	 */
	def loadBoxes() {

		for (var p = 0; p < pdfManager.getPdfPageCount; p++) {
			var ids = initLoading(p)
			for (int i : ids) {

				var box = new Box(
					BoxType.QUESTION,
					questionX(i) * maxX,
					questionY(i) * maxY,
					questionWidth(i) * maxX,
					questionHeight(i) * maxY
				)
				mainPane.addZone(box);
				questionList.loadQuestion(box, questionName(i), p, i, questionWorth(i))
			}
		}

		questionList.showOnlyPage(pdfManager.currentPdfPageNumber)
	}

	/**
	 * Chage la zone de qr code
	 */
	def loadQrCodeZone() {
		val qrCodeInService = service.getQrCodeZone()
		if (qrCodeInService.isPresent) {
			val qrCodeBox = new Box(
				BoxType.QR,
				qrCodeInService.get.x * maxX,
				qrCodeInService.get.y * maxY,
				qrCodeInService.get.width * maxX,
				qrCodeInService.get.height * maxY
			)
			qrCodeZone = new QrCodeZone(qrCodeBox, this)
			mainPane.addZone(qrCodeZone.zone)
		}
	}

	def postLoad() {
		loadedModel.set(true)
		pdfLoaded = true;
	}

	/**
	 * initialise the choicebox containing all the page numbers of the pdf
	 * to call whenever we load a new pdf into the editor
	 */
	def initPageSelection() {
		pageChoice.items.clear

		for (var i = 1; i <= pdfManager.getPdfPageCount(); i++) {
			if (!pageChoice.items.contains(i)) {
				pageChoice.getItems().add(i)
			}
		}
	}

	/**
	 * feches the current buffered image in the presenter representing the pdf and converts it and loads into the imageview
	 */
	def renderDocument() {
		val image = pdfManager.currentPdfPage
		mainPane.image = SwingFXUtils.toFXImage(image, null);
		maxX = mainPane.imageViewWidth
		maxY = mainPane.imageViewHeight

		pageNumberLabel.text = translate("label.page") + (pdfManager.currentPdfPageNumber + 1) + " / " +
			pdfManager.getPdfPageCount
		renderFlag = true
		pageChoice.value = pdfManager.currentPdfPageNumber + 1
	}

	/**
	 * changes the selected page to load and then renders it
	 */
	def selectPage(int pageNumber) {
		pdfManager.goToPdfPage(pageNumber);
		renderDocument
		questionList.showOnlyPage(pdfManager.currentPdfPageNumber)
	}

	/**
	 * goes to the next page of the current pdf
	 */
	def nextPage() {
		pdfManager.nextPdfPage();
		renderDocument
		questionList.showOnlyPage(pdfManager.currentPdfPageNumber)
	}

	def previousPage() {
		pdfManager.previousPdfPage();
		renderDocument
		questionList.showOnlyPage(pdfManager.currentPdfPageNumber)
	}

	def double getMaxY() {
		maxY
	}

	def double getMaxX() {
		maxX
	}

	def void clearVue() {
		mainPane.clear
		questionList.clear
		questionEditor.hideAll
	}

	def void createQrCode(double x, double y, double height, double width) {
		service.createQrCode(x as float, y as float, height as float, width as float)
	}

	def void resizeQrCode(double height, double width) {
		service.rescaleQrCode(height as float, width as float)
	}

	def void moveQrCode(double x, double y) {
		service.moveQrCode(x as float, y as float)
	}

	def int createQuestion(double x, double y, double height, double width) {
		service.createQuestion(pdfManager.pdfPageIndex, x as float, y as float, height as float, width as float)
	}

	def void removeQuestion(int ID) {
		service.removeQuestion(ID);
	}

	def void renameQuestion(int ID, String name) {
		service.renameQuestion(ID, name)
	}

	def void resizeQuestion(int ID, double height, double width) {
		service.rescaleQuestion(ID, height as float, width as float)
	}

	/**
	 * changes the x and y coordinates of the {@link Question} identified by the id
	 * @param int id : the unique ID of question
	 * @param float x : new x position
	 * @param float y : new y position
	 * @author : Benjamin Danlos
	 */
	def void moveQuestion(int id, double x, double y) {
		service.moveQuestion(id, x as float, y as float)
	}

	def void changeQuestionWorth(int id, float worth) {
		service.modifyMaxPoint(id, worth)
	}

	/**
	 * Supprime la zone de qr code de l'écran
	 */
	def void removeQrCodeZone() {
		if (qrCodeZone !== null) {
			mainPane.removeZone(qrCodeZone.zone)
		}
	}

	/**
	 * Loads the next question into questionToLoad
	 * if there is a new question, return true,
	 * else return false
	 */
	def double questionX(int id) {
		var result = -1.0;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.zone.x
			}
		}
		result
	}

	def double questionY(int id) {
		var result = -1.0;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.zone.y
			}
		}
		result
	}

	def double questionHeight(int id) {
		var result = -1.0;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.zone.heigth
			}
		}
		result
	}

	def double questionWidth(int id) {
		var result = -1.0;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.zone.width
			}
		}
		result
	}

	def String questionName(int id) {
		var result = "";
		for (Question q : questions) {
			if (q.id == id) {
				result = q.name
			}
		}
		result
	}

	def float questionWorth(int id) {
		var result = 0f;
		for (Question q : questions) {
			if (q.id == id) {
				if (q.gradeScale !== null)
					result = q.gradeScale.maxPoint
			}
		}
		result
	}

	def getSelectedTool() {
		this.currentTool
	}

	def setEdgeLoc(EdgeLocation edge) {
		this.edge = edge
	}

	def setSelectedTool(SelectedTool tool) {
		currentTool = tool
	}

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

	@FXML
	def void questionAreaPressed() {
		if (createBoxButton.selected) {
			setToQuestionAreaTool
		} else {
			setToNoTool
		}
	}

	@FXML
	def void iDAreaPressed() {
		setToIDAreaTool
	}

	@FXML
	def void qrAreaPressed() {
		if (createQrButton.selected) {
			removeQrCodeZone
			setToQRAreaTool
		} else {
			setToNoTool
		}
	}

	@FXML
	def void movePressed() {
		setToMoveCameraTool
	}

	@FXML
	def void nextPagePressed() {
		if (pdfLoaded)
			nextPage
	}

	@FXML
	def void saveTemplatePressed() {
		if (pdfLoaded)
			saveTemplate();
	}

	@FXML
	def void loadTemplatePressed() {
		loadTemplate();

	}

	@FXML
	def void previousPagePressed() {
		if (pdfLoaded)
			previousPage
	}

	@FXML
	def void switchToCorrectorPressed() {
		// FIXME
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
}
