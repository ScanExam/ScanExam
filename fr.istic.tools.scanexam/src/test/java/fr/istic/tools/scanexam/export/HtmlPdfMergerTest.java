package fr.istic.tools.scanexam.export;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.istic.tools.scanexam.exportation.HtmlPdfMerger;

/**
 * Test la classe HtmlPdfMerger qui ajoute du contenu html à un document pdf
 * 
 * @author Julien Cochet
 */
class HtmlPdfMergerTest {

	// ----------------------------------------------------------------------------------------------------
	/*
	 * ATTRIBUTS
	 */
	// ----------------------------------------------------------------------------------------------------

	/* Logger */
	private final static Logger LOGGER = LogManager.getLogger();
	/* Chemin vers les documents */
	private final static String RESOURCES_PATH = "src/test/resources/resources_export/";

	// ----------------------------------------------------------------------------------------------------
	/*
	 * TESTS
	 */
	// ----------------------------------------------------------------------------------------------------

	/**
	 * Supprime tous les éléments générés lors des précédents tests
	 */
	@BeforeAll
	static void init() {
		try {
			FileUtils.cleanDirectory(new File(RESOURCES_PATH + "generated/"));
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.info("Cannot find repository \"generated/\".");
		}
	}

	/**
	 * Vérifie la création d'un pdf à partir d'un fichier html
	 */
	@Test
	@DisplayName("Test - Creating a pdf from an html file")
	void testCreatePdfFromHtmlFile() {
		File htmlPage = new File(RESOURCES_PATH + "htmlPageWithoutResources.html");
		if (htmlPage.exists()) {
			Path pdfFilePath = Paths.get(RESOURCES_PATH + "generated/htmlPageWithoutResources.pdf");
			HtmlPdfMerger.createPdfFromHtmlFile(htmlPage, pdfFilePath);
			assertTrue(pdfFilePath.toFile().exists());
		} else {
			fail("htmlPageWithoutResources.html don't exist.");
		}
	}

	/**
	 * Vérifie la création d'un pdf à partir d'un fichier html avec du css et une
	 * image
	 */
	@Test
	@DisplayName("Test - Creating a pdf from an html file with resources")
	void testCreatePdfFromHtmlFileWithResources() {
		File htmlPage = new File(RESOURCES_PATH + "htmlPageWithResources.html");
		if (htmlPage.exists()) {
			Path pdfFilePath = Paths.get(RESOURCES_PATH + "generated/htmlPageWithResources.pdf");
			HtmlPdfMerger.createPdfFromHtmlFile(htmlPage, pdfFilePath);
			assertTrue(pdfFilePath.toFile().exists());
		} else {
			fail("htmlPageWithResources.html don't exist.");
		}
	}

	/**
	 * Vérifie la création d'un pdf à partir de contenu html
	 */
	@Test
	@DisplayName("Test - Creating a pdf from html content")
	void testCreatePdfFromHtmlContent() {
		String htmlContent = "<!DOCTYPE html>\r\n" + "<html lang=\"fr\">\r\n" + "	<head>\r\n"
				+ "		<meta charset=\"utf-8\"></meta>\r\n"
				+ "		<title>HTML Content Without Resources</title>\r\n" + "	</head>\r\n" + "	<body>\r\n"
				+ "		<h1>HTML Content</h1>\r\n" + "		<h2>Without Resources</h2>\r\n"
				+ "		<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat. Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue. Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue. Praesent egestas leo in pede. Praesent blandit odio eu enim. Pellentesque sed dui ut augue blandit sodales. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris sed pede pellentesque fermentum. Maecenas adipiscing ante non diam sodales hendrerit.</p>\r\n"
				+ "		<p>Ut velit mauris, egestas sed, gravida nec, ornare ut, mi. Aenean ut orci vel massa suscipit pulvinar. Nulla sollicitudin. Fusce varius, ligula non tempus aliquam, nunc turpis ullamcorper nibh, in tempus sapien eros vitae ligula. Pellentesque rhoncus nunc et augue. Integer id felis. Curabitur aliquet pellentesque diam. Integer quis metus vitae elit lobortis egestas. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Morbi vel erat non mauris convallis vehicula. Nulla et sapien. Integer tortor tellus, aliquam faucibus, convallis id, congue eu, quam. Mauris ullamcorper felis vitae erat. Proin feugiat, augue non elementum posuere, metus purus iaculis lectus, et tristique ligula justo vitae magna.</p>\r\n"
				+ "		<p>Aliquam convallis sollicitudin purus. Praesent aliquam, enim at fermentum mollis, ligula massa adipiscing nisl, ac euismod nibh nisl eu lectus. Fusce vulputate sem at sapien. Vivamus leo. Aliquam euismod libero eu enim. Nulla nec felis sed leo placerat imperdiet. Aenean suscipit nulla in justo. Suspendisse cursus rutrum augue. Nulla tincidunt tincidunt mi. Curabitur iaculis, lorem vel rhoncus faucibus, felis magna fermentum augue, et ultricies lacus lorem varius purus. Curabitur eu amet.</p>\r\n"
				+ "	</body>\r\n" + "</html>";
		Path pdfFilePath = Paths.get(RESOURCES_PATH + "generated/htmlContentWithoutResources.pdf");
		HtmlPdfMerger.createPdfFromHtmlContent(htmlContent, null, pdfFilePath);
		assertTrue(pdfFilePath.toFile().exists());
	}

