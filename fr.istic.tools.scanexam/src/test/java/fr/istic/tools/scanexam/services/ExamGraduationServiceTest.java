package fr.istic.tools.scanexam.services;


import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import fr.istic.tools.scanexam.services.api.ServiceGraduation;
import fr.istic.tools.scanexam.view.fx.graduation.ControllerFxGraduation;

// FIXME

public class ExamGraduationServiceTest 
{
	ServiceGraduation service;
	
	ControllerFxGraduation controller;
	
	final String PDF_PATH = "src/test/resources/resource_service/pfo_example.pdf";
	
	final String XMI_PATH = "src/test/resources/resource_service/foo.xmi";
	
	@BeforeEach
	void init() 
	{
		service = new ServiceImpl();
		controller = new ControllerFxGraduation();
		controller.init(service);
		
		//Optional<CreationTemplate> editionTemplate = TemplateIo.loadCreationTemplate(XMI_PATH);
		//service.setExam(editionTemplate.get().getExam());
	}
	

	
	@Test
	@DisplayName("Test de lecture d'un pdf")
	void openPdf() 
	{
		//boolean valid = controller.openCorrectionPdf(new File(PDF_PATH));
		//assertTrue(valid);
	}
	
	@Test
	@DisplayName("Test getNbPagesPdf")
	void getNbPagesPdfTest() throws IOException
	{
		//controller.openCorrectionPdf(new File(PDF_PATH));
		PDDocument document = PDDocument.load(new File(PDF_PATH));
		assertTrue(controller.getPdfManager().getPdfPageCount() == document.getNumberOfPages());
	}
	
	@Test
	@DisplayName("Test changement de page")
	void testChangementDePage()
	{
		//controller.openCorrectionPdf(new File(PDF_PATH));
		
		int pageNumber = controller.getPdfManager().currentPdfPageNumber();
		controller.getPdfManager().nextPdfPage();
		assertTrue(controller.getPdfManager().currentPdfPageNumber() == pageNumber + 1);
		controller.getPdfManager().previousPdfPage();
		assertTrue(controller.getPdfManager().currentPdfPageNumber() == pageNumber);
		
	}
	
}
