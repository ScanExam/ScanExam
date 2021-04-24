package fr.istic.tools.scanexam.view.fx.graduation

import fr.istic.tools.scanexam.core.Question
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.export.ExportExamToPdf
import fr.istic.tools.scanexam.export.GradesExportImpl
import fr.istic.tools.scanexam.mailing.StudentDataManager
import fr.istic.tools.scanexam.services.api.ServiceGraduation
import fr.istic.tools.scanexam.utils.Tuple3
import fr.istic.tools.scanexam.view.fx.FxSettings
import fr.istic.tools.scanexam.view.fx.PdfManager
import java.io.File
import java.io.IOException
import java.util.Arrays
import java.util.LinkedList
import java.util.List
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.embed.swing.SwingFXUtils
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.Spinner
import javafx.scene.control.ToggleButton
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import org.apache.logging.log4j.LogManager
import org.eclipse.xtend.lib.annotations.Accessors
import javafx.scene.input.KeyEvent
import fr.istic.tools.scanexam.config.LanguageManager

/**
 * Class used by the JavaFX library as a controller for the view. 
 * @author Benjamin Danlos
 */
class ControllerFxGraduation {

	static val logger = LogManager.logger


	
	BooleanProperty loadedModel = new SimpleBooleanProperty(this,"Is a template loaded",false);
	
	def setToLoaded(){
		loadedModel.set(false)
		loadedModel.set(true)
	}
	
	def toNotLoaded(){
		loadedModel.set(false)
	}
	
	def getLoadedModel(){
		loadedModel
	}
	
	Grader grader;
	QuestionListGraduation questionList;
	StudentListGraduation studentList;
	StudentDetails studentDetails;
	
	
	public PdfPaneWithAnotations mainPane;


	boolean botShow = false;
	boolean autoZoom = true;
	
	
	/**
	 * FXML Components
	 */
	@FXML
	public VBox root;
	@FXML
	public Pane topPane;
	@FXML
	public Button topButtonHidden;
	@FXML
	public Button topButtonActive;
	@FXML
	public Button botButtonHidden;
	@FXML
	public Button botButtonActive;
	@FXML
	public Pane bottomPane;
	@FXML
	public Pane parentPane;
	@FXML
	public ScrollPane studentListContainer;
	@FXML
	public ScrollPane questionListContainer;
	@FXML
	public ScrollPane scrollMain;
	@FXML
	public ScrollPane scrollBis;
	@FXML
	public VBox studentDetailsContainer;
	@FXML
	public VBox questionDetails;
	@FXML
	public Spinner<Double> gradeSpinner;
	@FXML
	public Spinner<Double> totalGradeSpinner;
	@FXML
	public HBox graderContainer;
	@FXML
	public Label instructionLabel;
	@FXML
	public Button nextStudentButton;
	@FXML
	public Button prevStudentButton;
	@FXML
	public Button nextQuestionButton;
	@FXML
	public Button prevQuestionButton;
	@FXML
	public ToggleButton annotationModeButton;
	@FXML
	public ToggleButton addAnnotationButton;
	
	@Accessors
	var ServiceGraduation service
	
	@Accessors
	var PdfManager pdfManager
	
	var List<Question> questions
	/**
	 * FXML Actions.
	 */
	@FXML
	def Pressed() {
	}
	
	/**
	 * Called when a <b>save</b> button is pressed
	 */
	@FXML
	def void savePressed() {
		logger.info("Save Called")
	}

	/**
	 * Called when a <b>save a</b> button is pressed
	 */
	@FXML
	def void saveAsPressed() {
		logger.info("Save as Called")
	}

	/**
	 * Called when a <b>next question</b> button is pressed
	 */
	@FXML
	def void nextQuestionPressed() {
		logger.info("Next Question Called")
		if (loadedModel.value)
		nextQuestion
	}

	/**
	 * Called when a <b>previous question pressed</b> button is pressed
	 */
	@FXML
	def void prevQuestionPressed() {
		logger.info("Previous Question Called")
		if (loadedModel.value)
		previousQuestion
	}

	/**
	 * Called when a <b>next student</b> button is pressed
	 */
	@FXML
	def void nextStudentPressed() {
		logger.info("Next Student Called")
		if (loadedModel.value)
		nextStudent
	}

