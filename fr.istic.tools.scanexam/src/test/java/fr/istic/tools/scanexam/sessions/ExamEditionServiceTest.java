package fr.istic.tools.scanexam.sessions;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.*;

import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.core.QuestionZone;
import fr.istic.tools.scanexam.services.ExamEditionService;

public class ExamEditionServiceTest 
{

	ExamEditionService session;

	@BeforeEach
	void init() 
	{
		session = new ExamEditionService();
	}

	@Test
	@DisplayName("Test - Ouverture d'un fichier XMI")
	void openTest() 
	{
		//Ouverture du fichier
		session.open("src/test/resources/resources_service/sampleExiste.xmi");

		//Verification du nombre de page et du nom de l'examen ouvert
		assertEquals(session.getPageNumber(), 6);
		assertEquals(session.getExamName(), "PFO_december_19");
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Ouverture d'un fichier XMI qui n'existe pas")
	//TODO Modifier open pour avoir une erreur si le path donnée de contient pas de fichier
	void openTestRobustesse() 
	{
		//ouverture d'un fichier qui n'existe pas
		Assertions.assertThrows(FileNotFoundException.class, () -> session.open("test/robustesse/sampleNoExiste.xmi"));
	}


	@Test
	@DisplayName("Test - Création d'un nouveaux projet")
	void createTest() throws IOException 
	{
		session.create(new File("src/test/resources/resources_service/pfo_example.pdf"));

		//ArrayList<BufferedImage> pages = new ArrayList<BufferedImage>();

		//chargement du fichier
		PDDocument document = PDDocument.load(new File("src/test/resources/resources_service/pfo_example.pdf"));

		//verification que le nombre de page est identique
		assertEquals(session.getPageNumber(),document.getNumberOfPages());

		//ajouter une ligne qui verifie que currentPdfPath = file.absolutePath
	}


	@SuppressWarnings({ "null", "static-access" })
	@Test
	@DisplayName("Test - ajoute d'une question")
	void addQuestionTest() 
	{
		//ouverture du fichier
		session.load("src/test/resources/resources_service/sampleExiste.xmi");

		//Creation d'une question
		Question newQuestion = null;
		newQuestion.setId(7);
		newQuestion.setName("TestQuestion");

		QuestionZone questionZone = null;
		questionZone.setHeigth(2);
		questionZone.setWidth(2);;
		questionZone.setX(2);;
		questionZone.setY(2);;

		newQuestion.setZone(questionZone);
		newQuestion.setGradeScale(null);


		assertNull(session.getQuestionZone(7));

		//Ajoute de la question
		session.addQuestion(newQuestion);

		assertNotNull(session.getQuestionZone(7));
	}


	@SuppressWarnings({ "null", "static-access" })
	@Test
	@DisplayName("Test - suppression d'une question")
	void removeQuestionTest() 
	{
		//ouverture du fichier
		session.load("src/test/resources/resources_service/sampleExiste.xmi");

		//Creation d'une question
		Question newQuestion = null;
		newQuestion.setId(7);
		newQuestion.setName("TestQuestion");

		QuestionZone questionZone = null;
		questionZone.setHeigth(2);
		questionZone.setWidth(2);;
		questionZone.setX(2);;
		questionZone.setY(2);;

		newQuestion.setZone(questionZone);
		newQuestion.setGradeScale(null);

		session.addQuestion(newQuestion);

		assertNotNull(session.getQuestionZone(7));

		session.removeQuestion(7);

		assertNull(session.getQuestionZone(7));
	}

	@SuppressWarnings("static-access")
	@Test
	@DisplayName("Test - navigation a la page suivante")
	void nextPageTest() 
	{
		//ouverture du fichier
		session.load("src/test/resources/resources_service/sampleExiste.xmi");

		int oldPage = session.getCurrentPageNumber();
		session.nextPage();
		int newPage = session.getCurrentPageNumber();

		if(oldPage == session.getPageNumber()) {
			assertEquals(1, newPage);
		}else {
			assertEquals(oldPage, newPage-1);
		}
	}


	@SuppressWarnings("static-access")
	@Test
	@DisplayName("Test - navigation a la page précédente")
	void previousPageTest() 
	{
		//ouverture du fichier
		session.load("src/test/resources/resources_service/sampleExiste.xmi");

		int oldPage = session.getCurrentPageNumber();
		session.previousPage();
		int newPage = session.getCurrentPageNumber();

		if(oldPage == 1) {
			assertEquals(session.getPageNumber(), newPage);
		}else {
			assertEquals(oldPage-1, newPage);
		}
	}

	@SuppressWarnings("static-access")
	@Test
	@DisplayName("Test - chargement d'un fichier")
	void loadTest() 
	{
		//Ouverture du fichier
		session.load("src/test/resources/resources_service/sampleExiste.xmi");

		assertEquals(session.getPageNumber(), 6);
	}


	@SuppressWarnings("static-access")
	@Test
	@DisplayName("Test - chargement d'un fichier qui n'existe pas")
	void loadTestRobustesse() 
	{
		//load d'un fichier qui n'existe pas
		Assertions.assertThrows(FileNotFoundException.class, () -> session.load("src/test/resources/resources_service/NoExiste.xmi"));
	}

	@SuppressWarnings({ "null", "static-access" })
	@Test
	@DisplayName("Test - Sauvegarde d'un fichier")
	void saveTest() 
	{

		//ajoute d'une question
		Question newQuestion = null;
		newQuestion.setId(7);
		newQuestion.setName("TestQuestion");

		QuestionZone questionZone = null;
		questionZone.setHeigth(2);
		questionZone.setWidth(2);;
		questionZone.setX(2);;
		questionZone.setY(2);;

		newQuestion.setZone(questionZone);
		newQuestion.setGradeScale(null);

		session.addQuestion(newQuestion);

		assertNull(session.getQuestionZone(7));

		session.save("src/test/resources/resources_service/sampleExiste.xmi");

		session.load("src/test/resources/resources_service/sampleExiste.xmi");

		//Verification que le changement est toujours d'actualiter
		assertNull(session.getQuestionZone(7));
	}
}
