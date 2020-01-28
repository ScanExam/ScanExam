package fr.istic.tools.scanexam.utils

import java.io.IOException
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import fr.istic.tools.scanexam.Exam
import fr.istic.tools.scanexam.GradingData
import fr.istic.tools.scanexam.ScanexamPackage
import java.io.File
import fr.istic.tools.scanexam.StudentGrade
import fr.istic.tools.scanexam.impl.GradingDataImpl

class ScanExamXtendUtils {

	def static GradingData load(File f) {
		var ResourceSet resourceSet = new ResourceSetImpl()
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
			Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl())
		resourceSet.getPackageRegistry().put(ScanexamPackage.eNS_URI, ScanexamPackage.eINSTANCE)
		var Resource resource = resourceSet.getResource(URI.createFileURI(f.absolutePath), true)
		return (resource.getContents().get(0) as GradingData)
	}

	def static void save(File f, GradingData data) throws IOException {
		var ResourceSet resourceSet = new ResourceSetImpl()
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
			Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl())
		resourceSet.getPackageRegistry().put(ScanexamPackage.eNS_URI, ScanexamPackage.eINSTANCE)
		var Resource resource = resourceSet.createResource(URI.createFileURI(f.absolutePath))
		resource.getContents().add(data)
		resource.save(null)
	}
	
	val static gradeMap = newHashMap(#[
			"A"->5,
			"B"->4,
			"C"->3,
			"D"->2,
			"E"->1,
			"F"->0
		]);
	
	def static computeGrade(StudentGrade studentGrade) {
		val exam = (studentGrade.eContainer as GradingData).exam
		//val scale = exam.questions.map[weight].reduce[p1, p2|p1+p2]
		
		var grade = 0.0;  
		for (questionGrade : studentGrade.questionGrades) {
			val qgrade =gradeMap.get(questionGrade.grade)
			if (qgrade!==null && questionGrade.validated) {
				grade += gradeMap.get(questionGrade.grade)*questionGrade.question.weight
			} 
		}
		Math.ceil(grade/exam.scale*16)/4;
	}
	
}