	/**
	 * Called when a <b>previous student</b> button is pressed
	 */
	@FXML
	def void prevStudentPressed() {
		logger.info("Previous Student Called")
		if (loadedModel.value)
		previousStudent
	}

	@FXML
	def void mainMouseEvent(MouseEvent e) {
		chooseMouseAction(e);
	}
	
	@FXML
	def void parentMouseEvent(MouseEvent e){
		if (currentTool == SelectedTool.MOVE_GRADER_TOOL) moveGrader(e)
	}
	
	
	
	
	//--- LOCAL VARIABLES ---//
	
	enum SelectedTool {
		NO_TOOL,
		MOVE_CAMERA_TOOL,
		CREATE_ANOTATION_TOOL,
		MOVE_ANOTATION_TOOL,
		MOVE_POINTER_TOOL,
		MOVE_GRADER_TOOL
	}
	
	@Accessors SelectedTool currentTool = SelectedTool.NO_TOOL;
	


	
	double imageWidth;
	double imageHeight;


	//---Getters/Setters---//

	def getQuestionList(){
		questionList
	}
	def getStudentList(){
		studentList
	}
	
	def setToAutoZoom(Boolean b) {
		this.autoZoom = b
	}
	//-----------------------//
	
	
	def void chooseMouseAction(MouseEvent e) {
		if (e.button == MouseButton.SECONDARY){
			moveImage(e);
			return ;
		}
		switch currentTool {
			case NO_TOOL: {
				moveImage(e)
			}
			case MOVE_CAMERA_TOOL: {
				moveImage(e)
			}
			case CREATE_ANOTATION_TOOL: {
				createNewAnotation(e)
			}
			case MOVE_ANOTATION_TOOL: {
				moveAnotation(e)
			}
			case MOVE_POINTER_TOOL: {
				movePointer(e)
			}
			case MOVE_GRADER_TOOL: {
				return
			}
		}
	}
	
	
	/**
	 * Toggles the visibility of the bottom window
	 */
	def void toggleBottom() throws IOException {
		bottomPane.setVisible(!botShow);
		botButtonHidden.setVisible(botShow);
		botShow = !botShow;
	}

