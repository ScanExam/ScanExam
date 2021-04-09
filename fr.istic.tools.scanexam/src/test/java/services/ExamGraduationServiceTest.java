package services;


import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.beust.jcommander.internal.Lists;

import fr.istic.tools.scanexam.core.templates.CreationTemplate;
import fr.istic.tools.scanexam.io.TemplateIo;
import fr.istic.tools.scanexam.presenter.PresenterEdition;
import fr.istic.tools.scanexam.presenter.PresenterGraduation;
import fr.istic.tools.scanexam.services.ServiceGraduation;
import fr.istic.tools.scanexam.services.ExamSingleton;


public class ExamGraduationServiceTest 
{
	ServiceGraduation service;
	
	PresenterGraduation presenter;
	
	final String PDF_PATH = "src/test/resources/resource_service/pfo_example.pdf";
	
	final String XMI_PATH = "src/test/resources/resource_service/foo.xmi";
	
	@BeforeEach
	void init() 
	{
		service = new ServiceGraduation();
		presenter = jailBreak(service);
		Optional<CreationTemplate> editionTemplate = TemplateIo.loadCreationTemplate(XMI_PATH);
		ExamSingleton.instance = editionTemplate.get().getExam();
	}
	
	private PresenterGraduation jailBreak(Object... args) {
		try { 
			final Class<?>[] argClasses = Lists.newArrayList(args).stream()
					.map(o -> o.getClass())
					.toArray(Class<?>[]::new);
			Constructor<PresenterGraduation> method = PresenterGraduation.class.getConstructor( argClasses);
			method.setAccessible(true);
			return (PresenterGraduation)method.newInstance(args);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | InstantiationException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	@Test
	@DisplayName("Test de lecture d'un pdf")
	void openPdf() 
	{
		boolean valid = presenter.openCorrectionPdf(new File(PDF_PATH));
		assertTrue(valid);
	}
	
	@Test
	@DisplayName("Test getNbPagesPdf")
	void getNbPagesPdfTest() throws IOException
	{
		presenter.openCorrectionPdf(new File(PDF_PATH));
		PDDocument document = PDDocument.load(new File(PDF_PATH));
		assertTrue(presenter.getPresenterPdf().getPdfPageCount() == document.getNumberOfPages());
	}
	
	@Test
	@DisplayName("Test changement de page")
	void testChangementDePage()
	{
		presenter.openCorrectionPdf(new File(PDF_PATH));
		
		int pageNumber = presenter.getPresenterPdf().currentPdfPageNumber();
		presenter.getPresenterPdf().nextPdfPage();
		assertTrue(presenter.getPresenterPdf().currentPdfPageNumber() == pageNumber + 1);
		presenter.getPresenterPdf().previousPdfPage();
		assertTrue(presenter.getPresenterPdf().currentPdfPageNumber() == pageNumber);
		
	}
	
}
