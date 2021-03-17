package fr.istic.tools.scanexam.view.fX.corrector

import fr.istic.tools.scanexam.launcher.LauncherFX
import java.io.File
import java.io.IOException
import java.util.Arrays
import java.util.Objects
import javafx.fxml.FXML
import javafx.geometry.Rectangle2D
import javafx.scene.Node
import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.control.ScrollPane
import javafx.scene.control.Spinner
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import org.apache.logging.log4j.LogManager
import java.util.LinkedList
import javafx.embed.swing.SwingFXUtils
import fr.istic.tools.scanexam.view.fX.GraduationAdapterFX

/**
 * Class used by the JavaFX library as a controller for the view. 
 * @author Benjamin Danlos
 */
class ControllerFXCorrector {

	static val logger = LogManager.logger
	/**
	 * High level Controllers to access the Presenters
	 */
	GraduationAdapterFX corrector;

	/**
	 * setter for the ControllerVueCorrection attribute
	 * @param {@link ControllerVueCorrection} controller instance of ControllerVueCorrection (not null) 
	 */
	def void setAdapterCorrection(GraduationAdapterFX adapterCor) {
		Objects.requireNonNull(adapterCor)
		corrector = adapterCor
	}

	/**
	 * @return current {@link ControllerVueCorrection} 
	 */
	def getAdapterCorrection() {
		corrector
	}
	Grader grader;

	boolean botShow = false;
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
	public Pane mainPane;
	@FXML
	public Pane parentPane;
	@FXML
	public ListView<StudentItem> leftList;
	@FXML
	public ListView<QuestionItem> rightList;
	@FXML
	public ImageView imview;
	@FXML
	public ScrollPane scrollMain;
	@FXML
	public ScrollPane scrollBis;
	@FXML
	public VBox studentDetails;
	@FXML
	public VBox questionDetails;
	@FXML
	public Spinner<Double> gradeSpinner;
	@FXML
	public Spinner<Double> totalGradeSpinner;

	@FXML
	def Pressed() {
	}
	
	/**
	 * Called when a <b>save</b> button is pressed
	 */
	@FXML
	def void savePressed() {
		println("Saving method");
	}

	/**
	 * Called when a <b>save a</b> button is pressed
	 */
	@FXML
	def void saveAsPressed() {
		println("Saving as method");
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
		println("Import method");
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
		println("Next question method");
		nextQuestion
	}

	/**
	 * Called when a <b>previous question pressed</b> button is pressed
	 */
	@FXML
	def void prevQuestionPressed() {
		println("Previous question method");
		previousQuestion
	}

	/**
	 * Called when a <b>next student</b> button is pressed
	 */
	@FXML
	def void nextStudentPressed() {
		println("Next student method");
		nextStudent
	}

	/**
	 * Called when a <b>previous student</b> button is pressed
	 */
	@FXML
	def void prevStudentPressed() {
		println("Previous student method");
		previousStudent
	}

	/**
	 * Called when a grade update button is pressed
	 */
	@FXML
	def void saveGradeButtonPressed() {
		println("save Grade method : " + gradeSpinner.getValue() + "/" + totalGradeSpinner.getValue);
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
	
	
	
	//--- LOCAL VARIABLES ---//
	
	enum SelectedTool {
		NO_TOOL,
		MOVE_CAMERA_TOOL
	}
	SelectedTool currentTool = SelectedTool.NO_TOOL;
	
	boolean pdfLoaded = false;
	
	double maxX; //la taille de l'image courrante
	double maxY;
	
	int currentQuestionIndex = 0;
	
	int currentStudentIndex = 0;
	
	double imageWidth;
	double imageHeight;

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
			println(source)
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
		imview.viewport = null;
	}

	// ---------------------------------//
	
	def init(){
		binds(root);
		binds(scrollMain);
		binds(scrollBis);
		
	}
	def void binds(Node n) {
		n.setOnKeyPressed([ event |
			{
				switch event.code {
					case KeyCode.RIGHT: nextQuestionPressed
					case KeyCode.LEFT: prevQuestionPressed
					case KeyCode.UP: prevStudentPressed  
					case KeyCode.DOWN: nextStudentPressed
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
					case KeyCode.RIGHT: nextQuestionPressed
					case KeyCode.LEFT: prevQuestionPressed
					case KeyCode.UP: nextStudentPressed
					case KeyCode.DOWN: prevStudentPressed
					default: logger.warn("Key not supported.")
				}
				event.consume
			}
		])
		binds(scrollMain);
		binds(scrollBis);
	}


	//---FILE MANAGEMENT---//
	
	
	
	def void load(){
		loadExam();
		loadStudentPdfs();
	}
	
	/**
	 * Opens a Open dialog box
	 * Used to choose a .xmi file representing a already started Graduation
	 */
	 
