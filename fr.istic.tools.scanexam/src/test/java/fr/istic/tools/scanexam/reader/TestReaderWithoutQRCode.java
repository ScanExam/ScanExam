package fr.istic.tools.scanexam.reader;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.istic.tools.scanexam.qrCode.reader.PdfReader;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeImpl;

public class TestReaderWithoutQRCode {
	
	PdfReader readerGood;
	PdfReader readerDirty;
	
	@BeforeEach
	void init() {
	
	File pFileGood = new File("pfo_example_Inserted.pdf");
	
	int nbPages = 8;
	int nbCopies = 5;
	
	readerGood = new PdfReaderWithoutQrCodeImpl(pFileGood, nbPages, nbCopies);
	
	File pFileDirty = new File("pfo_example_Dirty.pdf");
	
	readerDirty = new PdfReaderWithoutQrCodeImpl(pFileDirty, nbPages, nbCopies);
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
	@DisplayName("Test du renvoi de la structure au format de l'API quand tout est bon")
	void getStudentSheetsTestGood() {
		//yolo
	}
	
	@Test
	@DisplayName("Test du renvoi de la structure au format de l'API quand il manque une page")
	void getStundentSheetsTestDirty() {
		//yolo
	}
}
