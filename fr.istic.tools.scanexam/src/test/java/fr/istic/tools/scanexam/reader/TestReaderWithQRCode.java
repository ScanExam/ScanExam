package fr.istic.tools.scanexam.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.istic.tools.scanexam.utils.DataFactory;
import fr.istic.tools.scanexam.core.StudentSheet;
import fr.istic.tools.scanexam.qrCode.reader.PdfReader;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderQrCodeImpl;
import fr.istic.tools.scanexam.utils.ResourcesUtils;
import javafx.util.Pair;

public class TestReaderWithQRCode {

	static PdfReader readerGood;
	static PdfReader readerDirty;
	static int nbPages = 8;
	static int nbCopies = 250;

	//static PDDocument docGood;
	//static PDDocument docDirty;
	
	@BeforeAll
	static void init() throws IOException, InterruptedException {
		InputStream inStreamGood = ResourcesUtils.getInputStreamResource("QRCode/pfo_example_Inserted_Good.pdf");
		if (inStreamGood != null) {
			//docGood = PDDocument.load(inStreamGood);
			readerGood = new PdfReaderQrCodeImpl(inStreamGood, "main/resources/QRCode/pfo_example_Inserted_Good.pdf", nbPages, new Pair<Float, Float>(0.024853043f, 0.8928047f));
		}
		InputStream inStreamDirty = ResourcesUtils.getInputStreamResource("QRCode/pfo_example_Inserted_Dirty.pdf");
		if (inStreamDirty != null) {
			//docDirty = PDDocument.load(inStreamDirty);
			readerDirty = new PdfReaderQrCodeImpl(inStreamDirty, "main/resources/QRCode/pfo_example_Inserted_Dirty.pdf", nbPages, new Pair<Float, Float>(0.024853043f, 0.8928047f));
		}
		
		readerGood.readPDf();
		while(!readerGood.isFinished()) {
			TimeUnit.SECONDS.sleep(3);
			System.out.println("toujours pas mort " + readerGood.getNbPagesTreated());
		}
		
		System.out.println(readerGood.getNbPagesTreated());
		
		/*readerDirty.readPDf();
		while(!readerDirty.isFinished()) {
			TimeUnit.SECONDS.sleep(3);
		}*/
	}

	@AfterAll
	static void close() throws IOException{
		//docGood.close();
		//docDirty.close();
	}
	
	@Test
	@DisplayName("Test getNbPagesPdf dans le pdf complet")
	void getNbPagesPdfTestGood() throws InterruptedException {

		assertEquals(nbCopies * nbPages, readerGood.getNbPagesPdf());
	}

	/*@Test
	@DisplayName("Test getNbPagesPdf dans un pdf incomplet")
	void getNbPagesPdfTestDirty() {
		
		assertEquals(nbCopies * nbPages - 1, readerDirty.getNbPagesPdf());
	}*/
	
	@Test
	@DisplayName("Test getNbPagesTraitee")
	void getNbPagesTraiteePdfTest(){
		
		assertEquals(nbCopies * nbPages, readerGood.getNbPagesTreated());
	}
	
	@Test
	@DisplayName("Test du renvoi de la structure des copies complètes au format de l'API quand toutes les pages sont là")
	void getCompleteStudentSheetsTestGood() {
		
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

	/*@Test
	@DisplayName("Test du renvoi de la structure des copies complètes au format de l'API quand il manque une page")
	void getCompleteStundentSheetsTestDirty() {
		
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
	}*/
	
	
	@Test
	@DisplayName("Test du renvoi des copies non complètes sur un examen complet")
	void getUncompleteStudentSheetsGood() {
		assertEquals(0, readerGood.getUncompleteStudentSheets().size());
	}
	
	
	/*@Test
	@DisplayName("Test du renvoi des copies non complètes sur un examen non complet")
	void getUncompleteStudentSheetsDirty() {
		DataFactory dF = new DataFactory();
		List<StudentSheet> collection = new ArrayList<>();
		
		List<Integer> pages = new ArrayList<>();
		for(int j = 8; j <= 14; j++) {
			if(j==12)
				pages.add(-1);
			pages.add(j);
		}

		collection.add(dF.createStudentSheet(1, pages));
		
		List<StudentSheet> arr = new ArrayList<>(readerDirty.getUncompleteStudentSheets());
		
		boolean bool = true;
		
		for(int e = 0; e < arr.size(); e++){
			try{
				StudentSheet temp = collection.get(e);
				
				bool &= arr.get(e).getPosPage().containsAll(temp.getPosPage());
				bool &= temp.getPosPage().containsAll(arr.get(e).getPosPage());
				
			}catch(IndexOutOfBoundsException p ) {
				bool &= false;
			}
		}
		
		bool &= arr.size() == collection.size();
		assertEquals(true, bool);
	}*/
}