	 //path vers template edit
	 //pathervers tempplate pfsds
	def void loadExam() { 
		var fileChooser = new FileChooser();
		fileChooser.extensionFilters.add(new ExtensionFilter("XMI files", Arrays.asList("*.xmi")));
		fileChooser.initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator") +
			"Documents");
		var file = fileChooser.showOpenDialog(mainPane.scene.window)
		if (file !== null) {
			corrector.presenter.openEditionTemplate(file.path)
		} else {
			logger.warn("File not chosen")
		}
	}
	
	def void loadStudentPdfs(){
		var fileChooser = new FileChooser();
		fileChooser.extensionFilters.add(new ExtensionFilter("PDF files", Arrays.asList("*.pdf")));
		fileChooser.initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator") +
			"Documents");
		var file = fileChooser.showOpenDialog(mainPane.scene.window)
		if (file !== null) {
			corrector.presenter.openCorrectionPdf(file.path);
			renderCorrectedCopy();
			renderStudentCopy();
			loadQuestions();
			loadStudents();
			postLoad();
		} else {
			logger.warn("File not chosen")
		}
	}
	
	
	def void saveExam(){
		var fileChooser = new FileChooser();
		fileChooser.extensionFilters.add(new ExtensionFilter("XMI files", Arrays.asList("*.xmi")));
		fileChooser.initialDirectory = new File(System.getProperty("user.home") + System.getProperty("file.separator") +
			"Documents");
		var file = fileChooser.showSaveDialog(mainPane.scene.window)

		if (file !== null) {
			
		} 
		else {
			logger.warn("File not chosen")
		}
	}
	
	
	def void loadQuestions(){
		grader = new Grader();
		questionDetails.children.add(grader);
		for (var p = 0;p < corrector.presenter.templatePageAmount;p++) {
			var ids = corrector.presenter.initLoading(p);
			for (int i:ids) {
				var question = new QuestionItem();
				question.x = corrector.presenter.questionX(i) * imageWidth;
				question.y = corrector.presenter.questionY(i) * imageHeight;
				question.h = corrector.presenter.questionHeight(i) * imageHeight;
				question.w = corrector.presenter.questionWidth(i) * imageWidth;
				question.page = p
				question.questionId = i
				question.name = corrector.presenter.questionName(i);
				rightList.items.add(question);
				
				grader.add("test1","1",1,i)
				grader.add("test1","1",2,i)
			}
		}
	}
	
	def void loadStudents(){
		var ids = corrector.presenter.studentIds
		//TODO link to service
		//var ids = new LinkedList<Integer>();
		for (int i : ids) {
			leftList.items.add(new StudentItem(i))
		}
	}
	
	def void postLoad(){
		currentQuestionIndex = 0
		leftList.selectionModel.select(0);
		rightList.selectionModel.select(0);
		var list = new LinkedList<Integer>()
		list.add(1)
		grader.display(rightList.items.get(currentQuestionIndex).questionId,list)
		currentStudentIndex = 0;
	}
	
	//---------------------//
	
	//---NAVIGATION---//
	
	def void renderStudentCopy(){		
		var image = corrector.presenter.presenterPdf.currentPdfPage
		imview.image = SwingFXUtils.toFXImage(image, null);
		pdfLoaded = true;
		imageWidth = image.width
		imageHeight = image.height
	}
	
	def void renderCorrectedCopy(){
		
	}
	
	def void nextStudent(){
		currentStudentIndex++;	
		if (currentStudentIndex >= leftList.items.size) {
			currentStudentIndex = leftList.items.size-1;
		}
		corrector.presenter.presenterQuestion.nextStudent
		setSelectedStudent();
	}
	def void previousStudent(){
		currentStudentIndex--;
		if (currentStudentIndex < 0){
			currentStudentIndex = 0
		}
		corrector.presenter.presenterQuestion.previousStudent
		setSelectedStudent();
	}
	def void selectStudent(int index){
		currentStudentIndex = index;
		setSelectedStudent();
	}

	def void setSelectedStudent(){
		leftList.selectionModel.select(currentStudentIndex)
		var list = new LinkedList<Integer>()
		list.add(1)
		grader.display(rightList.items.get(currentQuestionIndex).questionId,list)
		display();
	}

	def void nextQuestion(){
		currentQuestionIndex++;
		if (currentQuestionIndex >= rightList.items.size) {
			currentQuestionIndex = rightList.items.size -1;
		}
		//corrector.presenter.presenterQuestion.nextQuestion
		setSelectedQuestion()
	}
	def void previousQuestion(){
		currentQuestionIndex--;
		if (currentQuestionIndex < 0){
			currentQuestionIndex = 0
		}
		//corrector.presenter.presenterQuestion.previousQuestion
		setSelectedQuestion()
	}
	def void selectQuestion(int index) {
		currentQuestionIndex = index;
		setSelectedQuestion()
	}

	def void setSelectedQuestion(){
		rightList.selectionModel.select(currentQuestionIndex)
		display();
		displayQuestion();
	}
	
	def void setZoomArea(int x, int y, int height, int width) {
		imview.viewport = new Rectangle2D(x,y,height,width);
	}
	
	//----------------//
	
	//---DISPLAYING---//
	def void display(){
		var i = corrector.presenter.getAbsolutePage(leftList.items.get(currentStudentIndex).studentId,rightList.items.get(currentQuestionIndex).page)
		if (!corrector.presenter.presenterPdf.atCorrectPage(i)){
			logger.warn("changing Page")
			selectPage(corrector.presenter.getAbsolutePage(leftList.items.get(currentStudentIndex).studentId,rightList.items.get(currentQuestionIndex).page))
		}
	}

	def void setZoomArea(double x, double y,double width ,double height) {
		imview.viewport = new Rectangle2D(x,y,width,height);
		logger.warn(imview.viewport)

	}

	def void displayQuestion(){
		setZoomArea(rightList.items.get(currentQuestionIndex).x,rightList.items.get(currentQuestionIndex).y,rightList.items.get(currentQuestionIndex).w,rightList.items.get(currentQuestionIndex).h)
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
		corrector.presenter.getPresenterPdf.goToPage(pageNumber)
		renderStudentCopy
	}
	//---------------------//
}