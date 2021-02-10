package fr.istic.tools.scanexam.sessions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.istic.tools.scanexam.qrCode.reader.Page;
import fr.istic.tools.scanexam.qrCode.reader.PdfReader;
import fr.istic.tools.scanexam.qrCode.reader.PdfReaderWithoutQrCodeImpl;

public class CreationSessionTest 
{
	
	CreationSession session;
	
	@BeforeEach
	void init() 
	{
		session = new CreationSession();
	}
	
	@Test
	@DisplayName("Créer un nouveau projet et ouvre un fichier PDF")
	void createProject() 
	{
		session.create("pfo_example.pdf");
		
	}
	

}
