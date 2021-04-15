package fr.istic.tools.scanexam.view.fx.graduation

import fr.istic.tools.scanexam.launcher.LauncherFX
import fr.istic.tools.scanexam.services.ExamSingleton
import fr.istic.tools.scanexam.view.AdapterGraduation
import fr.istic.tools.scanexam.view.fx.AdapterFxGraduation
import fr.istic.tools.scanexam.view.fx.FxSettings
import java.io.File
import java.io.IOException
import java.util.Arrays
import java.util.Objects
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.embed.swing.SwingFXUtils
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.Spinner
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

import static fr.istic.tools.scanexam.config.LanguageManager.translate

/**
 * Class used by the JavaFX library as a controller for the view. 
 * @author Benjamin Danlos
 */
class ControllerFxGraduation {

	static val logger = LogManager.logger
	/**
	 * High level Controllers to access the Presenters
	 */
	AdapterFxGraduation corrector;
	
	

	/**
	 * setter for the ControllerVueCorrection attribute
	 * @param {@link ControllerVueCorrection} controller instance of ControllerVueCorrection (not null) 
	 */
	def void setAdapterCorrection(AdapterFxGraduation adapterCor) {
		Objects.requireNonNull(adapterCor)
		corrector = adapterCor
	}

	/**
	 * @return current {@link ControllerVueCorrection} 
	 */
	def getAdapterCorrection() {
		corrector
	}
	
	@Accessors BooleanProperty loadedModel = new SimpleBooleanProperty(this,"Is a template loaded",false);
	
	Grader grader;
	QuestionListGraduation questionList;
	StudentListGraduation studentList;
	StudentDetails studentDetails;
	
	
	public PdfPaneWithAnotations mainPane;


	boolean botShow = false;
	boolean autoZoom = true;
	@FXML
	public Label gradeLabel
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
	 * Called when a <b>load</b> button is pressed
	 */
	@FXML
	def void loadPressed() {
		load();
	}

	/**
	 * Called when a <b>import</b> button is pressed
	 */
	@FXML
	def void importPressed() {
		logger.info("Import Called")

	}

