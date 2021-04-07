package services;


import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.istic.tools.scanexam.core.templates.CreationTemplate;
import fr.istic.tools.scanexam.io.TemplateIO;
import fr.istic.tools.scanexam.services.ExamGraduationService;
import fr.istic.tools.scanexam.services.ExamSingleton;


public class ExamGraduationServiceTest 
{
	ExamGraduationService service;
	
	final String PDF_PATH = "src/test/resources/resource_service/pfo_example.pdf";
	
	final String XMI_PATH = "src/test/resources/resource_service/foo.xmi";
	
	@BeforeEach
	void init() 
	{
		service = new ExamGraduationService();
		Optional<CreationTemplate> editionTemplate = TemplateIO.loadCreationTemplate(XMI_PATH);
		ExamSingleton.instance = editionTemplate.get().getExam();
	}
	
	@Test
	@DisplayName("Test de lecture d'un pdf")
	void openPdf() 
	{
		boolean valid = service.openCorrectionPdf(PDF_PATH);
		assertTrue(valid);
	}
	
	@Test
	@DisplayName("Test getNbPagesPdf")
	void getNbPagesPdfTest() throws IOException
	{
		service.openCorrectionPdf(PDF_PATH);
		PDDocument document = PDDocument.load(new File(PDF_PATH));
		assertTrue(service.currentPdfPageNumber() == document.getNumberOfPages());
	}
	
	@Test
	@DisplayName("Test changement de test")
	void testChangementDePage()
	{
		service.openCorrectionPdf(PDF_PATH);
		
		int pageNumber = service.getCurrentPageNumber();
		service.nextPdfPage();
		assertTrue(service.currentPdfPageNumber() == pageNumber + 1);
		service.previousPage();
		assertTrue(service.currentPdfPageNumber() == pageNumber);
		
	}
	
}
