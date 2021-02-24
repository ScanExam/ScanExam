package fr.istic.tools.scanexam.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.istic.tools.scanexam.api.DataFactory;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.qrCode.reader.PdfReader;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeImpl;
import fr.istic.tools.scanexam.utils.ResourcesUtils;

class TestReaderWithoutQRCode {

	PdfReader readerGood;
	PdfReader readerDirty;
	int nbPages = 8;
	int nbCopies = 3;

	@BeforeEach
	void init() {
		InputStream inStreamGood = ResourcesUtils.getInputStreamResource("/QRCode/pfo_Inserted.pdf");
		if (inStreamGood != null)
			readerGood = new PdfReaderWithoutQrCodeImpl(inStreamGood, nbPages, nbCopies);

		InputStream inStreamDirty = ResourcesUtils.getInputStreamResource("/QRCode/pfo_Dirty.pdf");
		if (inStreamDirty != null)
			readerDirty = new PdfReaderWithoutQrCodeImpl(inStreamDirty, nbPages, nbCopies);
	}

	@Test
	@DisplayName("Test de lecture d'un pdf complet")
	void readPdfTestGood() {
		assertTrue(readerGood.readPDf());
	}

	@Test
	@DisplayName("Test de lecture d'un pdf ou il manque une page")
	void readPdfTestDirty() {
		assertTrue(readerDirty.readPDf());
	}

	@Test
	@DisplayName("Test getNbPagesPdf dans le pdf complet")
	void getNbPagesPdfTestGood() {
		assertTrue(readerGood.readPDf());
		assertEquals(nbCopies * nbPages, readerGood.getNbPagesPdf());
	}

	@Test
	@DisplayName("Test getNbPagesPdf dans un pdf incomplet")
	void getNbPagesPdfTestDirty() {
		assertTrue(readerDirty.readPDf());
		assertEquals(nbCopies * nbPages -1 ,readerDirty.getNbPagesPdf());
	}

	@Test
	@DisplayName("Test getNbPagesTraitee")
	void getNbPagesTraiteePdfTest() {
		assertEquals(0,readerGood.getNbPagesTreated());
		assertTrue(readerGood.readPDf());
		assertEquals(nbCopies * nbPages,readerGood.getNbPagesTreated());
	}

	@Test
	@DisplayName("Test du renvoi de la structure au format de l'API quand toutes les pages sont là")
	void getCompleteStundentSheetsTestDirty() {
		assertEquals(true, readerGood.readPDf());
		DataFactory dF = new DataFactory();
		Set<StudentSheet> collection = new HashSet<>();
		
		
		for(int i = 0; i<nbCopies; i++) {
			List<Integer> pages = new ArrayList<>();
			for(int j = 0; j < nbPages; j++) {
				pages.add((i * nbPages)+j);
				
			}
			System.out.println(pages.toString());
			collection.add(dF.createStudentSheet(i, pages));
		}
		
		
		assertEquals(collection, readerGood.getCompleteStudentSheets());
	}
	
	@Test
	@DisplayName("Test du renvoi de la structure au format de l'API quand il manque une page")
	void getUncompleteStundentSheetsTestDirty() {
		// yolo
	}
}