	//XXX À améliorer
	/**
	 * Called when a <b>export</b> button is pressed
	 */
	@FXML
	def void exportPressed() {
		println("Export method");
		adapterCorrection.presenter.exportGrades
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
	def void swapToEditorPressed() {
		LauncherFX.swapToEditor
	}
	
	@FXML
	def void mainMouseEvent(MouseEvent e) {
		chooseMouseAction(e);
	}
	
	
	
	//-----------------------//
	
	
	//FIXME Pas terrible de mettre cela en public mais ControllerRoot n'a accès a aucun Presenter
	def AdapterGraduation getAdapter() {
		return corrector
	}
	
	
	//--- LOCAL VARIABLES ---//
	
	enum SelectedTool {
		NO_TOOL,
		MOVE_CAMERA_TOOL,
		CREATE_ANOTATION_TOOL
	}
	SelectedTool currentTool = SelectedTool.NO_TOOL;
	


	
	double imageWidth;
	double imageHeight;


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
			}
			case MOVE_CAMERA_TOOL: {
				moveImage(e)
			}
			case CREATE_ANOTATION_TOOL: {
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

	@FXML
	def void ZoomImage(ScrollEvent e) {
		var source = e.source as Node
		if (e.deltaY > 0) {
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
		mainPane.unZoom
	}

	// ---------------------------------//
	
	def init(){
		
		mainPane = new PdfPaneWithAnotations(this)
		parentPane.children.add(mainPane)
		
		questionList = new QuestionListGraduation(this);
		questionListContainer.content = questionList
		
		studentList = new StudentListGraduation(this);
		studentListContainer.content = studentList
		
		grader = new Grader(this);
		graderContainer.children.add(grader);
		
		
		studentDetails = new StudentDetails(this);
		studentDetailsContainer.children.add(studentDetails)
		binds(root);
		binds(scrollMain);
		binds(scrollBis);
		
		unLoaded();
		
		loadedModel.addListener([obs,oldVal,newVal | newVal ? loaded() : unLoaded()])
		
		nextQuestionButton.disableProperty.bind(loadedModel.not)
		prevQuestionButton.disableProperty.bind(loadedModel.not)
		prevStudentButton.disableProperty.bind(loadedModel.not)
		nextStudentButton.disableProperty.bind(loadedModel.not)
		
	}
	
	def void binds(Node n) {
		n.setOnKeyPressed([ event |
			{
					switch event.code {
						case FxSettings.BUTTON_NEXT_QUESTION: nextQuestionPressed
						case FxSettings.BUTTON_PREV_QUESTION: prevQuestionPressed
						case FxSettings.BUTTON_PREV_STUDENT: prevStudentPressed  
						case FxSettings.BUTTON_NEXT_STUDENT: nextStudentPressed
						default: logger.warn("Key not supported.")
					}
					event.consume
			}
		])
	}

	def void setKeybinds() {
		var s = mainPane.scene
		s.setOnKeyPressed([ event |
			{
					switch event.code {
						case FxSettings.BUTTON_NEXT_QUESTION: nextQuestionPressed
						case FxSettings.BUTTON_PREV_QUESTION: prevQuestionPressed
						case FxSettings.BUTTON_PREV_STUDENT: prevStudentPressed  
						case FxSettings.BUTTON_NEXT_STUDENT: nextStudentPressed
						default: logger.warn("Key not supported.")
					}
					event.consume
				}
		])
		binds(scrollMain);
		binds(scrollBis);
	}


	//---FILE MANAGEMENT---//
	
	
	/**
	 * Sets the state of loaded model to true, triggering a set of listeners
	 * To be used once the service loads a model 
	 */
	def void load(){
		loadedModel.set(true)
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
			adapter.presenter.saveTemplate(file.path)
			logger.info("Saving correction file")
		} 
		else {
			logger.warn("File not chosen")
		}
	}

	//Called after the service has finished loading
	def loaded(){
		renderCorrectedCopy();
		renderStudentCopy();
		loadQuestions();
		loadStudents();
		grader.visible = true;
		questionDetails.visible = true;
	}
	
	def unLoaded(){
		grader.visible = false;
		studentDetails.visible = false;
		questionList.clearItems
		studentList.clearItems
		
	}
	
	def update(){
		
	}
	
	def selectQuestionWithId(int id){
		
	}
	def selectStudentWithId(int id){
		
	}
	
	def void loadQuestions() {
		logger.info("Loading Questions")
		var currentQuestionId = 0;
		for (var p = 0;p < ExamSingleton.instance.pages.size;p++) {
			var ids = corrector.presenter.initLoading(p);
			for (int i:ids) {
				var question = new QuestionItemGraduation();
				question.x = corrector.presenter.questionX(i) * imageWidth;
				question.y = corrector.presenter.questionY(i) * imageHeight;
				question.h = corrector.presenter.questionHeight(i) * imageHeight;
				question.w = corrector.presenter.questionWidth(i) * imageWidth;
				question.page = p
				question.questionId = i
				question.name = corrector.presenter.questionName(i);
				question.worth = corrector.presenter.questionWorth(i)
				questionList.addItem(question)
			}
		}
	}
	
	def void loadStudents(){
		logger.info("Loading Students")
		var currentStudentId = 0;
		var ids = corrector.presenter.studentIds
		
		for (int i : ids) {
			var student = new StudentItemGraduation(i)
			studentList.addItem(student)
			if (currentStudentId == i) {
					selectStudent(student)
				}
		}
	}
	
	//---------------------//
	
	
	//--Anotations--//
	
	
	
	
	def createNewAnotation(MouseEvent e){
		var mousePositionX = Math.max(FxSettings.BOX_BORDER_THICKNESS,
								Math.min(e.x, mainPane.imageViewWidth- FxSettings.BOX_BORDER_THICKNESS));
		var mousePositionY = Math.max(FxSettings.BOX_BORDER_THICKNESS,
							Math.min(e.y, mainPane.imageViewHeight - FxSettings.BOX_BORDER_THICKNESS));
		mainPane.addNewAnotation(mousePositionX,mousePositionY);
	
	}

	def showAnotations(){
		//On veut prendre les donee de tt les anotations pour la page courante et l'etudiant courrant,
		//et ajouter ces anotations au mainPane
	}
	
	def hideAnotations(){
		//On veut enleve tts les anotations de mainPane
		//
		mainPane.removeAllAnotations
	}
	
	def enterAnotationMode(){
		mainPane.unZoom
		showAnotations
		currentTool = SelectedTool.CREATE_ANOTATION_TOOL
	}
	
	def leaveAnotationMode(){
		hideAnotations
		mainPane.zoomTo(questionList.currentItem.x,questionList.currentItem.y,questionList.currentItem.w,questionList.currentItem.h)
		SelectedTool.NO_TOOL
	}
	
	//-----------------//
	
	//---NAVIGATION---//
	
	def void renderStudentCopy(){		
		var image = corrector.presenter.presenterPdf.currentPdfPage
		mainPane.image = SwingFXUtils.toFXImage(image, null);
		imageWidth = image.width
		imageHeight = image.height
	}
	
	def void renderCorrectedCopy(){
		
	}
	
	def void nextStudent(){
		studentList.selectNextItem
		adapterCorrection.presenter.presenterQuestion.nextStudent
		setSelectedStudent();
	}
	def void previousStudent(){
		studentList.selectPreviousItem
		adapterCorrection.presenter.presenterQuestion.previousStudent
		setSelectedStudent();
	}
	def void selectStudent(StudentItemGraduation item){
		studentList.selectItem(item);
		setSelectedStudent();
	}

	def void setSelectedStudent(){
		if (!studentList.noItems) {
			focusStudent(studentList.currentItem)
			display();
			displayGrader();	
		}else {
			logger.warn("The student list is Empty")
		}
	}

	def void nextQuestion(){
		questionList.selectNextItem
		adapterCorrection.presenter.presenterQuestion.nextQuestion
		setSelectedQuestion()
	}
	def void previousQuestion(){
		questionList.selectPreviousItem
		adapterCorrection.presenter.presenterQuestion.previousQuestion
		setSelectedQuestion()
	}
	def void selectQuestion(QuestionItemGraduation item) {
		questionList.selectItem(item);
		adapterCorrection.presenter.presenterQuestion.selectQuestion(item.questionId)
		setSelectedQuestion()
	}

	def void setSelectedQuestion(){
		if (!questionList.noItems) {
			focusQuestion(questionList.currentItem)
			display();
			displayQuestion();
			displayGrader();
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
	
	def void setZoomArea(int x, int y, int height, int width) {
		if (autoZoom) 
			mainPane.zoomTo(x,y,height,width)
	}
	
	//----------------//
	
	//---DISPLAYING---//
	
	//On veut afficher la bonne page, donc on verifie si on est sur la bonne page, si non, on change de page
	def void display(){
		if (!studentList.noItems && !questionList.noItems) {
			var i = corrector.presenter.getAbsolutePage(studentList.currentItem.studentId,questionList.currentItem.page)
			if (!corrector.presenter.presenterPdf.atCorrectPage(i)){
				logger.info("Changing page")
				selectPage(corrector.presenter.getAbsolutePage(studentList.currentItem.studentId,questionList.currentItem.page))
			}
		}else {
			logger.warn("Cannot find correct page, student list or question is is empty")
		}
	}

	def void setZoomArea(double x, double y,double width ,double height) {
		mainPane.zoomTo(x,y,height,width);
	}

	def void displayQuestion(){
		if (autoZoom) 
			setZoomArea(questionList.currentItem.x,questionList.currentItem.y,questionList.currentItem.w,questionList.currentItem.h)
	}
	
	def void displayGrader(){
		if (!studentList.noItems && !questionList.noItems) {
			grader.changeGrader(questionList.currentItem,studentList.currentItem);
			updateGlobalGrade
		}else {
			logger.warn("Cannot load grader, student list or question is is empty")
		}
	}

	//----------------//

	
	
	//---ACTIONS ON MODEL---//
	
	def void setGrade(int studentId,int questionId,float grade) {
		
	}
	
	
	//----------------------//
	
	
	//---PAGE OPERATIONS---//
	def void nextPage() {
		corrector.presenter.getPresenterPdf.nextPdfPage
		renderStudentCopy
	}
	def void previousPage(){
		corrector.presenter.getPresenterPdf.previousPdfPage
		renderStudentCopy
	}
	def void selectPage(int pageNumber) {
		corrector.presenter.getPresenterPdf.goToPdfPage(pageNumber)
		renderStudentCopy
	}
	//---------------------//
	
	
	/**
	 * Met à jour la note globale affichée
	 */
	def void updateGlobalGrade() {
    	gradeLabel.text = translate("label.grade") + " " + adapter.presenter.globalGrade + "/" + adapter.presenter.globalScale
	}
	
}