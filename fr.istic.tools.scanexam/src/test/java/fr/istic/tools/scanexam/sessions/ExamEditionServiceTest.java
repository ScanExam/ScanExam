package fr.istic.tools.scanexam.sessions;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import fr.istic.tools.scanexam.io.TemplateIO;
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


	@Test
	@DisplayName("Test - ajoute d'une question")
	void addQuestionTest() 
	{
		//ouverture du fichier
		TemplateIO.loadCreationTemplate("src/test/resources/resources_service/sampleExiste.xmi");

		assertNull(session.getQuestionZone(7));

		//Ajoute de la question
		final int id = session.createQuestion(2, 2, 2, 2);

		assertNotNull(session.getQuestionZone(id));
	}


	@Test
	@DisplayName("Test - suppression d'une question")
	void removeQuestionTest() 
	{
		//ouverture du fichier
		TemplateIO.loadCreationTemplate("src/test/resources/resources_service/sampleExiste.xmi");

		//Creation d'une question
		final int id = session.createQuestion(2, 2, 2, 2);

		assertNotNull(session.getQuestionZone(id));

		session.removeQuestion(id);

		assertNull(session.getQuestionZone(id));
	}

	@Test
	@DisplayName("Test - navigation a la page suivante")
	void nextPageTest() 
	{
		//ouverture du fichier
		TemplateIO.loadCreationTemplate("src/test/resources/resources_service/sampleExiste.xmi");

		int oldPage = session.getCurrentPageNumber();
		session.nextPage();
		int newPage = session.getCurrentPageNumber();

		if(oldPage == session.getPageNumber()) {
			assertEquals(1, newPage);
		}else {
			assertEquals(oldPage, newPage-1);
		}
	}


	@Test
	@DisplayName("Test - navigation a la page précédente")
	void previousPageTest() 
	{
		//ouverture du fichier
		TemplateIO.loadCreationTemplate("src/test/resources/resources_service/sampleExiste.xmi");

		int oldPage = session.getCurrentPageNumber();
		session.previousPage();
		int newPage = session.getCurrentPageNumber();

		if(oldPage == 1) {
			assertEquals(session.getPageNumber(), newPage);
		}else {
			assertEquals(oldPage-1, newPage);
		}
	}

	@Test
	@DisplayName("Test - chargement d'un fichier")
	void loadTest() 
	{
		//Ouverture du fichier
		TemplateIO.loadCreationTemplate("src/test/resources/resources_service/sampleExiste.xmi");

		assertEquals(session.getPageNumber(), 6);
	}


	@Test
	@DisplayName("Test - chargement d'un fichier qui n'existe pas")
	void loadTestRobustesse() 
	{
		//load d'un fichier qui n'existe pas
		Assertions.assertThrows(FileNotFoundException.class, () -> TemplateIO.loadCreationTemplate("src/test/resources/resources_service/NoExiste.xmi"));
	}

	@Test
	@DisplayName("Test - Sauvegarde d'un fichier")
	void saveTest() 
	{

		final int id = session.createQuestion(2, 2, 2, 2);

		assertNull(session.getQuestionZone(id));

		session.save("src/test/resources/resources_service/sampleExiste.xmi");

		TemplateIO.loadCreationTemplate("src/test/resources/resources_service/sampleExiste.xmi");

		//Verification que le changement est toujours d'actualiter
		assertNull(session.getQuestionZone(id));
	}
}
