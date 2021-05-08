package fr.istic.tools.scanexam.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import fr.istic.tools.scanexam.api.DataFactory;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.services.api.ServiceEdition;
import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import fr.istic.tools.scanexam.utils.Tuple3;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;

// FIXME

public class ExamGraduationServiceTest 
{
	ServiceGraduation service;
	
	/* Base de confiance : getStudentSheets, assignStudentId, getQuestionGradeEntries */
	
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
		((ServiceEdition)service).open(path);
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
		assertEquals("a", service.getStudentSheets().iterator().next().getStudentName());
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
		sheet1.setStudentName("a");
		final var sheet2 = new DataFactory().createStudentSheet(1, List.of(4, 5, 6));
		final var sheet3 = new DataFactory().createStudentSheet(2, List.of(7, 8, 9));
		
		openTemplate();
		
		// Nouvelle correction
		final boolean result = service.initializeCorrection(List.of(sheet1, sheet2, sheet3));
		
		assertTrue(result);
		
		assertEquals(3, service.getStudentSheets().size());
		assertEquals("a", service.getStudentSheets().iterator().next().getStudentName());
	}
	
	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Nouvelle correction sans qu'un modèle d'édition soit ouvert")
	void createGraduationTest2() {
		final var sheet1 = new DataFactory().createStudentSheet(0, List.of(1, 2, 3));
		sheet1.setStudentName("a");
		final var sheet2 = new DataFactory().createStudentSheet(1, List.of(4, 5, 6));
		final var sheet3 = new DataFactory().createStudentSheet(2, List.of(7, 8, 9));
		
		// Nouvelle correction
		final boolean result = service.initializeCorrection(List.of(sheet1, sheet2, sheet3));
		
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
		
		assertEquals("b", sheet.getStudentName());
		
		service.assignStudentName("foo");
		assertEquals("foo", sheet.getStudentName());
	}
	
	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Appel de la fonction nextSheet plus de fois qu'il n'y a de copies")
	void nextSheetTest2() {
		openTemplate();
		openGraduation();
		
		for(int i = 0; i < 5; i++)
			service.nextSheet();
		
		final var iterator = service.getStudentSheets().iterator();
		StudentSheet sheet = null;
		// On accède à la dernière copie
		while(iterator.hasNext())
			sheet = iterator.next();
		
		assertEquals("c", sheet.getStudentName());
		service.assignStudentName("foo");
		assertEquals("foo", sheet.getStudentName());
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
		
		assertEquals("a", sheet.getStudentName());
		
		service.assignStudentName("foo");
		assertEquals("foo", sheet.getStudentName());
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
		
		assertEquals("a", sheet.getStudentName());
		service.assignStudentName("foo");
		assertEquals("foo", sheet.getStudentName());
	}
	
	
	@Test
	@DisplayName("Test - Obtention du de l'étudiant associé à une copie")
	void getStudentNameTest() {
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
	void getStudentNameTest2() {
		openTemplate();
		openGraduation();
		
		assertTrue(service.getStudentName(-1).isEmpty());
	}
	
	
	@Test
	@Tag("Robustesse")
	@DisplayName("Test - Édition de la liste des StudentSheets")
	void getStudentSheetsTest() {
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
		
		assertEquals("b", sheet.getStudentName());
		
		service.assignStudentName("foo");
		
		assertEquals("foo", sheet.getStudentName());
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
			assertEquals("a", sheet.getStudentName());
			service.assignStudentName("foo");
			assertEquals("foo", sheet.getStudentName());
		}
		
		{
			service.nextSheet();
			service.selectSheet(-10);
			final StudentSheet sheet = iterator.next();
			assertEquals("b", sheet.getStudentName());
			service.assignStudentName("bar");
			assertEquals("bar", sheet.getStudentName());
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
			service.addEntry(0, tuple1._2, tuple1._3);
			final var entries = service.getQuestionGradeEntries(0);
			assertEquals(1, entries.size());
			assertEquals(tuple1, entries.get(0));
		}
		
		final var tuple2 = Tuple3.of(1, "bar", 0.5f);
		
		{
			service.addEntry(0, tuple2._2, tuple2._3);
			final var entries = service.getQuestionGradeEntries(0);
			assertEquals(2, entries.size());
			assertEquals(tuple1, entries.get(0));
			assertEquals(tuple2, entries.get(1));
		}
	}
}
