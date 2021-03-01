package fr.istic.tools.scanexam.services
import fr.istic.tools.scanexam.core.CoreFactory
import fr.istic.tools.scanexam.core.templates.CreationTemplate
import fr.istic.tools.scanexam.core.templates.TemplatesPackage
import java.io.File
import java.util.Optional
import org.apache.pdfbox.pdmodel.PDDocument
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import static fr.istic.tools.scanexam.services.ExamSingleton.*
import java.io.ByteArrayOutputStream
import java.io.ByteArrayInputStream

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
	
	int questionId;
	
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
		questionId++;
		currentPage.questions.add(question.id,question);
		return questionId;
	}
	def rescaleQuestion(int id,float heigth,float width)
	{
		val question = currentPage.questions.get(id);
		question.zone.width = width
		question.zone.heigth = heigth
	}
	def moveQuestion(int id,float x,float y)
	{
		val question = currentPage.questions.get(id);
		question.zone.x = x
		question.zone.y = y 
	}
	
	def renameQuestion(int id,String name)
	{
		val question = currentPage.questions.get(id);
		question.name = name
	}
	def removeQuestion(int id)
	{
		currentPage.questions.remove(id);
	}
	

	override save(String path) {
		
		
		val outputStream = new ByteArrayOutputStream();
		document.save(outputStream);
		template.document.addAll(outputStream.toByteArray());
		outputStream.close();
		
		template.exam = ExamSingleton.instance
		val resourceSet = new ResourceSetImpl();
		val _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
		val _xMIResourceFactoryImpl = new XMIResourceFactoryImpl()
		_extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl)
		resourceSet.getPackageRegistry().put(TemplatesPackage.eNS_URI, TemplatesPackage.eINSTANCE);
		val resource = resourceSet.createResource(URI.createFileURI(path))
		resource.getContents().add(template);
		resource.save(null);
	}

	override open(String xmiPath) 
	{
		val creationTemplate = loadTemplate(xmiPath)

		if (creationTemplate.present) 
		{
			this.template = creationTemplate.get()
			ExamSingleton.instance = creationTemplate.get().exam
			val inputStream = new ByteArrayInputStream(creationTemplate.get().document);
			document = PDDocument.load(inputStream)

		}

	}

	def static Optional<CreationTemplate> loadTemplate(String path) {
		val resourceSet = new ResourceSetImpl();
		val _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
		val _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
		_extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl)

		resourceSet.getPackageRegistry().put(TemplatesPackage.eNS_URI, TemplatesPackage.eINSTANCE);

		var Resource resource = null;

		try {
			resource = resourceSet.getResource(URI.createFileURI(path), true)
		} catch (Throwable ex) {
			return Optional.empty;
		}

		return Optional.ofNullable(resource.getContents().get(0) as CreationTemplate)
	}

	def void create(File file) 
	{
		document = PDDocument.load(file)

		ExamSingleton.instance = CoreFactory.eINSTANCE.createExam()

		 
		for (i : 0 ..< document.pages.size()) 
		{
			
			ExamSingleton.instance.pages.add(CoreFactory.eINSTANCE.createPage());
		}
	}

	

}
