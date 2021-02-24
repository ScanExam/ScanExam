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

public class ExamGraduationServiceTest 
{
	@BeforeEach
	void init() 
	{
	
	
	}
	
	@Test
	@DisplayName("Test de lecture d'un pdf ou il manque une page")
	void readPdfTestDirty() 
	{
		
	}
	
	@Test
	@DisplayName("Test getNbPagesPdf")
	void getNbPagesPdfTest()
	{
	
	}
	
	@Test
	@DisplayName("Test getNbPagesTraitee")
	void getNbPagesTraiteePdfTest() {
		
	}
	
	
	@Test
	@DisplayName("Test du renvoi de la structure au format de l'API quand il manque une page")
	void getStundentSheetsTestDirty() 
	{
		
	}
}
