package fr.istic.tools.scanexam.interfaceFX;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import fr.istic.tools.scanexam.view.fx.PdfManager;

/**
 * Classe de tests unitaires de la classe {@link PdfManager} 
 * @author Benjamin
 *
 */
public class PdfManagerTest {
	
	final String PDF_PATH = "src/test/resources/resource_service/pfo_example.pdf";

	private PdfManager manager = new PdfManager();
	
	/* Base de confiance : create, pdfPageCount */
	
	@BeforeEach
	void init() {
		manager.create(new File(PDF_PATH));
	}
	
	@Test
	@DisplayName("Test go to page")
	void testGoToPage() {
		manager.goToPdfPage(3);
		assertEquals(3, manager.getPdfPageIndex());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test go to page inexistante")
	void testGoToPageInexistante() {
		int totalNumberOfPages = manager.getPdfPageCount();
		manager.goToPdfPage(totalNumberOfPages+1);
		assertEquals(0, manager.getPdfPageIndex());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test go to page negative")
	void testGoToPageNegative() {
		manager.goToPdfPage(-1);
		assertEquals(0, manager.getPdfPageIndex());
	}

	@Test
	@DisplayName("Test nextPage ")
	void testNextPage() {
		manager.nextPdfPage();
		assertEquals(1, manager.getPdfPageIndex());
	}

	@Test
	@DisplayName("Test reviousPage ")
	void testPreviousPage() {
		manager.goToPdfPage(2);
		manager.previousPdfPage();
		assertEquals(1, manager.getPdfPageIndex());
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Test nextPage out of bounds")
	void testNextPageOOB() {
		int totalNumberOfPages = manager.getPdfPageCount()-1;//0..n-1
		manager.goToPdfPage(totalNumberOfPages);
		manager.nextPdfPage();
		assertEquals(totalNumberOfPages, manager.getPdfPageIndex());
	}
	
	@Test
	@Tag("Robustesse")
	@DisplayName("Test previousPage out of bounds")
	void testPreviousPageOOB() {
		manager.previousPdfPage();
		assertEquals(0, manager.getPdfPageIndex());
	}
	
	@Test
	@DisplayName("Test number of pages")
	void testNumberOfPages() {
		assertEquals(7, manager.getPdfPageCount());
	}

	@Test
	@DisplayName("Test correct page")
	void testCorrectPage() {
		assertTrue(manager.atCorrectPage(0));
	}

	@Test
	@DisplayName("Test correct page after change")
	void testCorrectPageAfterChange() {
		manager.nextPdfPage();
		assertTrue(manager.atCorrectPage(1));
	}

	@Test
	@DisplayName("Test output stream not null")
	void testPdfOutputStreamNotNull() {
		ByteArrayOutputStream stream = manager.getPdfOutputStream();
		assertNotNull(stream);
	}

	@Test
	@DisplayName("Test input stream not null")
	void testPdfInputStreamNotNull() {
		ByteArrayInputStream stream = manager.getPdfInputStream();
		assertNotNull(stream);
	}
	
	@Test
	@DisplayName("Test pdf to image not null")
	void testPdfToImageNotNull() {
		BufferedImage image = manager.getCurrentPdfPage();
		assertNotNull(image);
	}
}
