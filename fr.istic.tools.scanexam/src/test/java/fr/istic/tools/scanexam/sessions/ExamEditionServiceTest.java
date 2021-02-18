package fr.istic.tools.scanexam.sessions;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import fr.istic.tools.scanexam.services.ExamEditionService;

public class ExamEditionServiceTest 
{
	
	ExamEditionService session;
	
	@BeforeEach
	void init() 
	{
		session = new ExamEditionService();
	}
	
	@Test
	@DisplayName("Créer un nouveau projet et ouvre un fichier PDF")
	void createProject() 
	{
		session.create("pfo_example.pdf");
		
	}
	

}
