package fr.istic.tools.scanexam.sessions

import fr.istic.tools.scanexam.core.Question
import java.io.File
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import fr.istic.tools.scanexam.core.templates.CreationTemplate
import fr.istic.tools.scanexam.core.Page
import fr.istic.tools.scanexam.core.templates.TemplatesPackage
import org.apache.pdfbox.pdmodel.PDDocument
import fr.istic.tools.scanexam.core.Exam
import fr.istic.tools.scanexam.core.impl.ExamImpl

/*
 * Representer l'état courant de l'interface graphique
 * doit elle être stoquée dans une classe faisant parti de l'API ?
 * 
 * 
 * Factory -> on créer une session en passant la précedente en paramètre.
 */
class CreationSession extends Session // TODO : renommer
{
	PDDocument document;
	
	CreationTemplate template;
	
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
		template.exam = super.exam;
		
		val resourceSet = new ResourceSetImpl();
    	val _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
    	val _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
    	_extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl);
    	resourceSet.getPackageRegistry().put(TemplatesPackage.eNS_URI, TemplatesPackage.eINSTANCE);
    	val resource = resourceSet.createResource(URI.createFileURI(path));
    	resource.getContents().add(template);
    	resource.save(null);
	}
	
	override open(File xmiFile) 
	{
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	def void create(String pdfPath)
	{
		document = PDDocument.load(new File(pdfPath));
		
		super.exam = null;
		
	}
	
}