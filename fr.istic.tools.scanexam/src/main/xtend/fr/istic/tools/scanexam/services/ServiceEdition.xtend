package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.core.CoreFactory
import fr.istic.tools.scanexam.core.templates.CreationTemplate
import fr.istic.tools.scanexam.core.templates.TemplatesFactory
import fr.istic.tools.scanexam.io.TemplateIO
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.Base64
import java.util.Optional
import org.eclipse.xtend.lib.annotations.Accessors

import static fr.istic.tools.scanexam.services.ExamSingleton.*
import fr.istic.tools.scanexam.core.QuestionZone
import java.io.File

/*
 * Representer l'état courant de l'interface graphique
 * doit elle être stoquée dans une classe faisant parti de l'API ?
 * 
 * 
 * Factory -> on créer une session en passant la précedente en paramètre.
 */
class ServiceEdition extends Service // TODO : renommer
{
	CreationTemplate template;
	
 	@Accessors int questionId;

	
	/**
	 * Permet de lier une Question q à une zone du PDF définie par un Rectangle R
	 * @param q Une Question
	 * @param r Un Rectangle
	 * @author degas
	 */
	def int createQuestion(int pdfPageIndex,float x,float y,float heigth,float width) 
	{
		
		val question = CoreFactory.eINSTANCE.createQuestion();
		question.id = questionId;
		question.gradeScale = CoreFactory.eINSTANCE.createGradeScale(); 
		question.zone = CoreFactory.eINSTANCE.createQuestionZone();
		question.zone.x = x
		question.zone.y = y 
		question.zone.width = width
		question.zone.heigth = heigth
		ExamSingleton.getPage(pdfPageIndex).questions.add(question);
		return questionId++;
	}
	
	def rescaleQuestion(int id,float heigth,float width)
	{
		val question = getQuestion(id);
		question.zone.width = width
		question.zone.heigth = heigth
	}
	
	def moveQuestion(int id,float x,float y)
	{
		val question = getQuestion(id)
		question.zone.x = x
		question.zone.y = y 
	}
	
	def renameQuestion(int id,String name)
	{
		val question = getQuestion(id)
		question.name = name
	}
	
	def removeQuestion(int id)
	{	
		for(page: ExamSingleton.instance.pages)
			for(question: page.questions)
				if(question.id == id)
					page.questions.remove(question)
	}
	
	
	
	
	
/**
	 * Modifie la note maximal que l'on peut attribuer a une question.
	 * @param questionId, l'ID de la question a laquelle on veut modifier la note maximal possible
	 * @param maxPoint, note maximal de la question question a ajouter
	 */
	 def modifMaxPoint(int questionId, float maxPoint) {
		val scale = getQuestion(questionId).gradeScale
		if(maxPoint>0){
			scale.maxPoint=maxPoint
		}
	}
	
	def save(ByteArrayOutputStream outputStream,File path) 
	{
		val encoded = Base64.getEncoder().encode(outputStream.toByteArray());
		template.encodedDocument = new String(encoded);
		outputStream.close();

		template.exam = ExamSingleton.instance

		TemplateIO.save(path,template);
	}
	
	def Optional<ByteArrayInputStream> open(String xmiPath) 
	{
		val creationTemplate = TemplateIO.loadCreationTemplate(xmiPath)

        if (creationTemplate.present) 
        {
            this.template = creationTemplate.get()
            ExamSingleton.instance = creationTemplate.get().exam
            val decoded = Base64.getDecoder().decode(creationTemplate.get().encodedDocument);
            
        
            questionId = ExamSingleton.instance.pages.stream.map[page | page.questions.size].reduce[acc, num | acc + num].get + 1
            return Optional.of(new ByteArrayInputStream(decoded));
        }
        return Optional.empty;
	}

	
	def void createTemplate(int pageNumber) 
	{
		template = TemplatesFactory.eINSTANCE.createCreationTemplate
	

		ExamSingleton.instance = CoreFactory.eINSTANCE.createExam()

		for (i : 0 ..< pageNumber) 
		{
			val page = CoreFactory.eINSTANCE.createPage()
		
			ExamSingleton.instance.pages.add(page);
		}
		questionId = 0
	}
	override onDocumentLoad(int pdfPageCount)
	{
		createTemplate(pdfPageCount); 
	}
		/** Retourne la zone associée à une question
	 * @param index Index de la question //FIXME (useless?)
	 * @author degas
	 */
	def QuestionZone getQuestionZone(int pageIndex,int questionIndex)
	{
		return ExamSingleton.getQuestion(pageIndex,questionIndex).zone
	}
	

	

}
