
package fr.istic.tools.scanexam.mailing;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TestSendMailXtend {

	private SendMailXtend mail;
	
	@BeforeEach
	void setUp() throws Exception {
		mail = new SendMailXtend();
		SendMailXtend.setSender("...@orange.fr");
		SendMailXtend.setSenderPassword("...");
		SendMailXtend.setRecipent("...@gmail.com");
		SendMailXtend.setTitle("Scanexam");
		SendMailXtend.setMessage("test");
		SendMailXtend.setPieceJointe("");
	}

	//Pour ce test completer les donnée ci-dessus
	@Test
	@DisplayName("Envoye mail reussi (Completer les données ci-dessus)")
	void test0() {
		SendMailXtend.sendMailXtend();
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Expediteur null")
	void test1() {
		SendMailXtend.setSender(null);
		Assertions.assertThrows(NullPointerException.class, () ->  SendMailXtend.sendMailXtend()); 
	}
	
	
	@Test
	@Tag("Robustesse")
	@DisplayName("Mot de passe null")
	void test2() {
		SendMailXtend.setSenderPassword(null);
		Assertions.assertThrows(NullPointerException.class, () ->  SendMailXtend.sendMailXtend()); 
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Destinataire null")
	void test3() {
		SendMailXtend.setRecipent(null);
		Assertions.assertThrows(NullPointerException.class, () ->  SendMailXtend.sendMailXtend()); 
	}
	
	@Test
	@Tag("Robustesse")
	@DisplayName("titre null")
	void test4() {
		SendMailXtend.setTitle(null);
		Assertions.assertThrows(NullPointerException.class, () ->  SendMailXtend.sendMailXtend()); 
	}
	
	@Test
	@Tag("Robustesse")
	@DisplayName("message null")
	void test5() {
		SendMailXtend.setMessage(null);
		Assertions.assertThrows(NullPointerException.class, () ->  SendMailXtend.sendMailXtend()); 
	}
	
	@Test
	@Tag("Robustesse")
	@DisplayName("Piece jointe null")
	void test6() {
		SendMailXtend.setPieceJointe(null);
		Assertions.assertThrows(NullPointerException.class, () ->  SendMailXtend.sendMailXtend()); 
	}
	
	@Test
	@Tag("Robustesse")
	@DisplayName("Adresse pas presente dans le fichier config")
	void test7() {
		SendMailXtend.setSender("adresse@non.presente");
		Assertions.assertThrows(NullPointerException.class, () ->  SendMailXtend.sendMailXtend()); 
	}
	
	@Test
	@Tag("Robustesse")
	@DisplayName("Adresse expediteur non valide")
	void test8() {
		SendMailXtend.setSender("adressegmail.com");
		Assertions.assertThrows(Exception.class, () ->  SendMailXtend.sendMailXtend()); 
	}
	
	@Test
	@Tag("Robustesse")
	@DisplayName("Adresse destinataire non valide")
	void test9() {
		SendMailXtend.setRecipent("adressegmail.com");
		Assertions.assertThrows(Exception.class, () ->  SendMailXtend.sendMailXtend()); 
	}
	
	@AfterEach
	void tearDown() throws Exception {
		mail = null;
		assertNull(mail);
	}

}