	/**
	 * Vérifie la création d'un pdf à partir de contenu html avec du css et une
	 * image
	 */
	@Test
	@DisplayName("Test - Creating a pdf from an html content with resources")
	void testCreatePdfFromHtmlContentWithResources() {
		String htmlContent = "<!DOCTYPE html>\r\n" + "<html lang=\"fr\">\r\n" + "	<head>\r\n"
				+ "		<meta charset=\"utf-8\"></meta>\r\n"
				+ "		<link rel=\"stylesheet\" href=\"styles.css\"></link>\r\n"
				+ "		<title>HTML Content With Resources</title>\r\n" + "	</head>\r\n" + "	<body>\r\n"
				+ "		<h1>HTML Content</h1>\r\n" + "		<h2>With Resources</h2>\r\n"
				+ "		<img src=\"m87blackhole.jpg\" alt=\"M87*\"></img>\r\n"
				+ "		<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat. Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue. Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue. Praesent egestas leo in pede. Praesent blandit odio eu enim. Pellentesque sed dui ut augue blandit sodales. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris sed pede pellentesque fermentum. Maecenas adipiscing ante non diam sodales hendrerit.</p>\r\n"
				+ "		<p>Ut velit mauris, egestas sed, gravida nec, ornare ut, mi. Aenean ut orci vel massa suscipit pulvinar. Nulla sollicitudin. Fusce varius, ligula non tempus aliquam, nunc turpis ullamcorper nibh, in tempus sapien eros vitae ligula. Pellentesque rhoncus nunc et augue. Integer id felis. Curabitur aliquet pellentesque diam. Integer quis metus vitae elit lobortis egestas. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Morbi vel erat non mauris convallis vehicula. Nulla et sapien. Integer tortor tellus, aliquam faucibus, convallis id, congue eu, quam. Mauris ullamcorper felis vitae erat. Proin feugiat, augue non elementum posuere, metus purus iaculis lectus, et tristique ligula justo vitae magna.</p>\r\n"
				+ "		<p>Aliquam convallis sollicitudin purus. Praesent aliquam, enim at fermentum mollis, ligula massa adipiscing nisl, ac euismod nibh nisl eu lectus. Fusce vulputate sem at sapien. Vivamus leo. Aliquam euismod libero eu enim. Nulla nec felis sed leo placerat imperdiet. Aenean suscipit nulla in justo. Suspendisse cursus rutrum augue. Nulla tincidunt tincidunt mi. Curabitur iaculis, lorem vel rhoncus faucibus, felis magna fermentum augue, et ultricies lacus lorem varius purus. Curabitur eu amet.</p>\r\n"
				+ "	</body>\r\n" + "</html>";
		Path resourcesPath = Paths.get(RESOURCES_PATH);
		Path pdfFilePath = Paths.get(RESOURCES_PATH + "generated/htmlContentWithResources.pdf");
		HtmlPdfMerger.createPdfFromHtmlContent(htmlContent, resourcesPath, pdfFilePath);
		assertTrue(pdfFilePath.toFile().exists());
	}

	/**
	 * Vérifie la création d'un nouveau pdf à partir d'un autre pdf et d'un fichier
	 * html
	 */
	@Test
	@DisplayName("Test - Merge an html file and a pdf")
	void testMergeHtmlFileWithPdf() {
		File htmlPage = new File(RESOURCES_PATH + "htmlPageWithResources.html");
		File pdfFile = new File(RESOURCES_PATH + "pdf.pdf");
		if (htmlPage.exists() && pdfFile.exists()) {
			Path newPdfFilePath = Paths.get(RESOURCES_PATH + "generated/pdfHtmlPageMerged.pdf");
			HtmlPdfMerger.mergeHtmlFileWithPdf(htmlPage, pdfFile, newPdfFilePath, true);
			assertTrue(newPdfFilePath.toFile().exists());
		} else {
			fail("htmlPageWithResources.html or pdf.pdf don't exist.");
		}
	}

