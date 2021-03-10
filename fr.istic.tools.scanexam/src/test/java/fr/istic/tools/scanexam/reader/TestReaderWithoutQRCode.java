package fr.istic.tools.scanexam.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
	int nbPages = 7;
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
		assertEquals(nbCopies * nbPages - 1, readerDirty.getNbPagesPdf());
	}

	@Test
	@DisplayName("Test getNbPagesTraitee")
	void getNbPagesTraiteePdfTest() {
		assertEquals(0, readerGood.getNbPagesTreated());
		assertTrue(readerGood.readPDf());
		assertEquals(nbCopies * nbPages, readerGood.getNbPagesTreated());
	}

	@Test
	@DisplayName("Test du renvoi de la structure des copies complètes au format de l'API quand toutes les pages sont là")
	void getCompleteStudentSheetsTestGood() {
		assertEquals(true, readerGood.readPDf());
		
		DataFactory dF = new DataFactory();
		List<StudentSheet> collection = new ArrayList<>();
		
		
		for(int i = 0; i<nbCopies; i++) {
			List<Integer> pages = new ArrayList<>();
			for(int j = 0; j < nbPages; j++) {
				pages.add((i * nbPages)+j);
				
			}
			collection.add(dF.createStudentSheet(i, pages));
		}
		List<StudentSheet> arr = new ArrayList<>(readerGood.getCompleteStudentSheets());		
		
		boolean bool = true;
		
		for(StudentSheet stdSh : arr){
			try{
				StudentSheet temp = collection.get(stdSh.getId());
				
				bool &= stdSh.getPosPage().containsAll(temp.getPosPage());
				bool &= temp.getPosPage().containsAll(stdSh.getPosPage());
				
			}catch(IndexOutOfBoundsException e ) {
				bool &= false;
			}
		}
		
		bool &= arr.size() == collection.size();
		
		assertEquals(true, bool);
	}

	@Test
	@DisplayName("Test du renvoi de la structure des copies complètes au format de l'API quand il manque une page")
	void getCompleteStundentSheetsTestDirty() {
		assertEquals(true, readerDirty.readPDf());
		
		DataFactory dF = new DataFactory();
		List<StudentSheet> collection = new ArrayList<>();
		
		
		for(int i = 0; i<nbCopies; i++) {
			List<Integer> pages = new ArrayList<>();
			for(int j = 0; j < nbPages; j++) {
				pages.add((i * nbPages)+j);
				
			}

			collection.add(dF.createStudentSheet(i, pages));
		}
		List<StudentSheet> arr = new ArrayList<>(readerDirty.getCompleteStudentSheets());		
		
		
		boolean bool = true;
		
		for(StudentSheet stdSh : arr){
			try{
				StudentSheet temp = collection.get(stdSh.getId());
				
				bool &= stdSh.getPosPage().containsAll(temp.getPosPage());
				bool &= temp.getPosPage().containsAll(stdSh.getPosPage());
				
			}catch(IndexOutOfBoundsException e ) {
				bool &= false;
			}
		}
		
		bool &= arr.size() == collection.size();
		assertEquals(false, bool);
	}
	
	//les copies incomplètes ne peuvent pas être testées car on a pas de notion d'ordre sans QRCodes
}
