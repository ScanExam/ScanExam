package fr.istic.tools.scanexam.reader;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.istic.tools.scanexam.qrCode.reader.Copie;

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
		System.out.println("nb copies : " + c.getNumCopie());
		assertEquals(c.getNumCopie(), 0);
	}
	
	
	
}
