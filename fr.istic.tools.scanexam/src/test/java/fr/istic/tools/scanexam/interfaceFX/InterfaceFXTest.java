package fr.istic.tools.scanexam.interfaceFX;

import org.junit.Before;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.NodeQuery;

import fr.istic.tools.scanexam.launcher.LauncherFX;
import fr.istic.tools.scanexam.view.fx.editor.PdfPane;
import fr.istic.tools.scanexam.view.fx.editor.QuestionListEdition;
import fr.istic.tools.scanexam.view.fx.editor.QuestionOptionsEdition;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

class InterfaceFXTest extends ApplicationTest {

	/**
	 * EMPLACEMENT DES BOUTONS
	 * package fr.istic.tools.scanexam.view.fx.editor; -> ControllerFxEdition
	 * package fr.istic.tools.scanexam.view.fx.graduation -> ControllerFxGraduation
	 * 
	 * Fichier fxml : src/main/resources/viewResources
	 * ConfigUI
	 */

	@SuppressWarnings("unused")
	private String localisationFileSave = "";
	
	//ROOTUI
	private MenuItem createNewTemplate;
	private MenuItem loadTemplate;
	private MenuItem SaveTemplate;
	private MenuItem exportToSheets;
	
	//ConfigUI
	private Button configSaveAndQuit;
	private Button configQuit;
	
	//
	
	//Barre
	private Button save;//
	private Button load;//
	private Button export;//
	
	//CorrectorUI
	private VBox root;
	private Pane topPane;
	private Button topButtonHidden;
	private Button botButtonHidden;
	private Button botButtonActive;
	private Pane bottomPane;
	private Pane mainPaneGradiation;
	private Pane parentPane;
	private ScrollPane studentListContainer;
	private ScrollPane questionListContainerGradiation;
	private ImageView imview;
	private ScrollPane scrollMain;
	private ScrollPane scrollBis;
	private VBox studentDetailsContainer;
	private VBox questionDetails;
	private Spinner<Double> gradeSpinner;
	private Spinner<Double> totalGradeSpinner;
	private HBox graderContainer;
	private Label instructionLabel;
	private Button nextStudentButton;//
	private Button prevStudentButton;//
	private Button nextQuestionButton;//
	private Button prevQuestionButton;//

	//EditorUI
	private ToggleButton createBoxButton;
	private Button nextPageButton;//
	private Button previousPageButton;//
	private PdfPane mainPaneEditor;
	private ScrollPane questionListContainerEditor;
	private ScrollPane gradeListContainer;
	private ChoiceBox<Integer> pageChoice;
	private Label pageNumberLabel;
	private QuestionListEdition questionList;
	private QuestionOptionsEdition questionEditor;
	private Pane mainPane;//

	
	@Before
	public void setUpClass() throws Exception {
		ApplicationTest.launch(LauncherFX.class);
	}
	
	/**
	 * Lancement de l'interface FX
	 */
	@Override
	public void start(Stage stage) throws Exception {
		new LauncherFX().start(stage);
		
		//RootUI
		//createNewTemplate = lookup("#createNewTemplatePressed").query();
		//loadTemplate = lookup("#loadTemplatePressedEditor").queryButton();
		//SaveTemplate = lookup("#SaveTemplatePressed").queryButton();
		//exportToSheets = lookup("#exportToSheets").queryButton();
		
		//ConfigUI
		configSaveAndQuit = lookup("#saveAndQuit").queryButton();
		configQuit = lookup("#quit").queryButton();
		
		//Barre
		save = lookup("#savePressed").queryButton();
		load = lookup("#loadPressed").queryButton();
		export = lookup("#exportPressed").queryButton();
		
		
		//CorrectorUI
		prevQuestionButton = lookup("#prevQuestionPressed").queryButton();
		nextQuestionButton = lookup("#nextQuestionPressed").queryButton();
		prevStudentButton = lookup("#prevStudentPressed").queryButton();
		nextStudentButton = lookup("#nextStudentPressed").queryButton();
		
		
		//EditorUI
		previousPageButton = lookup("#previousPagePressed").queryButton();
		nextPageButton = lookup("#nextPagePressed").queryButton();
		mainPane = lookup("#mainMouseEvent").queryTextFlow();
		//createBoxButton = lookup("#questionAreaPressed").queryButton();
		
	}
	
