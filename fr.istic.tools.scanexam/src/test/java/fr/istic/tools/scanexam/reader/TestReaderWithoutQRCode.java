package fr.istic.tools.scanexam.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import fr.istic.tools.scanexam.qrCode.reader.PdfReader;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeImpl;
import fr.istic.tools.scanexam.utils.ResourcesUtils;

class TestReaderWithoutQRCode {

	PdfReader readerGood;
	PdfReader readerDirty;
	int nbPages = 8;
	int nbCopies = 5;

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
		assertNotEquals(nbCopies * nbPages,readerDirty.getNbPagesPdf());
	}

	@Test
	@DisplayName("Test getNbPagesTraitee")
	void getNbPagesTraiteePdfTest() {
		assertEquals(0,readerGood.getNbPagesTreated());
		assertTrue(readerGood.readPDf());
		assertEquals(nbCopies * nbPages,readerGood.getNbPagesTreated());
	}

	@Test
	@DisplayName("Test du renvoi de la structure au format de l'API quand il manque une page")
	void getStundentSheetsTestDirty() {
		// yolo
	}
}
