package fr.istic.tools.scanexam.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import fr.istic.tools.scanexam.qrCode.reader.Page;

class TestPage {

	Page p = new Page(2, 8);

	@Test
	@DisplayName("Test de getNumPageInPDF")
	void getnumPageInPDFTest() {
		assertEquals(8, p.getNumPageInPDF());
	}

	@Test
	@DisplayName("Test de getNumPageInSubject")
	void getNumPageInSubjectTest() {
		assertEquals(2, p.getNumPageInSubject());
	}

	@Test
	@DisplayName("Test de equals")
	void equalsTest() {
		Page p2 = new Page(2, 8);
		Page p3 = new Page(5, 8);

		assertEquals(true, (p.equals(p2)));
		assertEquals(true, p.equals(p));
		assertEquals(false, p.equals(p3));
		assertEquals(false, p.equals(null));
	}

	@Test
	@DisplayName("Test de hash")
	void hashTest() {
		Page p2 = new Page(2, 8);
		Page p3 = new Page(5, 8);

		assertEquals(p.hashCode(), p2.hashCode());
		assertNotEquals(p.hashCode(), p3.hashCode());
	}

}