	@DisplayName("Premier Test : Clique Simple")
	@Test
    public void cliqueSimple() {
        clickOn(prevQuestionButton);
    }
	
	/**
	 * Liste des tests a realiser sur Editor
	 */
	
	@DisplayName("Creation d'une nouvelle template + sauvegarde")
	@Test
    public void CreationTemplateAndSave() {
        //clique sur file editor
		//clique sur create new template
		//Clique sur l'onglet editor
		
		//clique sur file editor
		//clique sur save template
		//Choisir l'emplacement
		//mettre comme nom 'ScanExamInterfaceTest.xmi'
		//Cliquer sur enregistrer
    }
	
	@DisplayName("Load fichier")
	@Test
    public void LoadFichier() {
		//clique sur file editor
		//clique sur load template
        //Selectionner le fichier a l'emplacement choisi
		//Verifier que le fichier est charger
		
    }
	
	@DisplayName("Ajout de deux question")
	@Test
    public void AddQuestion() {
		//Faire un load
		
		//Clique sur crée une question
		//faire un clique glisser dans la fenetre
		//Verifier qu'il y a une question de plus
		
		//refait la meme chose une seconde fois
		
		//Faire un save
    }
	
	@DisplayName("Modification Attribut question")
	@Test
    public void ModifQuestion() {
		//Faire un load
		
		//cliquer sur le nom dans question detail
		//entrer QuestionTestInterface pour le nom de la question
		
		//Cliquer sur le barem de la question
		//entrer 3
		
		//Verifier que les changement on bien pris effet
		
		//Faire un save
    }
	
	@DisplayName("Navigation dans les questions")
	@Test
    public void NavigQuestion() {
		//Faire un load
		
		//clique sur le boutton suiv a gauche
		//verification que la page n'est plus la meme
		//clique sur le bouton pres
		//verification que la page est la meme qu'on depart
    }
	
	@DisplayName("Suppression question")
	@Test
    public void SuppQuestion() {
		//Faire un load
		
		//cliquer sur retirer de la seconde question 
		
		//Verifier qu'il ne reste que une seul question
		
		//Faire un save
    }
	
	/**
	 * Liste des tests a realiser sur Correction
	 */
	
	@DisplayName("Corrector Load fichier")
	@Test
    public void LoadFichierForCorrector() {
		//clique sur corrector
		//clique sur load template
        //Selectionner le fichier a l'emplacement choisi
		//selectionner le PDF
		//Verifier que le fichier est charger
	}
	
	@DisplayName("Corrector changement de question")
	@Test
    public void changeQuestionForCorrector() {
		//clique sur corrector
		//clique sur load template
        //Selectionner le fichier a l'emplacement choisi
		//selectionner le PDF
	
		
		//cliquer sur next question
		//verifier que la question n'est plus la meme
		//cliquer sur last question
		//Verifier qu'on est de retour sur la question du depart
	}
	
	@DisplayName("Ajoute, suppression d'Entry")
	@Test
    public void ManagerEntry() {
		//clique sur corrector
		//clique sur load template
        //Selectionner le fichier a l'emplacement choisi
		//selectionner le PDF
	
		
		//cliquer sur Toggle Editable
		//cliquer sur ass new grade entry
		//Cliquer sur la note et la modifier a 1
		//cliquer sur le commentaire et marquer "GradeCommenteTest"
		
		//Rajouter une question
		//Verifier qu'il y a 2 entry
		//cliquer sur remove entry de la seconde question
		//Verifier qu'il ne reste que 1 entry
		//cliquer sur toggle Editable
		//cliquer sur sur le carrer en face des poin de la question pour ajouter l'entry
		//verifier que cela a bien ete fait
	}
		
	/**
	 * Liberation du clavier et de la souris
	 * @throws TimeoutException
	 */
	@After
	public void afterEachTest() throws TimeoutException {
		FxToolkit.hideStage();
		release(new KeyCode[]{});
		release(new MouseButton[]{});
	}

}
