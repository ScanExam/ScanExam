package fr.istic.tools.scanexam.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.services.api.ServiceEdition;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import fr.istic.tools.scanexam.utils.DataFactory;
import fr.istic.tools.scanexam.utils.Tuple3;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;

public class ExamGraduationServiceTest {
	ServiceGraduation service;

	/*
	 * Base de confiance : getStudentSheets, assignStudentId,
	 * getQuestionGradeEntries, getQuestionSelectedGradeEntries, getStudentInfos
	 */

	ControllerFxGraduation controller;

	/**
	 * Ouvre une nouvelle correction
	 */
	void openGraduation() {
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\fooCorrection.xmi").toString();
		// Ouverture du fichier
		service.openCorrectionTemplate(new File(path));
	}

	/**
	 * Ouvre un nouveau modèle d'examen
	 */
	void openTemplate() {
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\examTest.xmi").toString();
		// Ouverture du fichier
		((ServiceEdition) service).open(path);
	}

	@BeforeEach
	void init() {
		final ServiceImpl service = new ServiceImpl();
		this.service = service;
	}

	@Test
	@DisplayName("Test - Ouverture d'un fichier XMI")
	void openTest() {
		// Permet de convertir le path en fonction de l'OS
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\fooCorrection.xmi").toString();
		// Ouverture du fichier
		final Optional<InputStream> streamOpt = service.openCorrectionTemplate(new File(path));

		assertTrue(streamOpt.isPresent());

		assertEquals(3, service.getStudentSheets().size());
		assertEquals("a", service.getStudentSheets().iterator().next().getSheetName());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Ouverture d'un fichier XMI non valide")
	void openTest2() {
		// Permet de convertir le path en fonction de l'OS
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\notExlist.xmi").toString();
		// Ouverture du fichier
		final Optional<InputStream> streamOpt = service.openCorrectionTemplate(new File(path));

		assertFalse(streamOpt.isPresent());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Ouverture d'un fichier XMI non valide")
	void openTest3() {
		// Permet de convertir le path en fonction de l'OS
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\examTest.xmi").toString();
		// Ouverture du fichier
		final Optional<InputStream> streamOpt = service.openCorrectionTemplate(new File(path));

		assertFalse(streamOpt.isPresent());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Ouverture d'un fichier XMI qui n'est pas un XMI")
	void openTest4() {
		// Permet de convertir le path en fonction de l'OS
		final String path = System.getProperty("user.dir")
				+ Paths.get("\\src\\test\\resources\\resource_service\\pfo_example.pdf").toString();
		// Ouverture du fichier
		final Optional<InputStream> streamOpt = service.openCorrectionTemplate(new File(path));

		assertFalse(streamOpt.isPresent());
	}

	@Test
	@DisplayName("Test - Nouvelle correction")
	void createGraduationTest() {
		final var sheet1 = new DataFactory().createStudentSheet(0, List.of(1, 2, 3));
		sheet1.setSheetName("a");
		final var sheet2 = new DataFactory().createStudentSheet(1, List.of(4, 5, 6));
		final var sheet3 = new DataFactory().createStudentSheet(2, List.of(7, 8, 9));

		openTemplate();

		// Nouvelle correction
		final boolean result = service.initializeCorrection(List.of(sheet1, sheet2, sheet3), List.of(), List.of());

		assertTrue(result);

		assertEquals(3, service.getStudentSheets().size());
		assertEquals("a", service.getStudentSheets().iterator().next().getSheetName());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Nouvelle correction sans qu'un modèle d'édition soit ouvert")
	void createGraduationTest2() {
		final var sheet1 = new DataFactory().createStudentSheet(0, List.of(1, 2, 3));
		sheet1.setSheetName("a");
		final var sheet2 = new DataFactory().createStudentSheet(1, List.of(4, 5, 6));
		final var sheet3 = new DataFactory().createStudentSheet(2, List.of(7, 8, 9));

		// Nouvelle correction
		final boolean result = service.initializeCorrection(List.of(sheet1, sheet2, sheet3), List.of(), List.of());

		assertFalse(result);
	}

	@Test
	@DisplayName("Test - Sélection de la copie suivante")
	void nextSheetTest() {
		openTemplate();
		openGraduation();

		service.nextSheet();

		final var iterator = service.getStudentSheets().iterator();
		// On accède à la deuxième copie
		iterator.next();
		final StudentSheet sheet = iterator.next();

		assertEquals("b", sheet.getSheetName());

		service.assignStudentName("foo");
		assertEquals("foo", sheet.getSheetName());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Appel de la fonction nextSheet plus de fois qu'il n'y a de copies")
	void nextSheetTest2() {
		openTemplate();
		openGraduation();

		for (int i = 0; i < 5; i++)
			service.nextSheet();

		final var iterator = service.getStudentSheets().iterator();
		StudentSheet sheet = null;
		// On accède à la dernière copie
		while (iterator.hasNext())
			sheet = iterator.next();

		assertEquals("c", sheet.getSheetName());
		service.assignStudentName("foo");
		assertEquals("foo", sheet.getSheetName());
	}

	@Test
	@DisplayName("Test - Sélection de la copie précédente")
	void previousSheetTest() {
		openTemplate();
		openGraduation();

		// nextPage est vérifié, on essaye alors de retourner à la première page
		service.nextSheet();
		service.previousSheet();

		final var iterator = service.getStudentSheets().iterator();
		// On accède à la première copie
		final StudentSheet sheet = iterator.next();

		assertEquals("a", sheet.getSheetName());

		service.assignStudentName("foo");
		assertEquals("foo", sheet.getSheetName());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Appel de la fonction previousSheet lorsque l'on est sur la première copie")
	void previousSheetTest2() {
		openTemplate();
		openGraduation();

		service.previousSheet();

		// On accède à la première copie
		final var iterator = service.getStudentSheets().iterator();
		final StudentSheet sheet = iterator.next();

		assertEquals("a", sheet.getSheetName());
		service.assignStudentName("foo");
		assertEquals("foo", sheet.getSheetName());
	}

	@Test
	@DisplayName("Test - Obtention du de l'étudiant associé à une copie")
	void getSheetNameTest() {
		openTemplate();
		openGraduation();

		assertEquals("a", service.getStudentName(0).get());
		assertEquals("c", service.getStudentName(2).get());
		assertEquals("b", service.getStudentName(1).get());

		service.assignStudentName("foo");

		assertEquals("foo", service.getStudentName(0).get());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Obtention du de l'étudiant associé à une copie si l'ID de la copie n'existe pas")
	void getSheetNameTest2() {
		openTemplate();
		openGraduation();

		assertTrue(service.getStudentName(-1).isEmpty());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Édition de la liste des StudentSheets")
	void getStudentSheetsTest() {
		assertTrue(service.getStudentSheets().isEmpty());
		openTemplate();
		openGraduation();

		final var sheet = service.getStudentSheets().iterator().next();

		final var newSheet1 = new DataFactory().createStudentSheet(-1, List.of(4, 5, 6));
		final var newSheet2 = new DataFactory().createStudentSheet(-2, List.of(7, 8, 9));

		final var list = service.getStudentSheets();
		final var listCopy = new LinkedList<>(list);

		assertThrows(UnsupportedOperationException.class, () -> list.clear());
		assertThrows(UnsupportedOperationException.class, () -> list.remove(sheet));
		assertThrows(UnsupportedOperationException.class, () -> list.removeAll(List.of(sheet)));
		assertThrows(UnsupportedOperationException.class, () -> list.add(newSheet1));
		assertThrows(UnsupportedOperationException.class, () -> list.addAll(List.of(newSheet1, newSheet2)));

		assertEquals(listCopy, list);
	}

	@Test
	@DisplayName("Test - Sélection d'une copie à partir de son ID")
	void selectSheetTest() {
		openTemplate();
		openGraduation();

		service.selectSheet(1);

		final var iterator = service.getStudentSheets().iterator();
		// On accède à la deuxième copie
		iterator.next();
		final StudentSheet sheet = iterator.next();

		assertEquals("b", sheet.getSheetName());

		service.assignStudentName("foo");

		assertEquals("foo", sheet.getSheetName());
	}

	@Test
	@DisplayName("Test - Sélection d'une copie à partir d'un ID qui n'existe pas")
	void selectSheetTest2() {
		openTemplate();
		openGraduation();

		final var iterator = service.getStudentSheets().iterator();

		{
			service.selectSheet(-1);
			final StudentSheet sheet = iterator.next();
			assertEquals("a", sheet.getSheetName());
			service.assignStudentName("foo");
			assertEquals("foo", sheet.getSheetName());
		}

		{
			service.nextSheet();
			service.selectSheet(-10);
			final StudentSheet sheet = iterator.next();
			assertEquals("b", sheet.getSheetName());
			service.assignStudentName("bar");
			assertEquals("bar", sheet.getSheetName());
		}
	}

	@Test
	@DisplayName("Test - Obtention du nombre de pages")
	void getPageAmountTest() {
		openTemplate();
		openGraduation();

		assertEquals(7, service.getPageAmount());
	}

	@Test
	@DisplayName("Test - Obtention du nombre de questions")
	void numberOfQuestionsTest() {
		openTemplate();
		openGraduation();

		assertEquals(2, service.numberOfQuestions());
	}

	@Test
	@DisplayName("Test - Ajout d'une nouvelle entrée à la grille de correction")
	void addEntryTest() {
		openTemplate();
		openGraduation();

		{
			final var entries = service.getQuestionGradeEntries(0);
			assertTrue(entries.isEmpty());
		}

		final var tuple1 = Tuple3.of(0, "foo", 2f);

		{
			final var optRes1 = service.addEntry(0, tuple1._2, tuple1._3);
			final var entries = service.getQuestionGradeEntries(0);
			assertTrue(optRes1.isPresent());
			assertEquals(0, optRes1.get());
			assertEquals(1, entries.size());
			assertEquals(tuple1, entries.get(0));
		}

		final var tuple2 = Tuple3.of(1, "bar", 0.5f);

		{
			final var optRes2 = service.addEntry(0, tuple2._2, tuple2._3);
			final var entries = service.getQuestionGradeEntries(0);
			assertTrue(optRes2.isPresent());
			assertEquals(1, optRes2.get());
			assertEquals(2, entries.size());
			assertEquals(tuple1, entries.get(0));
			assertEquals(tuple2, entries.get(1));
		}

		// Cela n'affecte pas les autres questions
		{
			final var entries = service.getQuestionGradeEntries(1);
			assertTrue(entries.isEmpty());
		}
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Ajout d'une nouvelle entrée à la grille de correction sur une quesrion qui n'existe pas")
	void addEntryTest2() {
		openTemplate();
		openGraduation();

		{
			final var entries = service.getQuestionGradeEntries(0);
			assertTrue(entries.isEmpty());
		}

		final var tuple1 = Tuple3.of(0, "foo", 2f);

		{
			final var optRes1 = service.addEntry(-1, tuple1._2, tuple1._3);
			assertTrue(optRes1.isEmpty());
		}
	}

	@Test
	@DisplayName("Test - Modifie une entrée de la grille de correction")
	void modifyEntryTest() {
		openTemplate();
		openGraduation();

		final int id = service.addEntry(0, "foo", 2f).get();
		service.addEntry(0, "bar", 0.5f);

		service.modifyEntry(0, id, "foo2", 3);

		final var tuple1 = Tuple3.of(0, "foo2", 3f);
		final var tuple2 = Tuple3.of(1, "bar", 0.5f);
		final var entries = service.getQuestionGradeEntries(0);
		assertEquals(tuple1, entries.get(0));
		assertEquals(tuple2, entries.get(1));
	}

	@Test
	@DisplayName("Test - Supprime une entrée de la grille de correction")
	void removeEntryTest() {
		openTemplate();
		openGraduation();

		final int id = service.addEntry(0, "foo", 2f).get();
		final var tuple = Tuple3.of(1, "bar", 0.5f);
		service.addEntry(0, tuple._2, tuple._3);

		service.removeEntry(0, id);
		final var entries = service.getQuestionGradeEntries(0);

		assertEquals(1, entries.size());
		assertEquals(tuple, entries.get(0));
	}

	@Test
	@DisplayName("Test - Assigne une entrée à une réponse d'une copie")
	void assignGradeEntryTest() {
		openTemplate();
		openGraduation();

		final int id1 = service.addEntry(0, "foo", 0.5f).get();
		final int id2 = service.addEntry(0, "bar", 0.5f).get();

		{
			final var selected = service.getQuestionSelectedGradeEntries(0);
			assertTrue(selected.isEmpty());
		}

		final boolean result = service.assignGradeEntry(0, id1);
		assertTrue(result);

		{
			final var selected = service.getQuestionSelectedGradeEntries(0);
			assertEquals(1, selected.size());
			assertEquals(id1, selected.get(0));
		}

		{
			final boolean result2 = service.assignGradeEntry(0, id2);
			assertTrue(result2);
			final var selected = service.getQuestionSelectedGradeEntries(0);
			assertEquals(2, selected.size());
			assertEquals(id1, selected.get(0));
			assertEquals(id2, selected.get(1));
		}

		// Pas d'effet de bord
		for (int i = 1; i < service.numberOfQuestions(); i++)
			assertTrue(service.getQuestionSelectedGradeEntries(i).isEmpty());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Assigne des entrées dont la somme dépasse la valeur du barème ou est négative")
	void assignGradeEntryTest2() {
		openTemplate();
		openGraduation();

		final int id1 = service.addEntry(0, "foo", 0.5f).get();
		final int id2 = service.addEntry(0, "bar", 1f).get();
		final int id3 = service.addEntry(0, "bar", -1f).get();

		final boolean result = service.assignGradeEntry(0, id1);
		assertTrue(result);

		{
			final boolean result2 = service.assignGradeEntry(0, id2);
			assertFalse(result2);
			final var selected = service.getQuestionSelectedGradeEntries(0);
			assertEquals(1, selected.size());
			assertEquals(id1, selected.get(0));
		}

		{
			final boolean result2 = service.assignGradeEntry(0, id3);
			assertFalse(result2);
			final var selected = service.getQuestionSelectedGradeEntries(0);
			assertEquals(1, selected.size());
			assertEquals(id1, selected.get(0));
		}

		// Pas d'effet de bord
		for (int i = 1; i < service.numberOfQuestions(); i++)
			assertTrue(service.getQuestionSelectedGradeEntries(i).isEmpty());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Assigne une entrée qui n'existe pas")
	void assignGradeEntryTest3() {
		openTemplate();
		openGraduation();

		service.addEntry(0, "foo", 0.5f).get();

		final boolean result = service.assignGradeEntry(0, -1);
		assertFalse(result);

		{
			final var selected = service.getQuestionSelectedGradeEntries(0);
			assertTrue(selected.isEmpty());
		}

		// Pas d'effet de bord
		for (int i = 1; i < service.numberOfQuestions(); i++)
			assertTrue(service.getQuestionSelectedGradeEntries(i).isEmpty());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Assigne une entrée à une question qui n'existe pas")
	void assignGradeEntryTest4() {
		openTemplate();
		openGraduation();

		final int id = service.addEntry(0, "foo", 0.5f).get();

		final boolean result = service.assignGradeEntry(-1, id);
		assertFalse(result);

		{
			final var selected = service.getQuestionSelectedGradeEntries(0);
			assertTrue(selected.isEmpty());
		}

		// Pas d'effet de bord
		for (int i = 1; i < service.numberOfQuestions(); i++)
			assertTrue(service.getQuestionSelectedGradeEntries(i).isEmpty());
	}

	@Test
	@DisplayName("Test - Retire une entrée à une question")
	void retractGradeEntryTest() {
		openTemplate();
		openGraduation();

		final int id1 = service.addEntry(0, "foo", 0.5f).get();
		final int id2 = service.addEntry(0, "bar", 0.5f).get();

		service.assignGradeEntry(0, id1);
		service.assignGradeEntry(0, id2);

		final boolean result = service.retractGradeEntry(0, id1);
		assertTrue(result);

		final var selected = service.getQuestionSelectedGradeEntries(0);
		assertEquals(1, selected.size());
		assertEquals(id2, selected.get(0));
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Retire une entrée à une question dont la nouvelle somme fait dépasser le barème ou est négative")
	void retractGradeEntryTest2() {
		openTemplate();
		openGraduation();

		final int id1 = service.addEntry(0, "foo", -0.25f).get();
		final int id2 = service.addEntry(0, "bar", 0.5f).get();

		service.assignGradeEntry(0, id2);
		service.assignGradeEntry(0, id1);

		{
			service.retractGradeEntry(0, id2);
			final var selected = service.getQuestionSelectedGradeEntries(0);
			assertEquals(2, selected.size());
			assertEquals(id1, selected.get(1));
			assertEquals(id2, selected.get(0));
		}
	}

	@Test
	@DisplayName("Test - Calcule la note maximale obtenable par l'étudiant pour les réponses déjà notées")
	void getCurrentMaxGradeTest() {
		openTemplate();
		openGraduation();

		final int id1 = service.addEntry(0, "foo", 0.5f).get();
		final int id2 = service.addEntry(0, "bar", 0.25f).get();
		service.addEntry(0, "bar2", 1f).get();
		final int id4 = service.addEntry(1, "foo", 0.75f).get();

		assertEquals(0, service.getCurrentMaxGrade());

		service.assignGradeEntry(0, id1);
		assertEquals(1, service.getCurrentMaxGrade());

		service.assignGradeEntry(0, id2);
		assertEquals(1, service.getCurrentMaxGrade());

		service.assignGradeEntry(1, id4);
		assertEquals(2, service.getCurrentMaxGrade());
	}

	@Test
	@DisplayName("Test - Calcule la note obtenue par l'étudiant")
	void getCurrentMaxGradeTest2() {
		openTemplate();
		openGraduation();

		final int id1 = service.addEntry(0, "foo", 0.5f).get();
		final int id2 = service.addEntry(0, "bar", 0.25f).get();
		service.addEntry(0, "bar2", 1f).get();
		final int id4 = service.addEntry(1, "foo", 0.75f).get();

		assertEquals(0, service.getCurrentGrade());

		service.assignGradeEntry(0, id1);
		assertEquals(0.5, service.getCurrentGrade());

		service.assignGradeEntry(0, id2);
		assertEquals(0.75, service.getCurrentGrade());

		service.assignGradeEntry(1, id4);
		assertEquals(1.5, service.getCurrentGrade());
	}

	@Test
	@DisplayName("Test - Calcule la note maximale obtenable pour l'examen")
	void getGlobalScale() {
		openTemplate();
		openGraduation();

		assertEquals(2, service.getGlobalScale());
	}

	@Test
	@DisplayName("Test - Définit la liste des informations des étudiants")
	void setStudentInfos() {
		openTemplate();
		openGraduation();

		assertTrue(service.getStudentInfos().isEmpty());

		final List<List<String>> list = new ArrayList<>();
		list.add(Arrays.asList("87459658", "Spring", "Jeannette", "j.spring@mail.org"));
		list.add(Arrays.asList("00458241", "Lapin", "Didier", "didierlapin@mail.org"));

		service.setStudentInfos(list);

		assertEquals(list, service.getStudentInfos());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Définit la liste des informations des étudiants avec une entrée nulle")
	void setStudentInfos2() {
		openTemplate();
		openGraduation();

		assertTrue(service.getStudentInfos().isEmpty());
		assertThrows(NullPointerException.class, () -> service.setStudentInfos(null));
		assertTrue(service.getStudentInfos().isEmpty());
	}
}