	/**
	 * Used to resize the window containing the corrected exam
	 */
	def void dragBottom(MouseEvent event) {
		if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			bottomPane.setPrefHeight(
				Math.max(0,
					Math.min(bottomPane.getScene().getHeight() - 100,
						bottomPane.getScene().getHeight() - event.getSceneY())));

		}
	}

	var mouseOriginX = 0d;
	var mouseOriginY = 0d;
	var objectOriginX = 0d;
	var objectOriginY = 0d;

	def void moveImage(MouseEvent e) {

		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
			mouseOriginX = e.screenX
			mouseOriginY = e.screenY
			var source = e.source as Node
			objectOriginX = source.layoutX
			objectOriginY = source.layoutY
		}
		if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			var source = e.source as Node
			source.layoutX = objectOriginX + (e.screenX - mouseOriginX)
			source.layoutY = objectOriginY + (e.screenY - mouseOriginY)
		}
	}
	
	
	def void moveGrader(MouseEvent e){
		if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {}
		if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			grader.layoutX = e.x
			grader.layoutY = e.y
		}
		if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
			currentTool = SelectedTool.NO_TOOL;
		}
	}
	
	@Accessors TextAnotation currentAnotation;
	

	
	def void moveAnotation(MouseEvent e){
		var mousePositionX = Math.max(FxSettings.BOX_BORDER_THICKNESS,
								Math.min(e.x, mainPane.imageViewWidth- FxSettings.BOX_BORDER_THICKNESS - currentAnotation.width));
		var mousePositionY = Math.max(FxSettings.BOX_BORDER_THICKNESS,
							Math.min(e.y, mainPane.imageViewHeight - FxSettings.BOX_BORDER_THICKNESS - currentAnotation.height));
		if (e.eventType == MouseEvent.MOUSE_DRAGGED) {
			currentAnotation.move(mousePositionX,mousePositionY)
		}
		if (e.eventType == MouseEvent.MOUSE_RELEASED) {
			updateAnnotation(currentAnotation)
			currentTool = SelectedTool.NO_TOOL;
		}
			
	}
	
	def void movePointer(MouseEvent e){
		var mousePositionX = Math.max(FxSettings.BOX_BORDER_THICKNESS,
								Math.min(e.x, mainPane.imageViewWidth- FxSettings.BOX_BORDER_THICKNESS));
		var mousePositionY = Math.max(FxSettings.BOX_BORDER_THICKNESS,
							Math.min(e.y, mainPane.imageViewHeight - FxSettings.BOX_BORDER_THICKNESS));
		if (e.eventType == MouseEvent.MOUSE_DRAGGED) {
			currentAnotation.movePointer(mousePositionX,mousePositionY)
		}
		if (e.eventType == MouseEvent.MOUSE_RELEASED) {
			updateAnnotation(currentAnotation)
			currentTool = SelectedTool.NO_TOOL;
		}
			
	}

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
		e.consume
	}

	


	def void zoomTest() {
		setZoomArea(0, 0, 100, 200)
	}

	

	@FXML
	def void resetPosition() {
		mainPane.scaleX = 1;
		mainPane.scaleY = 1;
		mainPane.layoutX = 0;
		mainPane.layoutY = 0;
		grader.layoutX = 0;
		grader.layoutY = 0;
		mainPane.unZoom
	}

	// ---------------------------------//
	
	def init(ServiceGraduation serviceGraduation){
		
		parentPane.styleClass.add("parentPane")
		
		pdfManager = new PdfManager
		service = serviceGraduation
		
		mainPane = new PdfPaneWithAnotations(this)
		parentPane.children.add(mainPane)
		
		questionList = new QuestionListGraduation(this);
		questionListContainer.content = questionList
		
		studentList = new StudentListGraduation(this);
		studentListContainer.content = studentList
		
		grader = new Grader(this);
		parentPane.children.add(grader);
		
		
		studentDetails = new StudentDetails(this);
		studentDetailsContainer.children.add(studentDetails)
		
		
		unLoaded();
		
		loadedModel.addListener([obs,oldVal,newVal | newVal ? loaded() : unLoaded()])
		annotationModeButton.selectedProperty.addListener([obs,oldVal,newVal | newVal ? enterAnotationMode : leaveAnotationMode])
		addAnnotationButton.selectedProperty.addListener([obs,oldVal,newVal | toCreateAnnotation = newVal])
		nextQuestionButton.disableProperty.bind(loadedModel.not)
		prevQuestionButton.disableProperty.bind(loadedModel.not)
		prevStudentButton.disableProperty.bind(loadedModel.not)
		nextStudentButton.disableProperty.bind(loadedModel.not)
		annotationModeButton.disableProperty.bind(loadedModel.not)
		addAnnotationButton.disableProperty.bind(loadedModel.not)
		
	}

	def void setKeybinds() {
		var s = mainPane.scene
		s.addEventFilter(KeyEvent.KEY_PRESSED,[event | switch event.code {
						case FxSettings.BUTTON_NEXT_QUESTION: nextQuestionPressed
						case FxSettings.BUTTON_PREV_QUESTION: prevQuestionPressed
						case FxSettings.BUTTON_PREV_STUDENT: prevStudentPressed  
						case FxSettings.BUTTON_NEXT_STUDENT: nextStudentPressed
						case FxSettings.BUTTON_INTERACT_GRADER_1: grader.interactUsingIndex(1)
						case FxSettings.BUTTON_INTERACT_GRADER_2: grader.interactUsingIndex(2)
						case FxSettings.BUTTON_INTERACT_GRADER_3: grader.interactUsingIndex(3)
						case FxSettings.BUTTON_INTERACT_GRADER_4: grader.interactUsingIndex(4)
						case FxSettings.BUTTON_INTERACT_GRADER_5: grader.interactUsingIndex(5)
						case FxSettings.BUTTON_INTERACT_GRADER_6: grader.interactUsingIndex(6)
						case FxSettings.BUTTON_INTERACT_GRADER_7: grader.interactUsingIndex(7)
						case FxSettings.BUTTON_INTERACT_GRADER_8: grader.interactUsingIndex(8)
						case FxSettings.BUTTON_INTERACT_GRADER_9: grader.interactUsingIndex(9)
						case FxSettings.BUTTON_INTERACT_GRADER_0: grader.interactUsingIndex(10)
						default: logger.warn("Key not supported.")
					}
					])
	}


	//---LOADING FROM MODEL--//
	/**
	 * Cette section sert a charger le l'information du modele dans la vue, notament la liste des etudiant et questions.
	 * 
	 */
	
	
	/**
	 * Sets the state of loaded model to true, triggering a set of listeners
	 * To be used once the service loads a model 
	 */
	def boolean load(File file){
		
		val streamOpt = service.openCorrectionTemplate(file)
		if(streamOpt.present) {
			pdfManager.create(streamOpt.get)
			setToLoaded()
			return true
		}
		return false
	}
	
	
	 //path vers template edit
	 //pathervers tempplate pfsds
	
	def void saveExam(){
		var fileChooser = new FileChooser();
		fileChooser.extensionFilters.add(new ExtensionFilter("XMI files", Arrays.asList("*.xmi")));
		fileChooser.initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator") +
			"Documents");
		var file = fileChooser.showSaveDialog(mainPane.scene.window)

		if (file !== null) {
			saveTemplate(file.path)
			logger.info("Saving correction file")
		} 
		else {
			logger.warn("File not chosen")
		}
	}
	
	/**
	 * Exporte la correction des copies au format PDF
	 * @param folder Dossier où exporter
	 */
	def void exportGraduationToPdf(File folder) {
		ExportExamToPdf.exportExamsOfStudentsToPdfsWithAnnotations(pdfManager.pdfInputStream,service.studentSheets,folder)
	}

	/**
	 * Cette methode est a apeler une fois que le modele est pret.
	 * Pour charger les donne du modele dans lest list etudioant et questions
	 * 
	 *  */
	def loaded(){
		unLoaded()
		logger.info("Loading Vue")
		renderCorrectedCopy();
		renderStudentCopy();
		loadQuestions();
		loadStudents();
		setSelectedQuestion
		setSelectedStudent
		grader.visible = true;
		questionDetails.visible = true;
	}
	
	def unLoaded(){
		logger.info("Clearing current Vue")
		grader.visible = false;
		studentDetails.visible = false;
		questionList.clearItems
		studentList.clearItems
		
	}
	
	/**
	 * Envoie le nom du modèle au service
	 * @param templateName Nom du modèle
	 */
	def sendExamNameToService(String templateName) {
		service.setExamName(templateName)
	}
	
	def update(){
		
	}
	
	def selectQuestionWithId(int id){
		
	}
	def selectStudentWithId(int id){
		
	}
	/**
	 * Charge les questions present dans le modele.
	 * La liste des etudiants est presente dans studentList, qui affiche tout les etudiants.
	 * 
	 */
	def void loadQuestions() { //TODO FIX
		logger.info("Loading Questions")
		for (var p = 0;p < service.pageAmount;p++) {
			var ids =  initLoading(p);
			for (int i:ids) {
				var question = new QuestionItemGraduation();
				question.x = questionX(i) * imageWidth;
				question.y = questionY(i) * imageHeight;
				question.h = questionHeight(i) * imageHeight;
				question.w = questionWidth(i) * imageWidth;
				question.page = p
				question.questionId = i
				question.name = questionName(i);
				question.worth = questionWorth(i)
				questionList.addItem(question)
			}
		}
		if (questionList.noItems) logger.warn("The view has received no questions from service")
	}
	
	/**
	 * Charge les etudiant present dans le modele.
	 * La liste des etudiants est presente dans studentList, qui affiche tout les etudiants.
	 * 
	 */
	def void loadStudents(){
		logger.info("Loading Students")
		var ids = studentIds
		
		for (int i : ids) {
			var student = new StudentItemGraduation(i)
			student.studentName = service.getStudentName(i);
	
			studentList.addItem(student)
		}
	}
	
	//---------------------//
	
	
	//--Anotations--//
	/**
	 * Cette section contient les methodes pour gere les anotations.
	 * Du a l'implementation du zoom sur des question, il est necesaaire de "deZoom" (viewPort a null) et ensuite de recuper toute les anotations.
	 * Une fois deZoome, on peut ensuite placer des anotations si l'outils est selectionner.
	 * Une fois finit avec les annotations, on peut effacer les anotations de la vue, et rezoomer sur la question que l'on veut.
	 */
	
	
	/**
	 * Utiliser pour ajouter une anotations a la vue avec la sourie.
	 */
	def createNewAnotation(MouseEvent e){
		var mousePositionX = Math.max(FxSettings.BOX_BORDER_THICKNESS,
								Math.min(e.x, mainPane.imageViewWidth- FxSettings.BOX_BORDER_THICKNESS - TextAnotation.defaultWidth));
		var mousePositionY = Math.max(FxSettings.BOX_BORDER_THICKNESS,
							Math.min(e.y, mainPane.imageViewHeight - FxSettings.BOX_BORDER_THICKNESS - TextAnotation.defaultHeight));
		if (e.eventType == MouseEvent.MOUSE_PRESSED) {
				var annot = mainPane.addNewAnotation(mousePositionX,mousePositionY);
				addAnnotation(annot)
				addAnnotationButton.selected = false
			}
		
	
	}
	
	def setToCreateAnnotation(boolean b){
		if (b){
			if (!annotationMode) annotationModeButton.selected = true;
			currentTool = SelectedTool.CREATE_ANOTATION_TOOL
		} else {
			currentTool = SelectedTool.NO_TOOL
		}
	}
	
	/**
	 * Affiche toutes les annotations pour la page courrant et l'etudiant courrant
	 */
	def showAnotations(){
		mainPane.displayAnnotationsFor(questionList.currentItem,studentList.currentItem)
	}
	
	/**
	 * Enleve toutes les annotations de la vue
	 */
	def hideAnotations(){
		mainPane.removeAllAnotations
	}


	var annotationMode = false;
	var previousZoomMode = true;
	/**
	 * On rentre dans le mode d'annotations.
	 * il faut dezoom, afficher les annotations et metter l'outils courrant au mode anotation.
	 * 
	 */
	
	def enterAnotationMode(){
		mainPane.unZoom
		previousZoomMode = autoZoom;
		autoZoom = false;
		showAnotations
		annotationMode = true;
	}
	
	def leaveAnotationMode(){
		hideAnotations
		mainPane.zoomTo(questionList.currentItem.x,questionList.currentItem.y,questionList.currentItem.h,questionList.currentItem.w)
		currentTool = SelectedTool.NO_TOOL
		annotationMode = false;
		autoZoom = previousZoomMode;
	}
	
	//-----------------//
	
	
	//---NAVIGATION---//
	
	def void nextStudent(){
		studentList.selectNextItem
		service.nextSheet
		setSelectedStudent();
	}
	def void previousStudent(){
		studentList.selectPreviousItem
		service.previousSheet
		setSelectedStudent();
	}
	def void selectStudent(StudentItemGraduation item){
		studentList.selectItem(item);
		service.selectSheet(studentList.currentItem.studentId)
		setSelectedStudent();
	}

	def void setSelectedStudent(){
		if (!studentList.noItems) {
			focusStudent(studentList.currentItem)
			updateDisplayedPage
			updateDisplayedGrader
			updateStudentDetails
			updateDisplayedAnnotations
		}else {
			logger.warn("The student list is Empty")
		}
	}

	def void nextQuestion(){
		questionList.selectNextItem
		service.nextQuestion
		setSelectedQuestion()
	}
	def void previousQuestion(){
		questionList.selectPreviousItem
		service.previousQuestion
		setSelectedQuestion()
	}
	def void selectQuestion(QuestionItemGraduation item) {
		questionList.selectItem(item);
		service.selectQuestion(item.questionId)
		setSelectedQuestion()
	}

	def void setSelectedQuestion(){
		if (!questionList.noItems) {
			focusQuestion(questionList.currentItem)
			updateDisplayedPage
			updateDisplayedQuestion
			updateDisplayedGrader
			updateStudentDetails
			updateDisplayedAnnotations
		}else {
			logger.warn("The question list is Empty")
		}
		
	}
	
	def focusQuestion(QuestionItemGraduation item) {
		questionList.focusItem(item)
	}
	
	def focusStudent(StudentItemGraduation item) {
		studentList.focusItem(item)
		studentDetails.display(item)
		
	}
	
	//----------------//
	
	//---DISPLAYING---//
	
	def void renderStudentCopy(){
		logger.info("Call to RenderStudentCopy")
		var image = pdfManager.currentPdfPage
		mainPane.image = SwingFXUtils.toFXImage(image, null);
		imageWidth = image.width
		imageHeight = image.height
	}
	
	def void renderCorrectedCopy(){}
	
	/**
	 * Checks if we need to change the page and changes it if we need to.
	 */
	def void updateDisplayedPage(){
		if (!studentList.noItems && !questionList.noItems) {
			var i = service.getAbsolutePageNumber(studentList.currentItem.studentId,questionList.currentItem.page)
			if (!pdfManager.atCorrectPage(i)){
				logger.info("Changing page")
				selectPage(service.getAbsolutePageNumber(studentList.currentItem.studentId,questionList.currentItem.page))
			}
		}else {
			logger.warn("Cannot find correct page, student list or question is is empty")
		}
	}

	/**
	 * Changes the zoom to the current questions dimentions
	 */
	def void updateDisplayedQuestion(){
		if (autoZoom) 
			setZoomArea(questionList.currentItem.x,questionList.currentItem.y,questionList.currentItem.h,questionList.currentItem.w)
	}
	
	/**
	 * 
	 */
	def void updateDisplayedGrader(){
		if (!studentList.noItems && !questionList.noItems) {
			grader.changeGrader(questionList.currentItem,studentList.currentItem)
		}else {
			logger.warn("Cannot load grader, student list or question is is empty")
		}
	}
	
	/**
	 * Met à jour les détails de l'étudiant
	 */
	def void updateStudentDetails() {
    	studentDetails.updateGrade
    	studentDetails.updateQuality
	}
	
	def void updateDisplayedAnnotations(){
		if (annotationMode) {
			showAnotations()
		}
	}
	
	/**
	 * 
	 */
	def void setZoomArea(double x, double y, double height, double width) {
		if (autoZoom) 
			mainPane.zoomTo(x,y,height,width)
		logger.info("Zooming to" + x + y + height + width + autoZoom)
	}

	//----------------//

	//---PAGE OPERATIONS---//
	
	def void nextPage() {
		pdfManager.nextPdfPage
		renderStudentCopy
	}
	def void previousPage(){
		pdfManager.previousPdfPage
		renderStudentCopy
	}
	def void selectPage(int pageNumber) {
		pdfManager.goToPdfPage(pageNumber)
		renderStudentCopy
	}
	
	//---------------------//
	
	
	
		
	//---Grade entry management
	
	def List<Integer> getEntryIds(int questionId){
		var l = service.getQuestionGradeEntries(questionId);
		
		var result = new LinkedList<Integer>();
		for (Tuple3<Integer, String, Float> t : l) {
			result.add(t._1);
		}
		result
	}
	
	def List<Integer> getSelectedEntryIds(int questionId){
		service.getQuestionSelectedGradeEntries(questionId);
	}
	
	def String getEntryText(int entryId,int questionId){
		var l = service.getQuestionGradeEntries(questionId);
		for (Tuple3<Integer, String, Float> t : l) {
			if (entryId == t._1) {
				return t._2
			}
		}
		"Entry not found"
	}
	
	def float getEntryWorth(int entryId,int questionId){
		var l = service.getQuestionGradeEntries(questionId);
		for (Tuple3<Integer, String, Float> t : l) {
			if (entryId == t._1) {
				return t._3
			}
		}
		return -1
	}
	
	/**
	 * Retourne la note maximale que peut encore obtenir l'étudiant
	 * @return Note maximale que peut encore obtenir l'étudiant
	 */
	def float getCurrentMaxGrade() {
	    return service.currentMaxGrade
	}
	
	/**
	 * Retourne la note globale de la copie
	 * @return Note globale de la copie
	 */
	def float getGlobalGrade() {
	    return service.getCurrentGrade
	}
	    
	/**
	 * Retourne le barème total de l'examen
	 * @return Barème total de l'examen
	 */
	def float getGlobalScale() {
	    return service.getGlobalScale
	}
	
	/* SAVING  */
	def saveTemplate(String path){
		service.saveCorrectionTemplate(path,pdfManager.pdfOutputStream)
	}
	
	/* STUDENTS */
	
	def List<String> getStudentsSuggestedNames(String start){
		StudentDataManager.allNames
			.map(l | l.filter[n | n.toLowerCase().contains(start === null ? "" : start.toLowerCase())].toList)
			.orElse(List.of())
	}
	
	def LinkedList<Integer> getStudentIds(){ //TODO Change service impl to not return null
		var list = service.studentSheets
		var result = new LinkedList<Integer>()
		if (list !== null) {
			for (StudentSheet s : list) {
				result.add(s.id);
			}
		}
		else {
			logger.warn("Service returned null studentId list")
		}
		result
	}
	

	
		
	def LinkedList<Integer> initLoading(int pageNumber){
		questions = service.getQuestionAtPage(pageNumber)
		var ids = new LinkedList<Integer>();
		for (Question q : questions) {
			ids.add(q.id)
		}
		ids
	}
	
	
	 
	
	def double questionX(int id){
		var result = -1.0;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.zone.x
			}
		}
		result
	}
	
	def double questionY(int id){
		var result = -1.0;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.zone.y
			}
		}
		result
	}
	
	def double questionHeight(int id){
		var result = -1.0;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.zone.heigth
			}
		}
		result
	}
	
	def double questionWidth(int id){
		var result = -1.0;
		for (Question q : questions) {
			if (q.id == id) {
				result = q.zone.width
			}
		}
		result
	}
	
	def String questionName(int id){
		var result = "";
		for (Question q : questions) {
			if (q.id == id) {
				result = q.name
			}
		}
		result
	}
	
	def float questionWorth(int id){//TODO FIX (TODO FIX THE TODO)
		var result = -1f;
		for (Question q : questions) {
			if (q.id == id) {
				if (q.gradeScale !== null)
					result = q.gradeScale.maxPoint
			}
		}
		result
	}
	
	def void exportGrades() {
		var fileChooser = new FileChooser();
		fileChooser.extensionFilters.add(new ExtensionFilter(LanguageManager.translate("exportExcel.fileFormat"), Arrays.asList("*.xlsx")));
		fileChooser.initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator") +
			"Documents");
		var file = fileChooser.showSaveDialog(mainPane.scene.window)

		if (file !== null) {
			saveTemplate(file.path)
			logger.info("Export grade in Excel")
		} 
		else {
			logger.warn("File not chosen")
		}
	 (new GradesExportImpl).exportGrades(service.studentSheets, file)
	}
	
	def applyGrade(int questionId,int gradeId) {
        service.assignGradeEntry(questionId,gradeId);
    }
    
    def removeGrade(int questionId,int gradeId) {
        service.retractGradeEntry(questionId,gradeId);
    }
    /**
     * Ajoute une nouvelle entrée à la liste des points attribuable à la question
     * @param questionId l'ID de la question dans laquelle ajouter l'entrée
     * @param desc la description de l'entrée
     * @param point le nombre de point de l'entrée
     * @return l'ID de l'entrée
     */
    def int addEntry(int questionId, String desc, float point) {
        service.addEntry(questionId, desc, point)
    }
    
    /**
     * Modifie une entrée de la liste des points attribuable à la question
     * @param questionId l'ID de la question dans laquelle modifier l'entrée
     * @param gradeEntryId l'ID de l'entrée à modifier
     * @param desc la nouvelle description de l'entrée
     * @param point le nouveau nombre de point de l'entrée
     */
    def modifyEntry(int questionId, int gradeEntryId, String desc, float point) {
        service.modifyEntry(questionId, gradeEntryId, desc, point)            
    }
    
    /**
     * Supprime une entrée de la liste des points attribuable à la question
     * @param questionId l'ID de la question dans laquelle supprimer l'entrée
     * @param gradeEntryId l'ID de l'entrée à supprimer
     */
    def removeEntry(int questionId, int gradeEntryId) {
        service.removeEntry(questionId, gradeEntryId)
    }
    
    def renameStudent(int studentId,String newname){
        service.assignStudentId(newname)
    }
     def addAnnotation(TextAnotation annot){
    	
    	annot.annotId = service.addNewAnnotation(annot.annotX,annot.annotY,annot.annotW,annot.annotH,annot.annotPointerX,annot.annotPointerY,annot.annotText,questionList.currentItem.questionId,studentList.currentItem.studentId)
    	logger.info("Adding new Annotation to Model : ID = " + annot.annotId)
    }
    
    def updateAnnotation(TextAnotation annot) {
    	logger.info("Updating annotation in Model : ID = " + annot.annotId)
    	service.updateAnnotation(annot.annotX,annot.annotY,annot.annotW,annot.annotH,annot.annotPointerX,annot.annotPointerY,annot.annotText,annot.annotId,questionList.currentItem.questionId,studentList.currentItem.studentId)
    }
    
    def removeAnnotation(TextAnotation annot){
    	logger.info("Removing Annotation from  Model : ID = " + annot.annotId)
    	service.removeAnnotation(annot.annotId,questionList.currentItem.questionId,studentList.currentItem.studentId)
    }
    
}