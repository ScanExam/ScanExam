package fr.istic.tools.scanexam.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//import java.io.File;

//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.istic.tools.scanexam.qrCode.reader.Page;
//import fr.istic.tools.scanexam.qrCode.reader.PdfReader;
//import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeImpl;

public class TestPage {

	Page p = new Page(2,8);
	
	@Test
	@DisplayName("Test de getNumPageInPDF")
	void getnumPageInPDFTest() {
		assertEquals(p.getNumPageInPDF(),8 );
	}
	
	@Test
	@DisplayName("Test de getNumPageInSubject")
	void getNumPageInSubjectTest() {
		assertEquals(p.getNumPageInSubject(),2 );
	}
	
	@Test
	@DisplayName("Test de equals")
	void equalsTest() {
		Page p2 = new Page(2,8);
		Page p3 = new Page(5,8);
		
		assertTrue(p.equals(p2));
		assertTrue(p.equals(p));
		assertFalse(p.equals(p3));
		assertFalse(p.equals(null));
	}
	
	@Test
	@DisplayName("Test de hash")
	void hashTest() {
		Page p2 = new Page(2,8);
		Page p3 = new Page(5,8);
		
		assertEquals(p.hashCode(), p2.hashCode());
		assertNotEquals(p.hashCode(), p3.hashCode());
	}
	
}
