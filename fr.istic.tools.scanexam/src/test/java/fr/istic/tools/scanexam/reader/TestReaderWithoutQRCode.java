package fr.istic.tools.scanexam.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import fr.istic.tools.scanexam.qrCode.reader.PdfReader;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeImpl;
import fr.istic.tools.scanexam.utils.ResourcesUtils;

public class TestReaderWithoutQRCode {

	PdfReader readerGood;
	PdfReader readerDirty;

	@BeforeEach
	void init() {

		int nbPages = 8;
		int nbCopies = 5;


			InputStream inStreamGood = ResourcesUtils.getInputStreamResource("/QRCode/pfo_example.pdf");
			if(inStreamGood != null) 
			readerGood = new PdfReaderWithoutQrCodeImpl(inStreamGood, nbPages, nbCopies);

			InputStream inStreamDirty = ResourcesUtils.getInputStreamResource("/QRCode/pfo_example_dirty.pdf");
			if(inStreamDirty != null) 
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
	@DisplayName("Test getNbPagesPdf")
	void getNbPagesPdfTest() {
		assertTrue(readerGood.readPDf());
		assertEquals(readerGood.getNbPagesPdf(), 40);
	}

	@Test
	@DisplayName("Test getNbPagesTraitee")
	void getNbPagesTraiteePdfTest() {
		assertEquals(readerGood.getNbPagesTreated(), 0);
		assertTrue(readerGood.readPDf());
		assertEquals(readerGood.getNbPagesTreated(), 40);
	}


	@Test
	@DisplayName("Test du renvoi de la structure au format de l'API quand il manque une page")
	void getStundentSheetsTestDirty() {
		// yolo
	}
}
