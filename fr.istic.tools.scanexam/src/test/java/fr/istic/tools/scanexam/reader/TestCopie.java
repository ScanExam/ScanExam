package fr.istic.tools.scanexam.reader;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.istic.tools.scanexam.qrCode.reader.Copie;
import fr.istic.tools.scanexam.qrCode.reader.Page;
import fr.istic.tools.scanexam.qrCode.reader.PdfReader;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeImpl;

public class TestCopie {

	Copie c = new Copie(0,2,8);
	
	@Test
	@DisplayName("Test de getNumCopies")
	void getNumCopiesTest() {
		assertEquals(c.getNumCopie(), 0);
	}
	
	@Test
	@DisplayName("Test de getPageCopie")
	void getPageCopiesTest() {
		assertEquals(c.getNumCopie(), 8);
	}
	
	@Test
	@DisplayName("Test de addInSet")
	void getAddinSetTest() {
		Set<Page> setPages = new HashSet<>();
		Copie c1 = new Copie(0,0,0);
		Copie c2 = new Copie(0,1,1);
		Copie c3 = new Copie(0,2,2);
		Copie c4 = new Copie(0,3,3);
		c1.addInSet(setPages);
		c2.addInSet(setPages);
		c3.addInSet(setPages);
		c4.addInSet(setPages);
		
		
		assertTrue(c.isCopyComplete(4));
	}
	
	@Test
	@DisplayName("Test de isCopieComplete")
	void getisCopieCompleteTest() {
		Set<Page> setPages = new HashSet<>();
		
		
		assertTrue(c.isCopyComplete(0));
	}
	
}
