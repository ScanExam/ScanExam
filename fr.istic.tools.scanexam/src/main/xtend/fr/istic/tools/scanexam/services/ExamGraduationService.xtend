package fr.istic.tools.scanexam.services

import fr.istic.tools.scanexam.core.CoreFactory
import fr.istic.tools.scanexam.core.CorePackage
import fr.istic.tools.scanexam.core.Grade
import fr.istic.tools.scanexam.core.StudentSheet
import fr.istic.tools.scanexam.core.templates.CorrectionTemplate
import fr.istic.tools.scanexam.core.templates.TemplatesPackage
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.Base64
import java.util.Optional
import java.util.Set
import org.apache.pdfbox.pdmodel.PDDocument
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl

import static fr.istic.tools.scanexam.services.ExamSingleton.*

class ExamGraduationService extends Service
{
	int currentSheetIndex;
	 
	int currentQuestionIndex;
	
	
	Set<StudentSheet> studentSheets;
	
	CorrectionTemplate template;
	
	//Set<StudentSheet> visibleSheets;
	
	override save(String path) 
	{
		val outputStream = new ByteArrayOutputStream();
		document.save(outputStream);
		val encodedDoc = Base64.getEncoder().encode(outputStream.toByteArray());
		template.encodedDocument = new String(encodedDoc);
		outputStream.close();
		
		
		template.exam = ExamSingleton.instance
		
		  //TODO
          template.studentsheets.addAll(studentSheets) 
		
		val resourceSet = new ResourceSetImpl();
		val _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
		val _xMIResourceFactoryImpl = new XMIResourceFactoryImpl()
		_extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl)
			resourceSet.getPackageRegistry().put(CorePackage.eNS_URI, CorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(TemplatesPackage.eNS_URI, TemplatesPackage.eINSTANCE);
	
			
		val resource = resourceSet.createResource(URI.createFileURI(path))
		resource.getContents().add(template);
		resource.save(null);
	}
	
	
	override open(String xmiFile) 
	{
		val correctionTemplate = loadTemplate(xmiFile)
		
		 if (correctionTemplate.present) 
        {
            this.template = correctionTemplate.get()
            
            ExamSingleton.instance = correctionTemplate.get().exam
            
            //TODO
            template.studentsheets.addAll(studentSheets) 
            
            val decoded = Base64.getDecoder().decode(correctionTemplate.get().encodedDocument);
            document = PDDocument.load(decoded)
            
            return true
        }
		return false
	}
	
	
	def static Optional<CorrectionTemplate> loadTemplate(String path) {
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
        val template = resource.getContents().get(0);
        if (!(template instanceof CorrectionTemplate))
        {
            return Optional.empty;
        }
        return Optional.ofNullable(template as CorrectionTemplate)
    }
	
	
	def nextSheet() {
		if (currentSheetIndex+1 < studentSheets.size)
			currentSheetIndex++
	}
	
	
	def previousSheet() {
		if (currentSheetIndex > 0)
			currentSheetIndex--
			studentSheets.get(0).posPage;
	}
	
	def nextQuestion(){
		if (currentQuestionIndex + 1 < currentPage.questions.size)
			currentQuestionIndex++
	}
	
	def previousQuestion() {
		if (currentQuestionIndex > 0)
			currentQuestionIndex--
	}
	
	def setGrade (Grade grade){
		val position = studentSheets.get(currentSheetIndex).posPage.indexOf(currentQuestionIndex);
		studentSheets.get(currentSheetIndex).grades.set(position, grade);
	}
	
	override void create(File file) 
	{
		document = PDDocument.load(file)

		ExamSingleton.instance = CoreFactory.eINSTANCE.createExam()

		for (i : 0 ..< document.pages.size()) 
		{
			ExamSingleton.instance.pages.add(CoreFactory.eINSTANCE.createPage());
		}
	}
	
}