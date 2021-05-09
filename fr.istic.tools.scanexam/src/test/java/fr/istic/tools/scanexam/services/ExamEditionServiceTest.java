package fr.istic.tools.scanexam.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import fr.istic.tools.scanexam.core.Question;
import fr.istic.tools.scanexam.services.api.ServiceEdition;
import fr.istic.tools.scanexam.utils.ResourcesUtils;

public class ExamEditionServiceTest {
	ServiceEdition service;


	/* Base de confiance : getQuestionAtPage, getTemplatePageAmount */
	
	@BeforeEach
	void init() {
		service = new ServiceImpl();
	}

	@Test
	@DisplayName("Test - Ouverture d'un fichier XMI")
	void openTest() {
		// Permet de convertir le path en fonction de l'OS
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\examTest.xmi").toString();
		// Ouverture du fichier
		service.open(path);

		assertEquals("Foo", service.getExamName());
		assertEquals(7, service.getTemplatePageAmount());
		assertEquals(2, service.getQuestionAtPage(0).size());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Ouverture d'un fichier XMI qui n'existe pas")
	void openTestRobustesse() {
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\notExist.xmi").toString();
		final Optional<ByteArrayInputStream> inputStreamOpt = service.open(path);
		assertTrue(inputStreamOpt.isEmpty());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Ouverture d'un fichier XMI non valide")
	void openTestRobustesse2() {
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\fooCorrection.xmi").toString();
		final Optional<ByteArrayInputStream> inputStreamOpt = service.open(path);
		assertTrue(inputStreamOpt.isEmpty());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Ouverture d'un fichier qui n'est pas un XMI")
	void openTestRobustesse3() {
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\pfo_example.pdf").toString();
		final Optional<ByteArrayInputStream> inputStreamOpt = service.open(path);
		assertTrue(inputStreamOpt.isEmpty());
	}

	@Test
	@DisplayName("Test - Création d'un nouveau projet")
	void createTest() throws IOException {
		service.initializeEdition(7);
		assertEquals(7, service.getTemplatePageAmount());
	}

	@Test
	@DisplayName("Test - ajout d'une question")
	void addQuestionTest() {
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\examTest.xmi").toString();
		service.open(path);

		// Ajoute d'une question sur la dernière page (avec aucune question)
		{
			final List<Question> list = service.getQuestionAtPage(6);
			assertTrue(list.isEmpty());
			final int id = service.createQuestion(6, 2, 2, 2, 2);
			assertEquals(1, list.size());
			final Question question = list.get(0);
			assertTrue(question.getZone().getX() == 2 && question.getZone().getY() == 2 && question.getZone().getHeigth() == 2 && question.getZone().getWidth() == 2);
			assertTrue(question.getGradeScale().getSteps().isEmpty());
			assertEquals(0, question.getGradeScale().getMaxPoint());
			assertEquals(id, question.getId());
		}

		// Ajoute d'une question sur une page avec des questions
		{
			final List<Question> list = service.getQuestionAtPage(0);
			final List<Question> listCopy = new LinkedList<>(list);
			assertEquals(2, list.size());
			final int id = service.createQuestion(0, 2, 2, 2, 2);
			assertEquals(3, list.size());
			final Question question = list.get(2);
			assertTrue(question.getZone().getX() == 2 && question.getZone().getY() == 2 && question.getZone().getHeigth() == 2 && question.getZone().getWidth() == 2);
			assertTrue(question.getGradeScale().getSteps().isEmpty());
			assertEquals(0, question.getGradeScale().getMaxPoint());
			assertEquals(id, question.getId());
			// Les éléments de la liste sont bien les mêmes que avant ajout, à l'exception de l'élément ajouté
			final List<Question> filteredList = list.stream().filter(q -> q.getId() != id).collect(Collectors.toList());
			assertEquals(filteredList, listCopy);
		}
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - ajout d'une question sur une page qui n'existe pas")
	void addQuestionTest2() {
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\examTest.xmi").toString();
		service.open(path);

		assertThrows(IllegalArgumentException.class, () -> service.createQuestion(8, 2, 2, 2, 2));
		assertThrows(IllegalArgumentException.class, () -> service.createQuestion(-1, 2, 2, 2, 2));
	}

	@Test
	@DisplayName("Test - suppression d'une question")
	void removeQuestionTest() {
		// ouverture du fichier
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\examTest.xmi").toString();
		service.open(path);

		// Sur page vide
		{
			final int id = service.createQuestion(1, 2, 2, 2, 2);
			assertEquals(1, service.getQuestionAtPage(1).size());
			assertTrue(service.removeQuestion(id));
			assertTrue(service.getQuestionAtPage(1).isEmpty());
		}
		// Sur page non vide
		{
			final List<Question> list = service.getQuestionAtPage(0);
			final List<Question> listCopy = new LinkedList<>(service.getQuestionAtPage(0));
			final int id = list.get(0).getId();
			assertEquals(2, list.size());
			assertTrue(service.removeQuestion(id));
			assertEquals(1, list.size());
			// L'élément restant est bien le deuxième élément de la liste initiale
			final var questionOpt = listCopy.stream().filter(q -> q.getId() != id).findFirst();
			assertEquals(questionOpt.orElse(null), list.get(0));
		}
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - suppression d'une question qui n'existe pas")
	void removeQuestionTest2() {
		// ouverture du fichier
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\examTest.xmi").toString();
		service.open(path);

		// Sur page vide
		{
			assertTrue(service.getQuestionAtPage(1).isEmpty());
			assertFalse(service.removeQuestion(42));
			assertTrue(service.getQuestionAtPage(1).isEmpty());
		}
		// Sur page non vide
		{
			final List<Question> list = service.getQuestionAtPage(0);
			final List<Question> listCopy = new LinkedList<>(service.getQuestionAtPage(0));
			assertEquals(2, list.size());
			assertFalse(service.removeQuestion(42));
			assertEquals(2, list.size());
			// La liste n'a pas été modifiée
			assertEquals(list, listCopy);
		}
	}
	
	@Test
	@DisplayName("Test - modification du nombre de point maximum d'une question")
	void modifyMaxPointTest() {
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\examTest.xmi").toString();
		service.open(path);
		
		final Question firstQuestion = service.getQuestionAtPage(0).get(0);
		service.modifyMaxPoint(firstQuestion.getId(), 42);
		assertEquals(42, firstQuestion.getGradeScale().getMaxPoint());
		service.modifyMaxPoint(firstQuestion.getId(), 3);
		assertEquals(3, firstQuestion.getGradeScale().getMaxPoint());
	}
	
	@Test
	@DisplayName("Test - redimensionnement de la zone d'une question")
	void recaleQuestionTest() {
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\examTest.xmi").toString();
		service.open(path);
		
		final Question question = service.getQuestionAtPage(0).get(0);
		service.rescaleQuestion(question.getId(), 2, 3);
		assertTrue(Math.abs(question.getZone().getHeigth() - 2) < 0.00001);
		assertTrue(Math.abs(question.getZone().getWidth() - 3) < 0.00001);
		service.rescaleQuestion(question.getId(), 8, 9);
		assertTrue(Math.abs(question.getZone().getHeigth() - 8) < 0.00001);
		assertTrue(Math.abs(question.getZone().getWidth() - 9) < 0.00001);
	}
	
	@Test
	@DisplayName("Test - déplacement de la zone d'une question")
	void moveQuestionTest() {
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\examTest.xmi").toString();
		service.open(path);
		
		final Question question = service.getQuestionAtPage(0).get(0);
		service.moveQuestion(question.getId(), 2, 3);
		assertTrue(Math.abs(question.getZone().getX() - 2) < 0.00001);
		assertTrue(Math.abs(question.getZone().getY() - 3) < 0.00001);
		service.moveQuestion(question.getId(), 8, 9);
		assertTrue(Math.abs(question.getZone().getX() - 8) < 0.00001);
		assertTrue(Math.abs(question.getZone().getY() - 9) < 0.00001);
	}
	
	@Test
	@DisplayName("Test - renommer une question")
	void renameQuestionTest() {
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\examTest.xmi").toString();
		service.open(path);
		
		final Question question = service.getQuestionAtPage(0).get(0);
		service.renameQuestion(question.getId(), "Foo");
		assertEquals("Foo", question.getName());
		service.renameQuestion(question.getId(), "Bar");
		assertEquals("Bar", question.getName());
	}
	
	@Test
	@DisplayName("Test - renommer une question")
	void hasExamLoadedTest() {
		assertFalse(service.hasExamLoaded());
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\examTest.xmi").toString();
		service.open(path);
		assertTrue(service.hasExamLoaded());
	}

	@Test
	@DisplayName("Test - Sauvegarde d'un fichier")
	void saveTest() throws IOException {
		service.initializeEdition(7);
		service.setExamName("Test");
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\save_test_temp.xmi").toString();
		final var inStream = ResourcesUtils.getInputStreamResource("resource_service/pfo_example.pdf");
		final var outStream = new ByteArrayOutputStream();
		service.createQuestion(0, 2, 2, 2, 2);
		outStream.writeBytes(inStream.readAllBytes());
		service.saveEdition(outStream, new File(path));
		service = new ServiceImpl();
		service.open(path);
		assertEquals("Test", service.getExamName());
		assertEquals(1, service.getQuestionAtPage(0).size());
		final Question question = service.getQuestionAtPage(0).get(0);
		assertEquals(0, question.getId());
		assertEquals(2, question.getZone().getX());
		assertEquals(2, question.getZone().getY());
		assertEquals(2, question.getZone().getWidth());
		assertEquals(2, question.getZone().getHeigth());
		(new File(path)).delete();
	}
}
