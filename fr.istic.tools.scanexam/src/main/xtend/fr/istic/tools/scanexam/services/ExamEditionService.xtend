package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.core.Question
import java.io.File
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import fr.istic.tools.scanexam.core.templates.CreationTemplate
import fr.istic.tools.scanexam.core.templates.TemplatesPackage
import org.apache.pdfbox.pdmodel.PDDocument
import fr.istic.tools.scanexam.core.CoreFactory
import java.util.Optional

/*
 * Representer l'état courant de l'interface graphique
 * doit elle être stoquée dans une classe faisant parti de l'API ?
 * 
 * 
 * Factory -> on créer une session en passant la précedente en paramètre.
 */
class ExamEditionService extends Service // TODO : renommer
{
	PDDocument document;
	
	CreationTemplate template;
	
	String currentPdfPath;
	
	/**
	 * Permet de lier une Question q à une zone du PDF définie par un Rectangle R
	 * @param q Une Question
	 * @param r Un Rectangle
	 * @author degas
	 */
	def void addQuestion(Question q)
	{
		getPage().questions.add(q);
	}
	/**
	 * Supprime une question
	 * @param index Index de la question à supprimer
	 * 
	 * @author degas
	 */
	def void removeQuestion(int index)
	{
		getPage().questions.remove(index);
	}
	override save(String path) 
	{
		template.pdfPath = this.currentPdfPath
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
		val creationTemplate = load(xmiPath)
		
		if (creationTemplate.present)
		{
			this.template = creationTemplate.get()
			ExamSingleton.instance = creationTemplate.get().exam
			
			val pdfFile = new File(creationTemplate.get().pdfPath)
			document = PDDocument.load(pdfFile)
		}	
		
	}
	def static Optional<CreationTemplate> load(String path)
	{
		val resourceSet = new ResourceSetImpl();
   		val _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
    	val _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
    	_extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl)
    	
    	resourceSet.getPackageRegistry().put(TemplatesPackage.eNS_URI, TemplatesPackage.eINSTANCE);
    	
    	var Resource resource = null;
    	
    	try
    	{
    		 resource = resourceSet.getResource(URI.createFileURI(path), true)
    	}
    	catch (Throwable ex)
    	{
    	 	return Optional.empty;
    	}
    	
    	return Optional.ofNullable(resource.getContents().get(0) as CreationTemplate)
	}
	def void create(File file)
	{
		document = PDDocument.load(file)
		
		ExamSingleton.instance =  CoreFactory.eINSTANCE.createExam()
		
		for (i : 0 ..< document.pages.size())
		{
    		ExamSingleton.instance.pages.add(CoreFactory.eINSTANCE.createPage());
		}
		
	    currentPdfPath = file.absolutePath
	}
	
}
