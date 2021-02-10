
package fr.istic.tools.scanexam.mailing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import fr.istic.tools.scanexam.mailing.SendMailXtend;

class TestSendMailXtend {

	private SendMailXtend mail;
	
	@BeforeEach
	void setUp() throws Exception {
		mail = new SendMailXtend();
		mail.setSender("...@orange.fr");
		mail.setSenderPassword("...");
		mail.setRecipent("...@gmail.com");
		mail.setTitle("Scanexam");
		mail.setMessage("test");
		mail.setPieceJointe("");
	}

	//Pour ce test completer les donnée ci-dessus
	@Test
	@DisplayName("Envoye mail reussi (Completer les données ci-dessus)")
	void test0() {
		mail.sendMailXtend();
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Expediteur null")
	void test1() {
		mail.setSender(null);
		Assertions.assertThrows(NullPointerException.class, () ->  mail.sendMailXtend()); 
	}
	
	
	@Test
	@Tag("Robustesse")
	@DisplayName("Mot de passe null")
	void test2() {
		mail.setSenderPassword(null);
		Assertions.assertThrows(NullPointerException.class, () ->  mail.sendMailXtend()); 
	}

	@Test
	@Tag("Robustesse")
	@DisplayName("Destinataire null")
	void test3() {
		mail.setRecipent(null);
		Assertions.assertThrows(NullPointerException.class, () ->  mail.sendMailXtend()); 
	}
	
	@Test
	@Tag("Robustesse")
	@DisplayName("titre null")
	void test4() {
		mail.setTitle(null);
		Assertions.assertThrows(NullPointerException.class, () ->  mail.sendMailXtend()); 
	}
	
	@Test
	@Tag("Robustesse")
	@DisplayName("message null")
	void test5() {
		mail.setMessage(null);
		Assertions.assertThrows(NullPointerException.class, () ->  mail.sendMailXtend()); 
	}
	
	@Test
	@Tag("Robustesse")
	@DisplayName("Piece jointe null")
	void test6() {
		mail.setPieceJointe(null);
		Assertions.assertThrows(NullPointerException.class, () ->  mail.sendMailXtend()); 
	}
	
	@Test
	@Tag("Robustesse")
	@DisplayName("Adresse pas presente dans le fichier config")
	void test7() {
		mail.setSender("adresse@non.presente");
		Assertions.assertThrows(NullPointerException.class, () ->  mail.sendMailXtend()); 
	}
	
	@Test
	@Tag("Robustesse")
	@DisplayName("Adresse expediteur non valide")
	void test8() {
		mail.setSender("adressegmail.com");
		Assertions.assertThrows(Exception.class, () ->  mail.sendMailXtend()); 
	}
	
	@Test
	@Tag("Robustesse")
	@DisplayName("Adresse destinataire non valide")
	void test9() {
		mail.setRecipent("adressegmail.com");
		Assertions.assertThrows(Exception.class, () ->  mail.sendMailXtend()); 
	}
	
	@AfterEach
	void tearDown() throws Exception {
		mail = null;
		assertNull(mail);
	}

}