	/**
	 * Vérifie la création d'un nouveau pdf à partir d'un autre pdf et de contenu
	 * html
	 */
	@Test
	@DisplayName("Test - Merge html content and a pdf")
	void testMergeHtmlContentWithPdf() {
		File pdfFile = new File(RESOURCES_PATH + "pdf.pdf");
		if (pdfFile.exists()) {
			String htmlContent = "<!DOCTYPE html>\r\n" + "<html lang=\"fr\">\r\n" + "	<head>\r\n"
					+ "		<meta charset=\"utf-8\"></meta>\r\n"
					+ "		<link rel=\"stylesheet\" href=\"styles.css\"></link>\r\n"
					+ "		<title>HTML Content With Resources</title>\r\n" + "	</head>\r\n" + "	<body>\r\n"
					+ "		<h1>HTML Content</h1>\r\n" + "		<h2>With Resources</h2>\r\n"
					+ "		<img src=\"m87blackhole.jpg\" alt=\"M87*\"></img>\r\n"
					+ "		<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat. Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue. Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue. Praesent egestas leo in pede. Praesent blandit odio eu enim. Pellentesque sed dui ut augue blandit sodales. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris sed pede pellentesque fermentum. Maecenas adipiscing ante non diam sodales hendrerit.</p>\r\n"
					+ "		<p>Ut velit mauris, egestas sed, gravida nec, ornare ut, mi. Aenean ut orci vel massa suscipit pulvinar. Nulla sollicitudin. Fusce varius, ligula non tempus aliquam, nunc turpis ullamcorper nibh, in tempus sapien eros vitae ligula. Pellentesque rhoncus nunc et augue. Integer id felis. Curabitur aliquet pellentesque diam. Integer quis metus vitae elit lobortis egestas. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Morbi vel erat non mauris convallis vehicula. Nulla et sapien. Integer tortor tellus, aliquam faucibus, convallis id, congue eu, quam. Mauris ullamcorper felis vitae erat. Proin feugiat, augue non elementum posuere, metus purus iaculis lectus, et tristique ligula justo vitae magna.</p>\r\n"
					+ "		<p>Aliquam convallis sollicitudin purus. Praesent aliquam, enim at fermentum mollis, ligula massa adipiscing nisl, ac euismod nibh nisl eu lectus. Fusce vulputate sem at sapien. Vivamus leo. Aliquam euismod libero eu enim. Nulla nec felis sed leo placerat imperdiet. Aenean suscipit nulla in justo. Suspendisse cursus rutrum augue. Nulla tincidunt tincidunt mi. Curabitur iaculis, lorem vel rhoncus faucibus, felis magna fermentum augue, et ultricies lacus lorem varius purus. Curabitur eu amet.</p>\r\n"
					+ "	</body>\r\n" + "</html>";
			Path resourcesPath = Paths.get(RESOURCES_PATH);
			Path newPdfFilePath = Paths.get(RESOURCES_PATH + "generated/pdfHtmlContentMerged.pdf");
			HtmlPdfMerger.mergeHtmlContentWithPdf(htmlContent, resourcesPath, pdfFile, newPdfFilePath, true);
			assertTrue(newPdfFilePath.toFile().exists());
		} else {
			fail("pdf.pdf don't exist.");
		}
	}

	/**
	 * Vérifie la création d'un pdf fusionnant une page html et en pdf. Ce nouveau
	 * pdf écrasant celui donné en paramètre
	 */
	@Test
	@DisplayName("Test - Merge an html file and a pdf")
	void testMergeHtmlFileWithPdfOverwrite() {
		// Copie du pdf dans genereted pour pouvoir faire le test plusieurs fois
		Path originalPdfPath = Paths.get(RESOURCES_PATH + "pdf.pdf");
		Path copiedPdfPath = Paths.get(RESOURCES_PATH + "generated/copiedPdf.pdf");
		try {
			Files.copy(originalPdfPath, copiedPdfPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			LOGGER.info("Cannot find pdf.pdf.");
			e.printStackTrace();
		}

		// Tests de réécriture du pdf
		File htmlPage = new File(RESOURCES_PATH + "htmlPageWithResources.html");
		File pdfFile = copiedPdfPath.toFile();
		if (htmlPage.exists() && pdfFile.exists()) {
			// Nombre de fichiers dans generated/
			int nbGeneratedBefore = Paths.get(RESOURCES_PATH + "generated/").toFile().list().length;

			// Ecrasement du pdf
			HtmlPdfMerger.mergeHtmlFileWithPdf(htmlPage, pdfFile, copiedPdfPath, true);

			// Nombre de fichiers dans generated/
			int nbGeneratedAfter = Paths.get(RESOURCES_PATH + "generated/").toFile().list().length;

			assertEquals(nbGeneratedBefore, nbGeneratedAfter);
		} else {
			fail("htmlPageWithResources.html or pdf.pdf don't exist.");
		}
	}

}
