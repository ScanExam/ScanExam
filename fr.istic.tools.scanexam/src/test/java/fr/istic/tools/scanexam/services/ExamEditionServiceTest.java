package fr.istic.tools.scanexam.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.beust.jcommander.internal.Lists;

import fr.istic.tools.scanexam.io.TemplateIo;
import fr.istic.tools.scanexam.presenter.PresenterEdition;
import fr.istic.tools.scanexam.services.api.ServiceEdition;

public class ExamEditionServiceTest 
{
	ServiceEdition service;
	
	PresenterEdition presenter;
	
	@BeforeEach
	void init() 
	{
		service = new ServiceImpl();
		
		presenter = jailBreak(service);
		
	}
	
	private PresenterEdition jailBreak(Object... args) {
		try { 
			final Class<?>[] argClasses = Lists.newArrayList(args).stream()
					.map(o -> o.getClass())
					.toArray(Class<?>[]::new);
			Constructor<PresenterEdition> method = PresenterEdition.class.getConstructor( argClasses);
			method.setAccessible(true);
			return (PresenterEdition)method.newInstance(args);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	@Test
	@DisplayName("Test - Ouverture d'un fichier XMI")
	void openTest() 
	{
		//Ouverture du fichier
		service.open("src/test/resources/resources_service/sample.xmi");

		//Verification du nombre de page et du nom de l'examen ouvert
		assertEquals(presenter.getPresenterPdf().currentPdfPageNumber(), 6);
		assertEquals(service.getExamName(), "PFO_december_19");
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Ouverture d'un fichier XMI qui n'existe pas")
	//TODO Modifier open pour avoir une erreur si le path donnée de contient pas de fichier
	void openTestRobustesse() 
	{
		//ouverture d'un fichier qui n'existe pas
		Assertions.assertThrows(FileNotFoundException.class, () -> service.open("test/robustesse/sampleNoExiste.xmi"));
	}


	@Test
	@DisplayName("Test - Création d'un nouveaux projet")
	void createTest() throws IOException 
	{
		presenter.load("src/test/resources/resources_service/pfo_example.pdf");

		//ArrayList<BufferedImage> pages = new ArrayList<BufferedImage>();

		//chargement du fichier
		PDDocument document = PDDocument.load(new File("src/test/resources/resources_service/pfo_example.pdf"));

		//verification que le nombre de page est identique
		assertEquals(presenter.getPresenterPdf().getCurrentPdfPage(),document.getNumberOfPages());

		//ajouter une ligne qui verifie que currentPdfPath = file.absolutePath
	}


	@Test
	@DisplayName("Test - ajoute d'une question")
	void addQuestionTest() 
	{
		//ouverture du fichier
		TemplateIo.loadCreationTemplate("src/test/resources/resources_service/sampleExiste.xmi");
		
		int pageNumber = presenter.getPresenterPdf().currentPdfPageNumber();
		
		assertNull(service.getQuestionZone(pageNumber,7));

		//Ajoute de la question
		final int id = service.createQuestion(pageNumber,2, 2, 2, 2);

		assertNotNull(service.getQuestionZone(pageNumber,id));
	}


	@Test
	@DisplayName("Test - suppression d'une question")
	void removeQuestionTest() 
	{
		//ouverture du fichier
		TemplateIo.loadCreationTemplate("src/test/resources/resources_service/sampleExiste.xmi");
		
		int pageNumber = presenter.getPresenterPdf().currentPdfPageNumber();
		
		//Creation d'une question
		final int id = service.createQuestion(pageNumber,2, 2, 2, 2);

		assertNotNull(service.getQuestionZone(pageNumber,id));

		service.removeQuestion(id);

		assertNull(service.getQuestionZone(pageNumber,id));
	}

	@Test
	@DisplayName("Test - navigation a la page suivante")
	void nextPageTest() 
	{
		//ouverture du fichier
		TemplateIo.loadCreationTemplate("src/test/resources/resources_service/sampleExiste.xmi");

		int oldPage = presenter.getPresenterPdf().currentPdfPageNumber();
		presenter.getPresenterPdf().nextPdfPage();
		int newPage = presenter.getPresenterPdf().currentPdfPageNumber();

		if(oldPage == presenter.getPresenterPdf().currentPdfPageNumber()) {
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
		TemplateIo.loadCreationTemplate("src/test/resources/resources_service/sampleExiste.xmi");

		int oldPage = presenter.getPresenterPdf().currentPdfPageNumber();
		presenter.getPresenterPdf().previousPdfPage();
		int newPage = presenter.getPresenterPdf().currentPdfPageNumber();

		if(oldPage == 1) {
			assertEquals(presenter.getPresenterPdf().getPdfPageCount(), newPage);
		}else {
			assertEquals(oldPage-1, newPage);
		}
	}

	@Test
	@DisplayName("Test - chargement d'un fichier")
	void loadTest() 
	{
		//Ouverture du fichier
		TemplateIo.loadCreationTemplate("src/test/resources/resources_service/sampleExiste.xmi");

		assertEquals(presenter.getPresenterPdf().currentPdfPageNumber(), 6);
	}


	@Test
	@DisplayName("Test - chargement d'un fichier qui n'existe pas")
	void loadTestRobustesse() 
	{
		//load d'un fichier qui n'existe pas
		Assertions.assertThrows(FileNotFoundException.class, () -> TemplateIo.loadCreationTemplate("src/test/resources/resources_service/NoExiste.xmi"));
	}

	@Test
	@DisplayName("Test - Sauvegarde d'un fichier")
	void saveTest() 
	{
		int pageNumber = presenter.getPresenterPdf().currentPdfPageNumber();
		
		final int id = service.createQuestion(pageNumber,2, 2, 2, 2);

		assertNull(service.getQuestionZone(pageNumber,id));

		service.save(presenter.getPresenterPdf().getPdfOutputStream(),new File("src/test/resources/resources_service/sampleExiste.xmi"));

		TemplateIo.loadCreationTemplate("src/test/resources/resources_service/sampleExiste.xmi");

		//Verification que le changement est toujours d'actualité
		assertNull(service.getQuestionZone(pageNumber,id));
	}
}
