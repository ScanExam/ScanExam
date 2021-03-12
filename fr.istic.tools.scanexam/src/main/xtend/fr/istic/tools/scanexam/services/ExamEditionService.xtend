package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.api.DataFactory
import fr.istic.tools.scanexam.core.CoreFactory
import fr.istic.tools.scanexam.core.templates.CreationTemplate
import fr.istic.tools.scanexam.core.templates.TemplatesFactory
import fr.istic.tools.scanexam.io.TemplateIO
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.Base64
import org.apache.pdfbox.pdmodel.PDDocument
import org.eclipse.xtend.lib.annotations.Accessors

import static fr.istic.tools.scanexam.services.ExamSingleton.*

/*
 * Representer l'état courant de l'interface graphique
 * doit elle être stoquée dans une classe faisant parti de l'API ?
 * 
 * 
 * Factory -> on créer une session en passant la précedente en paramètre.
 */
class ExamEditionService extends Service // TODO : renommer
{
	CreationTemplate template;
	
 	@Accessors int questionId;
 	int gradeEntryId;
	
	/**
	 * Permet de lier une Question q à une zone du PDF définie par un Rectangle R
	 * @param q Une Question
	 * @param r Un Rectangle
	 * @author degas
	 */
	def int createQuestion(float x,float y,float heigth,float width) 
	{
		val question = CoreFactory.eINSTANCE.createQuestion();
		question.id = questionId;
		question.zone = CoreFactory.eINSTANCE.createQuestionZone();
		question.zone.x = x
		question.zone.y = y 
		question.zone.width = width
		question.zone.heigth = heigth
		currentPage.questions.add(question);
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
	
	
	//===================================================
	//                   GradeEntry
	//===================================================
	
	/**
	 * Ajoute une nouvelle entrée à la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle ajouter l'entrée
	 * @param desc la description de l'entrée
	 * @param point le nombre de point de l'entrée
	 * @return l'ID de l'entrée
	 */
	def int addEntry(int questionId, String desc, float point) {
		val DataFactory factory = new DataFactory
		val question = getQuestion(questionId)
		if(question.gradeScale === null)
			question.gradeScale = factory.createGradeScale
		val scale = question.gradeScale
		scale.steps.add(factory.createGradeEntry(questionId, desc, point))
		gradeEntryId++
	}
	
	/**
	 * Modifie une entrée de la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle modifier l'entrée
	 * @param gradeEntryId l'ID de l'entrée à modifier
	 * @param desc la nouvelle description de l'entrée
	 * @param point le nouveau nombre de point de l'entrée
	 */
	def modifyEntry(int questionId, int gradeEntryId, String desc, float point) {
		val scale = getQuestion(questionId).gradeScale
		val scaleEntry = scale.steps.findFirst[step | step.id == questionId]
		if(scaleEntry !== null) {
			scaleEntry.header = desc
			scaleEntry.step = point
		}				
	}
	
	/**
	 * Supprime une entrée de la liste des points attribuable à la question
	 * @param questionId l'ID de la question dans laquelle supprimer l'entrée
	 * @param gradeEntryId l'ID de l'entrée à supprimer
	 */
	def removeEntry(int questionId, int gradeEntryId) {
		val scale = getQuestion(questionId).gradeScale
		val scaleEntry = scale.steps.findFirst[step | step.id == questionId]
		if(scaleEntry !== null)
			scale.steps.remove(scaleEntry)
	}
	
	override save(String path) {
		
		val outputStream = new ByteArrayOutputStream();
		document.save(outputStream);
		val encoded = Base64.getEncoder().encode(outputStream.toByteArray());
		template.encodedDocument = new String(encoded);
		outputStream.close();

		template.exam = ExamSingleton.instance

		TemplateIO.save(path,template);
	}
	
	def boolean open(String xmiPath) 
	{
		val creationTemplate = TemplateIO.loadCreationTemplate(xmiPath)

        if (creationTemplate.present) 
        {
            this.template = creationTemplate.get()
            ExamSingleton.instance = creationTemplate.get().exam
            val decoded = Base64.getDecoder().decode(creationTemplate.get().encodedDocument);
            document = PDDocument.load(decoded)
            questionId = ExamSingleton.instance.pages.stream.map[page | page.questions.size].reduce[acc, num | acc + num].get + 1
            return true
        }
        return false
	}

	
	def void create(File file) 
	{
		template = TemplatesFactory.eINSTANCE.createCreationTemplate
		document = PDDocument.load(file)

		ExamSingleton.instance = CoreFactory.eINSTANCE.createExam()

		for (i : 0 ..< document.pages.size()) 
		{
			val page = CoreFactory.eINSTANCE.createPage()
		
			ExamSingleton.instance.pages.add(page);
		}
		questionId = 0
	}

	

}
